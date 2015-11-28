package com.prayer.kernel.query;

import com.prayer.kernel.i.Expression;
import com.prayer.kernel.i.Value;
import com.prayer.util.cv.SqlSegment;
import com.prayer.util.cv.Symbol;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
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
public class LikeExpression extends AbstractExpression implements Expression, SqlSegment {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 构造Like表达式
     * 
     * @param column
     * @param value
     * @param matchMode
     */
    public LikeExpression(@NotNull @NotBlank @NotEmpty final String column,
            @NotNull @InstanceOf(Value.class) final Value<?> value,
            @NotNull @InstanceOfAny(MatchMode.class) final MatchMode matchMode) {
        super(LIKE);
        this.setLeft(new ColumnLeafNode(column));
        this.setRight(new ValueLeafNode(matchMode.toMatchString(value.literal())));
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 构造LIKE语句
     */
    @Override
    @NotNull
    @NotBlank
    @NotEmpty
    public String toSql() {
        final StringBuilder sql = new StringBuilder();
        sql.append(this.getLeft().toSql()).append(Symbol.SPACE).append(this.getData()).append(Symbol.SPACE)
                .append(Symbol.S_QUOTES).append(this.getRight().toSql()).append(Symbol.S_QUOTES);
        return sql.toString();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
