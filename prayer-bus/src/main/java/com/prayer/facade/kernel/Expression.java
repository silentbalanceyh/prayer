package com.prayer.facade.kernel;
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
    /**
     * 判断当前Expression是否复杂表达式
     * @return
     */
    boolean isComplex();
}
