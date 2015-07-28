package com.prayer.kernel.builder;

import static com.prayer.util.Calculator.diff;
import static com.prayer.util.Calculator.intersect;
import static com.prayer.util.Instance.reservoir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.constant.SystemEnum.StatusFlag;
import com.prayer.db.conn.JdbcContext;
import com.prayer.db.conn.impl.JdbcConnImpl;
import com.prayer.exception.AbstractMetadataException;
import com.prayer.kernel.Builder;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.h2.FieldModel;
import com.prayer.model.h2.KeyModel;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.MinSize;
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
abstract class AbstractBuilder implements Builder { // NOPMD
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** 数据库连接 **/
	@NotNull
	private transient final JdbcContext context;
	/** 创建表的Sql语句 **/
	@NotNull
	private transient final List<String> sqlLines;
	/** Metadata对象 **/
	@NotNull
	private transient final GenericSchema schema;
	/** 构建过程中的Error信息 **/
	private transient AbstractMetadataException error;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param schema
	 */
	@PostValidateThis
	public AbstractBuilder(@NotNull final GenericSchema schema) {
		this.context = reservoir(MemoryPool.POOL_JDBC,schema.getIdentifier(),JdbcConnImpl.class);
		this.sqlLines = new ArrayList<>();
		this.schema = schema;
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
		return SqlDdlStatement.dropCSSql(this.getTable(), name);
	}

	/**
	 * 生成删除列的语句：类似：{ ALTER TABLE TABLE_NAME DROP COLUMN C_NAME }
	 * 
	 * @param column
	 * @return
	 */
	protected String genDropColumns(@NotNull @NotEmpty @NotBlank final String column) {
		return SqlDdlStatement.dropColSql(this.getTable(), column);
	}

	/**
	 * 生成添加列的语句：ALTER TABLE TABLE_NAME ADD [COLUMN LINE]
	 * 
	 * @param field
	 * @return
	 */
	protected String genAddColumns(@NotNull final FieldModel field) {
		return SqlDdlStatement.addColSql(this.getTable(), this.genColumnLine(field));
	}

	/**
	 * 生成修改列的语句：ALTER TABLE TABLE_NAME ALTER COLUMN [COLUMN LINE]
	 * 
	 * @param field
	 * @return
	 */
	protected String genAlterColumns(@NotNull final FieldModel field) {
		return SqlDdlStatement.alterColSql(this.getTable(), this.genColumnLine(field));
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
		return SqlDdlStatement.newFKSql(this.schema.getForeignKey(), this.schema.getForeignField());
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
			sql = SqlDdlStatement.newUKSql(key);
		} else if (KeyCategory.PrimaryKey == key.getCategory()) {
			sql = SqlDdlStatement.newPKSql(key);
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
			sql = SqlDdlStatement.addCSSql(this.getTable(), SqlDdlStatement.newFKSql(key, field));
		} else {
			sql = SqlDdlStatement.addCSSql(this.getTable(), this.genKeyLine(key));
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
		return SqlDdlStatement.newColumnSql(lengthTypes(), precisionTypes(), field);
	}

	/**
	 * 统计表中有多少数据
	 * 
	 * @return
	 */
	protected Long getRows() {
		return this.getContext().count(SqlDdlStatement.genCountRowsSql(this.getTable()));
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
			@MinSize(0) final Collection<String> oldCols, @MinSize(0) final Collection<String> newCols) {
		final ConcurrentMap<StatusFlag, Collection<String>> statusMap = new ConcurrentHashMap<>();
		// ADD：新集合减去旧的集合
		statusMap.put(StatusFlag.ADD, diff(newCols,oldCols));
		// DELET：旧集合减去新的集合
		statusMap.put(StatusFlag.DELETE, diff(oldCols,newCols));
		// UPDATE：两个集合的交集
		statusMap.put(StatusFlag.UPDATE, intersect(oldCols,newCols));
		return statusMap;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	/**
	 * 
	 * @return
	 */
	@Pre(expr = "_this.schema != null",lang = Constants.LANG_GROOVY)
	protected String getTable(){
		String table = null;
		if(null != this.schema.getMeta()){
			table = this.schema.getMeta().getTable();
		}
		return table;
	}
	/**
	 * @return the context
	 */
	@NotNull
	protected JdbcContext getContext() {
		return context;
	}
	/**
	 * @return the schema
	 */
	@NotNull
	protected GenericSchema getSchema(){
		return schema;
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
	public AbstractMetadataException getError() {
		return this.error;
	}
	/**
	 * 
	 * @param error
	 */
	protected void setError(final AbstractMetadataException error){
		this.error = error;
	}

	// ~ hashCode,equals,toString ============================
}
