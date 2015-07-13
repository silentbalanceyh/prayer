package com.test.meta.builder;

import static com.prayer.util.Instance.singleton;

import com.prayer.bus.schema.SchemaService;
import com.prayer.bus.schema.impl.SchemaSevImpl;
import com.prayer.db.conn.JdbcContext;
import com.prayer.db.conn.impl.JdbcConnImpl;
import com.prayer.meta.Builder;
/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractBuilderTestCase {	// NOPMD
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	private transient final SchemaService service;
	/** **/
	private transient final JdbcContext context;
	/** **/
	protected transient Builder builder;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 */
	public AbstractBuilderTestCase(){
		this.service = singleton(SchemaSevImpl.class);
		this.context = singleton(JdbcConnImpl.class);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	protected SchemaService getService(){
		return this.service;
	}
	/** **/
	protected JdbcContext getContext(){
		return this.context;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
