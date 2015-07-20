package com.prayer.kernel;
/**
 * 表达式接口
 * @author Lang
 */
public interface Expression {
	/**
	 * 生成最终的Sql表达式
	 * @return
	 */
	String toSql();
}
