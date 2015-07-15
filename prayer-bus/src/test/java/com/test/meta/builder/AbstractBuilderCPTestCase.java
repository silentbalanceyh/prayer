package com.test.meta.builder;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;

import com.prayer.bus.schema.SchemaService;
import com.prayer.bus.schema.impl.SchemaSevImpl;
import com.prayer.constant.Resources;
import com.prayer.db.conn.JdbcContext;
import com.prayer.db.conn.impl.JdbcConnImpl;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.DataLoadingException;
import com.prayer.exception.system.SerializationException;
import com.prayer.meta.Builder;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.schema.Importer;
import com.prayer.schema.json.internal.CommunionImporter;
import com.test.AbstractTestCase;

import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractBuilderCPTestCase extends AbstractTestCase{ // NOPMD
	// ~ Static Fields =======================================
	/** **/
	protected static final String BUILDER_FILE = "/schema/data/json/database/";
	// ~ Instance Fields =====================================
	/** **/
	private transient final SchemaService service;
	/** **/
	private transient final JdbcContext context;
	/** **/
	protected transient Builder builder;
	/** **/
	protected transient Importer importer;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 */
	public AbstractBuilderCPTestCase() {
		super();
		this.service = singleton(SchemaSevImpl.class);
		this.context = singleton(JdbcConnImpl.class);
	}

	// ~ Abstract Methods ====================================
	/** **/
	protected abstract Logger getLogger();

	/** **/
	protected abstract String getDbCategory();

	/** **/
	protected abstract Class<?> getBuilder();

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	protected SchemaService getService() {
		return this.service;
	}

	/** **/
	protected JdbcContext getContext() {
		return this.context;
	}

	/** **/
	protected boolean isValidDB() {
		return StringUtil.equals(getDbCategory(), Resources.DB_CATEGORY);
	}

	/** **/
	protected void beforeExecute(final String inputFile, final String globalId) {
		if (this.isValidDB()) {
			final GenericSchema schema = this.prepData(inputFile, globalId);
			if (null == schema) {
				this.executeNotMatch();
			} else {
				this.builder = instance(getBuilder().getName(), schema);
			}
		}
	}
	/** **/
	protected void afterExecute(){
		if(this.isValidDB() && null != this.builder){
			if(!this.builder.existTable()){
				this.builder.createTable();
			}
		}else{
			this.executeNotMatch();
		}
	}
	/** **/
	protected boolean createTable(){
		boolean ret = false;
		if(this.isValidDB() && null != this.builder){
			boolean exist = this.builder.existTable();
			if(exist){
				this.builder.purgeTable();
			}
			exist = this.builder.existTable();
			if(!exist){
				ret = this.builder.createTable();
			}
		}else{
			this.executeNotMatch();
		}
		return ret;
	}
	/** **/
	protected boolean purgeTable(){
		boolean ret = false;
		if(this.isValidDB() && null != this.builder){
			boolean exist = this.builder.existTable();
			if(!exist){
				this.builder.createTable();
			}
			exist = this.builder.existTable();
			if(exist){
				ret = this.builder.purgeTable();
			}
		}else{
			this.executeNotMatch();
		}
		return ret;
	}
	// ~ Private Methods =====================================

	/** **/
	private GenericSchema prepData(final String inputFile, final String globalId) {
		this.importer = new CommunionImporter(BUILDER_FILE + inputFile);
		GenericSchema schema = null;
		try {
			this.importer.importFile();
			this.importer.ensureSchema();
			schema = this.importer.transformModel();
			final GenericSchema prepSchema = this.service.findModel(globalId);
			if (null == prepSchema) {
				this.importer.loadData(schema);
			}
		} catch (SerializationException ex) {
			info(getLogger(), ex.getMessage(), ex);
		} catch (DataLoadingException ex) {
			info(getLogger(), ex.getMessage(), ex);
		} catch (AbstractSystemException ex) {
			info(getLogger(), ex.getMessage(), ex);
		} catch (AbstractSchemaException ex) {
			info(getLogger(), ex.getMessage(), ex);
		}
		return schema;
	}

	private void executeNotMatch() {
		info(getLogger(), "[T] Database not match ! Expected: " + getDbCategory() + ", Actual: " + Resources.DB_CATEGORY
				+ " Or Builder is Null: " + (this.builder == null));
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
