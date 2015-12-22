package com.prayer.base.dao;

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Log.jvmError;
import static com.prayer.util.Log.peError;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;

import com.prayer.base.exception.AbstractTransactionException;

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
public abstract class AbstractDaoImpl { // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** 获取日志记录器 **/
    protected abstract Logger getLogger();
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 开启新事务 **/
    @NotNull
    protected Transaction transaction(@NotNull final SqlSession session) {
        final TransactionFactory tranFactory = new JdbcTransactionFactory();
        return tranFactory.newTransaction(session.getConnection());
    }

    /** 事务提交方法 **/
    protected void submit(@NotNull final Transaction transaction, @NotNull @NotEmpty @NotBlank final String clazz)
            throws AbstractTransactionException {
        AbstractTransactionException throwExp = null;
        try {
            transaction.commit();
        } catch (SQLException ex) {
            jvmError(getLogger(),ex);
            throwExp = instance(clazz, getClass(), "Commit");
            peError(getLogger(),throwExp);
            try {
                transaction.rollback();
            } catch (SQLException exp) {
                jvmError(getLogger(),exp);
                throwExp = instance(clazz, getClass(), "Rollback");
                peError(getLogger(),throwExp);
            }
        } finally {
            try {
                transaction.close();
            } catch (SQLException ex) {
                jvmError(getLogger(),ex);
                throwExp = instance(clazz, getClass(), "Close");
                peError(getLogger(),throwExp);
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
