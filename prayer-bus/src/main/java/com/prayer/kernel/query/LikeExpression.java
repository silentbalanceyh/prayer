package com.prayer.kernel.query;

import com.prayer.constant.Symbol;
import com.prayer.kernel.Expression;
import com.prayer.kernel.SqlSegment;
import com.prayer.kernel.Value;

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
	public LikeExpression(@NotNull @NotBlank @NotEmpty final String column, @NotNull final Value<?> value,
			@NotNull final MatchMode matchMode) {
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
