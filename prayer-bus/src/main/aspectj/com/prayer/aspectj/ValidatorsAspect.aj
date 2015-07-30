package com.prayer.aspectj;

import static com.prayer.util.Instance.singleton;

import java.util.Arrays;

import com.prayer.constant.Constants;
import com.prayer.exception.AbstractMetadataException;
import com.prayer.exception.validator.LengthFailureException;
import com.prayer.exception.validator.NotNullFailureException;
import com.prayer.exception.validator.PatternFailureException;
import com.prayer.kernel.Record;
import com.prayer.kernel.Validator;
import com.prayer.kernel.Value;
import com.prayer.kernel.validator.MaxLengthValidator;
import com.prayer.kernel.validator.MinLengthValidator;
import com.prayer.kernel.validator.NotNullValidator;
import com.prayer.kernel.validator.PatternValidator;
import com.prayer.model.h2.FieldModel;
import com.prayer.util.StringKit;

/**
 * 内部验证器，验证几个特殊的Record中字段属性的约束信息
 * 
 * @author Lang
 *
 */
public aspect ValidatorsAspect extends AbstractValidatorAspect {
	// ~ Point Cut ===========================================
	/**
	 * 针对pattern的拦截点，因为set(String,String)内部调用了set(String,Value
	 * <?>)，所以仅仅在set(String,Value<?>)植入就可以了
	 **/
	pointcut PatternCut(final String field, final Value<?> value): execution(void com.prayer.kernel.model.GenericRecord.set*(String,Value<?>)) && args(field,value) && target(Record);

	// ~ Point Cut Implementation ============================
	/** 针对pattern拦截点的实现，需要抛出异常信息 **/
	before(final String field, final Value<?> value) throws AbstractMetadataException: PatternCut(field,value){
		// 1.获取被拦截的字段的Schema
		final FieldModel schema = this.getField(thisJoinPoint.getTarget(), field);
		// 2.Nullable的验证
		this.verifyNullable(schema, value);
		// 3.Pattern的验证
		this.verifyPattern(schema, value);
		// 4.Length的验证：minLength和maxLength
		this.verifyLength(schema, value);
	}

	// ~ Private Methods =====================================
	
	private void verifyLength(final FieldModel schema, final Value<?> value) throws AbstractMetadataException{
		if(null != schema && (Arrays.asList(T_PATTERNS).contains(value.getDataType()))){
			final int minLength = schema.getMinLength();
			if(Constants.RANGE != minLength){
				final Validator validator = singleton(MinLengthValidator.class);
				if(!validator.validate(value,minLength)){
					throw new LengthFailureException(getClass(), "min", schema.getName(), String.valueOf(minLength), value.literal());
				}
			}
			final int maxLength = schema.getMaxLength();
			if(Constants.RANGE != maxLength){
				final Validator validator = singleton(MaxLengthValidator.class);
				if(!validator.validate(value,maxLength)){
					throw new LengthFailureException(getClass(), "max", schema.getName(), String.valueOf(minLength), value.literal());
				}
			}
		}
	}

	private void verifyNullable(final FieldModel schema, final Value<?> value) throws AbstractMetadataException {
		if (null != schema) {
			final boolean isNull = schema.isNullable();
			if (!isNull) {
				final Validator validator = singleton(NotNullValidator.class);
				if (!validator.validate(value, Constants.T_OBJ_ARR)) {
					throw new NotNullFailureException(getClass(), schema.getName());
				}
			}
		}
	}

	/** **/
	private void verifyPattern(final FieldModel schema, final Value<?> value)
			throws AbstractMetadataException {
		if (null != schema && (Arrays.asList(T_PATTERNS).contains(value.getDataType()))) {
			final String pattern = schema.getPattern();
			if (StringKit.isNonNil(pattern)) {
				final Validator validator = singleton(PatternValidator.class);
				if (!validator.validate(value, pattern)) {
					throw new PatternFailureException(getClass(), value.literal(), schema.getName(), pattern);
				}
			}
		}
	}
}