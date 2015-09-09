package com.prayer.schema.db;

import static com.prayer.util.Instance.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.db.conn.MetadataConn;
import com.prayer.db.conn.impl.MetadataConnImpl;
import com.prayer.model.h2.FieldModel;

/**
 * 
 * @author Lang
 *
 */
public class FieldMapperTestCase extends AbstractMapperCase<FieldModel, String> { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(FieldMapperTestCase.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 */
	@BeforeClass		// NOPMD
	public static void setUp(){
		/** **/
		final MetadataConn metaConn = singleton(MetadataConnImpl.class);
		final String scriptFile = Resources.DB_SQL_DIR + MetadataConn.H2_SQL;
		metaConn.initMeta(Resources.class.getResourceAsStream(scriptFile));
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public Logger getLogger() {
		return LOGGER;
	}

	/** **/
	@Override
	public Class<?> getMapperClass() {
		return FieldMapper.class;
	}

	/** **/
	@Override
	public FieldModel instance() {
		return new FieldModel();
	}

	/** **/
	@Override
	public String[] filterFields() {
		return new String[] { "refMetaId" };
	}

	/** **/
	@Override
	public ConcurrentMap<String, Integer> fieldLenMap() {
		final ConcurrentMap<String, Integer> fieldMap = new ConcurrentHashMap<>();
		fieldMap.put("dateFormat", 32);
		fieldMap.put("unit", 32);
		return fieldMap;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
