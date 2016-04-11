package com.prayer.plugin.aspectj;

import static com.prayer.util.reflection.Instance.singleton;

import java.math.BigDecimal;
import java.util.Arrays;

import com.prayer.constant.Resources;
import com.prayer.exception.validator.LengthFailureException;
import com.prayer.exception.validator.NotNullFailureException;
import com.prayer.exception.validator.PatternFailureException;
import com.prayer.exception.validator.PrecisionFailureException;
import com.prayer.exception.validator.RangeFailureException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.kernel.Validator;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.plugin.AbstractValidatorAspect;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.type.DataType;
import com.prayer.util.string.StringKit;

/**
 * 内部验证器，验证几个特殊的Record中字段属性的约束信息
 * 
 * @author Lang
 *
 */
public aspect InternalValidatorAspect extends AbstractValidatorAspect {
    // ~ Point Cut ===========================================
    /**
     * 针对pattern的拦截点，因为set(String,String)内部调用了set(String,Value
     * <?>)，所以仅仅在set(String,Value<?>)植入就可以了
     **/
    pointcut ValidatorPointCut(final String field, final Value<?> value): execution(void com.prayer.model.crucial.*Record.set*(String,Value<?>)) && args(field,value) && target(Record);

    // ~ Point Cut Implementation ============================
    /** 针对pattern拦截点的实现，需要抛出异常信息 **/
    before(final String field, final Value<?> value) throws AbstractDatabaseException: ValidatorPointCut(field,value){
        if (Resources.DB_V_ENABLED) {
            // 1.获取被拦截的字段的Schema
            final PEField schema = this.getField(thisJoinPoint.getTarget(), field);
            // 2.Nullable的验证
            this.verifyNullable(schema, value);
            // 3.Pattern的验证
            this.verifyPattern(schema, value);
            // 4.Length的验证：minLength和maxLength
            this.verifyLength(schema, value);
            // 5.Range的验证：min和max
            this.verifyRange(schema, value);
            // 6.Precision的验证：precision和length
            this.verifyPrecision(schema, value);
        }
    }

    // ~ Private Methods =====================================

    private void verifyPrecision(final PEField schema, final Value<?> value) throws AbstractDatabaseException {
        if (null != schema && DataType.DECIMAL == value.getDataType()) {
            final int length = schema.getLength();
            final int precision = schema.getPrecision();
            // DecimalType必须包含上边两个属性
            final Validator validator = singleton("com.prayer.plugin.validator.PrecisionValidator");
            if (!validator.validate(value, precision, length)) {
                final BigDecimal curValue = (BigDecimal) value.getValue();
                throw new PrecisionFailureException(getClass(), schema.getName(), String.valueOf(length),
                        String.valueOf(precision),
                        value.literal() + ", length = " + curValue.precision() + ", precision = " + curValue.scale());
            }
        }
    }

    private void verifyRange(final PEField schema, final Value<?> value) throws AbstractDatabaseException {
        if (null != schema && (Arrays.asList(T_NUMBER).contains(value.getDataType()))) {
            final long min = schema.getMin();
            if (Constants.RANGE != min) {
                final Validator validator = singleton("com.prayer.plugin.validator.MinValidator");
                if (!validator.validate(value, min)) {
                    throw new RangeFailureException(getClass(), "min", schema.getName(), String.valueOf(min),
                            value.literal());
                }
            }
            final long max = schema.getMax();
            if (Constants.RANGE != max) {
                final Validator validator = singleton("com.prayer.plugin.validator.MaxValidator");
                if (!validator.validate(value, max)) {
                    throw new RangeFailureException(getClass(), "max", schema.getName(), String.valueOf(max),
                            value.literal());
                }
            }
        }
    }

    private void verifyLength(final PEField schema, final Value<?> value) throws AbstractDatabaseException {
        if (null != schema && (Arrays.asList(T_TEXT).contains(value.getDataType()))) {
            final int minLength = schema.getMinLength();
            if (Constants.RANGE != minLength) {
                final Validator validator = singleton("com.prayer.plugin.validator.MinLengthValidator");
                if (!validator.validate(value, minLength)) {
                    throw new LengthFailureException(getClass(), "min", schema.getName(), String.valueOf(minLength),
                            value.literal() + ", Length = " + value.literal().length());
                }
            }
            final int maxLength = schema.getMaxLength();
            if (Constants.RANGE != maxLength) {
                final Validator validator = singleton("com.prayer.plugin.validator.MaxLengthValidator");
                if (!validator.validate(value, maxLength)) {
                    throw new LengthFailureException(getClass(), "max", schema.getName(), String.valueOf(minLength),
                            value.literal() + ", Length = " + value.literal().length());
                }
            }
        }
    }

    private void verifyNullable(final PEField schema, final Value<?> value) throws AbstractDatabaseException {
        if (null != schema) {
            final boolean isNull = schema.isNullable();
            if (!isNull) {
                final Validator validator = singleton("com.prayer.plugin.validator.NotNullValidator");
                if (!validator.validate(value, Constants.T_OBJ_ARR)) {
                    throw new NotNullFailureException(getClass(), schema.getName());
                }
            }
        }
    }

    /** **/
    private void verifyPattern(final PEField schema, final Value<?> value) throws AbstractDatabaseException {
        if (null != schema && (Arrays.asList(T_TEXT).contains(value.getDataType()))) {
            final String pattern = schema.getPattern();
            if (StringKit.isNonNil(pattern)) {
                final Validator validator = singleton("com.prayer.plugin.validator.PatternValidator");
                if (!validator.validate(value, pattern)) {
                    throw new PatternFailureException(getClass(), value.literal(), schema.getName(), pattern);
                }
            }
        }
    }
}