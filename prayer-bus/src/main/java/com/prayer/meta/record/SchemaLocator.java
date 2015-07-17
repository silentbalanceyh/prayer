package com.prayer.meta.record;

import static com.prayer.util.Instance.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.bus.schema.SchemaService;
import com.prayer.bus.schema.impl.SchemaSevImpl;
import com.prayer.model.SystemEnum.ResponseCode;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.meta.GenericSchema;

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
public final class SchemaLocator {
	// ~ Static Fields =======================================
	/** **/
	private static final ConcurrentMap<String,GenericSchema> SCHEMA_MAP = new ConcurrentHashMap<>();
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 从系统中获取Schema
	 * @param identifier
	 * @return
	 */
	@NotNull
	public static GenericSchema getSchema(@NotNull @NotBlank @NotEmpty final String identifier){
		GenericSchema schema = SCHEMA_MAP.get(identifier);
		if(null == schema){
			// 如果schema没有存在于MAP中则直接从H2的数据库中读取对应的Schema
			final SchemaService service = singleton(SchemaSevImpl.class);
			final ServiceResult<GenericSchema> result = service.findSchema(identifier);
			if(ResponseCode.SUCCESS == result.getResponseCode()){
				schema = result.getResult();
				SCHEMA_MAP.put(identifier,schema);
			}
		}
		return schema;
	}
	// ~ Private Methods =====================================
	private SchemaLocator(){}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
