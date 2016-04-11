package com.prayer.model.query;

import com.prayer.facade.constant.Symbol;
import com.prayer.facade.model.crucial.Expression;
import com.prayer.facade.sql.SQLWord;
import com.prayer.fantasm.model.AbstractExpression;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * AND, OR
 * 
 * @author Lang
 *
 */
@Guarded
final class ProjectionExpression extends AbstractExpression implements Expression, SQLWord {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param left
     * @param right
     * @return
     */
    public static ProjectionExpression or(final Expression left, final Expression right) { // NOPMD
        return new ProjectionExpression(left, right, Connector.OR);
    }

    /**
     * 
     * @param left
     * @param right
     * @return
     */
    public static ProjectionExpression and(final Expression left, final Expression right) {
        return new ProjectionExpression(left, right, Connector.AND);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 复杂表达式，则在添加的时候需要加入括号以表明优先级
     */
    public boolean isComplex() {
        return true;
    }

    /** **/
    @Override
    @NotNull
    @NotBlank
    @NotEmpty
    public String toSql() {
        final StringBuilder sql = new StringBuilder();
        // 左表达式
        if (this.getLeft().isComplex()) {
            sql.append(Symbol.BRACKET_SL).append(this.getLeft().toSql()).append(Symbol.BRACKET_SR);
        } else {
            sql.append(this.getLeft().toSql());
        }
        // 右表达式
        sql.append(Symbol.SPACE).append(this.getData()).append(Symbol.SPACE);
        if (this.getRight().isComplex()) {
            sql.append(Symbol.BRACKET_SL).append(this.getRight().toSql()).append(Symbol.BRACKET_SR);
        } else {
            sql.append(this.getRight().toSql());
        }
        return sql.toString();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private ProjectionExpression(final Expression left, final Expression right, final String operator) {
        super(operator);
        this.setLeft(left);
        this.setRight(right);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
