package com.prayer.dao.meta.impl;

import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.kernel.query.OrderBy;
import com.prayer.kernel.query.Pager;
import com.prayer.model.h2.script.ScriptModel;
import com.prayer.schema.dao.ScriptDao;
import com.prayer.schema.dao.TemplateDao;

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
final class ScriptDaoImpl extends AbstractMetaDaoImpl<ScriptModel,String> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptDaoImpl.class);
    // ~ Instance Fields =====================================
    /** Schema Dao **/
    @NotNull
    @InstanceOf(TemplateDao.class)
    private transient ScriptDao dao;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public ScriptDaoImpl(){
        this.dao = singleton(com.prayer.schema.dao.impl.ScriptDaoImpl.class);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public ScriptDao getDao(){
        return this.dao;
    }
    /** **/
    @Override
    public Logger getLogger(){
        return LOGGER;
    }
    @Override
    public Record insert(Record record) throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Record update(Record record) throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
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
    
    
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
