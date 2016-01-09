package com.prayer.model.kernel;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.impl.std.SchemaSevImpl;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.constant.log.InfoKey;
import com.prayer.exception.system.SchemaNotFoundException;
import com.prayer.facade.bus.SchemaService;
import com.prayer.model.bus.ServiceResult;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 内部使用的SchemaLocator，保证系统中仅存在一份Schema
 * @author Lang
 *
 */
@Guarded
final class SchemaLocator {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaLocator.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 从系统中获取Schema
     * 
     * @param identifier
     * @return
     */
    @NotNull
    public static GenericSchema getSchema(@NotNull @NotBlank @NotEmpty final String identifier)
            throws SchemaNotFoundException {
        GenericSchema schema = MemoryPool.POOL_SCHEMA.get(identifier);
        if (null == schema) {
            info(LOGGER, InfoKey.INF_PM_LKP_META,identifier);
            // 如果schema没有存在于MAP中则直接从H2的数据库中读取对应的Schema
            final SchemaService service = singleton(SchemaSevImpl.class);
            final ServiceResult<GenericSchema> result = service.findSchema(identifier);
            if (ResponseCode.SUCCESS == result.getResponseCode()) {
                schema = result.getResult();
                MemoryPool.POOL_SCHEMA.put(identifier, schema);
            } else {
                throw new SchemaNotFoundException(SchemaLocator.class, identifier);
            }
        }
        return schema;
    }

    // ~ Private Methods =====================================
    private SchemaLocator() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
