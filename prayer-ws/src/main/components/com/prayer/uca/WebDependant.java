package com.prayer.uca;

import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.exception.AbstractWebException;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public interface WebDependant {
    /** **/
    String VAL_RET = "VRET";
    /** **/
    String CVT_RET = "CVET";

    /**
     * 很复杂的一个接口，综合了Convertor和Validator的功能，唯一不同的是会提供一个扩展查询SQL，
     * 用于让另外的关联数据和本身数据进行联合处理，包括联合验证以及联合转换
     * 
     * @param name
     * @param value
     * @param config
     * @param sqlQuery
     *            这个SQL Query返回单列的某个字段的值
     * @return
     * @throws AbstractWebException
     */
    JsonObject process(String name, Value<?> value, JsonObject config, String sqlQuery) throws AbstractWebException;
}
