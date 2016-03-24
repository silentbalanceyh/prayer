package com.prayer.business.impl.schema;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.builder.impl.MetadataBuilder;
import com.prayer.constant.Resources;
import com.prayer.constant.log.InfoKey;
import com.prayer.dao.impl.schema.CommuneImporter;
import com.prayer.dao.impl.schema.SchemaDaoImpl;
import com.prayer.exception.system.SchemaNotFoundException;
import com.prayer.exception.system.SerializationException;
import com.prayer.facade.builder.Builder;
import com.prayer.facade.business.schema.SchemaService;
import com.prayer.facade.dao.schema.Importer;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.Altimeter;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.exception.AbstractSystemException;
import com.prayer.fantasm.exception.AbstractTransactionException;
import com.prayer.model.business.ServiceResult;
import com.prayer.schema.json.SchemaAltimeter;

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
    private transient SchemaDao dao;
    /** 访问传统数据库的Builder **/
    @NotNull
    private transient Builder builder;
    /** 导入器 **/
    @NotNull
    private transient Importer importer;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public SchemaSevImpl() {
        this.dao = singleton(SchemaDaoImpl.class);
        this.builder = singleton(MetadataBuilder.class);
        this.importer = singleton(CommuneImporter.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<Schema> importSchema(@NotNull @NotEmpty @NotBlank final String filePath) {
        final ServiceResult<Schema> result = new ServiceResult<>();
        try {
            /** 1.读取Schema信息，从Json到H2中 **/
            final Schema schema = importer.read(filePath);
            /** 2.【Advanced验证】主要验证变更：Schema的变更是否合法的验证 **/
            final Altimeter altimeter = singleton(SchemaAltimeter.class, this.dao);
            altimeter.verify(schema);
            /** 3.将读取到的schema存如到H2 Database中 **/
            this.dao.save(schema);
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
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
            result.failure(ex);
        }
        return result;
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<Schema> syncMetadata(@NotNull final Schema schema) {
        final ServiceResult<Schema> result = new ServiceResult<>();
        try {
            this.builder.synchronize(schema);
            result.success(schema);
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
            result.failure(ex);
        }
        return result;
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<Schema> findById(@NotNull @NotEmpty @NotBlank final String identifier) {
        final ServiceResult<Schema> result = new ServiceResult<>();
        try {
            final Schema schema = this.dao.get(identifier);
            if (null == schema) {
                result.failure(new SchemaNotFoundException(getClass(), identifier));
            } else {
                result.success(schema);
            }
        } catch (AbstractTransactionException ex) {
            peError(LOGGER, ex);
            result.failure(ex);
        }
        return result;
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<Boolean> removeById(@NotNull @NotEmpty @NotBlank final String identifier) {
        final ServiceResult<Boolean> result = new ServiceResult<>();
        try {
            final Boolean ret = this.dao.delete(identifier);
            result.success(ret);
        } catch (AbstractTransactionException ex) {
            peError(LOGGER, ex);
            result.error(ex);
        }
        return result;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
