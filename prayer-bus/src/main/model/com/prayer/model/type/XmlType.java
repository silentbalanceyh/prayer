package com.prayer.model.type;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.model.crucial.Validator;
import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * 类型：Xml格式
 *
 * @author Lang
 * @see
 */
public class XmlType extends StringType {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final Validator innerValidator = singleton("com.prayer.plugin.validator.XmlValidator");

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public XmlType(final String value) throws AbstractDatabaseException{
        super(value);
        // Xml内容验证代码
        this.innerValidator.validate(new StringType(value));
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /** **/
    @Override
    public DataType getDataType() {
        return DataType.XML;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "XmlType [value=" + value + "]";
    }
}
