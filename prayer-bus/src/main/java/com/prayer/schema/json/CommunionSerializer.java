package com.prayer.schema.json;

import static com.prayer.util.Error.debug;

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
import com.prayer.exception.system.SerializationException;
import com.prayer.model.h2.FieldModel;
import com.prayer.model.h2.KeyModel;
import com.prayer.model.h2.MetaModel;
import com.prayer.schema.Serializer;

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
    /** **/
    private static final String ERR_20004 = "E20004";
    
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
    public MetaModel readMeta(@NotNull final JsonNode metaNode) throws SerializationException {
        final String metaStr = metaNode.toString();
        MetaModel meta = new MetaModel();
        try {
            meta = this.mapper.readValue(metaStr, new TypeReference<MetaModel>() {
            });
        } catch (JsonParseException ex) {
            debug(LOGGER, ERR_20004, ex, metaStr);
            throw new SerializationException(getClass(), "__meta__ ( Parsing )"); // NOPMD
        } catch (IOException ex) {
            debug(LOGGER, ERR_20004, ex, metaStr);
            throw new SerializationException(getClass(), "__meta__ ( I/O )"); // NOPMD
        }
        return meta;
    }

    /**
     * 
     */
    @Override
    @Pre(expr = "_this.mapper != null", lang = Constants.LANG_GROOVY)
    public List<KeyModel> readKeys(@NotNull final ArrayNode keysNodes) throws SerializationException {
        final String keysStr = keysNodes.toString();
        List<KeyModel> keys = new ArrayList<>();
        try {
            keys = this.mapper.readValue(keysStr, new TypeReference<List<KeyModel>>() {
            });
        } catch (JsonParseException ex) {
            debug(LOGGER, ERR_20004, ex, keysStr);
            throw new SerializationException(getClass(), "__keys__ ( Parsing )"); // NOPMD
        } catch (IOException ex) {
            debug(LOGGER, ERR_20004, ex, keysStr);
            throw new SerializationException(getClass(), "__keys__ ( I/O )"); // NOPMD
        }
        return keys;
    }

    /**
     * 
     */
    @Override
    @Pre(expr = "_this.mapper != null", lang = Constants.LANG_GROOVY)
    public List<FieldModel> readFields(@NotNull final ArrayNode fieldsNodes) throws SerializationException {
        final String fieldsStr = fieldsNodes.toString();
        List<FieldModel> fields = new ArrayList<>();
        try {
            fields = this.mapper.readValue(fieldsStr, new TypeReference<List<FieldModel>>() {
            });
        } catch (JsonParseException ex) {
            debug(LOGGER, ERR_20004, ex, fieldsStr);
            throw new SerializationException(getClass(), "__fields__ ( Parsing )"); // NOPMD
        } catch (IOException ex) {
            debug(LOGGER, ERR_20004, ex, fieldsStr);
            throw new SerializationException(getClass(), "__fields__ ( I/O )"); // NOPMD
        }
        return fields;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
