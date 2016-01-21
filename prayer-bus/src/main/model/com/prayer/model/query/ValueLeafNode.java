package com.prayer.model.query;

import java.util.Arrays;
import java.util.List;

import com.prayer.constant.Symbol;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Value;
import com.prayer.fantasm.model.AbstractExpression;
import com.prayer.model.type.DataType;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.guard.Guarded;

/**
 * 值表达式
 * @author Lang
 *
 */
@Guarded
final class ValueLeafNode extends AbstractExpression implements Expression{
    // ~ Static Fields =======================================
    /** **/
    private static final DataType[] WRAPPER_TYPES = { DataType.STRING, DataType.DATE, DataType.JSON, DataType.SCRIPT,
            DataType.XML, DataType.JSON };
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 直接值添加单引号的情况 
     * **/
    private static String quote(final Value<?> value){
        final List<DataType> wrapperTypes = Arrays.asList(WRAPPER_TYPES);
        String ret = value.literal();
        if(wrapperTypes.contains(value.getDataType())){
            ret = Symbol.S_QUOTES + value.literal() + Symbol.S_QUOTES;
        }
        return ret;
    }
    // ~ Constructors ========================================
    /**
     * 直接值构造
     * @param value
     */
    public ValueLeafNode(@InstanceOf(Value.class) final Value<?> value){
        // 直接值的构造主要考虑是否添加单引号
        super(quote(value));
    }
    /**
     * 直接使用String来构造值对象
     * @param value
     */
    public ValueLeafNode(final String value){
        super(value);
    }
    /**
     * ?带参数值构造
     */
    public ValueLeafNode(){
        super(String.valueOf(Symbol.QUESTION));
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 生成Sql部分
     */
    @Override
    public String toSql(){
        return this.getData();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
