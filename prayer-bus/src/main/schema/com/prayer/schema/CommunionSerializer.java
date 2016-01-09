package com.prayer.schema;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.debug.Log.peError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.Status;
import com.prayer.exception.system.SerializationException;
import com.prayer.facade.schema.Serializer;
import com.prayer.model.database.PEField;
import com.prayer.model.database.PEKey;
import com.prayer.model.database.PEMeta;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class CommunionSerializer implements Serializer {

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunionSerializer.class);

    // ~ Instance Fields =====================================
    /**
     * JackSon Mapper
     */
    @NotNull
    private transient final ObjectMapper mapper;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    @PostValidateThis
    public CommunionSerializer() {
        this.mapper = new ObjectMapper();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    @Pre(expr = "_this.mapper != null", lang = Constants.LANG_GROOVY)
    public PEMeta readMeta(@NotNull final JsonNode metaNode) throws SerializationException {
        final String metaStr = metaNode.toString();
        PEMeta meta = new PEMeta();
        try {
            meta = this.mapper.readValue(metaStr, new TypeReference<PEMeta>() {
            });
            // 默认设置status为SYSTEM，如果没有设置则是SYSTEM类型，特殊属性，Schema中不验证
            if (null == meta.getStatus()) {
                meta.setStatus(Status.SYSTEM);
            }
        } catch (JsonParseException ex) {
            jvmError(LOGGER, ex);
            final SerializationException error = new SerializationException(getClass(), "__meta__ ( Parsing )"); // NOPMD
            peError(LOGGER, error);
            throw error;// NOPMD
        } catch (IOException ex) {
            jvmError(LOGGER, ex);
            final SerializationException error = new SerializationException(getClass(), "__meta__ ( I/O )"); // NOPMD
            peError(LOGGER, error);
            throw error;// NOPMD
        }
        return meta;
    }

    /**
     * 
     */
    @Override
    @Pre(expr = "_this.mapper != null", lang = Constants.LANG_GROOVY)
    public List<PEKey> readKeys(@NotNull final ArrayNode keysNodes) throws SerializationException {
        final String keysStr = keysNodes.toString();
        List<PEKey> keys = new ArrayList<>();
        try {
            keys = this.mapper.readValue(keysStr, new TypeReference<List<PEKey>>() {
            });
        } catch (JsonParseException ex) {
            jvmError(LOGGER, ex);
            final SerializationException error = new SerializationException(getClass(), "__keys__ ( Parsing )"); // NOPMD
            peError(LOGGER, error);
            throw error;// NOPMD
        } catch (IOException ex) {
            jvmError(LOGGER, ex);
            final SerializationException error = new SerializationException(getClass(), "__keys__ ( I/O )"); // NOPMD
            peError(LOGGER, error);
            throw error;// NOPMD
        }
        return keys;
    }

    /**
     * 
     */
    @Override
    @Pre(expr = "_this.mapper != null", lang = Constants.LANG_GROOVY)
    public List<PEField> readFields(@NotNull final ArrayNode fieldsNodes) throws SerializationException {
        final String fieldsStr = fieldsNodes.toString();
        List<PEField> fields = new ArrayList<>();
        try {
            fields = this.mapper.readValue(fieldsStr, new TypeReference<List<PEField>>() {
            });
        } catch (JsonParseException ex) {
            jvmError(LOGGER, ex);
            final SerializationException error = new SerializationException(getClass(), "__fields__ ( Parsing )"); // NOPMD
            peError(LOGGER, error);
            throw error;// NOPMD
        } catch (IOException ex) {
            jvmError(LOGGER, ex);
            final SerializationException error = new SerializationException(getClass(), "__fields__ ( I/O )"); // NOPMD
            peError(LOGGER, error);
            throw error;// NOPMD
        }
        return fields;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
