package com.prayer.script.js;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;
import com.prayer.util.resource.DatumLoader;

/**
 * 
 * @author Lang
 *
 */
final class JSReader {
    // ~ Static Fields =======================================
    /** 读取器 **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.System.class);
    /** Folder **/
    private static final String FOLDER = INCEPTOR.getString(Point.System.SCRIPT_FOLDER);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static List<String> readScripts() {
        /** 1.生成路径数组 **/
        final Map<Integer, String> scripts = readFiles();
        final List<String> files = new ArrayList<>();
        /** 2.填充文件数组 **/
        for (final Integer order : scripts.keySet()) {
            final String file = FOLDER + scripts.get(order);
            files.add(file);
        }
        return files;
    }

    private static Map<Integer, String> readFiles() {
        /** 将文件按照顺序生成操作文件顺序表 **/
        final Properties prop = DatumLoader.getLoader(FOLDER + "init.properties");
        final Map<Integer, String> orderMap = new TreeMap<>();
        /** 遍历 **/
        final Enumeration<?> it = prop.propertyNames();
        while (it.hasMoreElements()) {
            final Object item = it.nextElement();
            final Integer key = Integer.parseInt(item.toString());
            /** 填充Map **/
            orderMap.put(key, prop.getProperty(item.toString()));
        }
        return orderMap;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private JSReader() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
