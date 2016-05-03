package com.prayer.business.deployment.impl;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.ObjectTransferer;
import com.prayer.dao.data.DataRecordDalor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.database.dao.RecordDao;
import com.prayer.facade.model.crucial.Expression;
import com.prayer.facade.model.crucial.Transducer.V;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.util.Transferer;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.crucial.DataRecord;
import com.prayer.model.query.Restrictions;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public final class DataExecutors {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DataExecutors.class);
    /** **/
    private static final RecordDao DAO = reservoir(DataRecord.class.getName(), DataRecordDalor.class);
    /** **/
    private static final Transferer TRANS = singleton(ObjectTransferer.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** Entity实体导入 **/
    public static void processEntity(final String identifier, final JsonArray data) throws AbstractException {
        final int size = data.size();
        for (int idx = 0; idx < size; idx++) {
            try {
                final JsonObject entity = data.getJsonObject(idx);
                final Record record = TRANS.toRecord(identifier, DataRecord.class, entity);
                DAO.insert(record);
            } catch (AbstractDatabaseException ex) {
                peError(LOGGER, ex);
                continue;
            }
        }
    }

    /** Relation导入 **/
    public static void processRelation(final String identifier, final JsonArray data) throws AbstractException {
        final int size = data.size();
        for (int idx = 0; idx < size; idx++) {
            try {
                final JsonObject relations = data.getJsonObject(idx);
                final List<Record> records = buildRecord(identifier, relations);
                for (final Record record : records) {
                    DAO.insert(record);
                }
            } catch (AbstractDatabaseException ex) {
                peError(LOGGER, ex);
                continue;
            }
        }
    }

    public static List<Record> buildRecord(final String identifier, final JsonObject data) throws AbstractException {
        final List<Record> records = new ArrayList<>();
        /** 1.计算Source **/
        final JsonObject source = data.getJsonObject("source");
        final List<Record> sources = queryRecords(source);
        if(Constants.ONE == sources.size()){
            /** 2.计算Targets **/
            final Record sourceR = sources.get(Constants.IDX);
            final JsonObject target = data.getJsonObject("target");
            final List<Record> targets = queryRecords(target);
            for(final Record targetR: targets){
                /** 3.构造Relations **/
                final JsonObject entity = new JsonObject();
                entity.put(source.getString("field"), sourceR.get(Constants.PID).literal());
                entity.put(target.getString("field"), targetR.get(Constants.PID).literal());
                /** 4.构造Record成功 **/
                records.add(TRANS.toRecord(identifier, DataRecord.class, entity));
            }
        }
        return records;
    }

    private static List<Record> queryRecords(final JsonObject data) throws AbstractException {
        final List<Record> records = new ArrayList<>();
        if (data.containsKey("id") && data.containsKey("value")) {
            /** 1.参数提取 **/
            final String identifier = data.getString("id");
            final JsonObject value = data.getJsonObject("value");
            if (Constants.ONE == value.fieldNames().size()) {
                /** 2.提取列信息 **/
                final String field = value.fieldNames().iterator().next();
                final Object values = value.getValue(field);
                /** 3.提取values **/
                final List<String> params = new ArrayList<>();
                if (values.getClass() == JsonArray.class) {
                    final JsonArray valueArr = ((JsonArray) values);
                    final int size = valueArr.size();
                    for (int idx = 0; idx < size; idx++) {
                        params.add(valueArr.getString(idx));
                    }
                } else if (values.getClass() == String.class) {
                    params.add(values.toString());
                }
                /** 提取返回值 **/
                records.addAll(queryRecords(identifier, field, params.toArray(Constants.T_STR_ARR)));
            }
            // 如果长度不为1跳过
        }
        return records;
    }

    private static List<Record> queryRecords(final String identifier, final String field, final String... value)
            throws AbstractException {
        /** 1.实例化Record **/
        final Record record = instance(DataRecord.class, identifier);
        Expression expr = null;
        /** 2.构造Expression **/
        if (Constants.ONE == value.length) {
            expr = Restrictions.eq(record.toColumn(field));
        } else if (Constants.TWO == value.length) {
            final String column = record.toColumn(field);
            expr = Restrictions.or(Restrictions.eq(column), Restrictions.eq(column));
        } else if (Constants.TWO < value.length) {
            final String column = record.toColumn(field);
            Expression finalExpr = Restrictions.eq(column);
            for (int idx = 1; idx < value.length; idx++) {
                finalExpr = Restrictions.or(finalExpr, Restrictions.eq(column));
            }
            expr = finalExpr;
        }
        /** 3.Values **/
        final List<Value<?>> values = new ArrayList<>();
        for (final String item : value) {
            values.add(V.get().getValue(record.fields().get(field), item));
        }
        /** 4.读取最终数据 **/
        return DAO.queryByFilter(record, Constants.T_STR_ARR, values, expr);
    }

    /** Purge操作 **/
    public static void purgeData(final String identifier) throws AbstractException {
        final Record record = instance(DataRecord.class, identifier);
        DAO.purge(record);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private DataExecutors() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
