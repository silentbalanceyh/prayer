package com.prayer.meta.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.prayer.model.SystemEnum.KeyCategory;
import com.prayer.model.meta.FieldModel;
import com.prayer.model.meta.GenericSchema;
import com.prayer.model.meta.KeyModel;
import com.prayer.model.meta.MetaModel;

import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */

@Guarded
final class BuilderMetaData {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** 传入Schema的引用，从H2中读取的元数据信息 **/
	@NotNull
	private transient final GenericSchema schema;
	/** 从H2中读取到的元数据信息 **/
	@NotNull
	private transient String table;

	/** 如果有外键列则该值属于外键 **/
	private transient FieldModel foreignField;
	/** 带外键名的值 **/
	private transient KeyModel foreignKey;
	/** 当前Model中所有键值 **/
	@MinSize(1)
	private transient Collection<KeyModel> keys;
	/** 当前Model中所有字段 **/
	@MinSize(1)
	private transient Collection<FieldModel> fields;
	/** 当前Model中的主键 **/
	@MinSize(1)
	private transient List<FieldModel> primaryKeys;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** 构造函数 **/
	public BuilderMetaData(@NotNull final GenericSchema schema) {
		this.schema = schema;
		if (null != schema.getMeta()) {
			this.initialize(schema);
		}
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private void initialize(final GenericSchema schema) {
		if (null != schema.getMeta()) {
			// 1.获取Metadata
			final MetaModel meta = schema.getMeta();
			this.table = meta.getTable();
			// 2.获取外键KeyModel
			this.keys = schema.getKeys().values();
			for (final KeyModel key : this.keys) {
				if (KeyCategory.ForeignKey == key.getCategory()) {
					this.foreignKey = key;
				}
			}
			// 3.根据外键列读取对应的Field配置，并且获取主键规范
			this.fields = schema.getFields().values();
			if (null == this.primaryKeys) {
				this.primaryKeys = new ArrayList<>();
			}
			for (final FieldModel field : this.fields) {
				if (field.isForeignKey()) {
					this.foreignField = field;
				} else if (field.isPrimaryKey()) {
					this.primaryKeys.add(field);
				}
			}
		}
	}

	// ~ Get/Set =============================================
	/**
	 * @return the table
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @return the foreignField
	 */
	public FieldModel getForeignField() {
		return foreignField;
	}

	/**
	 * @return the foreignKey
	 */
	public KeyModel getForeignKey() {
		return foreignKey;
	}

	/**
	 * @return the keys
	 */
	public Collection<KeyModel> getKeys() {
		return keys;
	}

	/**
	 * @return the fields
	 */
	public Collection<FieldModel> getFields() {
		return fields;
	}

	/**
	 * @return the primaryKeys
	 */
	public List<FieldModel> getPrimaryKeys() {
		return primaryKeys;
	}

	/**
	 * @return the schema
	 */
	public GenericSchema getSchema() {
		return schema;
	}
	// ~ hashCode,equals,toString ============================
}
