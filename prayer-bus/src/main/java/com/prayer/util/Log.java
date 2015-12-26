package com.prayer.util;

import java.text.MessageFormat;

import org.apache.log4j.Level;

import com.prayer.base.exception.AbstractException;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.Resources;
import com.prayer.util.cv.log.DebugKey;
import com.prayer.util.cv.log.ErrorKey;

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
    /** Info Properties **/
    private static final PropertyKit I_LOADER = new PropertyKit(Log.class, // NOPMD
            Resources.LOG_CFG_FOLDER + "/info.properties");
    /** Debug, Trace Properties **/
    private static final PropertyKit D_LOADER = new PropertyKit(Log.class, // NOPMD
            Resources.LOG_CFG_FOLDER + "/debug.properties");
    /** Error, Warning Properties **/
    private static final PropertyKit E_LOADER = new PropertyKit(Log.class, // NOPMD
            Resources.LOG_CFG_FOLDER + "/error.properties");

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
        if (Resources.IS_CONSOLE) {
            peDebug(logger, exp);
        } else {
            error(logger, ErrorKey.ERR_ENGINE, exp, exp.getClass().getName(), exp.getErrorMessage());
            if (logger.isTraceEnabled()) {
                exp.printStackTrace();// NOPMD
            }
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
        if (logger.isInfoEnabled() && !Resources.IS_CONSOLE) {
            String message = null;
            if (Constants.ZERO == params.length) {
                message = message(Level.INFO, key);
            } else {
                message = MessageFormat.format(message(Level.INFO, key), params);
            }
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
                logger.error(message + "***ERROR*** : " + exp.getMessage());
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
            ret = null == E_LOADER.getString(key) ? key : E_LOADER.getString(key);
            break;
        case Level.DEBUG_INT:
        case Level.TRACE_INT:
            ret = null == D_LOADER.getString(key) ? key : D_LOADER.getString(key);
            break;
        default:
            ret = null == I_LOADER.getString(key) ? key : I_LOADER.getString(key);
            break;
        }
        return ret;
    }

    private Log() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
