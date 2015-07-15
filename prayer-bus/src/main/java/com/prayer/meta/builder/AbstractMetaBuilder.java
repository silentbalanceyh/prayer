package com.prayer.meta.builder;

import static com.prayer.util.Instance.singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.db.conn.JdbcContext;
import com.prayer.db.conn.impl.JdbcConnImpl;
import com.prayer.meta.Builder;
import com.prayer.mod.meta.FieldModel;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.mod.meta.KeyModel;
import com.prayer.mod.meta.MetaModel;
import com.prayer.mod.meta.SystemEnum.KeyCategory;
import com.prayer.util.StringKit;

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
abstract class AbstractMetaBuilder implements Builder { // NOPMD
	// ~ Static Fields =======================================
	/** 成功的String字面量 **/
	protected static final String SUCCESS = "SUCCESS";
	/** 失败的String字面量 **/
	protected static final String FAILURE = "FAILURE";

	/** **/
	protected static enum StatusFlag {
		UPDATE, ADD, DELETE
	}

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

	/**
	 * 获取值为Null的指定列
	 * 
	 * @param column
	 * @return
	 */
	protected abstract Long nullRows(String column);

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 生成删除约束的语句：类似：{ ALTER TABLE TABLE_NAME DROP CONSTRAINT C_NAME }
	 * 
	 * @return
	 */
	protected String genDropConstraints(@NotNull final String name) {
		return SqlStatement.dropCSSql(this.getTable(), name);
	}

	/**
	 * 生成删除列的语句：类似：{ ALTER TABLE TABLE_NAME DROP COLUMN C_NAME }
	 * 
	 * @param column
	 * @return
	 */
	protected String genDropColumns(@NotNull final String column) {
		return SqlStatement.dropColSql(this.getTable(), column);
	}

	/**
	 * 生成添加列的语句：ALTER TABLE TABLE_NAME ADD [COLUMN LINE]
	 * 
	 * @param field
	 * @return
	 */
	protected String genAddColumns(@NotNull final FieldModel field) {
		return SqlStatement.addColSql(this.getTable(), this.genColumnLine(field));
	}

	/**
	 * 生成修改列的语句：ALTER TABLE TABLE_NAME ALTER COLUMN [COLUMN LINE]
	 * 
	 * @param field
	 * @return
	 */
	protected String genAlterColumns(@NotNull final FieldModel field) {
		return SqlStatement.alterColSql(this.getTable(), this.genColumnLine(field));
	}

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
	 * 生成约束
	 * 
	 * @param key
	 * @return
	 */
	protected String genAddCsLine(@NotNull final KeyModel key, final FieldModel field) {
		String sql = null;
		if (KeyCategory.ForeignKey == key.getCategory()) {
			sql = SqlStatement.addCSSql(this.getTable(), this.genKeyLine(key));
		} else {
			sql = SqlStatement.addCSSql(this.getTable(), SqlStatement.newFKSql(key, field));
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

	/**
	 * 统计表中有多少数据
	 * 
	 * @return
	 */
	protected Long getRows() {
		return this.getContext().count(SqlStatement.genCountRowsSql(this.getTable()));
	}

	/**
	 * 过滤空行SQL
	 * 
	 * @param line
	 */
	protected void addSqlLine(final String line) {
		if (StringKit.isNonNil(line)) {
			this.getSqlLines().add(line);
		}
	}

	/**
	 * 计算UPDATE，ADD, DELETE的三个列集合
	 * 
	 * @param oldCols
	 * @param newCols
	 * @return
	 */
	protected ConcurrentMap<StatusFlag, Collection<String>> getColumnStatus(
			@MinSize(1) final Collection<String> oldCols, @MinSize(1) final Collection<String> newCols) {
		final ConcurrentMap<StatusFlag, Collection<String>> statusMap = new ConcurrentHashMap<>();
		final Collection<String> exchangeSet = new HashSet<>();
		// ADD：新集合减去旧的集合
		exchangeSet.clear();
		exchangeSet.addAll(newCols);
		exchangeSet.removeAll(oldCols);
		statusMap.put(StatusFlag.ADD, exchangeSet);
		// DELET：旧集合减去新的集合
		exchangeSet.clear();
		exchangeSet.addAll(oldCols);
		exchangeSet.removeAll(newCols);
		statusMap.put(StatusFlag.DELETE, exchangeSet);
		// UPDATE：两个集合的交集
		exchangeSet.clear();
		exchangeSet.addAll(oldCols);
		exchangeSet.retainAll(newCols);
		statusMap.put(StatusFlag.UPDATE, exchangeSet);
		return statusMap;
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
