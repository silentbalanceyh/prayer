package com.prayer.fantasm.builder;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.constant.log.DebugKey;
import com.prayer.database.pool.impl.jdbc.JdbcConnImpl;
import com.prayer.facade.builder.Builder;
import com.prayer.facade.builder.Refresher;
import com.prayer.facade.builder.line.FieldSaber;
import com.prayer.facade.builder.line.KeySaber;
import com.prayer.facade.fun.builder.AutoIncrement;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.facade.sql.SQLStatement;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.sql.util.SqlDDLBuilder;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 抽象层的Builder信息
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractBuilder implements Builder, SQLStatement {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    @NotNull
    @InstanceOf(JdbcConnection.class)
    private transient final JdbcConnection connection; // NOPMD
    /** Builder **/
    private transient final SqlDDLBuilder builder;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /** **/
    @PostValidateThis
    public AbstractBuilder() {
        this.connection = singleton(JdbcConnImpl.class);
        this.builder = SqlDDLBuilder.create();
    }

    // ~ Abstract Methods ====================================

    /** 判断当前表是否存在 **/
    public abstract DataValidator getValidator();

    /** 日志记录器 **/
    public abstract Logger getLogger();

    /** 字段处理器 **/
    public abstract FieldSaber getFieldSaber();

    /** 键处理器 **/
    public abstract KeySaber getKeySaber();

    /** 获取更新器 **/
    public abstract Refresher getRefresher();

    /** 自增长字段语句 **/
    public abstract String buildIncrement(Schema schema);

    // ~ Override Methods =====================================
    /** **/
    @Override
    public boolean synchronize(@NotNull final Schema schema) throws AbstractDatabaseException {
        final boolean exist = this.exist(schema.getTable());
        // 表是否存在，不存在则执行Create，存在则执行Alter
        String sql = null;
        debug(getLogger(), "[D] Table " + schema.getTable() + " existing status. Result: exist = " + exist);
        if (exist) {
            sql = this.getRefresher().buildAlterSQL(schema);
        } else {
            sql = this.prepCreateSql(schema);
        }
        debug(getLogger(), DebugKey.INFO_SQL_STMT, sql);
        // TODO: DEBUG
        final int respCode = this.connection.executeBatch(sql);
        return Constants.RC_SUCCESS == respCode;
    }

    /**
     * 删除表语句
     **/
    @Override
    public boolean purge(@NotNull final Schema schema) throws AbstractDatabaseException {
        return this.purge(schema.getTable());
    }

    /**
     * 删除表语句
     */
    @Override
    public boolean purge(@NotNull @NotBlank @NotEmpty final String table) throws AbstractDatabaseException {
        final boolean exist = this.exist(table);
        if (exist) {
            final String sql = builder.buildDropTable(table);
            debug(getLogger(), DebugKey.INFO_SQL_STMT, sql);
            final int respCode = this.connection.executeBatch(sql);
            return Constants.RC_SUCCESS == respCode;
        } else {
            return false;
        }
    }

    // ~ Methods =============================================
    /** 子类使用连接 **/
    protected JdbcConnection getConnection() {
        return this.connection;
    }
    // ~ Private Methods =====================================

    private boolean exist(final String table) {
        /** 验证表是否存在 **/
        AbstractSchemaException error = this.getValidator().verifyTable(table);
        /** 如果error为空则表不存在 **/
        return null == error;
    }

    /**
     * 创建表语句
     * 
     * @param schema
     * @return
     */
    private String prepCreateSql(final Schema schema) {
        final List<String> lines = new ArrayList<>();
        /** 1.主键构建定义 **/
        {
            this.buildPrimaryKey(lines, schema, this::buildIncrement);
        }
        /** 2.字段定义信息，去掉主键字段 **/
        {
            for (final PEField field : schema.fields()) {
                if (!field.isPrimaryKey()) {
                    addLine(lines, this.getFieldSaber().buildLine(field));
                }
            }
        }
        /** 3.添加Unique/Primary Key约束 **/
        {
            for (final PEKey key : schema.keys()) {
                // buildLine(PEKey)仅针对UK和PK
                addLine(lines, this.getKeySaber().buildLine(key));
            }
        }
        /** 4.添加Foreign Key约束 **/
        {
            final ConcurrentMap<String, PEField> foreigns = this.extractForeignKey(schema);
            for (final PEKey key : schema.getForeignKey()) {
                addLine(lines, this.getKeySaber().buildLine(key, foreigns));
            }
        }
        /** 5.生成最终的SQL语句 **/
        return builder.buildCreateTable(schema.getTable(), lines);
    }

    private ConcurrentMap<String, PEField> extractForeignKey(final Schema schema) {
        final List<PEField> fields = schema.getForeignField();
        final ConcurrentMap<String, PEField> map = new ConcurrentHashMap<>();
        for (final PEField field : fields) {
            map.put(field.getColumnName(), field);
        }
        return map;
    }

    private void addLine(final List<String> sqls, final String line) {
        if (StringKit.isNonNil(line)) {
            sqls.add(line);
        }
    }

    private void buildPrimaryKey(final List<String> lines, final Schema schema, final AutoIncrement increment) {
        final MetaPolicy policy = schema.getPolicy();
        if (MetaPolicy.INCREMENT == policy) {
            addLine(lines, increment.build(schema));
        } else {
            // 1.获取所有主键
            final List<PEField> primarykeys = schema.getPrimaryKeys();
            // 2.所有主键添加到lines中
            for (final PEField primarykey : primarykeys) {
                addLine(lines, this.getFieldSaber().buildLine(primarykey));
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
