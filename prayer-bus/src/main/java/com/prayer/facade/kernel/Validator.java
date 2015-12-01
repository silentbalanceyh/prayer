package com.prayer.facade.kernel;

import com.prayer.base.exception.AbstractDatabaseException;

/**
 * 格式校验接口
 *
 * @author Lang
 * @see
 */
public interface Validator {
    /**
     * 校验字符串格式
     * 
     * @return
     */
    boolean validate(Value<?> value, Object... params) throws AbstractDatabaseException;
}
