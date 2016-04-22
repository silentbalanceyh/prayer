package com.prayer.util.debug;

import static com.prayer.util.Planar.flat;

import java.text.MessageFormat;

import org.apache.log4j.Level;

import com.prayer.constant.log.DebugKey;
import com.prayer.constant.log.ErrorKey;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.InceptBus;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.exception.ConstraintsViolatedException;
import net.sf.oval.guard.Guarded;

/**
 * <code>INFO级别不输出Exception信息</code>
 * <code>DEBUG级别会输出Exception，并且输出Exception中的Message，如果开启了Prayer的DEBUG则打印Stack堆栈信息</code>
 * <code>ERROR发生的时候输出Error信息，并且输出Error中的Message</code>
 * <code>开启了TRACE的时候在Debug时打印堆栈信息</code>
 * 
 * @author Lang
 *
 */
@Guarded
public final class Log { // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Special Error =======================================

    /**
     * JVM级别的异常信息
     * 
     * @param logger
     * @param exp
     */
    public static void jvmError(@NotNull final org.slf4j.Logger logger, @NotNull final Throwable exp) {
        debug(logger, DebugKey.EXP_JVM, exp, exp.getClass().getName(), exp.getMessage());
    }

    /**
     * Oval异常信息主要是ConstraintsViolatedException异常信息
     * 
     * @param logger
     * @param exp
     */
    public static void ovalError(@NotNull final org.slf4j.Logger logger,
            @NotNull final ConstraintsViolatedException exp) {
        debug(logger, DebugKey.EXP_OVAL, exp, exp.getClass().getName(), exp.getMessage());
    }

    /**
     * AbstractException异常信息
     * 
     * @param logger
     * @param exp
     */
    public static void peError(@NotNull final org.slf4j.Logger logger, @NotNull final AbstractException exp) {
        error(logger, ErrorKey.ERR_ENGINE, exp, exp.getClass().getName(), exp.getErrorMessage());
        if (logger.isTraceEnabled()) {
            exp.printStackTrace();// NOPMD
        }
    }

    /**
     * AbstractException异常信息
     * 
     * @param logger
     * @param exp
     */
    public static void peDebug(@NotNull final org.slf4j.Logger logger, @NotNull final AbstractException exp) {
        debug(logger, ErrorKey.ERR_ENGINE, exp, exp.getClass().getName(), exp.getErrorMessage());
        if (logger.isTraceEnabled()) {
            exp.printStackTrace();// NOPMD
        }
    }
    // ~ Static Block ========================================

    /**
     * INFO级别的输出信息
     * 
     * @param logger
     * @param key
     * @param params
     */
    public static void info(@NotNull final org.slf4j.Logger logger, @NotNull final String key, final Object... params) {
        String message = null;
        if (Constants.ZERO == params.length) {
            message = message(Level.INFO, key);
        } else {
            message = MessageFormat.format(message(Level.INFO, key), params);
        }
        if (logger.isInfoEnabled()) {
            // Output
            logger.info(message);
        }
    }

    /**
     * ERROR级别的输出信息
     * 
     * @param logger
     * @param key
     * @param params
     */
    public static void error(@NotNull final org.slf4j.Logger logger, @NotNull final String key,
            final Object... params) {
        error(logger, key, null, params);
    }

    /**
     * ERROR级别的输出信息
     * 
     * @param logger
     * @param key
     * @param exp
     * @param params
     */
    public static void error(@NotNull final org.slf4j.Logger logger, @NotNull final String key, final Throwable exp,
            final Object... params) {
        if (logger.isErrorEnabled()) {
            String message = null; // NOPMD
            if (Constants.ZERO == params.length) {
                message = message(Level.ERROR, key);
            } else {
                message = MessageFormat.format(message(Level.ERROR, key), params);
            }
            // Output
            if (null == exp) {
                logger.error(message);
            } else {
                if (exp instanceof AbstractException) {
                    final AbstractException error = (AbstractException) exp;
                    logger.error("[ *** ERROR" + error.getErrorCode() + " *** ] " + message);
                } else {
                    logger.error("[ *** JVM ERROR *** ] : " + message);
                }
            }
        }
    }

    /**
     * Debug不带异常输出
     * 
     * @param logger
     * @param key
     * @param params
     */
    public static void debug(@NotNull final org.slf4j.Logger logger, @NotNull final String key,
            final Object... params) {
        debug(logger, key, null, params);
    }

    /**
     * Debug带异常信息输出
     * 
     * @param logger
     * @param key
     * @param exp
     * @param params
     */
    public static void debug(@NotNull final org.slf4j.Logger logger, @NotNull final String key, final Throwable exp,
            final Object... params) {
        if (logger.isDebugEnabled()) {
            String message = null; // NOPMD
            if (Constants.ZERO == params.length) {
                message = message(Level.DEBUG, key);
            } else {
                message = MessageFormat.format(message(Level.DEBUG, key), params);
            }
            // Output
            logger.debug(message);
        }
        if (logger.isTraceEnabled() && null != exp) {
            exp.printStackTrace(); // NOPMD
        }
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private static String message(final Level level, final String key) {
        String ret = null;
        switch (level.toInt()) {
        case Level.WARN_INT:
        case Level.ERROR_INT:
            ret = flat(InceptBus.build(Point.System.class, Point.System.LOGS_ERROR).getString(key), key);
            break;
        case Level.DEBUG_INT:
        case Level.TRACE_INT:
            ret = flat(InceptBus.build(Point.System.class, Point.System.LOGS_DEBUG).getString(key), key);
            break;
        default:
            ret = flat(InceptBus.build(Point.System.class, Point.System.LOGS_INFO).getString(key), key);
            break;
        }
        return ret;
    }

    private Log() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
