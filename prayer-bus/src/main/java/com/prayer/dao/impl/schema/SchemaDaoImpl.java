package com.prayer.dao.impl.schema;

import static com.prayer.util.Error.info;
import static com.prayer.util.Generator.uuid;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.i.schema.SchemaDao;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.kernel.model.SchemaExpander;
import com.prayer.model.schema.FieldModel;
import com.prayer.model.schema.KeyModel;
import com.prayer.model.schema.MetaModel;
import com.prayer.plugin.mapper.FieldMapper;
import com.prayer.plugin.mapper.KeyMapper;
import com.prayer.plugin.mapper.MetaMapper;
import com.prayer.plugin.mapper.SessionManager;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class SchemaDaoImpl extends AbstractDaoImpl implements SchemaDao { // NOPMD

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaDaoImpl.class);
    /** Exception Class **/
    private static final String EXP_CLASS = "com.prayer.exception.system.DataLoadingException";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 日志记录器 **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** **/
    @Override
    @NotNull
    public GenericSchema create(@NotNull final GenericSchema schema) throws AbstractTransactionException {
        // 1.数据准备
        if (StringKit.isNonNil(schema.getMeta().getUniqueId())) {
            schema.getMeta().setUniqueId(null);
        }
        this.prepareData(schema);
        // 2.开启Mybatis的事务
        final SqlSession session = SessionManager.getSession();
        final Transaction transaction = transaction(session);
        {
            // 3.MetaModel的导入
            session.getMapper(MetaMapper.class).insert(schema.getMeta());
            // 4.KeyModel的导入
            session.getMapper(KeyMapper.class).batchInsert(new ArrayList<>(schema.getKeys().values()));
            // 5.FieldModel的导入
            session.getMapper(FieldMapper.class).batchInsert(new ArrayList<>(schema.getFields().values()));
        }
        // 6.事务完成提交
        submit(transaction, EXP_CLASS);
        return schema;
    }

    /** **/
    @Override
    @NotNull
    public GenericSchema synchronize(@NotNull final GenericSchema schema) throws AbstractTransactionException {
        // 1.刷新数据库中的Schema数据
        final GenericSchema latestSchema = this.refreshData(schema);
        // 2.数据准备
        this.prepareData(latestSchema);
        // 3.开启Mybatis的事务
        final SqlSession session = SessionManager.getSession();
        final Transaction transaction = transaction(session);
        {
            // 4.MetaModel的更新
            session.getMapper(MetaMapper.class).update(latestSchema.getMeta());
            // 5.KeyModel的更新
            final KeyMapper keyMapper = session.getMapper(KeyMapper.class);
            keyMapper.deleteByMeta(latestSchema.getMeta().getUniqueId());
            keyMapper.batchInsert(new ArrayList<>(latestSchema.getKeys().values()));
            // 6.FieldModel的更新
            final FieldMapper fieldMapper = session.getMapper(FieldMapper.class);
            fieldMapper.deleteByMeta(latestSchema.getMeta().getUniqueId());
            fieldMapper.batchInsert(new ArrayList<>(latestSchema.getFields().values()));
        }
        // 6.事务完成提交
        submit(transaction, EXP_CLASS);
        return latestSchema;
    }

    /** **/
    @Override
    public GenericSchema getById(@NotNull @NotBlank @NotEmpty final String globalId) {
        // 1.读取Meta
        final SqlSession session = SessionManager.getSession();
        final MetaMapper metaMapper = session.getMapper(MetaMapper.class);
        final MetaModel meta = metaMapper.selectByGlobalId(globalId);

        // 2.返回GenericSchema
        session.close();
        return extractSchema(meta);
    }

    /**
     * 
     */
    @Override
    public boolean deleteById(@NotNull @NotBlank @NotEmpty final String identifier) throws AbstractTransactionException {
        // 1.开启Mybatis的事务
        final SqlSession session = SessionManager.getSession();
        final Transaction transaction = transaction(session);
        final GenericSchema schema = this.getById(identifier);
        final String metaId = schema.getMeta().getUniqueId();
        // 2.删除Keys
        final KeyMapper keyMapper = session.getMapper(KeyMapper.class);
        keyMapper.deleteByMeta(metaId);
        // 3.删除Fields
        final FieldMapper fieldMapper = session.getMapper(FieldMapper.class);
        fieldMapper.deleteByMeta(metaId);
        // 4.删除Meta
        final MetaMapper metaMapper = session.getMapper(MetaMapper.class);
        metaMapper.deleteById(metaId);
        // 6.事务完成提交
        submit(transaction, EXP_CLASS);
        return true;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 
     * @param meta
     * @return
     */
    private GenericSchema extractSchema(final MetaModel meta) {
        // 1.读取Keys -> List
        List<KeyModel> keys = null;
        final SqlSession session = SessionManager.getSession();
        if (null != meta && null != meta.getUniqueId()) {
            final KeyMapper keyMapper = session.getMapper(KeyMapper.class);
            keys = keyMapper.selectByMeta(meta.getUniqueId());
        }
        // 2.读取Fields -> List
        List<FieldModel> fields = null;
        if (null != meta && null != meta.getUniqueId()) {
            final FieldMapper fieldMapper = session.getMapper(FieldMapper.class);
            fields = fieldMapper.selectByMeta(meta.getUniqueId());
        }
        session.close();
        return extractSchema(meta, keys, fields);
    }

    private GenericSchema extractSchema(final MetaModel meta, final List<KeyModel> keys,
            final List<FieldModel> fields) {
        if (null == meta) {
            return null;
        }
        final GenericSchema schema = new GenericSchema();
        schema.setIdentifier(meta.getGlobalId());
        schema.setMeta(meta);
        schema.setKeys(SchemaExpander.toKeysMap(keys));
        schema.setFields(SchemaExpander.toFieldsMap(fields));
        return schema;
    }

    /**
     * 1.在刷新数据过程GlobalId是不可更改的 2.GlobalId -> UniqueId 3.字段Field和Key以name作为不可变更维度
     * 
     * @param schema
     *            新的Json传入的Schema文件
     */
    private GenericSchema refreshData(final GenericSchema schema) {
        // 1.从数据库中读取原始schema
        final GenericSchema original = this.getById(schema.getIdentifier());
        // 2.拷贝Meta中的数据到original中执行Overwrite
        // uniqueId不执行更新
        // initOrder不执行更新
        // initSubOrder不执行更新
        // oobFile不执行更新
        // using不执行更新
        info(LOGGER, "[I] Meta from database original = " + original);
        if (null == original || null == original.getMeta() || null == schema || null == schema.getMeta()) {
            info(LOGGER, "[I] The meta data object does not exist in H2 : Global Id = " + schema.getIdentifier());
        } else {
            original.getMeta().setCategory(schema.getMeta().getCategory());
            original.getMeta().setGlobalId(schema.getMeta().getGlobalId());
            original.getMeta().setMapping(schema.getMeta().getMapping());
            original.getMeta().setName(schema.getMeta().getName());
            original.getMeta().setNamespace(schema.getMeta().getNamespace());
            original.getMeta().setPolicy(schema.getMeta().getPolicy());
            original.getMeta().setSeqInit(schema.getMeta().getSeqInit());
            original.getMeta().setSeqName(schema.getMeta().getSeqName());
            original.getMeta().setSeqStep(schema.getMeta().getSeqStep());
            original.getMeta().setSubKey(schema.getMeta().getSubKey());
            original.getMeta().setSubTable(schema.getMeta().getSubTable());
            original.getMeta().setTable(schema.getMeta().getTable());
            // 设置其他内容
            original.setFields(schema.getFields());
            original.setKeys(schema.getKeys());
            original.setIdentifier(schema.getIdentifier());
        }
        return original;
    }

    private void prepareData(final GenericSchema schema) {
        // 1.设置Meta的ID
        String metaId = null;
        if (StringKit.isNil(schema.getMeta().getUniqueId())) {
            // 创建新的Schema
            metaId = uuid();
            schema.getMeta().setUniqueId(metaId);
        } else {
            // 更新现有的Schema
            metaId = schema.getMeta().getUniqueId();
        }
        // 设置Identifier
        schema.setIdentifier(schema.getMeta().getGlobalId());
        // 2.设置Keys的ID
        for (final KeyModel key : schema.getKeys().values()) {
            key.setUniqueId(uuid());
            key.setRefMetaId(metaId);
        }
        // 3.设置Fields的ID
        for (final FieldModel model : schema.getFields().values()) {
            model.setUniqueId(uuid());
            model.setRefMetaId(metaId);
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
