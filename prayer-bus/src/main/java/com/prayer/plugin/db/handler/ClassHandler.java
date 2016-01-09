package com.prayer.plugin.db.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.prayer.util.reflection.Instance;

/**
 * 在Mybatis中处理Class<?>类型的数据库Handler，Prayer中依赖上层Class，因为Class可能不存在
 * 
 * @author Lang
 *
 */
public class ClassHandler extends BaseTypeHandler<Class<?>> { // NOPMD

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void setNonNullParameter(final PreparedStatement pstmt, final int idx, final Class<?> parameter,
            final JdbcType jdbcType) throws SQLException {
        final String paramStr = parameter.getName();
        pstmt.setString(idx, paramStr);
    }

    /** **/
    @Override
    public Class<?> getNullableResult(final ResultSet rst, final String name) throws SQLException {
        final String value = rst.getString(name);
        return getNull(!rst.wasNull(), value);
    }

    /** **/
    @Override
    public Class<?> getNullableResult(final ResultSet rst, final int idx) throws SQLException {
        final String value = rst.getString(idx);
        return getNull(!rst.wasNull(), value);
    }

    /** **/
    @Override
    public Class<?> getNullableResult(final CallableStatement cstmt, final int idx) throws SQLException {
        final String value = cstmt.getString(idx);
        return getNull(!cstmt.wasNull(), value);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private Class<?> getNull(final boolean ret, final String value) {
        Class<?> clazz = null;
        if (ret) {
            clazz = Instance.clazz(value);
        }
        return clazz;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
