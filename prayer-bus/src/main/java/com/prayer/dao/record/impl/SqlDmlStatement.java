package com.prayer.dao.record.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.prayer.constant.SqlSegment;
import com.prayer.constant.Symbol;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class SqlDmlStatement implements SqlSegment, Symbol {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param table
	 * @param columns
	 * @return
	 */
	@NotNull
	public static String prepInsertSQL(@NotNull final String table, @MinSize(1) final Set<String> columns) {
		// 1.构造INSERT部分
		final int paramLength = columns.size();
		final List<String> params = new ArrayList<>();	// 必须使用List，否则?,?,?这种会因为Set的原因直接导致最终的参数尺寸问题
		for (int idx = 0; idx < paramLength; idx++) {
			params.add(String.valueOf(Symbol.QUESTION));
		}
		// 2.使用模板构造参数语句
		return MessageFormat.format(TB_INSERT, table, StringKit.join(columns, Symbol.COMMA),
				StringKit.join(params, Symbol.COMMA));
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private SqlDmlStatement(){}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
