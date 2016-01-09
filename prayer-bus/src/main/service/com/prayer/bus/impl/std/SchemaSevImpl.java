package com.prayer.bus.impl.std;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.constant.Accessors;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.Resources;
import com.prayer.constant.log.InfoKey;
import com.prayer.dao.impl.schema.SchemaDaoImpl;
import com.prayer.exception.system.SchemaNotFoundException;
import com.prayer.exception.system.SerializationException;
import com.prayer.facade.bus.SchemaService;
import com.prayer.facade.dao.Builder;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.Importer;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.schema.json.CommunionImporter;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class SchemaSevImpl implements SchemaService {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaSevImpl.class);
    // ~ Instance Fields =====================================
    /** 访问H2的Schema数据层接口 **/
    @NotNull
    private transient final SchemaDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public SchemaSevImpl() {
        this.dao = singleton(SchemaDaoImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<GenericSchema> syncSchema(@NotNull @NotEmpty @NotBlank final String filePath) {
        final Importer importer = this.getImporter(filePath);
        final ServiceResult<GenericSchema> result = new ServiceResult<>();
        try {
            // 1.读取filePath -> AbstractSystemException
            importer.readSchema();
            // 2.验证Schema文件流程
            importer.ensureSchema();
            // 3.转换Schema文件
            final GenericSchema schema = importer.transformSchema();
            // 4.因为importer中已经检查过，所以不需要再检查
            importer.syncSchema(schema);
            // 5.成功代码
            result.success(schema);
            info(LOGGER, InfoKey.INF_DP_STEP1, filePath, Resources.META_CATEGORY);
        } catch (AbstractTransactionException ex) {
            peError(LOGGER, ex);
            result.error(ex);
        } catch (SerializationException ex) {
            peError(LOGGER, ex);
            result.error(ex);
        } catch (AbstractSystemException ex) {
            peError(LOGGER, ex);
            result.error(ex);
        } catch (AbstractSchemaException ex) {
            peError(LOGGER, ex);
            result.failure(ex);
        }
        return result;
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<GenericSchema> syncMetadata(@NotNull final GenericSchema schema) {
        // 使用池化单件模式，每一个ID的Schema拥有一个Builder
        final Builder builder = reservoir(MemoryPool.POOL_BUILDER, schema.getIdentifier(), Accessors.builder(), schema);
        if (builder.existTable()) {
            builder.syncTable(schema);
        } else {
            builder.createTable();
        }
        // 如果有错误则getError()就不是null值则会导致Build异常
        final ServiceResult<GenericSchema> result = new ServiceResult<>();
        if (null == builder.getError()) {
            info(LOGGER, InfoKey.INF_DP_STEP2, schema.getIdentifier(), Resources.META_CATEGORY, Resources.DB_CATEGORY);
            result.success(schema);
        } else {
            result.failure(builder.getError());
        }
        return result;
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<GenericSchema> findSchema(@NotNull @NotEmpty @NotBlank final String identifier) {
        final GenericSchema schema = this.dao.getById(identifier);
        final ServiceResult<GenericSchema> result = new ServiceResult<>();
        if (null == schema) {
            result.failure(new SchemaNotFoundException(getClass(), identifier));
        } else {
            result.success(schema);
        }
        return result;
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<Boolean> removeSchema(@NotNull @NotEmpty @NotBlank final String identifier) {
        final ServiceResult<Boolean> result = new ServiceResult<>();
        try {
            final Boolean ret = this.dao.deleteById(identifier);
            result.success(ret);
        } catch (AbstractTransactionException ex) {
            peError(LOGGER, ex);
            result.error(ex);
        }
        return result;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 注意Importer本身的刷新流程，根据文件路径刷新了filePath
     **/
    private Importer getImporter(final String filePath) {
        Importer importer = MemoryPool.POOL_IMPORTER.get(filePath);
        if (null == importer) {
            importer = reservoir(MemoryPool.POOL_IMPORTER, filePath, CommunionImporter.class, filePath);
        } else {
            importer.refreshSchema(filePath);
        }
        return importer;// instance(CommunionImporter.class.getName(),
                        // filePath);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
