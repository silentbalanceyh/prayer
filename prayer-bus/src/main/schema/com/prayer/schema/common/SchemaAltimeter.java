package com.prayer.schema.common;

import com.prayer.database.util.sql.SqlTypes;
import com.prayer.exception.schema.IdentifierReferenceException;
import com.prayer.facade.database.dao.schema.SchemaDao;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ArrayRuler;
import com.prayer.facade.schema.verifier.Altimeter;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.schema.AbstractAltimeter;
import com.prayer.model.meta.database.PEField;
import com.prayer.schema.json.JArrayHabitus;
import com.prayer.schema.json.ruler.UpdatingRuler;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 针对Schema本身的验证过程
 * 
 * @author Lang
 *
 */
@Guarded
public class SchemaAltimeter extends AbstractAltimeter implements Altimeter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** SchemaDao实例化 **/
    public SchemaAltimeter(@NotNull final SchemaDao dao) {
        super(dao);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    // 1.Global ID存在，Table Name存在：进入Schema的更新验证
    // 2.Global ID不存在，Table Name存在：抛出异常，不可以同一个Global ID引用同一个Table Name
    // 3.【Ignore】Global ID存在，Table Name不存在：直接将旧表切换成新表：目前步骤可跳过，实际上换成创建Table流程
    // 4.【Ignore】Global ID不存在，Table Name不存在：相当于全新的操作：木匾步骤同样跳过
    // 只有Table存在的时候会进入更新流程，而且如果Global ID不存在会抛异常
    @Override
    public void verify(@NotNull final Schema schema) throws AbstractException {
        /** 1.从系统中读取原始Schema **/
        final Schema stored = this.getDao().get(schema.identifier());
        /** 2.当前Table是否存在 **/
        final boolean exist = this.existTable(schema.getTable());
        if (exist) {
            if (null == stored) {
                /** 3.1.系统中没有这个Global ID记录，但有表记录 **/
                throw new IdentifierReferenceException(getClass(), schema.identifier(), schema.getTable());
            } else {
                /** 3.2.系统中存在这个Global ID，有表记录，执行更新流程验证 **/
                this.verifyUpdates(schema, stored);
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void verifyUpdates(final Schema schema, final Schema stored) throws AbstractSchemaException {
        /** 1.1.构建允许的类型集 **/
        final JsonObject allowedTypes = new JsonObject();
        /** 1.2.提取需要验证的Json原始数据，以JsonArray的方式处理 **/
        final JsonArray datas = new JsonArray();

        /** 2.从原始存储数据库中提取H2中的字段规范 **/
        final PEField[] storedFields = stored.fields();
        for (final PEField field : storedFields) {
            /** 3.从新的Schema中读取PEField **/
            final PEField newField = schema.getField(field.getName());
            if (null != newField && !field.getColumnType().equals(newField.getColumnType())) {
                /** 4.执行更新流程，Stored中存在的Field并且存在类型改变 **/
                final JsonObject metadata = getMetadata(field, newField);
                allowedTypes.put(field.getName(), metadata);
                /** 5.将需要验证的Old的Field内容添加到JsonArray用于构建Ruler使用的ArrayHabitus **/
                datas.add(field.toJson());
            }
        }
        /** 6.构造Ruler需要使用的ArrayHabitus **/
        final ArrayHabitus habitus = new JArrayHabitus(datas, Attributes.R_FIELDS);
        /** 7.构造Update Ruler **/
        final ArrayRuler ruler = new UpdatingRuler(allowedTypes);
        /** 8.执行验证逻辑 **/
        ruler.apply(habitus);
    }

    private JsonObject getMetadata(final PEField oldField, final PEField newField) {
        final JsonObject retData = new JsonObject();
        /** 1.抽取Target的Allowed Type信息 **/
        final String target = SqlTypes.get(newField.getColumnType());
        final JsonArray allowedType = SqlTypes.rvectors(target);
        /** 2.封装Addtional的信息 **/
        retData.put("types", allowedType);
        retData.put("target", SqlTypes.get(newField.getColumnType()));
        return retData;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
