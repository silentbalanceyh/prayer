package com.prayer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import jodd.util.StringUtil;
import net.sf.oval.constraint.MinLength;
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
	 * 从一个Json文件中读取Json字符串内容
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
				LOGGER.debug("[ERR-20003] Json parsing error.", ex);
			}
			throw new JsonParserException(JsonKit.class, ex.toString()); // NOPMD
		} catch (IOException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[ERR-20002] Resource I/O error.", ex);
			}
			throw new ResourceIOException(JsonKit.class, filePath); // NOPMD
		}
		return retNode;
	}

	/**
	 * 将一个jsonNode节点的内容转换成ArrayNode，传入的JsonNode是一个JsonArray
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
				LOGGER.debug("[ERR-20003] Json parsing error.", ex);
			}
		} catch (IOException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[ERR-20002] Resource I/O error.", ex);
			}
		}
		return arrNode;
	}

	/**
	 * 将一个arrayNode转换成一个List<JsonNode>列表，不带任何filter的转换
	 * 
	 * @param arrayNode
	 * @return
	 */
	public static List<JsonNode> fromJArray(@NotNull final ArrayNode arrayNode) {
		final List<JsonNode> jnList = new ArrayList<>();
		final Iterator<JsonNode> jnIt = arrayNode.iterator();
		while (jnIt.hasNext()) {
			final JsonNode jnNode = jnIt.next();
			if (jnNode.isContainerNode()) {
				jnList.add(jnNode);
			}
		}
		return jnList;
	}

	/**
	 * 计算attr属性在（JsonObject）jsonNode节点中出现的次数
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
	 * 计算attr属性在（JsonArray）array节点中的每一个Json节点（JsonObject）中出现的总次数
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
	 * @param arrayNode
	 * @param attr
	 * @param value
	 * @param caseSensitive
	 * @return
	 */
	public static int occursAttr(@NotNull final ArrayNode arrayNode,
			@NotNull @NotBlank @NotEmpty final String attr, final Object value,
			final boolean caseSensitive) {
		int occurs = 0;
		final Iterator<JsonNode> nodeIt = arrayNode.iterator();
		while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			occurs += occursAttr(node, attr, value, caseSensitive);
		}
		return occurs;
	}

	/**
	 * @param jsonNode
	 * @param attr
	 * @param value
	 * @param caseSensitive
	 * @return
	 */
	public static int occursAttr(@NotNull final JsonNode jsonNode,
			@NotNull @NotBlank @NotEmpty final String attr, final Object value,
			final boolean caseSensitive) {
		int occurs = 0;
		if (null == value) {
			// null 值检查
			final String jsonValue = jsonNode.path(attr).asText();
			if (null == jsonValue) {
				occurs++;
			}
		} else {
			// 非null值检测
			final String jsonValue = jsonNode.path(attr).asText();
			if (caseSensitive) {
				// 大小写敏感
				if (StringUtil.equals(jsonValue, value.toString())) {
					occurs++;
				}
			} else {
				// 大小写不敏感
				if (StringUtil.equals(StringUtil.toUpperCase(jsonValue),
						StringUtil.toUpperCase(value.toString()))) {
					occurs++;
				}
			}
		}
		return occurs;
	}

	/**
	 * 统计一个jsonNode节点中的属性的值value是否在values这些值的集合内（区分大小写匹配）
	 * 
	 * @param jsonNode
	 * @param attr
	 * @param values
	 * @return
	 */
	public static boolean isAttrIn(@NotNull final JsonNode jsonNode,
			@NotNull @NotBlank @NotEmpty final String attr,
			@MinLength(1) final String... values) {
		final JsonNode attrNode = jsonNode.path(attr);
		final String jsonValue = attrNode.asText();
		boolean ret = false;
		for (final String value : values) {
			if (StringKit.isNonNil(jsonValue)
					&& StringUtil.equals(value, jsonValue)) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	/**
	 * 从arrayNode中查找满足filter条件的JsonNode的列表
	 * 
	 * @param filter
	 * @return
	 */
	public static List<JsonNode> findNodes(@NotNull final ArrayNode arrayNode,
			final ConcurrentMap<String, Object> filter) {
		final List<JsonNode> jnList = new ArrayList<>();
		if (null == filter || filter.isEmpty()) {
			jnList.addAll(fromJArray(arrayNode));
		} else {
			final Iterator<JsonNode> nodeIt = arrayNode.iterator();
			while (nodeIt.hasNext()) {
				final JsonNode jsonNode = nodeIt.next();
				if (isMatch(jsonNode, filter)) {
					jnList.add(jsonNode);
				}
			}
		}
		return jnList;
	}

	/**
	 * 当前jsonNode是否满足attr的filter，满足则返回true，否则false
	 * 
	 * @param jsonNode
	 * @param filter
	 * @return
	 */
	public static boolean isMatch(@NotNull final JsonNode jsonNode,
			final ConcurrentMap<String, Object> filter) {
		boolean ret = true;
		if (null != filter && !filter.isEmpty()) {
			// filter中没有任何内容，直接返回匹配
			for (final String key : filter.keySet()) {
				final int occurs = occursAttr(jsonNode, key, filter.get(key),
						true);
				if (occurs <= 0) {
					ret = false;
					break;
				}
			}
		}
		return ret;
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
