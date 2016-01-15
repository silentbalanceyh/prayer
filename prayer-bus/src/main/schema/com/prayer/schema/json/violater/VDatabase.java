package com.prayer.schema.json.violater;

import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.util.jdbc.SqlDDL;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class VDatabase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库的表名 **/
    private transient String table;
    /** 数据库的列名 **/
    private transient String column;
    /** 数据库的类型名 **/
    private transient String type;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param habitus
     * @param config
     * @return
     */
    public static VDatabase create(@NotNull final ObjectHabitus habitus, @NotNull final JsonObject config) {
        return new VDatabase(habitus, config);
    }

    // ~ Constructors ========================================
    private VDatabase(final ObjectHabitus habitus, final JsonObject config) {
        // 读取Table
        if (config.containsKey("table")) {
            final String attr = config.getString("table");
            if (StringKit.isNonNil(attr)) {
                this.table = habitus.get(attr);
            }
        }
        // 读取Column
        if (config.containsKey("column")) {
            final String attr = config.getString("column");
            if (StringKit.isNonNil(attr)) {
                this.column = habitus.get(attr);
            }
        }
        // 读取type
        if (config.containsKey("type")) {
            final String attr = config.getString("type");
            if (StringKit.isNonNil(attr)) {
                this.type = SqlDDL.DB_TYPES.get(habitus.get(attr));
            }
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * @return the table
     */
    public String getTable() {
        return table;
    }

    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
