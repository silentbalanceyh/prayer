package com.prayer.plugin.db.handler;

import static com.prayer.util.Converter.toStr;

import java.io.Reader;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.prayer.util.entity.stream.StreamList;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class JsonListHandler extends BaseTypeHandler<List<JsonObject>> {    // NOPMD

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public List<JsonObject> getNullableResult(final ResultSet retSet, final String columnName) throws SQLException {
        final Clob retValue = retSet.getClob(columnName);
        return getNull(!retSet.wasNull(),retValue);
    }

    /** **/
    @Override
    public List<JsonObject> getNullableResult(final ResultSet retSet, final int columnIndex) throws SQLException {
        final Clob retValue = retSet.getClob(columnIndex);
        return getNull(!retSet.wasNull(),retValue);
    }

    /** **/
    @Override
    public List<JsonObject> getNullableResult(final CallableStatement callStmt, final int columnIndex) throws SQLException {
        final Clob retValue = callStmt.getClob(columnIndex);
        return getNull(!callStmt.wasNull(),retValue);
    }

    /** **/
    @Override
    public void setNonNullParameter(final PreparedStatement pstmt, final int colIndex, final List<JsonObject> parameter,
            final JdbcType jdbcType) throws SQLException {
        if (null != parameter) {
            final JsonArray array = StreamList.fromList(parameter);
            final Reader reader = new StringReader(array.encodePrettily());
            pstmt.setClob(colIndex, reader);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    
    private List<JsonObject> getNull(final boolean ret, final Clob value){
        List<JsonObject> retList = new ArrayList<>();
        if(ret){
            final JsonArray array = new JsonArray(toStr(value));
            retList = StreamList.toList(array);
        }
        return retList;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
