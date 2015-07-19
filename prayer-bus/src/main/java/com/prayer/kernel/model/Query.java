package com.prayer.kernel.model;

import com.prayer.kernel.SqlSegment;
import com.prayer.kernel.Value;
import com.prayer.model.type.DataType;
import com.prayer.model.type.StringType;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class Query implements SqlSegment {
	// ~ Static Fields =======================================
	/** **/
	private static final DataType[] WRAPPER_TYPES = { DataType.STRING, DataType.DATE, DataType.JSON, DataType.SCRIPT,
			DataType.XML, DataType.JSON };
	// ~ Instance Fields =====================================
	/** **/
	private transient Query left;
	/** **/
	private transient Query right;
	/** **/
	@NotNull
	@NotEmpty
	@NotBlank
	private transient String data; // NOPMD

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * (A Condition) AND (B Condition)
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public static Query and(@NotNull final Query left, @NotNull final Query right) {
		return new Query(left, AND, right);
	}

	/**
	 * (A Condition) OR (B Condition)
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public static Query or(@NotNull final Query left, @NotNull final Query right) { // NOPMD
		return new Query(left, OR, right);
	}

	/**
	 * (Column = Value)
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public static Query eq(@NotNull @NotEmpty @NotBlank final String column, @NotNull final Value<?> value) { // NOPMD
		return buildQuery(column, EQUAL, value);
	}

	/**
	 * (Column <> Value)
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public static Query neq(@NotNull @NotEmpty @NotBlank final String column, @NotNull final Value<?> value) { // NOPMD
		return buildQuery(column, NOT_EQUAL, value);
	}

	/**
	 * (Column < Value)
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public static Query lt(@NotNull @NotEmpty @NotBlank final String column, @NotNull final Value<?> value) { // NOPMD
		return buildQuery(column, LESS_THAN, value);
	}

	/** **/
	private static Query buildQuery(final String column, final String operator, final Value<?> value) {
		final Query left = new Query(column);
		final Query right = new Query(value.literal());
		return new Query(left, operator, right);
	}

	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	private Query(@NotNull @NotEmpty @NotBlank final String data) {
		this(null, data, null);
	}

	/** **/
	@PostValidateThis
	private Query(final Query left, @NotNull @NotEmpty @NotBlank final String data, final Query right) {
		this.left = left;
		this.right = right;
		this.data = data;
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		if (null == left && null == right) {
			builder.append(data.replaceAll("\"", "'"));
		} else if (null != left && null != right) { // NOPMD
			builder.append(left).append(data).append(right);
		} else {
			
		}
		return builder.toString();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================

	/**
	 * @return the left
	 */
	public Query getLeft() {
		return left;
	}

	/**
	 * @return the right
	 */
	public Query getRight() {
		return right;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	// ~ hashCode,equals,toString ============================

	public static void main(String args[]) {
		Query tree = Query.eq("TEST", new StringType("XY"));
		System.out.println(tree);
	}
}
