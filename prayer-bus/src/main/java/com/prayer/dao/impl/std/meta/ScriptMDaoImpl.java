package com.prayer.dao.impl.std.meta;

import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.dao.AbstractMDaoImpl;
import com.prayer.dao.impl.schema.ScriptDaoImpl;
import com.prayer.facade.dao.schema.ScriptDao;
import com.prayer.facade.dao.schema.TemplateDao;
import com.prayer.model.h2.vertx.ScriptModel;

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
final class ScriptMDaoImpl extends AbstractMDaoImpl<ScriptModel, String> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptMDaoImpl.class);
    // ~ Instance Fields =====================================
    /** Schema Dao **/
    @InstanceOf(TemplateDao.class)
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
    public ScriptModel newT(){
        return new ScriptModel();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
