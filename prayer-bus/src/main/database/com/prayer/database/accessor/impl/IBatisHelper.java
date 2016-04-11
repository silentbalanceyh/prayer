package com.prayer.database.accessor.impl;

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

import com.prayer.constant.Resources;
import com.prayer.exception.database.DataAccessException;
import com.prayer.facade.metadata.mapper.IBatisMapper;
import com.prayer.facade.metadata.mapper.PEAddressMapper;
import com.prayer.facade.metadata.mapper.PEFieldMapper;
import com.prayer.facade.metadata.mapper.PEIndexMapper;
import com.prayer.facade.metadata.mapper.PEKeyMapper;
import com.prayer.facade.metadata.mapper.PEMetaMapper;
import com.prayer.facade.metadata.mapper.PERouteMapper;
import com.prayer.facade.metadata.mapper.PERuleMapper;
import com.prayer.facade.metadata.mapper.PEScriptMapper;
import com.prayer.facade.metadata.mapper.PETriggerMapper;
import com.prayer.facade.metadata.mapper.PEUriMapper;
import com.prayer.facade.metadata.mapper.PEVColumnMapper;
import com.prayer.facade.metadata.mapper.PEVerticleMapper;
import com.prayer.facade.metadata.mapper.PEViewMapper;
import com.prayer.facade.model.entity.Entity;
import com.prayer.fantasm.exception.AbstractTransactionException;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEIndex;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;
import com.prayer.model.meta.database.PETrigger;
import com.prayer.model.meta.database.PEVColumn;
import com.prayer.model.meta.database.PEView;
import com.prayer.model.meta.vertx.PEAddress;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEScript;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.model.meta.vertx.PEVerticle;
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
        try {
            /** 简单的单例实现 **/
            if (null == factory) {
                /** 读取MyBatis配置 **/
                final InputStream configuration = IOKit.getFile(Resources.T_CFG_MYBATIS);
                if (null != configuration) {
                    /** 构造Factory **/
                    factory = new SqlSessionFactoryBuilder().build(configuration, Resources.T_CFG_MB_ENV);
                }
            }
        } catch (Exception ex) {
            jvmError(LOGGER, ex);
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
            // TODO：【完成】如果添加新的Entity需要修改
            MDATA.put(PEScript.class, PEScriptMapper.class);
            MDATA.put(PEAddress.class, PEAddressMapper.class);
            MDATA.put(PERoute.class, PERouteMapper.class);
            MDATA.put(PERule.class, PERuleMapper.class);
            MDATA.put(PEUri.class, PEUriMapper.class);
            MDATA.put(PEVerticle.class, PEVerticleMapper.class);
            MDATA.put(PEField.class, PEFieldMapper.class);
            MDATA.put(PEIndex.class, PEIndexMapper.class);
            MDATA.put(PEKey.class, PEKeyMapper.class);
            MDATA.put(PEMeta.class, PEMetaMapper.class);
            MDATA.put(PETrigger.class, PETriggerMapper.class);
            MDATA.put(PEVColumn.class, PEVColumnMapper.class);
            MDATA.put(PEView.class, PEViewMapper.class);
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
