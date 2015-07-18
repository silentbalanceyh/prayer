package com.prayer.model.record;

import static com.prayer.util.Instance.instance;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.metadata.Record;
import com.prayer.metadata.Value;
import com.prayer.model.meta.GenericSchema;
import com.prayer.model.type.DataType;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class GenericRecord implements Record {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** 全局标识符 **/
	@NotNull
	private transient final String _identifier;
	/** 和当前Record绑定的Schema引用 **/
	@NotNull
	private transient final GenericSchema _schema;
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
		this._schema = SchemaLocator.getSchema(identifier);
		this.data = new ConcurrentHashMap<>();
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public void set(final String name, final Value<?> value) {
		this.data.put(name, value);
	}

	/** **/
	@Override
	public void set(final String name, final String value) {
		final DataType type = this._schema.getFields().get(name).getType();
		final Value<?> wrapperValue = instance(DataType.toClass(type), value);
		this.set(name, wrapperValue);
	}

	/** **/
	@Override
	public Value<?> get(final String name) {
		return this.data.get(name);
	}

	/**
	 * 获取全局标识符
	 */
	@Override
	public String identifier() {
		return this._identifier;
	}

	/** 获取属性集 **/
	@Override
	public Set<String> columns() {
		return this._schema.getColumns();
	}
	/** 获取当前Record的Schema定义 **/
	@Override
	public GenericSchema schema(){
		return this._schema;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
