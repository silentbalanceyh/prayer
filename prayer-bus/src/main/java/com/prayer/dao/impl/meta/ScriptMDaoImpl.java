package com.prayer.dao.impl.meta;

import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.impl.schema.ScriptDaoImpl;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.facade.dao.schema.ScriptDao;
import com.prayer.facade.dao.schema.TemplateDao;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.kernel.Value;
import com.prayer.model.bus.OrderBy;
import com.prayer.model.bus.Pager;
import com.prayer.model.vertx.ScriptModel;

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

    @Override
    public Record selectById(Record record, Value<?> uniqueId) throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Record selectById(Record record, ConcurrentMap<String, Value<?>> uniqueIds)
            throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean delete(Record record) throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Record> queryByFilter(Record record, String[] columns, List<Value<?>> params, Expression filters)
            throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Record> queryByFilter(Record record, String[] columns, List<Value<?>> params, Expression filters,
            OrderBy orders) throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ConcurrentMap<Long, List<Record>> queryByPage(Record record, String[] columns, List<Value<?>> params,
            Expression filters, OrderBy orders, Pager pager) throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Record update(Record record) throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
