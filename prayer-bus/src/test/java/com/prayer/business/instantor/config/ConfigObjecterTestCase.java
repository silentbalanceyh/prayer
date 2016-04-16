package com.prayer.business.instantor.config;

import static com.prayer.util.reflection.Instance.clazz;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.AbstractInstantor;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.model.entity.Entity;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PEAddress;
import com.prayer.model.meta.vertx.PEScript;

/**
 * 
 * @author Lang
 *
 */
public class ConfigObjecterTestCase extends AbstractInstantor {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigObjecterTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testScript() throws AbstractException {
        /** 1.准备数据 **/
        final List<Entity> entities = this.preparedListData("instantor1-config-script.json", PEScript.class);
        /** 2.调用Config接口读取数据 **/
        final ConfigInstantor instantor = this.getConfigItor();
        /** 3.调用script接口 **/
        final PEScript existing = instantor.script("LANG2");
        assertNotNull(existing);
        final PEScript none = instantor.script("TEST1");
        assertNull(none);
        /** 4.删除数据 **/
        this.purgeListData(entities, PEScript.class);
    }

    /** **/
    @Test
    public void testAddress() throws AbstractException {
        /** 1.准备数据 **/
        final List<Entity> entities = this.preparedListData("instantor1-config-address.json", PEAddress.class);
        /** 2.调用Config接口读取数据 **/
        final ConfigInstantor instantor = this.getConfigItor();
        /** 3.调用address接口 **/
        final PEAddress address = instantor.address(clazz("com.prayer.model.entity.clazz.EntityHandler"));
        assertNotNull(address);
        final PEAddress none = instantor.address(clazz("com.prayer.model.entity.clazz.NoneHandler"));
        assertNull(none);
        /** 4.删除数据 **/
        this.purgeListData(entities, PEAddress.class);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
