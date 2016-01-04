package com.prayer.facade.mapper;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.prayer.constant.Resources;
import com.prayer.util.io.IOKit;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Mybatis框架中的SQL Session管理器
 *
 * @author Lang
 * @see
 */
@Guarded
public final class SessionManager {
    // ~ Static Fields =======================================
    /** Session Factory 的单例 **/
    private static SqlSessionFactory sessionFactory;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 静态初始化 **/
    static {
        /** 简单的单例实现 **/
        if (null == sessionFactory) {
            final InputStream inStream = IOKit.getFile(Resources.T_CFG_MYBATIS);
            if (null != inStream) {
                sessionFactory = new SqlSessionFactoryBuilder().build(inStream, Resources.T_CFG_MB_ENV);
            }
        }
    }

    // ~ Static Methods ======================================
    /** 获取Session实例 **/
    @NotNull
    public static SqlSession getSession() {
        return sessionFactory.openSession();
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Get/Set =============================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private SessionManager() {
    }
    // ~ hashCode,equals,toString ============================
}
