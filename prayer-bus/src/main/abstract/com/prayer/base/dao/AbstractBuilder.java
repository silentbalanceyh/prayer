package com.prayer.base.dao; // NOPMD

import static com.prayer.util.Calculator.diff;
import static com.prayer.util.Calculator.intersect;
import static com.prayer.util.reflection.Instance.reservoir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.Constants;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.constant.SystemEnum.StatusFlag;
import com.prayer.facade.dao.Builder;
import com.prayer.facade.dao.JdbcConnection;
import com.prayer.model.database.PEField;
import com.prayer.model.database.PEKey;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.pool.impl.jdbc.RecordConnImpl;
import com.prayer.util.jdbc.SqlDDL;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.AssertFieldConstraints;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
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
public abstract class AbstractBuilder implements Builder { // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    @NotNull
    @InstanceOf(JdbcConnection.class)
    private transient final JdbcConnection context; // NOPMD
    /** 创建表的Sql语句 **/
    @NotNull
    private transient final List<String> sqlLines;
    /** Metadata对象 **/
    @NotNull
    @InstanceOfAny(GenericSchema.class)
    private transient final GenericSchema schema; // NOPMD
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
    public AbstractBuilder(@AssertFieldConstraints("schema") final GenericSchema schema) {
        this.context = reservoir(MemoryPool.POOL_JDBC, schema.getIdentifier(), RecordConnImpl.class);
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

    /**
     * 获取值为Unique的执行列的数量
     * 
     * @param column
     * @return
     */
    protected abstract Long uniqueRows(String column);

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 生成删除约束的语句：类似：{ ALTER TABLE TABLE_NAME DROP CONSTRAINT C_NAME }
     * 
     * @return
     */
    protected String genDropConstraints(@NotNull @NotEmpty @NotBlank final String name) {
        return SqlDDL.dropCSSql(this.getTable(), name);
    }

    /**
     * 生成删除列的语句：类似：{ ALTER TABLE TABLE_NAME DROP COLUMN C_NAME }
     * 
     * @param column
     * @return
     */
    protected String genDropColumns(@NotNull @NotEmpty @NotBlank final String column) {
        return SqlDDL.dropColSql(this.getTable(), column);
    }

    /**
     * 生成添加列的语句：ALTER TABLE TABLE_NAME ADD [COLUMN LINE]
     * 
     * @param field
     * @return
     */
    protected String genAddColumns(@NotNull @InstanceOfAny(PEField.class) final PEField field) {
        return SqlDDL.addColSql(this.getTable(), this.genColumnLine(field));
    }

    /**
     * 生成修改列的语句：ALTER TABLE TABLE_NAME ALTER COLUMN [COLUMN LINE]
     * 
     * @param field
     * @return
     */
    protected String genAlterColumns(@NotNull @InstanceOfAny(PEField.class) final PEField field) {
        return SqlDDL.alterColSql(this.getTable(), this.genColumnLine(field));
    }

    /**
     * 生成Foreign Key的行，类似：{ CONSTRAINT FK_NAME FOREIGN KEY (COLUMN) REFERENCES
     * REF_TABLE(REF_ID) ON DELETE SET NULL ON UPDATE SET NULL}
     * 
     * @param foreignKey
     * @param foreignField
     * @return
     */
    protected List<String> genForeignKeys() {
        final List<PEKey> fkeys = this.schema.getForeignKey();
        final List<PEField> ffields = this.schema.getForeignField();
        final List<String> fkeyLines = new ArrayList<>();
        for (int idx = 0; idx < fkeys.size(); idx++) {
            fkeyLines.add(SqlDDL.newFKSql(fkeys.get(idx), ffields.get(idx)));
        }
        return fkeyLines;
    }

    /**
     * 生成Unique/Primary Key，类似：{ CONSTRAINT UK_NAME UNIQUE (COLUMN) }
     * 
     * @param key
     * @return
     */
    protected String genKeyLine(@NotNull @InstanceOfAny(PEKey.class) final PEKey key) {
        String sql = null;
        if (KeyCategory.UniqueKey == key.getCategory()) {
            sql = SqlDDL.newUKSql(key);
        } else if (KeyCategory.PrimaryKey == key.getCategory()) {
            sql = SqlDDL.newPKSql(key);
        }
        return sql;
    }

    /**
     * 生成约束
     * 
     * @param key
     * @return
     */
    protected String genAddCsLine(@NotNull @InstanceOfAny(PEKey.class) final PEKey key,
            @InstanceOfAny(PEField.class) final PEField field) {
        String sql = null;
        if (KeyCategory.ForeignKey == key.getCategory()) {
            sql = SqlDDL.addCSSql(this.getTable(), SqlDDL.newFKSql(key, field));
        } else {
            sql = SqlDDL.addCSSql(this.getTable(), this.genKeyLine(key));
        }
        return sql;
    }

    /**
     * 生成普通行SQL不带末尾逗号，类似：{ NAME VARCHAR(256) NOT NULL }
     * 
     * @param field
     * @return
     */
    protected String genColumnLine(@NotNull @InstanceOfAny(PEField.class) final PEField field) {
        return SqlDDL.newColumnSql(lengthTypes(), precisionTypes(), field);
    }

    /**
     * 
     * 
     * @param field
     * @return
     */
    protected String getColType(@NotNull @InstanceOfAny(PEField.class) final PEField field) {
        return SqlDDL.getColType(field);
    }

    /**
     * 统计表中有多少数据
     * 
     * @return
     */
    protected Long getRows() {
        return this.getContext().count(SqlDDL.genCountRowsSql(this.getTable()));
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
    protected ConcurrentMap<StatusFlag, Collection<String>> getColumnStatus(final Collection<String> oldCols,
            final Collection<String> newCols) {
        final ConcurrentMap<StatusFlag, Collection<String>> statusMap = new ConcurrentHashMap<>();
        // ADD：新集合减去旧的集合
        statusMap.put(StatusFlag.ADD, diff(newCols, oldCols));
        // DELET：旧集合减去新的集合
        statusMap.put(StatusFlag.DELETE, diff(oldCols, newCols));
        // UPDATE：两个集合的交集
        statusMap.put(StatusFlag.UPDATE, intersect(oldCols, newCols));
        return statusMap;
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    /**
     * 
     * @return
     */
    @Pre(expr = "_this.schema != null", lang = Constants.LANG_GROOVY)
    protected String getTable() {
        String table = null;
        if (null != this.schema.getMeta()) {
            table = this.schema.getMeta().getTable();
        }
        return table;
    }

    /**
     * @return the context
     */
    @NotNull
    protected JdbcConnection getContext() {
        return context;
    }

    /**
     * @return the schema
     */
    @NotNull
    protected GenericSchema getSchema() {
        return schema;
    }

    /**
     * @return the sqlLines
     */
    @NotNull
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
    protected void setError(final AbstractDatabaseException error) {
        this.error = error;
    }

    // ~ hashCode,equals,toString ============================
}
