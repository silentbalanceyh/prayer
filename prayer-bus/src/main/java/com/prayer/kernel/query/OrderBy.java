package com.prayer.kernel.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;

import jodd.util.StringUtil;
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
public class OrderBy implements Serializable {
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 1512151704404777705L;
	// ~ Instance Fields =====================================
	/**
	 * 
	 */
	private transient final List<String> orderBy = new ArrayList<>();

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 */
	public OrderBy() {
		this.clear();
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	public void add(@NotNull @NotBlank @NotEmpty final String column, @NotNull @NotBlank @NotEmpty final String flag) {
		final StringBuilder statement = new StringBuilder();
		statement.append(column).append(Symbol.SPACE);
		if (StringUtil.equals("ASC", flag.toUpperCase(Locale.getDefault()))) {
			statement.append("ASC");
		} else {
			statement.append("DESC");
		}
		this.orderBy.add(statement.toString());
	}
	/** 清除掉当前的OrderBy Queue **/
	public void clear(){
		if(!orderBy.isEmpty()){
			orderBy.clear();
		}
	}

	/** **/
	public String toSql() {
		String orderBySql = "";
		if (!orderBy.isEmpty()) {
			orderBySql = StringUtil.join(this.orderBy.toArray(Constants.T_STR_ARR), Symbol.COMMA);
		}
		return orderBySql;
	}

	/** **/
	public boolean containOrderBy() {
		return !this.orderBy.isEmpty();
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
