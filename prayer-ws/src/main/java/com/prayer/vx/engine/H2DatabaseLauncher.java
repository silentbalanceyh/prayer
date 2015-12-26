package com.prayer.vx.engine;

import static com.prayer.util.Instance.singleton;

import com.prayer.base.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public final class H2DatabaseLauncher {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final H2DatabaseServer server = singleton(H2DatabaseServer.class);
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 创建启动器
     * 
     * @return
     */
    public static H2DatabaseLauncher create() {
        return new H2DatabaseLauncher();
    }
    
    /** **/
    public static void main(final String... args) throws Exception {
        final H2DatabaseLauncher launcher = H2DatabaseLauncher.create();
        launcher.runTool(args);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    
    public void runTool(final String... args) throws AbstractException{
        this.server.start();
    }
    // ~ Private Methods =====================================
    private H2DatabaseLauncher() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
