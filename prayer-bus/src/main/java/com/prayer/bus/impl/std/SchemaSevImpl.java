package com.prayer.bus.impl.std;

import static com.prayer.util.Instance.reservoir;
import static com.prayer.util.Instance.singleton;
import static com.prayer.util.bus.BusinessLogger.error;
import static com.prayer.util.bus.BusinessLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.impl.schema.SchemaDaoImpl;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.exception.system.SchemaNotFoundException;
import com.prayer.exception.system.SerializationException;
import com.prayer.facade.bus.SchemaService;
import com.prayer.facade.dao.builder.Builder;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.Importer;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;
import com.prayer.schema.json.CommunionImporter;
import com.prayer.util.bus.BusinessLogger;
import com.prayer.util.cv.Accessors;
import com.prayer.util.cv.MemoryPool;

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
        } catch (AbstractTransactionException ex) {
            error(LOGGER, BusinessLogger.E_PROCESS_ERR, "4.Data Loading", ex.toString());
            result.error(ex);
        } catch (SerializationException ex) {
            error(LOGGER, BusinessLogger.E_PROCESS_ERR, "3.Serialization", ex.toString());
            result.error(ex);
        } catch (AbstractSystemException ex) {
            error(LOGGER, BusinessLogger.E_PROCESS_ERR, "1.Reading json schema file. file = " + filePath, ex.toString());
            result.error(ex);
        } catch (AbstractSchemaException ex) {
            error(LOGGER, BusinessLogger.E_PROCESS_ERR, "2.Verifying Schema", ex.toString());
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
        if(null == builder.getError()){
        	result.success(schema);
        }else{
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
            error(LOGGER, BusinessLogger.E_AT_ERROR, ex.toString());
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
            info(LOGGER, BusinessLogger.I_IMPORTER_NEW, filePath);
        } else {
            importer.refreshSchema(filePath);
            info(LOGGER, BusinessLogger.I_IMPORTER_EXIST, filePath);
        }
        return importer;// instance(CommunionImporter.class.getName(),
                        // filePath);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
