package com.prayer.bus.std.impl;

import static com.prayer.bus.util.BusLogger.info;
import static com.prayer.util.Instance.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.script.ScriptException;

import org.slf4j.Logger;

import com.prayer.bus.script.JSEngine;
import com.prayer.bus.script.JSEnv;
import com.prayer.bus.script.JSEnvExtractor;
import com.prayer.bus.util.Interruptor;
import com.prayer.bus.util.ParamExtractor;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.dao.record.RecordDao;
import com.prayer.dao.record.impl.RecordDaoImpl;
import com.prayer.exception.AbstractException;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.kernel.model.GenericRecord;
import com.prayer.kernel.query.OrderBy;
import com.prayer.kernel.query.Pager;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/** **/
@Guarded
public abstract class AbstractSevImpl {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final ParamExtractor extractor;
    /** **/
    @NotNull
    private transient final JSEnvExtractor jsExtractor;
    /** **/
    @NotNull
    private transient final RecordDao recordDao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public AbstractSevImpl() {
        this.extractor = singleton(ParamExtractor.class);
        this.recordDao = singleton(RecordDaoImpl.class);
        this.jsExtractor = singleton(JSEnvExtractor.class);
    }

    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @NotNull
    protected RecordDao getDao() {
        return this.recordDao;
    }

    /** **/
    protected ServiceResult<JsonObject> sharedSave(@NotNull final JsonObject jsonObject)
            throws ScriptException, AbstractException {
        final ServiceResult<JsonObject> ret = new ServiceResult<>();
        // 1. 初始化脚本引擎以及Record对象
        final Record record = new GenericRecord(jsonObject.getString(Constants.PARAM.ID));
        // 2. 将Java和脚本引擎连接实现变量共享
        initJSEnv(jsonObject, record);
        // 3. 执行Java脚本插入数据
        JsonObject retJson = null;
        if (Interruptor.isUpdate(record)) {
            info(getLogger(), " Updating mode : Input => " + record.toString());
            final Record updated = this.executeUpdate(record);
            retJson = extractor.extractRecord(updated);
        } else {
            info(getLogger(), " Inserting mode : " + record.toString());
            final Record inserted = this.recordDao.insert(record);
            retJson = extractor.extractRecord(inserted);
        }
        // 4. 根据Filters的内容过滤属性
        this.extractor.filterRecord(retJson, jsonObject);
        ret.success(retJson);
        return ret;
    }

    /** **/
    protected ServiceResult<JsonObject> sharedDelete(@NotNull final JsonObject jsonObject)
            throws ScriptException, AbstractException {
        final ServiceResult<JsonObject> ret = new ServiceResult<>();
        // 1. 初始化脚本引擎以及Record对象
        final Record record = new GenericRecord(jsonObject.getString(Constants.PARAM.ID));
        // 2. 将Java和脚本引擎连接实现变量共享
        initJSEnv(jsonObject, record);
        // 3. 删除当前记录
        boolean deleted = this.recordDao.delete(record);
        ret.success(new JsonObject().put("DELETED", deleted));
        return ret;
    }

    /** **/
    protected ServiceResult<JsonObject> sharedPage(@NotNull final JsonObject jsonObject)
            throws ScriptException, AbstractException {
        final ServiceResult<JsonObject> ret = new ServiceResult<>();
        // 1. 初始化脚本引擎以及Record对象
        final Record record = new GenericRecord(jsonObject.getString(Constants.PARAM.ID));
        // 2. 将Java和脚本引擎连接实现变量共享
        final JSEnv env = initJSEnv(jsonObject, record);
        // 3. 执行Java脚本插入数据
        ConcurrentMap<Long, List<Record>> retMap = new ConcurrentHashMap<>();
        final OrderBy orders = env.getOrder();
        if (null != orders && orders.containOrderBy()) {
            // Order By Processing....
            retMap = this.getDao().queryByPage(record, Constants.T_STR_ARR, env.getValues(), env.getExpr(), orders,
                    env.getPager());
        } else {
            // TODO: OrderBy Error
        }
        // 5.构造结果
        final JsonObject retObj = new JsonObject();
        if (Constants.ONE == retMap.size()) {
            final Map.Entry<Long, List<Record>> entry = retMap.entrySet().iterator().next();
            retObj.put(Constants.PARAM.PAGE.RET_COUNT, entry.getKey());
            // 6.Filter
            final JsonArray retList = new JsonArray();
            for (final Record retR : entry.getValue()) {
                final JsonObject retJson = extractor.extractRecord(retR);
                this.extractor.filterRecord(retJson, jsonObject);
                retList.add(retJson);
            }
            retObj.put(Constants.PARAM.PAGE.RET_LIST, retList);
        }else{
            // TODO: Return Value Error
        }
        return ret.success(retObj);
    }

    /** **/
    protected ServiceResult<JsonArray> sharedFind(@NotNull final JsonObject jsonObject)
            throws ScriptException, AbstractException {
        final ServiceResult<JsonArray> ret = new ServiceResult<>();
        // 1. 初始化脚本引擎以及Record对象
        final Record record = new GenericRecord(jsonObject.getString(Constants.PARAM.ID));
        // 2. 将Java和脚本引擎连接实现变量共享
        final JSEnv env = initJSEnv(jsonObject, record);
        // 3. 执行Java脚本插入数据
        List<Record> retList = new ArrayList<>();
        final OrderBy orders = env.getOrder();
        if (null != orders && orders.containOrderBy()) {
            // Order By Processing....
            retList = this.getDao().queryByFilter(record, Constants.T_STR_ARR, env.getValues(), env.getExpr(), orders);
        } else {
            // Non Order By Processing...
            retList = this.getDao().queryByFilter(record, Constants.T_STR_ARR, env.getValues(), env.getExpr());
        }
        // 4. 查询结果
        final JsonArray retArray = new JsonArray();
        for (final Record retR : retList) {
            final JsonObject retJson = extractor.extractRecord(retR);
            this.extractor.filterRecord(retJson, jsonObject);
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
                queried = this.recordDao.selectById(record, record.idKV());
            } else {
                final Value<?> value = record.idKV().values().iterator().next();
                queried = this.recordDao.selectById(record, value);
            }
            info(getLogger(), " Updating mode : Queried => " + queried.toString());
            for (final String field : record.fields().keySet()) {
                final Value<?> value = record.get(field);
                // 标记为NU的则不更新
                if (null != value && !StringUtil.equals(value.literal(), "NU")) {
                    queried.set(field, record.get(field));
                }
            }
            info(getLogger(), " Updating mode : Updated => " + queried.toString());
            return this.recordDao.update(queried);
        } else {
            throw error;
        }
    }

    private JSEnv initJSEnv(final JsonObject jsonObject, final Record record) throws ScriptException {
        final JSEngine engine = JSEngine.getEngine(jsonObject.getJsonObject(Constants.PARAM.DATA));
        final JSEnv env = new JSEnv();
        // 1.设置变量绑定
        env.setRecord(record);
        engine.put(JSEngine.ENV, env);
        // 2.关于OrderBy的判断，参数中包含了orders的信息
        this.injectOrders(jsonObject, record, env);
        // 3.关于Pager的注入
        this.injectPager(jsonObject, env);
        // 4.设置全局脚本
        engine.execute(jsExtractor.extractJSEnv());
        // 5.执行局部配置脚本
        engine.execute(jsExtractor.extractJSContent(jsonObject));
        return env;
    }

    private void injectPager(final JsonObject jsonObject, final JSEnv env) {
        final JsonObject data = jsonObject.getJsonObject(Constants.PARAM.DATA);
        if (data.containsKey(Constants.PARAM.PAGE.NAME)) {
            JsonObject pager = null;
            try {
                pager = data.getJsonObject(Constants.PARAM.PAGE.NAME);
            } catch (ClassCastException ex) {
                info(getLogger(), " Convert to String paring : " + ex.toString());
                pager = new JsonObject(data.getString(Constants.PARAM.PAGE.NAME));
            }
            if (null != pager) {
                env.setPager(Pager.create(pager));
            }
        }
    }

    private void injectOrders(final JsonObject jsonObject, final Record record, final JSEnv env) {
        final JsonObject data = jsonObject.getJsonObject(Constants.PARAM.DATA);
        if (data.containsKey(Constants.PARAM.ORDERS)) {
            JsonArray orderArr = null;
            try {
                orderArr = data.getJsonArray(Constants.PARAM.ORDERS);
            } catch (ClassCastException ex) {
                info(getLogger(), " Convert to String parsing : " + ex.toString());
                orderArr = new JsonArray(data.getString(Constants.PARAM.ORDERS));
            }
            if (null != orderArr && null != record) {
                env.setOrder(OrderBy.create(orderArr, record));
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
