package com.test.schema;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.DataLoadingException;
import com.prayer.exception.system.SerializationException;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.schema.Importer;
import com.prayer.schema.Serializer;
import com.prayer.schema.dao.SchemaDao;
import com.prayer.schema.dao.impl.SchemaDaoImpl;
import com.prayer.schema.json.CommunionSerializer;
import com.prayer.schema.json.internal.CommunionImporter;
import com.test.AbstractTestCase;

/**
 * 
 * @author Lang
 * @see
 */
public abstract class AbstractSchemaTestCase extends AbstractTestCase {
	// ~ Static Fields =======================================
	/** **/
	protected static final String SCHEMA_ROOT = "/schema/data/json/validation/";
	/** **/
	protected static final String M_IMPORT_FILE = "importFile()";
	// ~ Instance Fields =====================================
	/** **/
	protected transient Importer importer;
	/** **/
	protected transient Serializer serializer;
	/** **/
	protected transient SchemaDao service;	// NOPMD

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public AbstractSchemaTestCase() {
		super(CommunionImporter.class.getName());
		this.serializer = new CommunionSerializer();
		this.service = singleton(SchemaDaoImpl.class);
	}

	// ~ Abstract Methods ====================================
	/** **/
	public abstract Logger getLogger();

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 
	 * @param inputFile
	 * @param errMsg
	 */
	protected void testImport(final String inputFile, final String errMsg) throws AbstractSchemaException {
		setMethod(M_IMPORT_FILE);
		importer = new CommunionImporter(SCHEMA_ROOT + inputFile);
		try {
			importer.readSchema();
			importer.ensureSchema();
		} catch (AbstractSystemException ex) {
			info(getLogger(),errMsg,ex);
		}
		failure("[T-ERROR] " + errMsg);
	}

	/** **/
	protected void executeSync(final String identifier) {
		try {
			// 如果存在旧的先删除
			GenericSchema dbSchema = this.service.getById(identifier);
			if(null != dbSchema){
				this.service.deleteById(dbSchema.getIdentifier());
			}
			// 1.读取Schema信息
			importer.readSchema();
			// 2.验证Schema文件
			importer.ensureSchema();
			// 3.转换Schema
			final GenericSchema schema = this.importer.transformSchema();
			// 4.同步数据
			dbSchema = this.service.getById(identifier);
			if(null == dbSchema){
				this.importer.syncSchema(schema);
			}
			info(getLogger(),"[T] =======================> Prepare Data Finished! ");
		} catch (DataLoadingException ex) {
			info(getLogger(), "4.Data Loading Exception. Loading Data...", ex);
		} catch (SerializationException ex) {
			info(getLogger(), "3.Serialization Exception. ", ex);
		} catch (AbstractSystemException ex) {
			info(getLogger(), "1.Reading json schema file.", ex);
		} catch (AbstractSchemaException ex) {
			info(getLogger(), "2.Error when verifying json schema.", ex);
		}
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
