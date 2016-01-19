package com.prayer.plugin.aspectj;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.base.plugin.AbstractValidatorAspect;
import com.prayer.constant.Resources;
import com.prayer.exception.validator.CustomValidatorException;
import com.prayer.facade.kernel.Validator;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.record.Record;
import com.prayer.model.meta.database.PEField;

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
        if (Resources.DB_V_ENABLED) {
            // 1.获取被拦截的字段的Schema
            final PEField schema = this.getField(thisJoinPoint.getTarget(), field);
            if (null != schema) {
                // 2.获取Validator名称
                final Validator validator = singleton(schema.getValidator());
                if (!validator.validate(value)) {
                    throw new CustomValidatorException(getClass(), schema.getValidator().getName());
                }
            }
        }
    }
}
