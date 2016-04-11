package com.prayer.model.query;

import com.prayer.facade.model.crucial.Expression;
import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.model.AbstractExpression;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 二元操作符底层表达式
 * 
 * @author Lang
 *
 */
@Guarded
final class StandardExpression extends AbstractExpression implements Expression {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * COLUMN OP VALUE
     * 
     * @param column
     * @param value
     */
    public StandardExpression(@NotNull @NotBlank @NotEmpty final String column,
            @NotNull @NotBlank @NotEmpty final String operator, final Value<?> value) {
        super(operator);
        this.setLeft(new ColumnLeafNode(column));
        this.setRight(new ValueLeafNode(value));
    }

    /**
     * COLUMN OP ?
     * 
     * @param column
     */
    public StandardExpression(@NotNull @NotBlank @NotEmpty final String column,
            @NotNull @NotBlank @NotEmpty final String operator) {
        super(operator);
        this.setLeft(new ColumnLeafNode(column));
        this.setRight(new ValueLeafNode());
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    @NotNull
    @NotBlank
    @NotEmpty
    public String toSql() {
        final StringBuilder sql = new StringBuilder();
        sql.append(this.getLeft().toSql()).append(this.getData()).append(this.getRight().toSql());
        return sql.toString();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
