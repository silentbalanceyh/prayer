package com.prayer.bus.impl.std;

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;
import static com.prayer.util.bus.BusinessLogger.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.i.RecordDao;
import com.prayer.exception.AbstractException;
import com.prayer.exception.web.ServiceOrderByException;
import com.prayer.exception.web.ServiceReturnSizeException;
import com.prayer.kernel.i.Record;
import com.prayer.kernel.i.Value;
import com.prayer.model.bus.OrderBy;
import com.prayer.model.bus.ServiceResult;
import com.prayer.script.JSEngine;
import com.prayer.script.JSEnv;
import com.prayer.util.bus.Interruptor;
import com.prayer.util.bus.RecordSerializer;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.MetaPolicy;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class ServiceHelper {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHelper.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final RecordSerializer serializer;
    /** **/
    @NotNull
    private transient final RecordDao dao;
    /** **/
    @NotNull
    @NotBlank
    @NotEmpty
    private transient final String recordCls;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public ServiceHelper(@NotNull final RecordDao recordDao, @NotNull final Class<?> entityCls){
        this.serializer = singleton(RecordSerializer.class);
        this.dao = recordDao;
        this.recordCls = entityCls.getName();
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    /** **/
    @NotNull
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonObject> sharedSave(@NotNull final JsonObject jsonObject)
            throws ScriptException, AbstractException {
        final ServiceResult<JsonObject> ret = new ServiceResult<>();
        // 1. 初始化脚本引擎以及Record对象
        final Record record = instance(this.recordCls, jsonObject.getString(Constants.PARAM.ID));
        // 2. 将Java和脚本引擎连接实现变量共享
        JSEngine.initJSEnv(jsonObject, record);
        // 3. 执行Java脚本插入数据
        JsonObject retJson = null;
        if (Interruptor.isUpdate(record)) {
            info(LOGGER, " Updating mode : Input => " + record.toString());
            final Record updated = this.executeUpdate(record);
            retJson = serializer.extractRecord(updated);
        } else {
            info(LOGGER, " Inserting mode : " + record.toString());
            final Record inserted = this.dao.insert(record);
            retJson = this.serializer.extractRecord(inserted);
        }
        // 4. 根据Filters的内容过滤属性
        this.serializer.filterRecord(retJson, jsonObject);
        ret.success(retJson);
        return ret;
    }
    


    /** **/
    @NotNull
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonObject> sharedDelete(@NotNull final JsonObject jsonObject)
            throws ScriptException, AbstractException {
        final ServiceResult<JsonObject> ret = new ServiceResult<>();
        // 1. 初始化脚本引擎以及Record对象
        final Record record = instance(this.recordCls, jsonObject.getString(Constants.PARAM.ID));
        // 2. 将Java和脚本引擎连接实现变量共享
        JSEngine.initJSEnv(jsonObject, record);
        // 3. 删除当前记录
        boolean deleted = this.dao.delete(record);
        ret.success(new JsonObject().put("DELETED", deleted));
        return ret;
    }

    /** **/
    @NotNull
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonObject> sharedPage(@NotNull final JsonObject jsonObject)
            throws ScriptException, AbstractException {
        ServiceResult<JsonObject> ret = new ServiceResult<>();
        // 1. 初始化脚本引擎以及Record对象
        final Record record = instance(this.recordCls, jsonObject.getString(Constants.PARAM.ID));
        // 2. 将Java和脚本引擎连接实现变量共享
        final JSEnv env = JSEngine.initJSEnv(jsonObject, record);
        // 3. 执行Java脚本插入数据
        ConcurrentMap<Long, List<Record>> retMap = new ConcurrentHashMap<>();
        final OrderBy orders = env.getOrder();
        if (null != orders && orders.containOrderBy()) {
            // Order By Processing....
            retMap = this.dao.queryByPage(record, Constants.T_STR_ARR, env.getValues(), env.getExpr(), orders,
                    env.getPager());
        } else {
            // Order By Exception
            throw new ServiceOrderByException(getClass());
        }
        // 5.构造结果
        final JsonObject retObj = new JsonObject();
        if (Constants.ONE == retMap.size()) {
            final Map.Entry<Long, List<Record>> entry = retMap.entrySet().iterator().next();
            retObj.put(Constants.PARAM.PAGE.RET_COUNT, entry.getKey());
            // 6.Filter
            final JsonArray retList = new JsonArray();
            for (final Record retR : entry.getValue()) {
                final JsonObject retJson = serializer.extractRecord(retR);
                this.serializer.filterRecord(retJson, jsonObject);
                retList.add(retJson);
            }
            retObj.put(Constants.PARAM.PAGE.RET_LIST, retList);
            ret.success(retObj);
        } else {
            // Return Size Exception
            throw new ServiceReturnSizeException(getClass(), String.valueOf(Constants.ONE));
        }
        return ret;
    }

    /** **/
    @NotNull
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonArray> sharedFind(@NotNull final JsonObject jsonObject)
            throws ScriptException, AbstractException {
        final ServiceResult<JsonArray> ret = new ServiceResult<>();
        // 1. 初始化脚本引擎以及Record对象
        final Record record = instance(this.recordCls, jsonObject.getString(Constants.PARAM.ID));
        // 2. 将Java和脚本引擎连接实现变量共享
        final JSEnv env = JSEngine.initJSEnv(jsonObject, record);
        // 3. 执行Java脚本插入数据
        List<Record> retList = new ArrayList<>();
        final OrderBy orders = env.getOrder();
        if (null != orders && orders.containOrderBy()) {
            // Order By Processing....
            retList = this.dao.queryByFilter(record, Constants.T_STR_ARR, env.getValues(), env.getExpr(), orders);
        } else {
            // Non Order By Processing...
            retList = this.dao.queryByFilter(record, Constants.T_STR_ARR, env.getValues(), env.getExpr());
        }
        // 4. 查询结果
        final JsonArray retArray = new JsonArray();
        for (final Record retR : retList) {
            final JsonObject retJson = serializer.extractRecord(retR);
            this.serializer.filterRecord(retJson, jsonObject);
            retArray.add(retJson);
        }
        return ret.success(retArray);
    }
    // ~ Private Methods =====================================

    private Record executeUpdate(final Record record) throws AbstractException {
        // 更新过程才会有的问题
        final AbstractException error = Interruptor.interruptPK(record);
        if (null == error) {
            // 更新遍历，更新时需要从数据库中拿到Record
            Record queried = null;
            if (MetaPolicy.COLLECTION == record.policy()) {
                queried = this.dao.selectById(record, record.idKV());
            } else {
                final Value<?> value = record.idKV().values().iterator().next();
                queried = this.dao.selectById(record, value);
            }
            info(LOGGER, " Updating mode : Queried => " + queried.toString());
            for (final String field : record.fields().keySet()) {
                final Value<?> value = record.get(field);
                // 标记为NU的则不更新
                if (null != value && !StringUtil.equals(value.literal(), "NU")) {
                    queried.set(field, record.get(field));
                }
            }
            info(LOGGER, " Updating mode : Updated => " + queried.toString());
            return this.dao.update(queried);
        } else {
            throw error;
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
