package com.prayer.kernel.model;

import static com.prayer.util.Error.debug;
import static com.prayer.util.Instance.instance;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.database.FieldInvalidException;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.model.type.DataType;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class GenericRecord implements Record {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericRecord.class);
	// ~ Instance Fields =====================================
	/** 全局标识符 **/
	@NotNull
	private transient final String _identifier;
	/** 和当前Record绑定的Schema引用 **/
	@NotNull
	private transient GenericSchema _schema; // NOPMD
	/** 当前Record中的数据 **/
	private transient final ConcurrentMap<String, Value<?>> data;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param _identifier
	 */
	@PostValidateThis
	public GenericRecord(@NotNull @NotBlank @NotEmpty final String identifier) {
		this._identifier = identifier;
		try {
			this._schema = SchemaLocator.getSchema(identifier);
		} catch (AbstractSystemException ex) {
			this._schema = null; // NOPMD
			debug(LOGGER, getClass(), "D20006", ex, identifier);
		}
		this.data = new ConcurrentHashMap<>();
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@Pre(expr = "_this.data != null", lang = Constants.LANG_GROOVY)
	public void set(@NotNull @NotBlank @NotEmpty final String name, final Value<?> value)
			throws AbstractDatabaseException {
		this.data.put(name, value);
	}

	/** **/
	@Override
	@Pre(expr = "_this._schema != null", lang = Constants.LANG_GROOVY)
	public void set(@NotNull @NotBlank @NotEmpty final String name, final String value)
			throws AbstractDatabaseException {
		this.verifyField(name);
		final DataType type = this._schema.getFields().get(name).getType();
		final Value<?> wrapperValue = instance(DataType.toClass(type), value);
		this.set(name, wrapperValue);
	}

	/** **/
	@Override
	public Value<?> get(@NotNull @NotBlank @NotEmpty final String name) throws AbstractDatabaseException {
		this.verifyField(name);
		return this.data.get(name);
	}

	/**
	 * 获取全局标识符
	 */
	@Override
	@NotNull
	public String identifier() {
		return this._identifier;
	}

	/** 获取数据库列集 **/
	@Override
	@Pre(expr = "_this._schema != null", lang = Constants.LANG_GROOVY)
	public Set<String> columns() {
		return this._schema.getColumns();
	}

	/** 获取当前Record的Schema定义 **/
	@Override
	@NotNull
	public GenericSchema schema() {
		return this._schema;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private void verifyField(final String name) throws AbstractDatabaseException {
		if (!this._schema.getFields().containsKey(name)) {
			throw new FieldInvalidException(getClass(), name, this._schema.getIdentifier());
		}
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
