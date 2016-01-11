package com.prayer.accessor.impl;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.constant.Resources;
import com.prayer.exception.database.DataAccessException;
import com.prayer.facade.entity.Entity;
import com.prayer.facade.metadata.mapper.IBatisMapper;
import com.prayer.facade.metadata.mapper.PEScriptMapper;
import com.prayer.model.vertx.PEScript;
import com.prayer.util.io.IOKit;

import net.sf.oval.guard.Guarded;

/**
 * IBatis的帮助类
 * 
 * @author Lang
 *
 */
@Guarded
public final class IBatisHelper {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(IBatisHelper.class);
    /** Session Factory实例，仅维持一个 **/
    private static SqlSessionFactory factory;
    /** Class -> Mapper **/
    private static ConcurrentMap<Class<?>, Class<?>> MDATA = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    /** 构造一个Transaction **/
    private transient Transaction transaction;
    /** 构造一个SqlSession **/
    private transient SqlSession session;

    // ~ Static Block ========================================
    /** 静态初始化 **/
    static {
        /** 简单的单例实现 **/
        if (null == factory) {
            /** 读取MyBatis配置 **/
            final InputStream configuration = IOKit.getFile(Resources.T_CFG_MYBATIS);
            if (null != configuration) {
                /** 构造Factory **/
                factory = new SqlSessionFactoryBuilder().build(configuration, Resources.T_CFG_MB_ENV);
            }
        }
    }

    // ~ Static Methods ======================================
    /**
     * 创建Helper的实例
     * 
     * @return
     */
    public static IBatisHelper create() {
        return new IBatisHelper();
    }

    // ~ Constructors ========================================
    /**
     * 私有构造函数
     */
    private IBatisHelper() {
        /**
         * 填充Mapper
         */
        if (MDATA.isEmpty()) {
            MDATA.put(PEScript.class, PEScriptMapper.class);
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 传入Entity的Class类型
     * 
     * @param clazz
     */
    @SuppressWarnings("unchecked")
    public IBatisMapper<Entity, Serializable> beginTransaction(final Class<?> clazz) {
        this.session = factory.openSession();
        final TransactionFactory transaction = new JdbcTransactionFactory();
        this.transaction = transaction.newTransaction(session.getConnection());
        return (IBatisMapper<Entity, Serializable>) session.getMapper(MDATA.get(clazz));
    }

    /**
     * 用于查询的Mapper获取，不需要事务的操作
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public IBatisMapper<Entity, Serializable> beginQuery(final Class<?> clazz) {
        this.session = factory.openSession();
        return (IBatisMapper<Entity, Serializable>) session.getMapper(MDATA.get(clazz));
    }

    /**
     * 用于查询的时候的Session关闭操作
     */
    public void endQuery() {
        if (null != this.session) {
            this.session.close();
        }
    }

    /**
     * 
     * @throws AbstractTransactionException
     */
    public void endTransaction() throws AbstractTransactionException {
        AbstractTransactionException throwExp = null;
        try {
            transaction.commit();
        } catch (SQLException ex) {
            jvmError(LOGGER, ex);
            throwExp = instance(DataAccessException.class, getClass(), "Commit");
            peError(LOGGER, throwExp);
            try {
                transaction.rollback();
            } catch (SQLException exp) {
                jvmError(LOGGER, exp);
                throwExp = instance(DataAccessException.class, getClass(), "Rollback");
                peError(LOGGER, throwExp);
            }
        } finally {
            try {
                transaction.close();
            } catch (SQLException ex) {
                jvmError(LOGGER, ex);
                throwExp = instance(DataAccessException.class, getClass(), "Close");
                peError(LOGGER, throwExp);
            }
        }
        if (null != throwExp) {
            throw throwExp;
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
