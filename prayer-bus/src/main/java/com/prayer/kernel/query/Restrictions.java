package com.prayer.kernel.query;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SqlSegment;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.metadata.ProjectionInvalidException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Value;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 */
@Guarded
public final class Restrictions implements SqlSegment { // NOPMD
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Restrictions.class);
    /** 左表达式 **/
    private static final String LEFT = "left";
    /** 右表达式 **/
    private static final String RIGHT = "right";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * COLUMN = Value
     * 
     * @param column
     * @param value
     * @return
     */
    @NotNull
    public static Expression eq(@NotNull @NotBlank @NotEmpty final String column, @NotNull final Value<?> value) { // NOPMD
        return new StandardExpression(column, EQUAL, value);
    }

    /**
     * COLUMN = ?
     * 
     * @param column
     * @return
     */
    @NotNull
    public static Expression eq(@NotNull @NotBlank @NotEmpty final String column) { // NOPMD
        return new StandardExpression(column, EQUAL);
    }

    /**
     * COLUMN <> Value
     * 
     * @param column
     * @param value
     * @return
     */
    @NotNull
    public static Expression neq(@NotNull @NotBlank @NotEmpty final String column, @NotNull final Value<?> value) { // NOPMD
        return new StandardExpression(column, NOT_EQUAL, value);
    }

    /**
     * COLUMN <> ?
     * 
     * @param column
     * @return
     */
    @NotNull
    public static Expression neq(@NotNull @NotBlank @NotEmpty final String column) { // NOPMD
        return new StandardExpression(column, NOT_EQUAL);
    }

    /**
     * COLUMN < Value
     * 
     * @param column
     * @param value
     * @return
     */
    @NotNull
    public static Expression lt(@NotNull @NotBlank @NotEmpty final String column, @NotNull final Value<?> value) { // NOPMD
        return new StandardExpression(column, LESS_THAN, value);
    }

    /**
     * COLUMN < ?
     * 
     * @param column
     * @return
     */
    @NotNull
    public static Expression lt(@NotNull @NotBlank @NotEmpty final String column) { // NOPMD
        return new StandardExpression(column, LESS_THAN);
    }

    /**
     * COLUMN <= Value
     * 
     * @param column
     * @param value
     * @return
     */
    @NotNull
    public static Expression le(@NotNull @NotBlank @NotEmpty final String column, @NotNull final Value<?> value) { // NOPMD
        return new StandardExpression(column, LESS_EQ_THAN, value);
    }

    /**
     * COLUMN <= ?
     * 
     * @param column
     * @return
     */
    @NotNull
    public static Expression le(@NotNull @NotBlank @NotEmpty final String column) { // NOPMD
        return new StandardExpression(column, LESS_EQ_THAN);
    }

    /**
     * COLUMN > Value
     * 
     * @param column
     * @param value
     * @return
     */
    @NotNull
    public static Expression gt(@NotNull @NotBlank @NotEmpty final String column, @NotNull final Value<?> value) { // NOPMD
        return new StandardExpression(column, GREATER_THAN, value);
    }

    /**
     * COLUMN > ?
     * 
     * @param column
     * @return
     */
    @NotNull
    public static Expression gt(@NotNull @NotBlank @NotEmpty final String column) { // NOPMD
        return new StandardExpression(column, GREATER_THAN);
    }

    /**
     * COLUMN >= Value
     * 
     * @param column
     * @param value
     * @return
     */
    @NotNull
    public static Expression ge(@NotNull @NotBlank @NotEmpty final String column, @NotNull final Value<?> value) { // NOPMD
        return new StandardExpression(column, GREATER_EQ_THAN, value);
    }

    /**
     * COLUMN >= ?
     * 
     * @param column
     * @return
     */
    @NotNull
    public static Expression ge(@NotNull @NotBlank @NotEmpty final String column) { // NOPMD
        return new StandardExpression(column, GREATER_EQ_THAN);
    }

    /**
     * 
     * @param column
     * @param value
     * @return
     */
    @NotNull
    public static Expression like(@NotNull @NotBlank @NotEmpty final String column, @NotNull final Value<?> value,
            @NotNull final MatchMode matchMode) { // NOPMD
        return new LikeExpression(column, value, matchMode);
    }

    /**
     * COLUMN IS NULL
     * 
     * @param column
     * @return
     */
    @NotNull
    public static Expression isNull(@NotNull @NotBlank @NotEmpty final String column) { // NOPMD
        return NullableExpression.getIsNull(column);
    }

    /**
     * COLUMN IS NOT NULL
     * 
     * @param column
     * @return
     */
    @NotNull
    public static Expression isNotNull(@NotNull @NotBlank @NotEmpty final String column) { // NOPMD
        return NullableExpression.getIsNotNull(column);
    }

    /**
     * Expr1 AND Expr2
     * 
     * @param left
     * @param right
     * @return
     */
    @NotNull
    public static Expression and(@NotNull @InstanceOf(Expression.class) final Expression left,
            @NotNull @InstanceOf(Expression.class) final Expression right) throws AbstractDatabaseException {
        verifyExpr(left, right);
        return ProjectionExpression.and(left, right);
    }

    /**
     * Expr1 OR Expr2
     * 
     * @param left
     * @param right
     * @return
     */
    @NotNull
    public static Expression or(@NotNull @InstanceOf(Expression.class) final Expression left,
            @NotNull @InstanceOf(Expression.class) final Expression right) // NOPMD
                    throws AbstractDatabaseException {
        verifyExpr(left, right);
        return ProjectionExpression.or(left, right);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private static void verifyExpr(final Expression left, final Expression right) throws AbstractDatabaseException {
        AbstractDatabaseException exp = null;
        if (left instanceof ColumnLeafNode) {
            exp = new ProjectionInvalidException(Restrictions.class, LEFT, ColumnLeafNode.class.getName());
        } else if (left instanceof ValueLeafNode) {
            exp = new ProjectionInvalidException(Restrictions.class, LEFT, ValueLeafNode.class.getName());
        }
        if (null != exp) {
            info(LOGGER, exp.getErrorMessage());
            throw exp;
        }
        if (right instanceof ColumnLeafNode) {
            exp = new ProjectionInvalidException(Restrictions.class, RIGHT, ColumnLeafNode.class.getName());
        } else if (right instanceof ValueLeafNode) {
            exp = new ProjectionInvalidException(Restrictions.class, RIGHT, ValueLeafNode.class.getName());
        }
        if (null != exp) {
            info(LOGGER, exp.getErrorMessage());
            throw exp;
        }
    }

    private Restrictions() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
