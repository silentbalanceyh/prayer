package com.prayer.bus.std.impl;

import static com.prayer.util.Instance.singleton;

import java.util.List;

import javax.script.ScriptException;

import org.slf4j.Logger;

import com.prayer.bus.script.JSEngine;
import com.prayer.bus.script.JSEnv;
import com.prayer.bus.script.JSEnvExtractor;
import com.prayer.bus.util.ParamExtractor;
import com.prayer.constant.Constants;
import com.prayer.dao.record.RecordDao;
import com.prayer.dao.record.impl.RecordDaoImpl;
import com.prayer.exception.AbstractException;
import com.prayer.kernel.Record;
import com.prayer.kernel.model.GenericRecord;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonObject;
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
        final Record inserted = this.recordDao.insert(record);
        final JsonObject retJson = extractor.extractRecord(inserted);
        // 4. 根据Filters的内容过滤属性
        this.extractor.filterRecord(retJson, jsonObject);
        ret.setResult(retJson);
        return ret;
    }

    /** **/
    protected ServiceResult<JsonObject> sharedFind(@NotNull final JsonObject jsonObject)
            throws ScriptException, AbstractException {
        final ServiceResult<JsonObject> ret = new ServiceResult<>();
        // 1. 初始化脚本引擎以及Record对象
        final Record record = new GenericRecord(jsonObject.getString(Constants.PARAM.ID));
        // 2. 将Java和脚本引擎连接实现变量共享
        final JSEnv env = initJSEnv(jsonObject, record);
        // 3. 执行Java脚本插入数据
        final List<Record> retList = this.getDao().queryByFilter(record, Constants.T_STR_ARR,
                env.getValues(), env.getExpr());
        // 4. 查询结果
        if (Constants.ONE == retList.size()) {
            final Record queried = retList.get(0);
            final JsonObject retJson = extractor.extractRecord(queried);
            this.extractor.filterRecord(retJson, jsonObject);
            ret.setResult(retJson);
        }
        return ret;
    }

    // ~ Private Methods =====================================
    private JSEnv initJSEnv(final JsonObject jsonObject, final Record record) throws ScriptException {
        final JSEngine engine = JSEngine.getEngine(jsonObject.getJsonObject(Constants.PARAM.DATA));
        final JSEnv env = new JSEnv();
        // 1.设置变量绑定
        env.setRecord(record);
        engine.put(JSEngine.ENV, env);
        // 2.设置全局脚本
        engine.execute(jsExtractor.extractJSEnv());
        // 3.执行局部配置脚本
        engine.execute(jsExtractor.extractJSContent(jsonObject));
        return env;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
