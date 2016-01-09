package com.prayer.starter;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.base.exception.AbstractException;
import com.prayer.engine.H2DatabaseServer;

/**
 * H2 Database的单独启动类
 * @author Lang
 *
 */
public final class MetaServerStarter {
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
    public static MetaServerStarter create() {
        return new MetaServerStarter();
    }
    
    /** **/
    public static void main(final String... args) throws AbstractException {
        final MetaServerStarter launcher = MetaServerStarter.create();
        launcher.runTool(args);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param args
     * @throws AbstractException
     */
    public void runTool(final String... args) throws AbstractException{
        this.server.start();
    }
    // ~ Private Methods =====================================
    private MetaServerStarter() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
