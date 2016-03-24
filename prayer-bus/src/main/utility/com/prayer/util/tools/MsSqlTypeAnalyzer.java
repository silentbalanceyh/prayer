package com.prayer.util.tools;

import static com.prayer.util.reflection.Instance.reservoir;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.jolbox.bonecp.BoneCPDataSource;
import com.prayer.builder.impl.util.SqlTypes;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.Resources;
import com.prayer.util.io.PropertyKit;

import io.vertx.core.json.JsonArray;

public class MsSqlTypeAnalyzer {
    // ~ Static Fields =======================================
    /**
     * 资源加载器
     */
    private static final PropertyKit LOADER = new PropertyKit(MsSqlTypeAnalyzer.class, Resources.DB_CFG_FILE); // NOPMD
    /** **/
    private static BoneCPDataSource DS = null;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** **/
    static {
        if (null == DS) {
            DS = (BoneCPDataSource) reservoir(MemoryPool.POOL_DATA_SOURCE, Resources.DB_CATEGORY,
                    BoneCPDataSource.class);
            DS.setDriverClass(LOADER.getString(Resources.DB_CATEGORY + ".jdbc.driver"));
            DS.setJdbcUrl(LOADER.getString(Resources.DB_CATEGORY + ".jdbc.url"));
            DS.setUsername(LOADER.getString(Resources.DB_CATEGORY + ".jdbc.username"));
            DS.setPassword(LOADER.getString(Resources.DB_CATEGORY + ".jdbc.password"));
        }
    }
    // ~ Static Methods ======================================

    public static void main(String args[]) {
        final JsonArray types = SqlTypes.types();
        // 1.生成删除表语句
        final List<String> deleteSql = genTableDropSql(types);
        for (final String sql : deleteSql) {
            try {
                executeBatch(sql);
            } catch (SQLException ex) {
                continue;
            }
        }
        // 2.生成表生成语句
        final List<String> createSql = genTableCreateSql(types);
        for (final String sql : createSql) {
            try {
                executeBatch(sql);
            } catch (SQLException ex) {
                continue;
            }
        }
        // 3.生成修改报表
        final StringBuilder content = new StringBuilder();
        content.append("{");
        for (int idx = 0; idx < types.size(); idx++) {
            final ConcurrentMap<String, String> executeMap = genColumnAlterSql(types.getString(idx), types);
            content.append('"').append(types.getString(idx)).append("\":[");
            for (int sqlIdx = 0; sqlIdx < types.size(); sqlIdx++) {
                final String typeKey = types.getString(sqlIdx);
                final String sql = executeMap.get(typeKey);
                try {
                    executeBatch(sql);
                    System.out.println("Success -> " + sql);
                    if (!types.getString(idx).equals(typeKey)) {
                        content.append('"').append(typeKey).append("\",");
                    }
                } catch (Exception ex) {
                    System.out.println("Failure -> " + sql);
                    continue;
                }
            }
            // Timestamp
            if (!content.toString().endsWith("[")) {
                content.delete(content.length() - 1, content.length());
            }
            content.append("],");
        }
        content.delete(content.length() - 1, content.length());
        content.append("}");
        // 4.生成json文件
        try {
            final File out = new File("C:\\Users\\Yu\\Desktop\\Types\\changes.json");
            if (!out.exists()) {
                out.createNewFile();
            }
            int length = content.length();
            FileOutputStream fos = new FileOutputStream(out);
            fos.write(content.toString().getBytes("UTF-8"), 0, length);
            fos.flush();
            fos.close();
            // 5.生成删除表语句
            for (final String sql : deleteSql) {
                try {
                    executeBatch(sql);
                } catch (SQLException ex) {
                    continue;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void executeBatch(final String sql) throws SQLException {
        final Connection conn = DriverManager.getConnection(LOADER.getString(Resources.DB_CATEGORY + ".jdbc.url"),
                LOADER.getString(Resources.DB_CATEGORY + ".jdbc.username"),
                LOADER.getString(Resources.DB_CATEGORY + ".jdbc.password"));
        final Statement stms = conn.createStatement();
        stms.executeUpdate(sql);
        stms.close();
        conn.close();
    }

    private static ConcurrentMap<String, String> genColumnAlterSql(final String type, final JsonArray types) {
        final ConcurrentMap<String, String> sqls = new ConcurrentHashMap<>();
        for (int idx = 0; idx < types.size(); idx++) {
            final StringBuilder sql = new StringBuilder();
            final String table = "TST_ANA_" + type.replaceAll("\\(MAX\\)", "_MAX");
            sql.append("ALTER TABLE ").append(table).append(" ALTER COLUMN ").append("C_")
                    .append(types.getString(idx).replaceAll("\\(MAX\\)", "_MAX")).append(" ").append(type).append(";");
            sqls.put(types.getString(idx), sql.toString());
        }
        return sqls;
    }

    private static List<String> genTableCreateSql(final JsonArray types) {
        final List<String> sqls = new ArrayList<>();
        for (int idx = 0; idx < types.size(); idx++) {
            final StringBuilder sql = new StringBuilder();
            sql.append("CREATE TABLE TST_ANA_").append(types.getString(idx).replaceAll("\\(MAX\\)", "_MAX"))
                    .append("(");
            for (int tIdx = 0; tIdx < types.size(); tIdx++) {
                sql.append("C_").append(types.getString(tIdx).replaceAll("\\(MAX\\)", "_MAX")).append(" ")
                        .append(types.getString(tIdx)).append(" NULL,");
            }
            sql.delete(sql.length() - 1, sql.length());
            sql.append(");");
            sqls.add(sql.toString());
        }
        return sqls;
    }

    private static List<String> genTableDropSql(final JsonArray types) {
        final List<String> sqls = new ArrayList<>();
        for (int idx = 0; idx < types.size(); idx++) {
            final StringBuilder sql = new StringBuilder();
            sql.append("DROP TABLE TST_ANA_").append(types.getString(idx).replaceAll("\\(MAX\\)", "_MAX")).append(";");
            sqls.add(sql.toString());
        }
        return sqls;
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
