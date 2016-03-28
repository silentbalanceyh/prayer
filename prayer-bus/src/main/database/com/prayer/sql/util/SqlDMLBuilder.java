package com.prayer.sql.util;

import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.sql.SQLStatement;
import com.prayer.model.business.OrderBy;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class SqlDMLBuilder implements SQLStatement {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 创建Builder **/
    public static SqlDMLBuilder create() {
        return singleton(SqlDMLBuilder.class);
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods ( Read ) ====================================
    /**
     * 构建SELECT语句
     * @param table
     * @param columns
     * @param whereExpr
     * @param orders
     * @return
     */
    @NotNull
    public String buildSelect(@NotNull @NotBlank @NotEmpty final String table, @NotNull final List<String> columns,
            @InstanceOf(Expression.class) final Expression whereExpr,
            @InstanceOfAny(OrderBy.class) final OrderBy orders) {
        // 1.构造列部分Projection
        String cols = String.valueOf(Symbol.STAR);
        // 2.设置Columns
        if (Constants.ZERO < columns.size()) {
            cols = StringKit.join(columns, Symbol.COMMA);
        }
        // 3.使用模板构造参数语句
        final String majorClouse = MessageFormat.format(OP_SELECT, cols, table);
        // 4.构建最终语句
        return this.buildFinalSql(majorClouse, whereExpr, orders);
    }

    // ~ Methods ( Write ) ===================================
    /**
     * 插入用的INSERT语句
     * 
     * @param table
     * @param columns
     * @return
     */
    @NotNull
    public String buildInsert(@NotNull @NotBlank @NotEmpty final String table,
            @MinSize(1) final Collection<String> columns) {
        // 1.构造INSERT部分
        final int paramLen = columns.size();
        // 2.必须使用List，Set会导致?,?,?的尺寸问题
        final List<String> params = new ArrayList<>();
        for (int idx = 0; idx < paramLen; idx++) {
            params.add(String.valueOf(Symbol.QUESTION));
        }
        // 3.使用模板构造参数语句
        final String colPart = StringKit.join(columns, Symbol.COMMA);
        final String valPart = StringKit.join(columns, Symbol.COMMA);
        return MessageFormat.format(OP_INSERT, table, colPart, valPart);
    }

    /**
     * 更新用的UPDATE语句
     * 
     * @param table
     * @param columns
     * @param whereExpr
     * @return
     */
    @NotNull
    public String buildUpdate(@NotNull @NotBlank @NotEmpty final String table,
            @MinSize(1) final Collection<String> columns,
            @NotNull @InstanceOf(Expression.class) final Expression whereExpr) {
        // 1.构造UPDATE部分
        final String[] colAtts = columns.toArray(Constants.T_STR_ARR);
        final List<String> params = new ArrayList<>();
        for (final String colAtt : colAtts) {
            // final String segment =
            // MessageFormat.format(OP_ASSIGN,colAtt,Symbol.QUESTION);
            params.add(MessageFormat.format(OP_ASSIGN, colAtt, Symbol.QUESTION));
        }
        // 2.使用UPDATE和WHERE模板
        final String majorClouse = MessageFormat.format(OP_UPDATE, table, StringKit.join(params, Symbol.COMMA));
        final String whereClouse = MessageFormat.format(OP_WHERE, whereExpr.toSql());
        return majorClouse + Symbol.SPACE + whereClouse;
    }

    /**
     * 删除用的DELETE语句
     * 
     * @param table
     * @param whereExpr
     * @return
     */
    @NotNull
    public String buildDelete(@NotNull @NotEmpty @NotBlank final String table,
            @InstanceOf(Expression.class) final Expression whereExpr) {
        // 1.构造DELETE构造参数语句
        final String majorClouse = MessageFormat.format(OP_DELETE, table);
        // 2.生成最终语句
        return this.buildFinalSql(majorClouse, whereExpr, null);
    }

    /**
     * 查询用的COUNT语句
     * 
     * @param table
     * @param whereExpr
     * @return
     */
    @NotNull
    public String buildCount(@NotNull @NotEmpty @NotBlank final String table,
            @InstanceOf(Expression.class) final Expression whereExpr) {
        // 1.构造COUNT主干部分
        final String majorClouse = MessageFormat.format(OP_COUNT, table);
        // 2.生成最终语句
        return this.buildFinalSql(majorClouse, whereExpr, null);
    }

    // ~ Private Methods =====================================

    private String buildFinalSql(final String majorClouse, final Expression whereExpr, final OrderBy orders) {
        // 1.最终输出SQL
        final StringBuilder retSql = new StringBuilder();
        // 2.Where子句
        retSql.append(majorClouse);
        if (null != whereExpr) {
            final String whereClouse = MessageFormat.format(OP_WHERE, whereExpr.toSql());
            retSql.append(Symbol.SPACE).append(whereClouse);
        }
        // 3.Order By子句
        if (null != orders && orders.containOrderBy()) {
            retSql.append(Symbol.SPACE).append(OP_ORDER_BY).append(Symbol.SPACE).append(orders.toSql());
        }
        return retSql.toString();
    }

    private SqlDMLBuilder() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
