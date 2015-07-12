package com.prayer.schema.json;

import static com.prayer.util.Error.debug;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.system.SerializationException;
import com.prayer.mod.sys.FieldModel;
import com.prayer.mod.sys.KeyModel;
import com.prayer.mod.sys.MetaModel;
import com.prayer.schema.Serializer;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

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
	public CommunionSerializer(){
		this.mapper = new ObjectMapper();
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * @param metaNode
	 * @throws SerializationException
	 */
	@Override
	public MetaModel readMeta(@NotNull final JsonNode metaNode) throws SerializationException {
		final String metaStr = metaNode.toString();
		MetaModel meta = new MetaModel();
		try {
			meta = this.mapper.readValue(metaStr, new TypeReference<MetaModel>() {
			});
		} catch (JsonParseException ex) {
			debug(LOGGER, "E20004", ex, metaStr);
			throw new SerializationException(getClass(), "__meta__ ( Parsing )"); // NOPMD
		} catch (IOException ex) {
			debug(LOGGER, "E20004", ex, metaStr);
			throw new SerializationException(getClass(), "__meta__ ( I/O )"); // NOPMD
		}
		return meta;
	}

	@Override
	public List<KeyModel> readKeys(@NotNull ArrayNode keysNodes) throws SerializationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FieldModel> readFields(@NotNull ArrayNode fieldsNodes) throws SerializationException {
		// TODO Auto-generated method stub
		return null;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
