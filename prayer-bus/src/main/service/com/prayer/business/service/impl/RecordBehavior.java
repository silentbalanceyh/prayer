package com.prayer.business.service.impl;

import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.script.js.JSEngine;
import com.prayer.exception.web.JSScriptEngineException;
import com.prayer.facade.business.RecordService;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.fun.endpoint.Behavior;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.InquiryMarchal;
import com.prayer.model.web.WebRequest;
import com.prayer.model.web.WebResponse;
import com.prayer.util.business.Commutator;
import com.prayer.util.debug.Log;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RecordBehavior implements RecordService {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordBehavior.class);
    // ~ Instance Fields =====================================
    /**
     * 实体类信息
     */
    @NotNull
    private transient final Class<?> entityCls;
    /**
     * Write
     */
    @NotNull
    private transient final WrPerformer wrPerformer;
    /**
     * Read
     */
    private transient final RdPerformer rdPerformer;
    /**
     * 转换数据用的类
     */
    @NotNull
    private transient final Commutator commutator;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 必须传入实体Record类型用于标记是DataRecord还是MetaRecord
     * 
     * @param entityCls
     */
    public RecordBehavior(@NotNull final Class<?> entityCls) {
        /**
         * 当前Behavior的实体类型
         */
        this.entityCls = entityCls;
        /**
         * 当前转换器
         */
        this.commutator = singleton(Commutator.class);
        /**
         * 将当前的Performer实例化
         */
        this.wrPerformer = reservoir(entityCls.getName(), WrPerformer.class, entityCls);
        this.rdPerformer = reservoir(entityCls.getName(), RdPerformer.class, entityCls);

    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * Find方法
     * 
     * @param request
     * @return
     */
    @Override
    public WebResponse find(@NotNull final WebRequest request) {
        return this.execute(this::findAct, request);
    }

    /**
     * 
     * @param page
     * @return
     */
    @Override
    public WebResponse page(@NotNull final WebRequest request) {
        return this.execute(this::pageAct, request);
    }

    /**
     * Save方法：Update/Insert
     */
    @Override
    public WebResponse save(@NotNull final WebRequest request) {
        return this.execute(this::saveAct, request);
    }

    /**
     * Delete专用
     */
    @Override
    public WebResponse remove(@NotNull final WebRequest request) {
        return this.execute(this::removeAct, request);
    }

    // ~ Private Methods =====================================

    private JsonArray extractList(final List<Record> retList) throws AbstractDatabaseException {
        final JsonArray retArr = new JsonArray();
        for (final Record retR : retList) {
            final JsonObject data = this.commutator.extract(retR);
            retArr.add(data);
        }
        return retArr;
    }

    private JsonObject extractObject(final JsonArray retArr) throws AbstractDatabaseException {
        return this.extractObject(retArr, Long.valueOf(retArr.size()));
    }

    private JsonObject extractObject(final JsonArray retArr, final Long count) throws AbstractDatabaseException {
        final JsonObject retObj = new JsonObject();
        retObj.put(Constants.PARAM.PAGE.RET_COUNT, count);
        retObj.put(Constants.PARAM.PAGE.RET_LIST, retArr);
        return retObj;
    }

    /** **/
    private WebResponse pageAct(final WebRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Record对象 **/
        final Record record = instance(this.entityCls, request.getIdentifier());
        /** 2.将Java和脚本引擎连接 **/
        final InquiryMarchal marchal = JSEngine.initJSEnv(request, record);
        /** 3.执行查询 **/
        final ConcurrentMap<Long, List<Record>> retMap = this.rdPerformer.performPage(record, marchal);
        /** 4.生成响应结果 **/
        final Map.Entry<Long, List<Record>> entry = retMap.entrySet().iterator().next();
        final WebResponse response = new WebResponse();
        response.success(extractObject(extractList(entry.getValue()), entry.getKey()));
        return response;
    }

    /** **/
    private WebResponse findAct(final WebRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Record对象 **/
        final Record record = instance(this.entityCls, request.getIdentifier());
        /** 2.将Java和脚本引擎连接 **/
        final InquiryMarchal marchal = JSEngine.initJSEnv(request, record);
        /** 3.执行查询 **/
        final List<Record> retList = this.rdPerformer.performFind(record, marchal);
        /** 4.执行结果 **/
        final WebResponse response = new WebResponse();
        response.success(extractObject(extractList(retList)));
        return response;

    }

    /** Remove执行方法 **/
    private WebResponse removeAct(final WebRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Record对象 **/
        final Record record = instance(this.entityCls, request.getIdentifier());
        /** 2.将Java和脚本引擎连接 **/
        JSEngine.initJSEnv(request, record);
        /** 3.删除当前记录 **/
        final boolean deleted = this.wrPerformer.performDelete(record);
        final WebResponse response = new WebResponse();
        response.success(new JsonObject().put("DELETED", deleted));
        return response;
    }

    /** Save执行方法 **/
    private WebResponse saveAct(final WebRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Record对象 **/
        final Record record = instance(this.entityCls, request.getIdentifier());
        /** 2.将Java和脚本引擎连接 **/
        JSEngine.initJSEnv(request, record);
        /** 3.将record执行插入操作 **/
        Record inserted = null;
        if (Brancher.isUpdate(record)) {
            inserted = this.wrPerformer.performUpdate(record, request.getProjection().getFilters());
        } else {
            inserted = this.wrPerformer.performInsert(record, request.getProjection().getFilters());
        }
        // 返回正确响应结果
        final WebResponse response = new WebResponse();
        response.success(this.commutator.extract(inserted));
        return response;
    }

    /** Controller Method **/
    private WebResponse execute(final Behavior behavior, final WebRequest request) {
        final WebResponse response = new WebResponse();
        try {
            final WebResponse ret = behavior.dispatch(request);
            response.success(ret.getResult());
        } catch (ScriptException ex) {
            Log.jvmError(LOGGER, ex);
            response.failure(new JSScriptEngineException(getClass(), ex.toString()));
        } catch (AbstractException ex) {
            Log.peError(LOGGER, ex);
            response.failure(ex);
        }
        return response;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
