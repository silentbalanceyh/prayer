package com.prayer.meta.builder;

import static com.prayer.util.Instance.singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.prayer.db.conn.JdbcContext;
import com.prayer.db.conn.impl.JdbcConnImpl;
import com.prayer.meta.Builder;
import com.prayer.mod.meta.FieldModel;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.mod.meta.KeyModel;
import com.prayer.mod.meta.MetaModel;
import com.prayer.mod.meta.SystemEnum.KeyCategory;

import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
abstract class AbstractMetaBuilder implements Builder {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** 数据库连接 **/
	@NotNull
	private transient final JdbcContext context;
	/** 创建表的Sql语句 **/
	@NotNull
	private transient final List<String> sqlLines;
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
	private transient final List<FieldModel> primaryKeys;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param schema
	 */
	@PostValidateThis
	public AbstractMetaBuilder(@NotNull final GenericSchema schema) {
		this.schema = schema;
		this.context = singleton(JdbcConnImpl.class);
		this.sqlLines = new ArrayList<>();
		this.primaryKeys = new ArrayList<>();
		if (null != schema.getMeta()) {
			this.initialize(schema);
		}
	}

	// ~ Abstract Methods ====================================
	/**
	 * 支持length属性的类型
	 * 
	 * @return
	 */
	protected abstract String[] lengthTypes();

	/**
	 * 支持decimal属性的类型
	 * 
	 * @return
	 */
	protected abstract String[] precisionTypes();

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 生成Foreign Key的行，类似：{ CONSTRAINT FK_NAME FOREIGN KEY (COLUMN) REFERENCES
	 * REF_TABLE(REF_ID) }
	 * 
	 * @param foreignKey
	 * @param foreignField
	 * @return
	 */
	protected String genForeignKey() {
		return SqlStatement.newFKSql(foreignKey, foreignField);
	}

	/**
	 * 生成Unique/Primary Key，类似：{ CONSTRAINT UK_NAME UNIQUE (COLUMN) }
	 * 
	 * @param key
	 * @return
	 */
	protected String genKeyLine(@NotNull final KeyModel key) {
		String sql = null;
		if (KeyCategory.UniqueKey == key.getCategory()) {
			sql = SqlStatement.newUKSql(key);
		} else if (KeyCategory.PrimaryKey == key.getCategory()) {
			sql = SqlStatement.newPKSql(key);
		}
		return sql;
	}

	/**
	 * 生成普通行SQL不带末尾逗号，类似：{ NAME VARCHAR(256) NOT NULL }
	 * 
	 * @param field
	 * @return
	 */
	protected String genColumnLine(@NotNull final FieldModel field) {
		return SqlStatement.newColumnSql(lengthTypes(), precisionTypes(), field);
	}
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
	protected String getTable() {
		return table;
	}

	/**
	 * @return the foreignField
	 */
	protected FieldModel getForeignField() {
		return foreignField;
	}

	/**
	 * @return the foreignKey
	 */
	protected KeyModel getForeignKey() {
		return foreignKey;
	}

	/**
	 * @return the keys
	 */
	protected Collection<KeyModel> getKeys() {
		return keys;
	}

	/**
	 * @return the fields
	 */
	protected Collection<FieldModel> getFields() {
		return fields;
	}

	/**
	 * @return the primaryKeys
	 */
	public List<FieldModel> getPrimaryKeys() {
		return primaryKeys;
	}

	/**
	 * @return the context
	 */
	public JdbcContext getContext() {
		return context;
	}

	/**
	 * @return the sqlLines
	 */
	public List<String> getSqlLines() {
		return sqlLines;
	}

	/**
	 * @return the schema
	 */
	public GenericSchema getSchema() {
		return schema;
	}

	// ~ hashCode,equals,toString ============================
}
