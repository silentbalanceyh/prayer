package com.prayer.base.accessor;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;

import java.io.InputStream;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;

import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.constant.Resources;
import com.prayer.util.io.IOKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 抽象Dao实现，主要处理事务管理
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractIBatisAccessor { // NOPMD
    // ~ Static Fields =======================================
    /** Session Factory 的单例 **/
    private static SqlSessionFactory factory;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 静态初始化 **/
    static {
        /** 简单的单例实现 **/
        if (null == factory) {
            /** 读取MyBatis配置信息 **/
            final InputStream configuration = IOKit.getFile(Resources.T_CFG_MYBATIS);
            if (null != configuration) {
                /** 构造SessionFactory **/
                factory = new SqlSessionFactoryBuilder().build(configuration, Resources.T_CFG_MB_ENV);
            }
        }
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** 获取日志记录器 **/
    protected abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================

    /** 获取SqlSession实例 **/
    @NotNull
    protected SqlSession session() {
        return factory.openSession();
    }

    /** 开启新事务 **/
    @NotNull
    protected Transaction transaction() {
        final SqlSession session = this.session();
        final TransactionFactory transaction = new JdbcTransactionFactory();
        return transaction.newTransaction(session.getConnection());
    }

    /** 事务提交方法 **/
    protected void submit(@NotNull final Transaction transaction, @NotNull @NotEmpty @NotBlank final String clazz)
            throws AbstractTransactionException {
        AbstractTransactionException throwExp = null;
        try {
            transaction.commit();
        } catch (SQLException ex) {
            jvmError(getLogger(), ex);
            throwExp = instance(clazz, getClass(), "Commit");
            peError(getLogger(), throwExp);
            try {
                transaction.rollback();
            } catch (SQLException exp) {
                jvmError(getLogger(), exp);
                throwExp = instance(clazz, getClass(), "Rollback");
                peError(getLogger(), throwExp);
            }
        } finally {
            try {
                transaction.close();
            } catch (SQLException ex) {
                jvmError(getLogger(), ex);
                throwExp = instance(clazz, getClass(), "Close");
                peError(getLogger(), throwExp);
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
