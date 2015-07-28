package com.prayer.aspectj;

import java.util.Arrays;

import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.model.h2.FieldModel;

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
	/** 针对pattern拦截点的实现 **/
	before(final String field, final Value<?> value): PatternCut(field,value) {
		// 1.获取被拦截的字段的Schema
		final FieldModel fieldSchema = this.getField(thisJoinPoint.getTarget(), field);
		if (null != fieldSchema) {
			final String pattern = fieldSchema.getPattern();
			if (null != pattern && (Arrays.asList(T_PATTERNS).contains(value.getDataType()))) {

			}
		}
	}
	// ~ Private Methods =====================================
}