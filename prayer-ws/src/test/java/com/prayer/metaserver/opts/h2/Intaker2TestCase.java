package com.prayer.metaserver.opts.h2;

import static com.prayer.util.reflection.Instance.singleton;

import org.junit.Test;

import com.prayer.facade.engine.metaserver.OptionsIntaker;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.metaserver.h2.H2OptionsIntaker;

/**
 * 
 * @author Lang
 *
 */
public class Intaker2TestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final OptionsIntaker INTAKER = singleton(H2OptionsIntaker.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Test
    public void testValidOpts1() throws AbstractException {
        INTAKER.ingest("/metaserver/opts/valid/config.properties");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
