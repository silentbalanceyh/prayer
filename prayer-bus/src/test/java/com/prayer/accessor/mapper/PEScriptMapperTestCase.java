package com.prayer.accessor.mapper;

import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.facade.entity.Entity;
import com.prayer.facade.metadata.mapper.IBatisMapper;
import com.prayer.facade.metadata.mapper.PEScriptMapper;
import com.prayer.model.vertx.PEScript;

/**
 * 
 * @author Lang
 *
 */
public class PEScriptMapperTestCase extends AbstractMapperTestCase<PEScript> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    @Override
    protected Logger getLogger() {
        // TODO Auto-generated method stub
        return null;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testInsert() throws AbstractDatabaseException {
        final PEScript instance = new PEScript(this.readData("/mapper/pescript/script.json"));
        final IBatisMapper<Entity,String> mapper = (IBatisMapper<Entity,String>)session().getMapper(PEScriptMapper.class);
        mapper.insert(instance);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
