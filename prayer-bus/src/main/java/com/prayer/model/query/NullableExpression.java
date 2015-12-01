package com.prayer.model.query;

import com.prayer.facade.kernel.Expression;
import com.prayer.util.cv.SqlSegment;
import com.prayer.util.cv.Symbol;

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
final class NullableExpression extends AbstractExpression implements Expression, SqlSegment {
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
        final String operator = IS + Symbol.SPACE + NULL;
        return new NullableExpression(column, operator);
    }

    /**
     * 
     * @param column
     * @return
     */
    public static NullableExpression getIsNotNull(@NotNull @NotBlank @NotEmpty final String column) {
        final String operator = IS + Symbol.SPACE + NOT + Symbol.SPACE + NULL;
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