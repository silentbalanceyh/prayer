package com.prayer;

import static com.prayer.util.reflection.Instance.reservoir;
import static org.junit.Assert.fail;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.util.io.PropertyKit;

/**
 * Prayer测试框架基类，用于测试系统的基础
 * 
 * @author Lang
 *
 */
public abstract class AbstractCommonTool implements ErrorKeys { // NOPMD
    // ~ Static Fields =======================================
    /**
     * Error property ASSERT_LOADER to read Error Message
     */
    private static PropertyKit ASSERT_LOADER = new PropertyKit(AbstractCommonTool.class, "/asserts.properties");
    /** **/
    protected static final ConcurrentMap<String, PropertyKit> OBJ_POOLS = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    /** **/
    private transient PropertyKit loader;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================

    // ~ Constructors ========================================
    /** **/
    public AbstractCommonTool() {
        loader = reservoir(OBJ_POOLS, getClass().getName(), PropertyKit.class, "/proploader.properties");
    }

    // ~ Abstract Methods ====================================
    /** 获取当前类的日志器 **/
    protected abstract Logger getLogger();

    /** 获取被测试类类名 **/
    protected abstract Class<?> getTarget();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 格式化消息
     * 
     * @param messageKey
     * @param params
     * @return
     */
    protected String message(final String messageKey, final Object... params) {
        // 生成测试用例消息前缀
        final StringBuilder message = new StringBuilder();
        message.append('[').append(messageKey).append(']');
        // 是否提供了Class位置，如果没有提供则不输出Class
        final Class<?> clazz = getTarget();
        if (null != clazz) {
            message.append(" Class -> ").append(clazz.getName()).append(" |");
        }
        message.append(' ').append(MessageFormat.format(ASSERT_LOADER.getString(messageKey), params));
        return message.toString();
    }

    /**
     * 输出Debug信息
     * 
     * @param logger
     * @param messageKey
     * @param error
     * @param params
     * @return
     */
    protected void debug(final Logger logger, final String messageKey, final Throwable error, final Object... params) {
        if (logger.isDebugEnabled()) {
            if (null == error) {
                logger.debug(message(messageKey, params));
            } else {
                logger.debug(message(messageKey, params), error);
            }
        }
    }

    /** 一般用于异常测试，异常如果没有抛出来则手动设置failure **/
    protected void failure(final String messageKey, final Object... params) {
        fail(message(messageKey, params));
    }

    /** **/
    protected void failure(final Object object) {
        fail("[TEST] Class -> " + getClass() + ", Message -> " + object);
    }
    
    /**
     * 获取资源加载器
     * 
     * @return
     */
    protected PropertyKit getLoader() {
        return loader;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
