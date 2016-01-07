package com.prayer.plugin.db.handler;

import static com.prayer.util.Converter.toStr;

import java.io.Reader;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class JsonObjectHandler extends BaseTypeHandler<JsonObject> {    // NOPMD

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public JsonObject getNullableResult(final ResultSet retSet, final String columnName) throws SQLException {
        final Clob retValue = retSet.getClob(columnName);
        JsonObject retObj = new JsonObject();
        if (!retSet.wasNull()) {
            retObj = new JsonObject(toStr(retValue));
        }
        return retObj;
    }

    /** **/
    @Override
    public JsonObject getNullableResult(final ResultSet retSet, final int columnIndex) throws SQLException {
        final Clob retValue = retSet.getClob(columnIndex);
        JsonObject retObj = new JsonObject();
        if (!retSet.wasNull()) {
            retObj = new JsonObject(toStr(retValue));
        }
        return retObj;
    }

    /** **/
    @Override
    public JsonObject getNullableResult(final CallableStatement callStmt, final int columnIndex) throws SQLException {
        final Clob retValue = callStmt.getClob(columnIndex);
        JsonObject retObj = new JsonObject();
        if (!callStmt.wasNull()) {
            retObj = new JsonObject(toStr(retValue));
        }
        return retObj;
    }

    /** **/
    @Override
    public void setNonNullParameter(final PreparedStatement pstmt, final int colIndex, final JsonObject parameter,
            final JdbcType jdbcType) throws SQLException {
        if (null != parameter) {
            final Reader reader = new StringReader(parameter.encodePrettily());
            pstmt.setClob(colIndex, reader);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
