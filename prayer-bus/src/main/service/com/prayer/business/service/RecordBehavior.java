package com.prayer.business.service;

import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.script.ScriptException;

import com.prayer.dao.ObjectTransferer;
import com.prayer.facade.business.service.RecordService;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.script.Region;
import com.prayer.facade.util.Transferer;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.Eidolon;
import com.prayer.model.business.behavior.ActRequest;
import com.prayer.script.js.JSRegion;

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
    public JsonObject find(@NotNull final ActRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Region **/
        final Region region = new JSRegion(this.entityCls);
        /** 2.执行脚本请求 **/
        final Eidolon marchal = region.execute(request);
        /** 3.执行查询 **/
        final List<Record> retList = this.rdPerformer.performFind(marchal);
        /** 4.执行结果 **/
        return extractObject(extractList(retList));
    }

    /**
     * 
     * @param page
     * @return
     */
    @Override
    public JsonObject page(@NotNull final ActRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Region **/
        final Region region = new JSRegion(this.entityCls);
        /** 2.执行脚本请求 **/
        final Eidolon marchal = region.execute(request);
        /** 3.执行分页 **/
        final ConcurrentMap<Long, List<Record>> retMap = this.rdPerformer.performPage(marchal);
        /** 4.生成响应结果 **/
        final Map.Entry<Long, List<Record>> entry = retMap.entrySet().iterator().next();
        return extractObject(extractList(entry.getValue()), entry.getKey());
    }

    /**
     * Save方法：Update/Insert
     */
    @Override
    public JsonObject save(@NotNull final ActRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Region **/
        final Region region = new JSRegion(this.entityCls);
        /** 2.执行脚本请求 **/
        final Eidolon marchal = region.execute(request);
        final Record record = marchal.getRecord();
        /** 3.将record执行插入操作 **/
        Record inserted = null;
        if (Brancher.isUpdate(record, request)) {
            inserted = this.wrPerformer.performUpdate(record, request.getProjection().getFilters());
        } else {
            inserted = this.wrPerformer.performInsert(record, request.getProjection().getFilters());
        }
        /** 4.生成响应结果 **/
        return this.transferer.fromRecord(inserted);
    }

    /**
     * Delete专用
     */
    @Override
    public JsonObject remove(@NotNull final ActRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Region **/
        final Region region = new JSRegion(this.entityCls);
        /** 2.执行脚本请求 **/
        final Eidolon marchal = region.execute(request);
        final Record record = marchal.getRecord();
        /** 3.删除当前记录 **/
        final boolean deleted = this.wrPerformer.performDelete(record);
        return new JsonObject().put("DELETED", deleted);
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
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
