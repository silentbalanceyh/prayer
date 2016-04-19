package com.prayer.util.string;

import static com.prayer.util.debug.Log.jvmError;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;

import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class StringKit {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(StringKit.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    public static String decodeURL(final String inputValue) {
        String ret = inputValue;
        try {
            ret = URLDecoder.decode(inputValue, InceptBus.build(Point.System.class).getString(Point.System.ENCODING));
        } catch (UnsupportedEncodingException ex) {
            jvmError(LOGGER, ex);
        } catch (IllegalArgumentException ex) {
            jvmError(LOGGER, ex);
        }
        return ret;
    }

    /**
     * 
     * @param collection
     * @param separator
     * @return
     */
    @NotNull
    public static String join(@NotNull @MinSize(1) final Collection<String> collection, final char separator) {
        return join(collection, separator, false);
    }

    /**
     * 如果appendEnd为false则效果同上
     * 
     * @param collection
     * @param separator
     * @return
     */
    @NotNull
    public static String join(@NotNull @MinSize(1) final Collection<String> collection, final char separator,
            final boolean appendEnd) {
        final StringBuilder retStr = new StringBuilder();
        if (Constants.ONE == collection.size()) {
            retStr.append(collection.iterator().next());
        } else {
            for (final String item : collection) {
                if (isNonNil(item)) {
                    retStr.append(item).append(separator);
                }
            }
            if (!appendEnd) {
                retStr.deleteCharAt(retStr.length() - 1);
            }
        }
        return retStr.toString();
    }

    /**
     *
     * 
     * @param collection
     * @param separationString
     * @return
     */
    @NotNull
    public static String join(@NotNull @MinSize(1) final Collection<String> collection, final String separationString) {
        final StringBuilder retStr = new StringBuilder();
        if (Constants.ONE == collection.size()) {
            retStr.append(collection.iterator().next());
        } else {
            for (final String item : collection) {
                if (isNonNil(item)) {
                    retStr.append(item).append(separationString);
                }
            }
        }
        return retStr.toString();
    }

    /**
     * isNotBlank / isNotEmpty中包含了!= null
     * 
     * @param strValue
     * @return
     */
    public static boolean isNonNil(final String strValue) {
        boolean ret = false;
        if (isNotBlank(strValue) && isNotEmpty(strValue)) {
            ret = true;
        }
        return ret;
    }

    /**
     * isBlank / isEmpty中包含了== null
     * 
     * @param strValue
     * @return
     */
    public static boolean isNil(final String strValue) {
        boolean ret = false;
        if (isBlank(strValue) || isEmpty(strValue.trim())) {
            ret = true;
        }
        return ret;
    }

    /**
     * 判断两个String是否相等
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(final String str1, final String str2) {
        return (str1 != null) ? (str1.equals(str2)) : (str2 == null);
    }

    /**
     * 重载方法，将String转换成大写
     * 
     * @param str
     * @return
     */
    public static String upper(final String str) {
        return upper(str, null);
    }

    /**
     * 将String转换成大写
     * 
     * @param str
     * @param locale
     * @return
     */
    public static String upper(final String str, Locale locale) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = null;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c > 127) {
                // found non-ascii char, fallback to the slow unicode detection
                if (locale == null) {
                    locale = Locale.getDefault();
                }
                return str.toUpperCase(locale);
            }
            if ((c >= 'a') && (c <= 'z')) {
                if (sb == null) {
                    sb = new StringBuilder(str);
                }
                sb.setCharAt(i, (char) (c - 32));
            }
        }
        if (sb == null) {
            return str;
        }
        return sb.toString();
    }
    /**
     * 
     * @param string
     * @return
     */
    public static boolean digitsAndSigns(CharSequence string) {
        int size = string.length();
        for (int i = 0; i < size; i++) {
            char c = string.charAt(i);
            if ((c <= '9' && c >= '0') == false && (c != '-') && (c != '+')) {
                return false;
            }
        }
        return true;
    }

    /**
     * 快速Split方法，比原始的正则表达式方式String.split()快
     * 
     * @param src
     * @param delimiter
     * @return
     */
    public static String[] split(final String src, final String delimiter) {
        final int maxparts = (src.length() / delimiter.length()) + 2;
        final int[] positions = new int[maxparts];
        final int dellen = delimiter.length();

        int i, j = 0;
        int count = 0;
        positions[0] = -dellen;
        while ((i = src.indexOf(delimiter, j)) != -1) {
            count++;
            positions[count] = i;
            j = i + dellen;
        }
        count++;
        positions[count] = src.length();

        final String[] result = new String[count];

        for (i = 0; i < count; i++) {
            result[i] = src.substring(positions[i] + dellen, positions[i + 1]);
        }
        return result;
    }

    // ~ Private Methods =====================================

    private static boolean isNotEmpty(final CharSequence string) {
        return string != null && string.length() > 0;
    }

    private static boolean isBlank(final CharSequence string) {
        return ((string == null) || containsOnlyWhitespaces(string));
    }

    private static boolean isNotBlank(final String string) {
        return ((string != null) && !containsOnlyWhitespaces(string));
    }

    private static boolean isEmpty(final CharSequence string) {
        return ((string == null) || (string.length() == 0));
    }

    private static boolean containsOnlyWhitespaces(CharSequence string) {
        int size = string.length();
        for (int i = 0; i < size; i++) {
            char c = string.charAt(i);
            if (false == (c <= Symbol.SPACE)) {
                return false;
            }
        }
        return true;
    }

    private StringKit() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
