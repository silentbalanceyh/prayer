package com.prayer.dao.impl.std.meta;

import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.dao.AbstractMDaoImpl;
import com.prayer.dao.impl.metadata.ScriptDaoImpl;
import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.facade.dao.metadata.ScriptDao;
import com.prayer.model.vertx.PEScript;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class ScriptMDaoImpl extends AbstractMDaoImpl<PEScript, String> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptMDaoImpl.class);
    // ~ Instance Fields =====================================
    /** Schema Dao **/
    @InstanceOf(MetaAccessor.class)
    private transient final ScriptDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public ScriptMDaoImpl() {
        super();
        this.dao = singleton(ScriptDaoImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @NotNull
    public ScriptDao getDao() {
        return this.dao;
    }

    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    
    /** **/
    @Override
    public PEScript newT(){
        return new PEScript();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
