package com.prayer.kernel.query;

import com.prayer.kernel.Expression;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 树状表达式对象
 * 
 * @author Lang
 *
 */
@Guarded
abstract class AbstractExpression implements Expression {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/**
	 * Expression表达式的左值
	 **/
	private transient AbstractExpression left;
	/**
	 * Expression表达式的右值
	 */
	private transient AbstractExpression right;
	/**
	 * Expression表达式的中间连接符号，连接符号分为两种： 1.连接符：AND、OR等，连接左右Expression 2.操作符：=,
	 * <=, >=等，连接左列名以及右边的值对象
	 */
	@NotNull
	@NotEmpty
	@NotBlank
	private transient String data; // NOPMD
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================

	/**
	 * 构造完整的表达式树
	 * 
	 * @param left
	 * @param data
	 * @param right
	 */
	@PostValidateThis
	AbstractExpression(final AbstractExpression left, @NotNull @NotEmpty @NotBlank final String data,
			final AbstractExpression right) {
		this.left = left;
		this.right = right;
		this.data = data;
	}

	/**
	 * 构造最底层数据节点
	 * 
	 * @param data
	 */
	@PostValidateThis
	AbstractExpression(@NotNull @NotBlank @NotEmpty final String data) {
		this(null, data, null);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	/**
	 * @param left
	 *            the left to set
	 */
	public void setLeft(final AbstractExpression left) {
		this.left = left;
	}

	/**
	 * @param right
	 *            the right to set
	 */
	public void setRight(final AbstractExpression right) {
		this.right = right;
	}

	/**
	 * @return the left
	 */
	public AbstractExpression getLeft() {
		return left;
	}

	/**
	 * @return the right
	 */
	public AbstractExpression getRight() {
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
