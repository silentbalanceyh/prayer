package com.prayer.dao.schema;

import static com.prayer.util.Generator.uuid;
import static com.prayer.util.debug.Log.debug;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.database.accessor.impl.MetaAccessorImpl;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.database.accessor.MetaAccessor;
import com.prayer.facade.database.dao.schema.SchemaDao;
import com.prayer.facade.model.entity.Entity;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.fantasm.exception.AbstractTransactionException;
import com.prayer.model.crucial.schema.JsonSchema;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;
import com.prayer.model.query.Restrictions;
import com.prayer.model.type.StringType;

import io.vertx.core.json.JsonObject;
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
public final class SchemaDalor implements SchemaDao {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaDalor.class);

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
        /** 3.插入Keys和Fields **/
        accessor(PEKey.class).insert(schema.keys());
        accessor(PEField.class).insert(schema.fields());
        /** 4.如果没有任何异常抛出则可直接插入成功 **/
        return schema;
    }

    @Override
    public Schema get(@NotNull @NotEmpty @NotBlank final String identifier) throws AbstractTransactionException {
        /** 1.根据identifier从系统中读取PEMeta **/
        final List<Entity> metaList = accessor(PEMeta.class).queryList(condition(identifier));
        Schema schema = null;
        if (!metaList.isEmpty() && Constants.ONE == metaList.size()) {
            schema = new JsonSchema();
            final Entity entity = metaList.get(Constants.IDX);
            if (null != entity) {
                /** 2.设置JsonSchema的Meta部分 **/
                schema.meta(new PEMeta(entity.toJson()));
                final String whereSql = condition(entity);
                /** 3.设置Keys部分 **/
                final List<Entity> keys = accessor(PEKey.class).queryList(whereSql);
                schema.keys(keys.toArray(new PEKey[] {}));
                /** 4.设置Fields部分 **/
                final List<Entity> fields = accessor(PEField.class).queryList(whereSql);
                schema.fields(fields.toArray(new PEField[] {}));
            }
        }
        return schema;
    }

    /** **/
    @Override
    public boolean delete(@NotNull @NotEmpty @NotBlank final String identifier) throws AbstractTransactionException {
        /** 1.根据Identifier删除数据 **/
        final Schema schema = this.get(identifier);
        if (null != schema) {
            /** 2.删除Keys和Fields **/
            {
                accessor(PEKey.class).deleteList(condition(schema));
                accessor(PEField.class).deleteList(condition(schema));
            }
            /** 3.删除Meta **/
            accessor(PEMeta.class).deleteById(schema.totem());
        }
        return true;
    }

    /** **/
    @Override
    public List<String> purge() throws AbstractTransactionException {
        /** 0.获取返回的Meta集合 **/
        final List<String> tables = new ArrayList<>();
        final List<Entity> metas = accessor(PEMeta.class).getAll();
        for (final Entity meta : metas) {
            if (null != meta) {
                final JsonObject data = meta.toJson();
                tables.add(data.getString(Attributes.M_TABLE));
            }
        }
        /** 1.先删除Key **/
        accessor(PEKey.class).purge();
        /** 2.再删除Field **/
        accessor(PEField.class).purge();
        /** 3.最后删除Meta **/
        accessor(PEMeta.class).purge();
        return tables;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private String condition(final Entity entity) {
        return Restrictions.eq("R_META_ID", new StringType(entity.id().toString())).toSql();
    }

    private String condition(final Schema schema) {
        return Restrictions.eq("R_META_ID", new StringType(schema.totem().toString())).toSql();
    }

    private String condition(final String identifier) {
        return Restrictions.eq("S_GLOBAL_ID", new StringType(identifier)).toSql();
    }

    /** **/
    private MetaAccessor accessor(final Class<?> genericT) {
        return new MetaAccessorImpl(genericT);
    }

    /** 执行save之前的准备工作 **/
    private boolean completeData(final Schema schema) throws AbstractTransactionException {
        boolean isUpdate = false;
        /** 1.判断是Insert还是Update **/
        assert (null != schema.identifier()) : "";
        final Schema selected = this.get(schema.identifier());
        Serializable metaId = null;
        if (null == selected) {
            debug(LOGGER, "[SCH] Go to creating process...");
            /** 2.1.1.判断schema的Id信息 **/
            metaId = schema.totem();
            if (null == metaId) {
                metaId = schema.totem(uuid());
            }
            isUpdate = false;
        } else {
            debug(LOGGER, "[SCH] Go to updating process...");
            /** 2.2.1.删除之前的Keys和Fields **/
            {
                accessor(PEKey.class).deleteList(condition(selected));
                accessor(PEField.class).deleteList(condition(selected));
            }
            /** 2.2.2.使用selected作为最新的metaId **/
            metaId = selected.totem();
            // 将Selected中的数据同步到传入的Schema里
            schema.totem(metaId);
            isUpdate = true;
        }

        /** 3.刷新所有Keys，Fields对应的MetaId **/
        schema.synchronize(metaId);
        return isUpdate;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
