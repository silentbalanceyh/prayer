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
public class QueryTree implements SqlSegment {
	// ~ Static Fields =======================================
	/** **/
	private static final DataType[] WRAPPER_TYPES = { DataType.STRING, DataType.DATE, DataType.JSON, DataType.SCRIPT,
			DataType.XML, DataType.JSON };
	// ~ Instance Fields =====================================
	/** **/
	private transient QueryTree left;
	/** **/
	private transient QueryTree right;
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
	public static QueryTree and(@NotNull final QueryTree left, @NotNull final QueryTree right) {
		return new QueryTree(left, AND, right);
	}

	/**
	 * (A Condition) OR (B Condition)
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public static QueryTree or(@NotNull final QueryTree left, @NotNull final QueryTree right) { // NOPMD
		return new QueryTree(left, OR, right);
	}

	/**
	 * (Column = Value)
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public static QueryTree eq(@NotNull @NotEmpty @NotBlank final String column, @NotNull final Value<?> value) { // NOPMD
		return buildQuery(column, EQUAL, value);
	}

	/**
	 * (Column <> Value)
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public static QueryTree neq(@NotNull @NotEmpty @NotBlank final String column, @NotNull final Value<?> value) { // NOPMD
		return buildQuery(column, NOT_EQUAL, value);
	}

	/**
	 * (Column < Value)
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public static QueryTree lt(@NotNull @NotEmpty @NotBlank final String column, @NotNull final Value<?> value) { // NOPMD
		return buildQuery(column, LESS_THAN, value);
	}

	/** **/
	private static QueryTree buildQuery(final String column, final String operator, final Value<?> value) {
		final QueryTree left = new QueryTree(column);
		final QueryTree right = new QueryTree(value.literal());
		return new QueryTree(left, operator, right);
	}

	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	private QueryTree(@NotNull @NotEmpty @NotBlank final String data) {
		this(null, data, null);
	}

	/** **/
	@PostValidateThis
	private QueryTree(final QueryTree left, @NotNull @NotEmpty @NotBlank final String data, final QueryTree right) {
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
			builder.append(data);
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
	public QueryTree getLeft() {
		return left;
	}

	/**
	 * @return the right
	 */
	public QueryTree getRight() {
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
		QueryTree tree = QueryTree.eq("TEST", new StringType("XY"));
		System.out.println(tree);
	}
}
