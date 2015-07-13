package com.prayer.meta.builder;

import static com.prayer.util.Instance.singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.db.conn.JdbcContext;
import com.prayer.db.conn.impl.JdbcConnImpl;
import com.prayer.meta.Builder;
import com.prayer.mod.meta.FieldModel;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.mod.meta.KeyModel;
import com.prayer.mod.meta.MetaModel;
import com.prayer.mod.meta.SystemEnum.KeyCategory;
import com.prayer.util.PropertyKit;
import com.prayer.util.StringKit;

import jodd.util.ArraysUtil;
import jodd.util.StringUtil;
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
public abstract class AbstractMetaBuilder implements SqlSegment, Builder {
	// ~ Static Fields =======================================
	/** 数据库类型映射 **/
	protected static final ConcurrentMap<String, String> DB_TYPES = new ConcurrentHashMap<>();

	// ~ Instance Fields =====================================
	/** 数据库连接 **/
	@NotNull
	private transient final JdbcContext context;
	/** 创建表的Sql语句 **/
	@NotNull
	private transient final List<String> sqlLines;
	/** 传入Schema的引用 **/
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
	/** 初始化数据类型映射表，直接根据Database填充 **/
	static {
		final PropertyKit loader = new PropertyKit(AbstractMetaBuilder.class, Resources.DB_TYPES_FILE);
		final Properties prop = loader.getProp();
		for (final Object key : prop.keySet()) {
			if (null != key && StringKit.isNonNil(key.toString())) {
				final String keyStr = key.toString();
				final String[] keys = keyStr.split("\\.");
				if (Constants.TWO == keys.length && StringUtil.equals(keys[0], Resources.DB_CATEGORY)
						&& StringKit.isNonNil(keys[1])) {
					DB_TYPES.put(keys[1], prop.getProperty(keyStr));
				}
			}
		}
	}

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
		// 1.初始化缓冲区
		final StringBuilder sql = new StringBuilder();
		// 2.添加外键约束
		if (null != foreignKey && null != foreignField && KeyCategory.ForeignKey == foreignKey.getCategory()
				&& foreignField.isForeignKey()) {
			sql.append(CONSTRAINT).append(SPACE).append(foreignKey.getName()).append(SPACE).append(FOREIGN)
					.append(SPACE).append(KEY).append(SPACE).append(BRACKET_SL)
					.append(StringKit.join(foreignKey.getColumns(),COMMA)).append(BRACKET_SR).append(SPACE)
					.append(REFERENCES).append(SPACE).append(foreignField.getRefTable()).append(BRACKET_SL)
					.append(foreignField.getRefId()).append(BRACKET_SR);
		}
		return sql.toString();
	}

	/**
	 * 生成Unique/Primary Key，类似：{ CONSTRAINT UK_NAME UNIQUE (COLUMN) }
	 * 
	 * @param key
	 * @return
	 */
	protected String genKeyLine(@NotNull final KeyModel key) {
		// 1.初始化缓冲区
		final StringBuilder sql = new StringBuilder();
		// 2.添加约束
		if (KeyCategory.UniqueKey == key.getCategory()) {
			// CONSTRAINT UK_NAME UNIQUE (COL1,COL2)
			sql.append(CONSTRAINT).append(SPACE).append(key.getName()).append(SPACE).append(UNIQUE).append(SPACE)
					.append(BRACKET_SL).append(StringKit.join(key.getColumns(),COMMA)).append(BRACKET_SR);
		} else if (KeyCategory.PrimaryKey == key.getCategory()) {
			// CONSTRAINT PK_NAME PRIMARY KEY (COL1,COL2)
			sql.append(CONSTRAINT).append(SPACE).append(key.getName()).append(SPACE).append(PRIMARY).append(SPACE)
					.append(KEY).append(SPACE).append(BRACKET_SL).append(StringKit.join(key.getColumns(),COMMA))
					.append(BRACKET_SR);
		}
		return sql.toString();
	}

	/**
	 * 生成普通行SQL不带末尾逗号，类似：{ NAME VARCHAR(256) NOT NULL }
	 * 
	 * @param field
	 * @return
	 */
	protected String genColumnLine(@NotNull final FieldModel field) {
		// 1.初始化缓冲区
		final StringBuilder sql = new StringBuilder();
		final String columnType = DB_TYPES.get(field.getColumnType());

		// 2.字段名、数据类型
		sql.append(field.getColumnName()).append(SPACE).append(columnType);
		// 3.包含了length属性的字段构建
		if (ArraysUtil.contains(lengthTypes(), columnType)) {
			sql.append(BRACKET_SL).append(field.getLength()).append(BRACKET_SR);
		} else if (ArraysUtil.contains(precisionTypes(), columnType)) {
			sql.append(BRACKET_SL).append(field.getLength()).append(COMMA).append(field.getPrecision())
					.append(BRACKET_SR);
		}
		// 4.中间空白字符
		sql.append(SPACE);
		// 5.字段是否为空的设置
		if (!field.isNullable()) {
			sql.append(NOT).append(SPACE).append(NULL);
		}
		return sql.toString();
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
