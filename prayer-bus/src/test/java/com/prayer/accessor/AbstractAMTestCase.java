package com.prayer.accessor;

import static com.prayer.util.reflection.Instance.genericT;

import org.slf4j.Logger;

import com.prayer.accessor.impl.MetaAccessorImpl;
import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonObject;

/**
 * IBATIS的Mapper接口测试
 * 
 * @author Lang
 *
 */
public abstract class AbstractAMTestCase<T> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient MetaAccessor accessor;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractAMTestCase() {
        final Class<?> tCls = genericT(getClass());
        System.out.println(tCls);
        if (null != tCls) {
            this.accessor = new MetaAccessorImpl(tCls);
        }
    }

    // ~ Abstract Methods ====================================
    /** 返回日志记录器 **/
    protected abstract Logger getLogger();

    // ~ Override Methods ====================================
    /**
     * 
     * @return
     */
    protected MetaAccessor getAccessor(){
        return this.accessor;
    }
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

    // ~ Template Methods ====================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
