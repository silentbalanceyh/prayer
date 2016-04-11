package com.prayer.configuration;

import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.assertNotEquals;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.prayer.configuration.impl.ConfigBllor;
import com.prayer.facade.configuration.ConfigInstantor;
import com.prayer.model.meta.vertx.PEVerticle;

/**
 * 
 * @author Lang
 *
 */
public class ConfigServiceTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 发布用的Service **/
    private transient final ConfigInstantor configer = singleton(ConfigBllor.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 在执行整个类中的TestCase之前 **/
    @BeforeClass
    public static void setClsUp() {
        EnvInitializer.reference().prepareEnv();
    }

    /** **/
    @Test
    public void testVerticles() {
        final ConcurrentMap<String, List<PEVerticle>> ret = configer.verticles();
        assertNotEquals(0, ret.size());
    }

    /** **/
    @Test
    public void testVerticles2() {
        final List<PEVerticle> ret = configer.verticles("__DEFAULT__");
        assertNotEquals(0, ret.size());
    }
    // ~ Private Methods =====================================

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
