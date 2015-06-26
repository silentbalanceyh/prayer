package com.prayer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prayer.exception.unchecked.JsonParserException;
import com.prayer.exception.unchecked.ResourceIOException;

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
	public static Map<String, JsonNode> readJson(final String filePath) {
		Map<String, JsonNode> retMap = null;	// NOPMD
		try {
			final InputStream inStream = IOKit.getFile(filePath, JsonKit.class);
			retMap = MAPPER.readValue(inStream, // NOPMD
					new TypeReference<Map<String, JsonNode>>() {
					});
		} catch (JsonMappingException | JsonParseException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] Json parsing error.", ex);
			}
			throw new JsonParserException(JsonKit.class, ex.getMessage()); // NOPMD
		} catch (IOException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] Resource I/O error.", ex);
			}
			throw new ResourceIOException(JsonKit.class, ex.getMessage()); // NOPMD
		}
		return retMap;
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private JsonKit() {
	}
	// ~ hashCode,equals,toString ============================
}
