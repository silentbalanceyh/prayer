package com.prayer.kernel.query;

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
 */
@Guarded
public final class Restrictions implements SqlSegment {
	// ~ Static Fields =======================================
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

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Restrictions() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
