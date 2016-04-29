package com.prayer.util.vertx;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
    public static ConcurrentMap<String, Double> accept(final MultiMap map) {
        String accept = map.get(HttpHeaders.ACCEPT);
        final ConcurrentMap<String, Double> mimes = new ConcurrentHashMap<>();
        if (null != accept) {
            // application/xml;q=0.8, application/json;q=1.0, text/plain;q=0.0
            // -> Array
            // [0] -> application/xml;q=0.8
            // [1] -> application/json;q=1.0
            // [2] -> text/plain;q=0.0
            String[] acceptArr = accept.split(String.valueOf(Symbol.COMMA));
            for (final String acceptItem : acceptArr) {
                /** 使用分号解析 **/
                // [0] -> application/xml = 0.8
                // [1] -> application/json = 1.0
                // [2] -> text/plain = 0.0
                final String[] mimeArr = acceptItem.trim().split(String.valueOf(Symbol.SEMICOLON));
                if (Constants.TWO == mimeArr.length) {
                    final String mime = mimeArr[Constants.IDX];
                    final String qstr = mimeArr[Constants.ONE];
                    final int idx = qstr.indexOf(Symbol.EQUAL);
                    if (0 <= idx) {
                        final Double qvalue = Double.parseDouble(qstr.substring(idx + 1, qstr.length()));
                        mimes.put(mime, qvalue);
                    }
                }
            }
        }
        return mimes;
    }

    /** 广域匹配 **/
    public static boolean wideMatch(final String actual, final String expected) {
        /** 1.如果二者相等，直接匹配 **/
        if (actual.equals(expected)) {
            return true;
        }
        /** 2.抽取 **/
        final String[] actualArr = actual.split("/");
        final String[] expectArr = expected.split("/");
        if (Constants.TWO != actualArr.length && Constants.TWO != expectArr.length) {
            return false;
        }
        /** 2.1.如果expected父类子类都是* **/
        if (Symbol.STAR == expectArr[Constants.IDX].charAt(Constants.IDX)
                && Symbol.STAR == expectArr[Constants.ONE].charAt(Constants.IDX)) {
            return true;
        }
        /** 3.父类匹配，子类通配 **/
        // actual: application/json
        // expected: application/*
        if (actualArr[Constants.IDX].equals(expectArr[Constants.IDX])) {
            // 父类相等
            // 子类为* 或其他
            if (Symbol.STAR == expectArr[Constants.ONE].charAt(Constants.IDX)) {
                return true;
            } else if (expectArr[Constants.ONE].equals(actualArr[Constants.IDX])) {
                return true;
            }
        }
        /** 4.子类匹配，父类通配 **/
        // actual: application/json
        // expected: */json
        if (actualArr[Constants.ONE].equals(expectArr[Constants.ONE])) {
            // 子类相等
            // 父类为* 或其他
            if (Symbol.STAR == expectArr[Constants.IDX].charAt(Constants.IDX)) {
                return true;
            } else if (expectArr[Constants.IDX].equals(actualArr[Constants.IDX])) {
                return true;
            }
        }
        return false;
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
