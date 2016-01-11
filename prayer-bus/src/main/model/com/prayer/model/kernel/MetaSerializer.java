package com.prayer.model.kernel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Resources;
import com.prayer.util.io.PropertyKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class MetaSerializer {
    // ~ Static Fields =======================================
    /** 读取Schema属性文件 **/
    private static final PropertyKit LOADER = new PropertyKit(Resources.OOB_SCHEMA_FILE);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** Field -> Column **/
    public static ConcurrentMap<String, String> getFCMap(@NotNull @NotBlank @NotEmpty final String identifier) {
        final List<String> fields = Arrays.asList(LOADER.getArray(identifier + ".field.names"));
        final List<String> columns = Arrays.asList(LOADER.getArray(identifier + ".column.names"));
        final ConcurrentMap<String, String> retMap = new ConcurrentHashMap<>();
        // 防止越界
        final int length = Math.min(fields.size(), columns.size());
        for (int idx = 0; idx < length; idx++) {
            retMap.put(fields.get(idx), columns.get(idx));
        }
        return retMap;
    }

    /** Column -> Field **/
    public static ConcurrentMap<String, String> getCFMap(@NotNull @NotBlank @NotEmpty final String identifier) {
        final List<String> fields = Arrays.asList(LOADER.getArray(identifier + ".field.names"));
        final List<String> columns = Arrays.asList(LOADER.getArray(identifier + ".column.names"));
        final ConcurrentMap<String, String> retMap = new ConcurrentHashMap<>();
        // 防止越界
        final int length = Math.min(fields.size(), columns.size());
        for (int idx = 0; idx < length; idx++) {
            retMap.put(columns.get(idx), fields.get(idx));
        }
        return retMap;
    }
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
