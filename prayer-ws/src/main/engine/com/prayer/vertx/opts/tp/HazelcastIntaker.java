package com.prayer.vertx.opts.tp;

import com.hazelcast.config.Config;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.fantasm.exception.AbstractException;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class HazelcastIntaker implements Intaker<Config> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Config ingest() throws AbstractException {
        // TODO Hazelcast扩展，用于读取配置，现在不设置
        return new Config();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
