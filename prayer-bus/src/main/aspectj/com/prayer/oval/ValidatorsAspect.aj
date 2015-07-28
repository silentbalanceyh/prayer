package com.prayer.oval;

import com.prayer.kernel.Value;

/**
 * 内部验证器，验证几个特殊的Record中字段属性的约束信息
 * 
 * @author Lang
 *
 */
public aspect ValidatorsAspect {
	/** 
	 * 针对pattern的拦截点，因为set(String,String)内部调用了set(String,Value<?>)，所以仅仅在set(String,Value<?>)植入就可以了
	 * **/
	pointcut PatternCut(String field, Value<?> value): execution(void com.prayer.kernel.model.GenericRecord.set*(String,Value<?>)) && args(field,value);

	/** 针对pattern拦截点的实现 **/
	before(String field, Value<?> value): PatternCut(field,value) {
		System.out.println(field + "=" + value.literal());
		
	}
}
