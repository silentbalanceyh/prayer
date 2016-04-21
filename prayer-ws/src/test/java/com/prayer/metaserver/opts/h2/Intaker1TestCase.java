package com.prayer.metaserver.opts.h2;

import static com.prayer.util.reflection.Instance.singleton;

import org.junit.Test;

import com.prayer.exception.launcher.ParameterMissedException;
import com.prayer.exception.launcher.ReferenceFileException;
import com.prayer.facade.engine.OptionsIntaker;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.metaserver.h2.H2OptionsIntaker;

/**
 * 
 * @author Lang
 *
 */
public class Intaker1TestCase {
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
    @Test(expected = ParameterMissedException.class)
    public void testReqOpts1() throws AbstractException {
        INTAKER.ingest("/metaserver/opts/invalid/required1.properties");
    }

    /** **/
    @Test(expected = ParameterMissedException.class)
    public void testReqOpts2() throws AbstractException {
        INTAKER.ingest("/metaserver/opts/invalid/required2.properties");
    }

    /** **/
    @Test(expected = ParameterMissedException.class)
    public void testReqOpts3() throws AbstractException {
        INTAKER.ingest("/metaserver/opts/invalid/required3.properties");
    }

    /** **/
    @Test(expected = ParameterMissedException.class)
    public void testReqOpts4() throws AbstractException {
        INTAKER.ingest("/metaserver/opts/invalid/required4.properties");
    }

    /** **/
    @Test(expected = ParameterMissedException.class)
    public void testReqOpts5() throws AbstractException {
        INTAKER.ingest("/metaserver/opts/invalid/required5.properties");
    }

    /** **/
    @Test(expected = ReferenceFileException.class)
    public void testRefOpts6() throws AbstractException {
        INTAKER.ingest("/metaserver/opts/invalid/file1.properties");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
