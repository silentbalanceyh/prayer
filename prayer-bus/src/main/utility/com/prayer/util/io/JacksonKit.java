package com.prayer.util.io; // NOPMD

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.debug.Log.peError;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prayer.exception.system.JsonParserException;
import com.prayer.exception.system.ResourceIOException;
import com.prayer.fantasm.exception.AbstractSystemException;

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
public final class JacksonKit { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonKit.class);
    /** **/
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param reference
     * @param file
     * @return
     */
    public static <T> T fromFile(@NotNull final TypeReference<T> reference,
            @NotNull @NotEmpty @NotBlank final String file) throws AbstractSystemException {
        T ret = null;
        try {
            final InputStream inStream = IOKit.getFile(file);
            ret = MAPPER.readValue(inStream, reference);
        } catch (JsonParseException ex) {
            jvmError(LOGGER, ex);
            final AbstractSystemException error = new JsonParserException(JacksonKit.class, ex.toString()); // NOPMD
            peError(LOGGER, error);
            throw error; // NOPMD
        } catch (IOException ex) {
            jvmError(LOGGER, ex);
            ex.printStackTrace();
            final AbstractSystemException error = new ResourceIOException(JacksonKit.class, file); // NOPMD
            peError(LOGGER, error);
            throw error;// NOPMD
        }
        return ret;
    }

    /**
     * 
     * @param reference
     * @param content
     * @return
     */
    public static <T> T fromStr(@NotNull final TypeReference<T> reference,
            @NotNull @NotEmpty @NotBlank final String content) {
        T retObj = null;
        try {
            retObj = MAPPER.readValue(content, reference);
        } catch (JsonParseException ex) {
            jvmError(LOGGER, ex);
        } catch (IOException ex) {
            jvmError(LOGGER, ex);
        }
        return retObj;
    }

    /**
     * 
     * @param reference
     * @param content
     * @return
     */
    public static <T> T fromStr(@NotNull final Class<T> reference, @NotNull @NotEmpty @NotBlank final String content) {
        T retObj = null;
        try {
            retObj = MAPPER.readValue(content, reference);
        } catch (JsonParseException ex) {
            jvmError(LOGGER, ex);
        } catch (IOException ex) {
            jvmError(LOGGER, ex);
        }
        return retObj;
    }

    /**
     * 
     * @param object
     * @return
     */
    public static String toStr(final Object object) {
        String retStr = null;
        try {
            retStr = MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            jvmError(LOGGER, ex);
        }
        return retStr;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private JacksonKit() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}