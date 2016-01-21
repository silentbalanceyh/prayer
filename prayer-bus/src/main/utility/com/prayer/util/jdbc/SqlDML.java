package com.prayer.util.jdbc;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.prayer.constant.Constants;
import com.prayer.constant.SqlSegment;
import com.prayer.constant.Symbol;
import com.prayer.facade.kernel.Expression;
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
@Deprecated
public final class SqlDML implements SqlSegment, Symbol {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param table
     * @param columns
     * @return
     */
    @NotNull
    public static String prepInsertSQL(@NotNull @NotBlank @NotEmpty final String table,
            @MinSize(1) final Collection<String> columns) {
        // 1.构造INSERT部分
        final int paramLength = columns.size();
        final List<String> params = new ArrayList<>(); // 必须使用List，否则?,?,?这种会因为Set的原因直接导致最终的参数尺寸问题
        for (int idx = 0; idx < paramLength; idx++) {
            params.add(String.valueOf(Symbol.QUESTION));
        }
        // 2.使用模板构造参数语句
        return MessageFormat.format(TB_INSERT, table, StringKit.join(columns, Symbol.COMMA),
                StringKit.join(params, Symbol.COMMA));
    }

    /**
     * 
     * @param table
     * @param columns
     * @return
     */
    @NotNull
    public static String prepUpdateSQL(@NotNull @NotBlank @NotEmpty final String table,
            @MinSize(1) final Collection<String> columns, @NotNull final Expression whereExpr) {
        // 1.构造UPDATE部分
        final String[] columnArr = columns.toArray(Constants.T_STR_ARR);
        final List<String> params = new ArrayList<>();
        for (int idx = 0; idx < columnArr.length; idx++) {
            params.add(columnArr[idx] + Symbol.EQUAL + Symbol.QUESTION);
        }
        // 2.使用模板构造参数语句
        final String majorClouse = MessageFormat.format(TB_UPDATE, table, StringKit.join(params, Symbol.COMMA));
        final String whereClouse = MessageFormat.format(TB_WHERE, whereExpr.toSql());
        return majorClouse + SPACE + whereClouse;
    }

    /**
     * 
     * @param columns
     * @param whereExpr
     * @return
     */
    @NotNull
    public static String prepSelectSQL(@NotNull @NotBlank @NotEmpty final String table,
            @NotNull final List<String> columns, @InstanceOf(Expression.class) final Expression whereExpr,
            @InstanceOfAny(OrderBy.class) final OrderBy orders) {
        // 1.构造列部分
        String cols = "*";
        if (Constants.ZERO < columns.size()) {
            cols = StringKit.join(columns, COMMA);
        }
        // 2.使用模板构造参数语句
        final String majorClouse = MessageFormat.format(TB_SELECT, cols, table);
        // 3.如果Expression为null则查询所有记录
        return finalizeSql(majorClouse, whereExpr, orders);
    }

    /**
     * 带查询的Count
     */
    @NotNull
    public static String prepCountSQL(@NotNull @NotBlank @NotEmpty final String table,
            @InstanceOf(Expression.class) final Expression whereExpr) {
        // 1.构造Count主干语句
        final String majorClouse = MessageFormat.format(TB_COUNT, table);
        // 2.最终Count语句
        return finalizeSql(majorClouse, whereExpr, null);
    }

    /**
     * 
     * @param table
     * @param whereExpr
     * @return
     */
    @NotNull
    public static String prepDeleteSQL(@NotNull @NotBlank @NotEmpty final String table,
            @InstanceOf(Expression.class) final Expression whereExpr) {
        // 1.使用模板构造参数语句
        final String majorClouse = MessageFormat.format(TB_DELETE, table);
        // 3.如果Expression为null则删除所有记录
        return finalizeSql(majorClouse, whereExpr, null);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static String finalizeSql(final String majorClouse, final Expression whereExpr, final OrderBy orders) {
        final StringBuilder retSql = new StringBuilder();
        if (null == whereExpr) {
            retSql.append(majorClouse);
        } else {
            final String whereClouse = MessageFormat.format(TB_WHERE, whereExpr.toSql());
            retSql.append(majorClouse).append(SPACE).append(whereClouse);
        }
        // Order By Closure
        if (null != orders && orders.containOrderBy()) {
            retSql.append(SPACE).append(ORDER_BY).append(SPACE).append(orders.toSql());
        }
        return retSql.toString();
    }

    private SqlDML() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
