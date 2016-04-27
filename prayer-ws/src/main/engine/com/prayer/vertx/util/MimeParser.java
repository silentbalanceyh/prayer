package com.prayer.vertx.util;

import java.util.List;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpHeaders;

/**
 * 
 * @author Lang
 *
 */
public class MimeParser {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** 1.从Headers中提取Content-Type的Mime值 **/
    public static String content(final MultiMap map) {
        String content = map.get(HttpHeaders.CONTENT_TYPE);
        /** Mime格式 **/
        final int idx = content.indexOf(Symbol.SEMICOLON);
        if (0 <= idx) {
            // application/json;charset=UTF-8;param1=a;param2=c;
            /** 截取第一部分 **/
            content = content.substring(Constants.IDX, idx);
        }
        return content;
    }

    /** 2.解析Headers提取Accept的Mime值 **/
    public static List<String> accept(final MultiMap map) {
        return null;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private MimeParser() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
