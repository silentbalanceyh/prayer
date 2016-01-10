package com.prayer.accessor.mapper;

import com.prayer.base.accessor.AbstractIBatisAccessor;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonObject;
/**
 * IBATIS的Mapper接口测试
 * @author Lang
 *
 */
public abstract class AbstractMapperTestCase<T> extends AbstractIBatisAccessor{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    
    /**
     * 读取JsonObject的方法
     * 
     * @param file
     * @return
     */
    protected JsonObject readData(final String file) {
        final String content = IOKit.getContent(file);
        final JsonObject json = new JsonObject();
        if (null != content) {
            json.mergeIn(new JsonObject(content));
        }
        return json;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
