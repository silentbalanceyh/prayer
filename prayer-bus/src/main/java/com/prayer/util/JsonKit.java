package com.prayer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import jodd.util.StringUtil;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.JsonParserException;
import com.prayer.exception.system.ResourceIOException;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
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
	public static JsonNode readJson(
			@NotNull @NotEmpty @NotBlank final String filePath)
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

	/**
	 * 
	 * @param jsonNode
	 * @return
	 */
	public static ArrayNode fromJObject(@NotNull final JsonNode jsonNode) {
		ArrayNode arrNode = null; // NOPMD
		final String content = jsonNode.toString();
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[I] Converted json content: " + content);
			}
			arrNode = MAPPER.readValue(content, new TypeReference<ArrayNode>() {
			});
		} catch (JsonParseException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] Json parsing error.", ex);
			}
		} catch (IOException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] Resource I/O error.", ex);
			}
		}
		return arrNode;
	}

	/**
	 * 属性attr在json节点中出现的次数
	 * 
	 * @param jsonNode
	 * @param attr
	 * @return
	 */
	public static int occursAttr(@NotNull final JsonNode jsonNode,
			@NotNull @NotBlank @NotEmpty final String attr) {
		int occurs = 0;
		final Iterator<String> attrIt = jsonNode.fieldNames();
		while (attrIt.hasNext()) {
			if (StringUtil.equals(attrIt.next(), attr)) {
				occurs++;
			}
		}
		return occurs;
	}
	/**
	 * 属性attr在array节点中的每一个Json节点中出现的总次数
	 * 
	 * @param arrayNode
	 * @param attr
	 * @return
	 */
	public static int occursAttr(@NotNull final ArrayNode arrayNode,
			@NotNull @NotBlank @NotEmpty final String attr) {
		int occurs = 0;
		final Iterator<JsonNode> nodeIt = arrayNode.iterator();
		while (nodeIt.hasNext()) {
			occurs += occursAttr(nodeIt.next(), attr);
		}
		return occurs;
	}
	/**
	 * 统计Array节点中某个属性值（每个JsonObject）为固定值的次数
	 * @param arrayNode
	 * @param attr
	 * @param value
	 * @return
	 */
	public static int occursAttr(@NotNull final ArrayNode arrayNode,
			@NotNull @NotBlank @NotEmpty final String attr,
			final Object value){
		int occurs = 0;
		final Iterator<JsonNode> nodeIt = arrayNode.iterator();
		while(nodeIt.hasNext()){
			final JsonNode node = nodeIt.next();
			if(null == value){
				// null值检测
				final String jsonValue = node.path(attr).asText();
				if(null == jsonValue){
					occurs++;
				}
			}else{
				// 非null值检测
				final String jsonValue = node.path(attr).asText();
				if (StringUtil.equals(StringUtil.toUpperCase(jsonValue),
						StringUtil.toUpperCase(value.toString()))) {
					occurs++;
				}
			}
		}
		return occurs;
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
