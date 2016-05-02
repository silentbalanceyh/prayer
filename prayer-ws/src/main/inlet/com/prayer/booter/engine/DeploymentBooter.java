package com.prayer.booter.engine;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.reservoir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.Launcher;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.launcher.common.DeploymentLauncher;

import net.sf.oval.internal.Log;
import net.sf.oval.logging.LoggerFactorySLF4JImpl;

/**
 * MetaServer的部署器，用于执行标准目录的部署
 * 
 * @author Lang
 *
 */
public class DeploymentBooter {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DeploymentBooter.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 执行器
     */
    public static void main(final String[] args) {
        /** 1.在Consoler中替换日志输出 **/
        Log.setLoggerFactory(new LoggerFactorySLF4JImpl());
        /** 2.是否处理Schema部分的Purge和Deploy **/
        // TODO：后期从args中传递
        final boolean schema = false;
        final Launcher launcher = reservoir(String.valueOf(schema), DeploymentLauncher.class, schema);
        try {
            launcher.start();
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
        }
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
