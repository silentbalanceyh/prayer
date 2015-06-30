package com.prayer.util;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.JsonParserException;
import com.prayer.exception.system.ResourceIOException;

/**
 * 
 * @author Lang
 * @see
 */
public final class JsonKit {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonKit.class);
	/** **/
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static JsonNode readJson(final String filePath)
			throws AbstractSystemException {
		JsonNode retNode = null; // NOPMD
		try {
			final InputStream inStream = IOKit.getFile(filePath, JsonKit.class);
			retNode = MAPPER.readValue(inStream, // NOPMD
					new TypeReference<JsonNode>() {
					});
		} catch (JsonParseException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] Json parsing error.", ex);
			}
			throw new JsonParserException(JsonKit.class, ex.toString()); // NOPMD
		} catch (IOException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] Resource I/O error.", ex);
			}
			throw new ResourceIOException(JsonKit.class, filePath); // NOPMD
		}
		return retNode;
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private JsonKit() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
