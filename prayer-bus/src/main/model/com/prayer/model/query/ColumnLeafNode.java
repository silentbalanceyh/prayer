package com.prayer.model.query;

import com.prayer.facade.kernel.Expression;
import com.prayer.fantasm.model.AbstractExpression;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 列表达式
 * @author Lang
 *
 */
@Guarded
final class ColumnLeafNode extends AbstractExpression implements Expression{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 节点构造函数
     * @param column
     */
    public ColumnLeafNode(@NotNull @NotEmpty @NotBlank final String column){
        super(column);
        this.setLeft(null);
        this.setRight(null);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    @NotNull
    @NotBlank
    @NotEmpty
    public String toSql(){
        return this.keywordFilter(getData());
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 
     * @param columnName
     * @return
     */
    private String keywordFilter(final String columnName){
        // TODO: 列名规范，不可为SQL关键字，有必要的时候需要添加双引号
        return columnName;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
