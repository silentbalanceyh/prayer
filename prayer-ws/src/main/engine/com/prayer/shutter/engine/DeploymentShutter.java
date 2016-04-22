package com.prayer.shutter.engine;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.common.launcher.DeploymentLauncher;
import com.prayer.facade.engine.Launcher;
import com.prayer.fantasm.exception.AbstractException;

/**
 * MetaServer的部署器，用于执行标准目录的部署
 * 
 * @author Lang
 *
 */
public class DeploymentShutter {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DeploymentShutter.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 执行器
     */
    public static void main(final String[] args) {
        final Launcher launcher = singleton(DeploymentLauncher.class);
        try {
            launcher.stop();
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
