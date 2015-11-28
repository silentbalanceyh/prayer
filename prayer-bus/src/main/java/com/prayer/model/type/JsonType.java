package com.prayer.model.type;

import static com.prayer.util.Instance.singleton;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.facade.kernel.Validator;
import com.prayer.facade.kernel.Value;

/**
 * 类型: Xml格式的字符串
 *
 * @author Lang
 * @see
 */
public class JsonType extends StringType implements Value<String> {
    // ~ Static Fields =======================================
    /** **/
    private transient final Validator innerValidator = singleton("com.prayer.plugin.validator.JsonValidator");

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public JsonType(final String value) throws AbstractDatabaseException{
        super(value);
        // Json内容验证代码
        this.innerValidator.validate(new StringType(value));
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public DataType getDataType() {
        return DataType.JSON;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "JsonType [value=" + value + "]";
    }
}
