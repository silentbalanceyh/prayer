package com.prayer.util;

import static com.prayer.util.debug.Log.jvmError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
public final class Converter {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param instance
     * @param name
     * @return
     */
    public static String toStr(@NotNull final JsonObject instance, @NotNull @NotEmpty @NotBlank final String field) {
        String ret = null;
        final Class<?> type = instance.getValue(field).getClass();
        if (JsonObject.class == type) {
            final JsonObject obj = instance.getJsonObject(field);
            if (null != obj) {
                ret = obj.encode();
            }
        } else if (JsonArray.class == type) {
            final JsonArray arr = instance.getJsonArray(field);
            if (null != arr) {
                ret = arr.encode();
            }
        } else {
            ret = instance.getString(field);
        }
        return ret;
    }

    /**
     * 
     * @param sets
     * @return
     */
    @NotNull
    @NotEmpty
    @NotBlank
    public static String toStr(@NotNull @MinSize(1) final Set<String> sets) {
        return toStr(sets.toArray(Constants.T_STR_ARR));
    }

    /**
     * 
     * @param setArr
     * @return
     */
    @NotNull
    @NotEmpty
    @NotBlank
    public static String toStr(@NotNull @MinSize(1) final String... setArr) {
        final StringBuilder retStr = new StringBuilder();
        for (int i = 0; i < setArr.length; i++) {
            retStr.append(setArr[i]);
            if (i < setArr.length - 1) {
                retStr.append(Symbol.COMMA);
            }
        }
        return retStr.toString();
    }

    /**
     * 
     * @param istream
     * @return
     */
    public static String toStr(@NotNull final InputStream istream) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final byte[] data = new byte[Constants.BYTE_BUF_SIZE];
        int count = Constants.RANGE;
        String ret = null;
        while (Constants.RANGE != (count = istream.read(data, Constants.ZERO, Constants.BYTE_BUF_SIZE))) { // NOPMD
            out.write(data, Constants.ZERO, count);
        }
        if (Constants.ZERO < out.size()) {
            ret = new String(out.toByteArray(), Resources.SYS_ENCODING);
            // Close Output Stream
            out.close();
        }
        return ret;
    }

    /**
     * 
     * @param clob
     * @return
     */
    public static String toStr(@NotNull final Clob clob) {
        String retStr = null;
        try {
            final Reader reader = clob.getCharacterStream();
            final char[] charArr = new char[(int) clob.length()];
            reader.read(charArr);
            reader.close();
            retStr = new String(charArr);
        } catch (SQLException ex) {
            jvmError(LOGGER,ex);
        } catch (IOException ex) {
            jvmError(LOGGER,ex);
        }
        return retStr;
    }

    /**
     * 
     * @param clazz
     * @param inputStr
     * @return
     */
    public static <T extends Enum<T>> T fromStr(@NotNull final Class<T> clazz,
            @NotNull @NotBlank @NotEmpty final String inputStr) {
        T retEnum = null;
        try {
            retEnum = Enum.valueOf(clazz, inputStr);
        } catch (IllegalArgumentException ex) {
            jvmError(LOGGER,ex);
        }
        return retEnum;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Converter() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
