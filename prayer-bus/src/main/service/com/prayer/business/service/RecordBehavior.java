package com.prayer.business.service;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.script.js.JSEngine;
import com.prayer.dao.ObjectTransferer;
import com.prayer.exception.web.JSScriptEngineException;
import com.prayer.facade.business.service.RecordService;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.fun.endpoint.Behavior;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.util.Transferer;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.Eidolon;
import com.prayer.model.business.behavior.ActRequest;
import com.prayer.model.business.behavior.ActResponse;
import com.prayer.util.debug.Log;

import io.vertx.core.http.HttpMethod;
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
    @NotNull
    private transient final RdPerformer rdPerformer;
    /**
     * 
     */
    @NotNull
    private transient final Transferer transferer;

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
        this.transferer = singleton(ObjectTransferer.class);
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
    public ActResponse find(@NotNull final ActRequest request) {
        return this.execute(request, new HttpMethod[] { HttpMethod.GET }, this::findAct);
    }

    /**
     * 
     * @param page
     * @return
     */
    @Override
    public ActResponse page(@NotNull final ActRequest request) {
        return this.execute(request, new HttpMethod[] { HttpMethod.POST }, this::pageAct);
    }

    /**
     * Save方法：Update/Insert
     */
    @Override
    public ActResponse save(@NotNull final ActRequest request) {
        return this.execute(request, new HttpMethod[] { HttpMethod.PUT, HttpMethod.POST }, this::saveAct);
    }

    /**
     * Delete专用
     */
    @Override
    public ActResponse remove(@NotNull final ActRequest request) {
        return this.execute(request, new HttpMethod[] { HttpMethod.DELETE }, this::removeAct);
    }

    // ~ Private Methods =====================================

    private JsonArray extractList(final List<Record> retList) throws AbstractDatabaseException {
        final JsonArray retArr = new JsonArray();
        for (final Record retR : retList) {
            final JsonObject data = this.transferer.fromRecord(retR);
            retArr.add(data);
        }
        return retArr;
    }

    private JsonObject extractObject(final JsonArray retArr) throws AbstractDatabaseException {
        return this.extractObject(retArr, Long.valueOf(retArr.size()));
    }

    private JsonObject extractObject(final JsonArray retArr, final Long count) throws AbstractDatabaseException {
        final JsonObject retObj = new JsonObject();
        retObj.put(Constants.PARAM.ADMINICLE.PAGE.RET.COUNT, count);
        retObj.put(Constants.PARAM.ADMINICLE.PAGE.RET.LIST, retArr);
        return retObj;
    }

    /** **/
    private ActResponse pageAct(final ActRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Record对象 **/
        final Record record = this.transferer.toRecord(request.getIdentifier(), this.entityCls, request.getData());
        info(LOGGER, "[ACT] " + record.toString());
        /** 2.将Java和脚本引擎连接 **/
        final Eidolon marchal = JSEngine.initJSEnv(request, record);
        /** 3.执行查询 **/
        final ConcurrentMap<Long, List<Record>> retMap = this.rdPerformer.performPage(record, marchal);
        /** 4.生成响应结果 **/
        final Map.Entry<Long, List<Record>> entry = retMap.entrySet().iterator().next();
        final ActResponse response = new ActResponse();
        response.success(extractObject(extractList(entry.getValue()), entry.getKey()));
        return response;
    }

    /** **/
    private ActResponse findAct(final ActRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Record对象 **/
        final Record record = this.transferer.toRecord(request.getIdentifier(), this.entityCls, request.getData());
        info(LOGGER, "[ACT] " + record.toString());
        /** 2.将Java和脚本引擎连接 **/
        final Eidolon marchal = JSEngine.initJSEnv(request, record);
        /** 3.执行查询 **/
        final List<Record> retList = this.rdPerformer.performFind(record, marchal);
        /** 4.执行结果 **/
        final ActResponse response = new ActResponse();
        response.success(extractObject(extractList(retList)));
        return response;

    }

    /** Remove执行方法 **/
    private ActResponse removeAct(final ActRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Record对象 **/
        final Record record = this.transferer.toRecord(request.getIdentifier(), this.entityCls, request.getData());
        info(LOGGER, "[ACT] " + record.toString());
        /** 2.将Java和脚本引擎连接 **/
        JSEngine.initJSEnv(request, record);
        /** 3.删除当前记录 **/
        final boolean deleted = this.wrPerformer.performDelete(record);
        final ActResponse response = new ActResponse();
        response.success(new JsonObject().put("DELETED", deleted));
        return response;
    }

    /** Save执行方法 **/
    private ActResponse saveAct(final ActRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Record对象 **/
        final Record record = this.transferer.toRecord(request.getIdentifier(), this.entityCls, request.getData());
        info(LOGGER, "[ACT] " + record.toString());
        /** 2.将Java和脚本引擎连接 **/
        JSEngine.initJSEnv(request, record);
        /** 3.将record执行插入操作 **/
        Record inserted = null;
        if (Brancher.isUpdate(record, request)) {
            inserted = this.wrPerformer.performUpdate(record, request.getProjection().getFilters());
        } else {
            inserted = this.wrPerformer.performInsert(record, request.getProjection().getFilters());
        }
        // 返回正确响应结果
        final ActResponse response = new ActResponse();
        response.success(this.transferer.fromRecord(inserted));
        return response;
    }

    /** Controller Method **/
    private ActResponse execute(final ActRequest request, final HttpMethod[] methods, final Behavior behavior) {
        final ActResponse response = new ActResponse();
        try {
            /** **/
            final ActResponse ret = behavior.dispatch(request);
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
