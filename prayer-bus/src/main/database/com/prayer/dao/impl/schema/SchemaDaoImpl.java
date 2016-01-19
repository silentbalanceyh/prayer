package com.prayer.dao.impl.schema;

import static com.prayer.util.Generator.uuid;
import static com.prayer.util.debug.Log.debug;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.accessor.impl.MetaAccessorImpl;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.schema.Schema;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;
import com.prayer.model.query.Restrictions;
import com.prayer.model.type.StringType;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
// TODO: 目前没有加入分布式事务处理，等到分布式事务处理可完成了过后则可以考虑设置事务级操作
@Guarded
public final class SchemaDaoImpl implements SchemaDao {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaDaoImpl.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 从底层Accessor级别同步Schema信息到H2 Database **/
    @Override
    public Schema save(@NotNull final Schema schema) throws AbstractTransactionException {
        /** 1.数据准备 **/
        boolean isUpdate = this.completeData(schema);
        /** 2.插入Meta **/
        if (isUpdate) {
            accessor(PEMeta.class).update(schema.meta());
        } else {
            accessor(PEMeta.class).insert(schema.meta());
        }
        /** 3.删除之前的Keys和Fields **/
        {
            accessor(PEKey.class).deleteList(condition(schema));
            accessor(PEField.class).deleteList(condition(schema));
        }
        /** 4.插入Keys和Fields **/
        accessor(PEKey.class).insert(schema.keys());
        accessor(PEField.class).insert(schema.fields());
        /** 5.如果没有任何异常抛出则可直接插入成功 **/
        return schema;
    }

    @Override
    public Schema get(String identifier) throws AbstractTransactionException {
        // TODO Auto-generated method stub
        return null;
    }

    /** **/
    @Override
    public boolean delete(@NotNull @NotEmpty @NotBlank final String identifier) throws AbstractTransactionException {
        /** 1.根据Identifier删除数据 **/
        final Schema schema = this.get(identifier);
        /** 2.删除Keys和Fields **/
        {
            accessor(PEKey.class).deleteList(condition(schema));
            accessor(PEField.class).deleteList(condition(schema));
        }
        /** 3.删除Meta **/
        accessor(PEMeta.class).deleteById(identifier);
        return true;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private String condition(final Schema schema) {
        return Restrictions.eq("R_META_ID", new StringType(schema.totem().toString())).toSql();
    }

    /** **/
    private MetaAccessor accessor(final Class<?> genericT) {
        return new MetaAccessorImpl(genericT);
    }

    /** 执行save之前的准备工作 **/
    private boolean completeData(final Schema schema) throws AbstractTransactionException {
        boolean isUpdate = false;
        /** 1.判断schema的Id信息 **/
        Serializable metaId = schema.totem();
        if (null == metaId) {
            debug(LOGGER, "[SCH] Go to creating process...");
            metaId = schema.totem(uuid());
        } else {
            debug(LOGGER, "[SCH] Go to updating process...");
            isUpdate = true;
        }
        /** 2.刷新所有Keys，Fields对应的MetaId **/
        schema.synchronize(metaId);
        return isUpdate;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
