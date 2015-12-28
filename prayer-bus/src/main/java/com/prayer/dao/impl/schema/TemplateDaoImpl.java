package com.prayer.dao.impl.schema;

import static com.prayer.util.Generator.uuid;
import static com.prayer.util.Instance.field;
import static com.prayer.util.Instance.reservoir;
import static com.prayer.util.Log.peError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.dao.AbstractDaoImpl;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.dao.impl.jdbc.H2ConnImpl;
import com.prayer.exception.database.MapperClassNullException;
import com.prayer.facade.dao.jdbc.JdbcContext;
import com.prayer.facade.dao.schema.TemplateDao;
import com.prayer.facade.mapper.H2TMapper;
import com.prayer.facade.mapper.SessionManager;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.MemoryPool;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 一个Dao的标准实现模型，包含了模板方法
 * 
 * @author Lang
 *
 */
@SuppressWarnings("unchecked")
@Guarded
public class TemplateDaoImpl<T, ID extends Serializable> extends AbstractDaoImpl implements TemplateDao<T, ID> { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateDaoImpl.class);
    /** Exception Class **/
    protected static final String EXP_CLASS = "com.prayer.exception.vertx.DataAccessException";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 日志记录器 **/
    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    /** 获取Mapper类型 **/
    protected Class<?> getMapper() {
        return null;
    }

    /** 可支持批量创建的创建方法 **/
    @Override
    public List<T> insert(@NotNull @MinSize(1) final T... entities) throws AbstractTransactionException {
        // 1.设置返回List
        final List<T> retList = new ArrayList<>();
        // 2.开启Mybatis事务处理
        final SqlSession session = SessionManager.getSession();
        final Transaction transaction = transaction(session);
        // 3.执行插入操作
        final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
        {
            if (Constants.ONE == entities.length) {
                final T entity = entities[0];
                // 3.1.1.单挑记录ID设置
                if (null == field(entity, Constants.PID)) {
                    field(entity, Constants.PID, uuid());
                }
                // 3.1.2.数据库插入
                mapper.insert(entity);
                retList.add(entity);
            } else {
                // 3.2.1.批量插入
                for (final T item : entities) {
                    if (null == field(item, Constants.PID)) {
                        field(item, Constants.PID, uuid());
                    }
                }
                // 3.2.2.批量处理插入
                final List<T> params = Arrays.asList(entities);
                mapper.batchInsert(params);
                retList.addAll(params);
            }
        }
        // 4.事务提交
        submit(transaction, EXP_CLASS);
        return retList;
    }

    /** 更新的模板方法 **/
    @Override
    public T update(@NotNull final T entity) throws AbstractTransactionException {
        // 1.开启Mybatis事务
        final SqlSession session = SessionManager.getSession();
        final Transaction transaction = transaction(session);
        // 2.获取Mapper
        final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
        {
            // 3.更新数据信息
            mapper.update(entity);
        }
        // 4.事务提交
        submit(transaction, EXP_CLASS);
        return entity;
    }

    /** 删除的模板方法 **/
    @Override
    public boolean deleteById(@NotNull final ID uniqueId) throws AbstractTransactionException {
        // 1.开启Mybatis事务
        final SqlSession session = SessionManager.getSession();
        final Transaction transaction = transaction(session);
        // 2.删除当前记录
        final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
        mapper.deleteById(uniqueId);
        // 3.事务提交完成
        submit(transaction, EXP_CLASS);
        return true;
    }

    /** 获取实体的模板方法 **/
    @Override
    public T getById(@NotNull final ID uniqueId) {
        // 1.初始化SqlSession
        final SqlSession session = SessionManager.getSession();
        T ret = null;
        try {
            // 2.获取Mapper
            final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
            // 3.读取返回信息
            ret = mapper.selectById(uniqueId);
            // 4.关闭Session返回结构
            session.close();
        } catch (AbstractTransactionException ex) {
            peError(LOGGER,ex);
        }
        return ret;
    }

    /** 获取所有数据的模板方法 **/
    @Override
    public List<T> getAll() {
        // 1.初始化SqlSession
        final SqlSession session = SessionManager.getSession();
        List<T> retList = null;
        try {
            // 2.获取Mapper
            final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
            // 3.读取返回列表
            retList = mapper.selectAll();
            // 4.关闭Session返回最终结果
            session.close();
        } catch (AbstractTransactionException ex) {
            peError(LOGGER,ex);
        }
        return retList;
    }
    /**
     * 
     * @return
     */
    @Override
    public List<T> getByPage(@Min(1) final int index, @Min(1) final int size, @NotNull @NotEmpty @NotBlank final String orderBy){
        // 1.初始化SqlSession
        final SqlSession session = SessionManager.getSession();
        // 2.计算偏移量
        final int start = (index - 1) * size;
        List<T> retList = null;
        try{
            // 3.获取Mapper
            final H2TMapper<T,ID> mapper = (H2TMapper<T,ID>)session.getMapper(mapper());
            // 4.读取返回列表
            retList = mapper.selectByPage(orderBy, size, start);
            // 5.关闭Session返回最终结果
            session.close();
        }catch(AbstractTransactionException ex){
            peError(LOGGER,ex);
        }
        return retList;
    }

    /** 清空数据库中的数据 **/
    @Override
    public boolean clear() throws AbstractTransactionException {
        // 1.开启Mybatis事务
        final SqlSession session = SessionManager.getSession();
        final Transaction transaction = transaction(session);
        // 2.删除当前记录
        final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
        mapper.purgeData();
        // 3.事务提交完成
        submit(transaction, EXP_CLASS);
        return true;
    }
    /** 获取连接信息 **/
    @NotNull
    @InstanceOf(JdbcContext.class)
    public JdbcContext getContext(@NotNull @NotEmpty @NotBlank final String identifier){
        JdbcContext context = MemoryPool.POOL_JDBC.get(identifier);
        if (null == context) {
            context = reservoir(MemoryPool.POOL_JDBC, identifier, H2ConnImpl.class);
        }
        return context;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Class<?> mapper() throws AbstractTransactionException {
        final Class<?> mapperClass = getMapper();
        if (null == mapperClass) {
            throw new MapperClassNullException(getClass(), getClass().getName());
        }
        return mapperClass;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
