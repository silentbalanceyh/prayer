package com.prayer.business.instantor.config;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.AbstractInstantor;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.entity.Entity;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEUri;

/**
 * 
 * @author Lang
 *
 */
public class ConfigUCAObtainerTestCase extends AbstractInstantor {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUCAObtainerTestCase.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger(){
        return LOGGER;
    }
    // ~ Methods =============================================
    /** **/
    @Test
    public void testUCA() throws AbstractException{
        /** 1.准备数据 **/
        final List<Entity> entities = this.preparedListData("instantor3-config-rule.json", PERule.class);
        final List<Entity> uris = this.preparedListData("instantor3-config-uri.json", PEUri.class);
        String uriId = null;
        {
            if(Constants.ONE == uris.size()){
                final Entity uri = uris.get(Constants.IDX);
                uriId = uri.id().toString();
                for(final Entity entity: entities){
                    final PERule rule = (PERule)entity;
                    rule.setRefUID(uri.id().toString());
                    this.accessor(PERule.class).update(rule);
                }
            }
        }
        /** 2.调用Config接口读取数据 **/
        final ConfigInstantor instantor = this.getConfigItor();
        /** 3.调用UCA接口 -- Dependants **/
        final ConcurrentMap<String,List<PERule>> dependants = instantor.dependants(uriId);
        assertEquals(3,dependants.size());
        assertEquals(1,dependants.get("TEST.RULE6").size());
        assertEquals(1,dependants.get("TEST.RULE5").size());
        assertEquals(1,dependants.get("TEST.RULE4").size());
        /** 3.调用UCA接口 -- Convertors **/
        final ConcurrentMap<String,List<PERule>> convertors = instantor.convertors(uriId);
        assertEquals(2,convertors.size());
        assertEquals(1,convertors.get("TEST.RULE6").size());
        assertEquals(1,convertors.get("TEST.RULE5").size());
        /** 3.调用UCA接口 -- Validators **/
        final ConcurrentMap<String,List<PERule>> validators = instantor.validators(uriId);
        assertEquals(5,validators.size());
        assertEquals(1,validators.get("TEST.RULE4").size());
        assertEquals(3,validators.get("TEST.RULE1").size());
        assertEquals(2,validators.get("TEST.RULE2").size());
        assertEquals(2,validators.get("TEST.RULE3").size());
        assertEquals(2,validators.get("TEST.RULE7").size());
        /** 4.删除数据 **/
        this.purgeListData(entities, PERule.class);
        this.purgeListData(uris, PEUri.class);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
