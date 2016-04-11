package com.prayer.model.type;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.model.crucial.Validator;
import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * 类型：Script格式【默认JavaScript】
 *
 * @author Lang
 * @see
 */
public class ScriptType extends StringType implements Value<String> {
    // ~ Static Fields =======================================
    /** **/
    private transient final Validator innerValidator = singleton("com.prayer.plugin.validator.ScriptValidator");

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public ScriptType(final String value) throws AbstractDatabaseException{
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
