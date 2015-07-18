package com.prayer.metadata.builder;

import static com.prayer.util.Instance.reservoir;
import static com.prayer.util.Instance.singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.db.conn.JdbcContext;
import com.prayer.db.conn.impl.JdbcConnImpl;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Builder;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.meta.FieldModel;
import com.prayer.model.meta.KeyModel;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.MinSize;
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
abstract class AbstractMetaBuilder implements Builder { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final ConcurrentMap<String,JdbcContext> JDBC_POOL = new ConcurrentHashMap<>();
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
	/** Metadata对象 **/
	@NotNull
	private transient final BuilderMetaData metadata;
	/** 构建过程中的Error信息 **/
	private transient AbstractDatabaseException error;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param schema
	 */
	@PostValidateThis
	public AbstractMetaBuilder(@NotNull final GenericSchema schema) {
		this.context = reservoir(JDBC_POOL,schema.getIdentifier(),JdbcConnImpl.class);
		this.sqlLines = new ArrayList<>();
		this.metadata = singleton(BuilderMetaData.class,schema);
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
	protected String genDropConstraints(@NotNull @NotEmpty @NotBlank final String name) {
		return SqlStatement.dropCSSql(this.metadata.getTable(), name);
	}

	/**
	 * 生成删除列的语句：类似：{ ALTER TABLE TABLE_NAME DROP COLUMN C_NAME }
	 * 
	 * @param column
	 * @return
	 */
	protected String genDropColumns(@NotNull @NotEmpty @NotBlank final String column) {
		return SqlStatement.dropColSql(this.metadata.getTable(), column);
	}

	/**
	 * 生成添加列的语句：ALTER TABLE TABLE_NAME ADD [COLUMN LINE]
	 * 
	 * @param field
	 * @return
	 */
	protected String genAddColumns(@NotNull final FieldModel field) {
		return SqlStatement.addColSql(this.metadata.getTable(), this.genColumnLine(field));
	}

	/**
	 * 生成修改列的语句：ALTER TABLE TABLE_NAME ALTER COLUMN [COLUMN LINE]
	 * 
	 * @param field
	 * @return
	 */
	protected String genAlterColumns(@NotNull final FieldModel field) {
		return SqlStatement.alterColSql(this.metadata.getTable(), this.genColumnLine(field));
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
		return SqlStatement.newFKSql(this.metadata.getForeignKey(), this.metadata.getForeignField());
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
			sql = SqlStatement.addCSSql(this.metadata.getTable(), SqlStatement.newFKSql(key, field));
		} else {
			sql = SqlStatement.addCSSql(this.metadata.getTable(), this.genKeyLine(key));
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
		return this.getContext().count(SqlStatement.genCountRowsSql(this.metadata.getTable()));
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
		Collection<String> exchangeSet = new HashSet<>();
		// ADD：新集合减去旧的集合
		exchangeSet.clear();
		exchangeSet.addAll(newCols);
		exchangeSet.removeAll(oldCols);
		statusMap.put(StatusFlag.ADD, exchangeSet);
		// DELET：旧集合减去新的集合
		exchangeSet = new HashSet<>();
		exchangeSet.clear();
		exchangeSet.addAll(oldCols);
		exchangeSet.removeAll(newCols);
		statusMap.put(StatusFlag.DELETE, exchangeSet);
		// UPDATE：两个集合的交集
		exchangeSet = new HashSet<>();
		exchangeSet.clear();
		exchangeSet.addAll(oldCols);
		exchangeSet.retainAll(newCols);
		statusMap.put(StatusFlag.UPDATE, exchangeSet);
		return statusMap;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	/**
	 * 
	 * @return
	 */
	@NotNull
	protected BuilderMetaData getMetadata(){
		return metadata;
	}
	/**
	 * @return the context
	 */
	@NotNull
	protected JdbcContext getContext() {
		return context;
	}

	/**
	 * @return the sqlLines
	 */
	protected List<String> getSqlLines() {
		return sqlLines;
	}

	/**
	 * 
	 */
	@Override
	public AbstractDatabaseException getError() {
		return this.error;
	}
	/**
	 * 
	 * @param error
	 */
	protected void setError(final AbstractDatabaseException error){
		this.error = error;
	}

	// ~ hashCode,equals,toString ============================
}
