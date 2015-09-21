package com.prayer.model.type;

import static com.prayer.util.Instance.singleton;

import com.prayer.exception.AbstractMetadataException;
import com.prayer.kernel.Validator;
import com.prayer.kernel.Value;

/**
 * 类型：Script格式【默认JavaScript】
 *
 * @author Lang
 * @see
 */
public class ScriptType extends StringType implements Value<String> {
    // ~ Static Fields =======================================
    /** **/
    private transient final Validator innerValidator = singleton("com.prayer.kernel.validator.ScriptValidator");

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public ScriptType(final String value) throws AbstractMetadataException{
        super(value);
        // Script内容验证代码
        this.innerValidator.validate(new StringType(value));
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public DataType getDataType() {
        return DataType.SCRIPT;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "ScriptType [value=" + value + "]";
    }
}
