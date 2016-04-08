package com.prayer.configuration.impl;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.ArrayList;
import java.util.List;

import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Value;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.query.Restrictions;
import com.prayer.model.type.StringType;

/**
 * 配置数据专用查询条件生成器，只能使用AND和=两种操作
 * 
 * @author Lang
 * 
 */
public final class AndEqer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** And 条件列 **/
    private final transient List<String> columns = new ArrayList<>();
    /** And 条件列的值 **/
    private final transient List<Value<?>> values = new ArrayList<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 静态工厂 **/
    public static AndEqer reference(){
        return singleton(AndEqer.class);
    }
    // ~ Constructors ========================================
    /** **/
    private AndEqer(){}
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 添加一个Filter
     * 
     * @param column
     * @param literal
     */
    public AndEqer build(final String column, final String literal) {
        this.columns.add(column);
        this.values.add(new StringType(literal));
        return this;
    }

    /**
     * 重新设置Filter
     */
    public AndEqer reset() {
        this.columns.clear();
        this.values.clear();
        return this;
    }

    /**
     * 生成where子句的条件
     * 
     * @return
     */
    public String build() throws AbstractDatabaseException {
        Expression filter = null;
        final int size = columns.size();
        for (int idx = 0; idx < size; idx++) {
            if (null == filter) {
                // filter为空构建第一个
                filter = Restrictions.eq(columns.get(idx), values.get(idx));
            } else {
                // filter不为空构建新的，并且使用and连接
                final Expression right = Restrictions.eq(columns.get(idx), values.get(idx));
                filter = Restrictions.and(filter, right);
            }
        }
        return filter.toSql();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
