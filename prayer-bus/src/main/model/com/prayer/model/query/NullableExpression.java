package com.prayer.model.query;

import com.prayer.base.model.AbstractExpression;
import com.prayer.constant.Symbol;
import com.prayer.facade.dao.builder.SQLWord;
import com.prayer.facade.kernel.Expression;

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
final class NullableExpression extends AbstractExpression implements Expression, SQLWord {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param column
     * @return
     */
    public static NullableExpression getIsNull(@NotNull @NotBlank @NotEmpty final String column) {
        final String operator = Connector.IS + Symbol.SPACE + Comparator.NULL;
        return new NullableExpression(column, operator);
    }

    /**
     * 
     * @param column
     * @return
     */
    public static NullableExpression getIsNotNull(@NotNull @NotBlank @NotEmpty final String column) {
        final String operator = Connector.IS + Symbol.SPACE + Comparator.NOT + Symbol.SPACE + Comparator.NULL;
        return new NullableExpression(column, operator);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @NotNull
    @NotBlank
    @NotEmpty
    public String toSql() {
        final StringBuilder sql = new StringBuilder();
        sql.append(this.getLeft().toSql()).append(Symbol.SPACE).append(this.getData());
        return sql.toString();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private NullableExpression(final String column, final String operator) {
        super(operator);
        this.setLeft(new ColumnLeafNode(column));
        this.setRight(null);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
