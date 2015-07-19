package com.prayer.kernel.model;

import java.util.Arrays;
import java.util.List;

import com.prayer.constant.Symbol;
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
	// ~ and和or属于连接词，可以针对Query进行连接，这种情况不需要任何语句的封装
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

	// ~ 下边属于操作符部分，所以可直接进行最小语句设置
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

	/**
	 * (Column <= Value)
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public static Query le(@NotNull @NotEmpty @NotBlank final String column, @NotNull final Value<?> value) { // NOPMD
		return buildQuery(column, LESS_EQ_THAN, value);
	}

	/**
	 * (Column > Value)
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public static Query gt(@NotNull @NotEmpty @NotBlank final String column, @NotNull final Value<?> value) { // NOPMD
		return buildQuery(column, GREATER_THAN, value);
	}

	/**
	 * (Column >= Value)
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public static Query ge(@NotNull @NotEmpty @NotBlank final String column, @NotNull final Value<?> value) { // NOPMD
		return buildQuery(column, GREATER_EQ_THAN, value);
	}

	/**
	 * (Column IS NULL)
	 * 
	 * @param column
	 * @return
	 */
	public static Query nul(@NotNull @NotEmpty @NotBlank final String column) {
		return buildQuery(column, IS, new StringType(NULL));
	}

	/**
	 * (Column IS NOT NULL)
	 * 
	 * @param column
	 * @return
	 */
	public static Query nil(@NotNull @NotEmpty @NotBlank final String column) {
		return buildQuery(column, IS, new StringType(NOT + Symbol.SPACE + NULL));
	}

	/** **/
	private static Query buildQuery(final String column, final String operator, final Value<?> value) {
		final Query left = new Query(column);
		final List<DataType> wrapperTypes = Arrays.asList(WRAPPER_TYPES);
		Query right = null;
		if (wrapperTypes.contains(value.getDataType())) {
			// 针对特殊类型需要封装
			right = new Query(Symbol.S_QUOTES + value.literal() + Symbol.S_QUOTES);
		} else {
			right = new Query(value.literal());
		}
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
}
