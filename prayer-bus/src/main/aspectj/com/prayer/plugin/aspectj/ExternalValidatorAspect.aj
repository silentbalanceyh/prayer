package com.prayer.plugin.aspectj;

import static com.prayer.util.Instance.singleton;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.validator.CustomValidatorException;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.kernel.Validator;
import com.prayer.facade.kernel.Value;
import com.prayer.model.h2.schema.FieldModel;
import com.prayer.util.StringKit;

/**
 * 外部验证器：调用自定义验证Validator验证Record中的字段
 * 
 * @author Lang
 *
 */
public aspect ExternalValidatorAspect extends AbstractValidatorAspect {
    // ~ Point Cut ===========================================
    /**
     * 针对pattern的拦截点，因为set(String,String)内部调用了set(String,Value
     * <?>)，所以仅仅在set(String,Value<?>)植入就可以了
     **/
    pointcut ValidatorPointCut(final String field, final Value<?> value): execution(void com.prayer.model.kernel.GenericRecord.set*(String,Value<?>)) && args(field,value) && target(Record);

    // ~ Point Cut Implementation ============================
    /** 针对pattern拦截点的实现，需要抛出异常信息 **/
    after(final String field, final Value<?> value) throws AbstractDatabaseException: ValidatorPointCut(field,value){
        // 1.获取被拦截的字段的Schema
        final FieldModel schema = this.getField(thisJoinPoint.getTarget(), field);
        if (null != schema) {
            // 2.获取Validator名称
            final String validatorClass = schema.getValidator();
            if (StringKit.isNonNil(validatorClass)) {
                final Validator validator = singleton(validatorClass);
                if (!validator.validate(value)) {
                    throw new CustomValidatorException(getClass(), validatorClass);
                }
            }
        }
    }
}
