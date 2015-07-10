package com.prayer.meta.schema.json;

import static com.prayer.util.JsonKit.fromJObject;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class KeysEnsurer implements InternalEnsurer {
	// ~ Static Fields =======================================
	/** **/
	private static final ConcurrentMap<String, String> REGEX_MAP = new ConcurrentHashMap<>();
	// ~ Instance Fields =====================================
	/** **/
	private transient AbstractSchemaException error;
	/** **/
	@NotNull
	private transient final ArrayNode keysNode;
	/** **/
	@NotNull
	private transient final JArrayValidator validator;

	// ~ Static Block ========================================
	/** Put Regex **/
	static {
		REGEX_MAP.put(Attributes.K_NAME, Attributes.RGX_K_NAME);
		REGEX_MAP.put(Attributes.K_CATEGORY, Attributes.RGX_K_CATEGORY);
		REGEX_MAP.put(Attributes.K_MULTI, Attributes.RGX_K_MULTI);
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param keysNode
	 */
	@PostValidateThis
	public KeysEnsurer(@NotNull final ArrayNode keysNode) {
		this.keysNode = keysNode;
		this.error = null; // NOPMD
		this.validator = new JArrayValidator(this.keysNode, Attributes.R_KEYS);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Override
	public void validate() throws AbstractSchemaException {
		// 1.验证Zero长度异常
		validateKeysAttr();
		interrupt();
		// 2.验证Required属性
		validateKeysRequired();
		interrupt();
		// 3.验证每个字段属性的Pattern
		validateKeysPattern();
		interrupt();
		// 4.验证columns属性的相关信息
		validateColumns();
		interrupt();
	}

	/**
	 * 根据结果判断返回值
	 * 
	 * @param result
	 */
	@Override
	public void interrupt() throws AbstractSchemaException {
		if (null != this.error) {
			throw this.error;
		}
	}

	// ~ Private Methods =====================================
	/**
	 * 
	 * @return
	 */
	private boolean validateKeysAttr() {
		// 25.1.验证__keys__的长度
		this.error = validator.verifyZero();
		if (null != this.error) {
			return false; // NOPMD
		}
		// 25.2.验证元素是否为JsonObject
		this.error = validator.verifyJObjectNodes();
		if (null != this.error) {
			return false; // NOPMD
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateKeysRequired() {
		// 26.验证__keys__中的属性
		final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
		int count = 0;
		while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			final JObjectValidator validator = new JObjectValidator(node, // NOPMD
					"Node: __keys__ ==> index: " + count);
			// 26.1.验证__keys__中的Json对象的Required属性
			this.error = validator.verifyRequired(Attributes.K_NAME,
					Attributes.K_MULTI, Attributes.K_CATEGORY,
					Attributes.K_COLUMNS);
			if (null != this.error) {
				break;
			}
			count++;
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateKeysPattern() {
		// 27.验证__keys__中的属性的模式匹配
		final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
		int count = 0;
		outer: while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			final JObjectValidator validator = new JObjectValidator(node, // NOPMD
					"Node: __keys__ ==> index: " + count + ", name: "
							+ node.path(Attributes.K_NAME).asText());
			// 27.1.模式匹配
			for (final String attr : REGEX_MAP.keySet()) {
				this.error = validator.verifyPattern(attr, REGEX_MAP.get(attr));
				if (null != this.error) {
					break outer;
				}
			}
			count++;
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateColumns() {
		// 28.验证columns的格式
		final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
		while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			final ArrayNode columns = fromJObject(node
					.path(Attributes.K_COLUMNS));
			final JArrayValidator validator = new JArrayValidator(columns,	// NOPMD
					Attributes.K_COLUMNS);
			// 28.1.验证columns中是否有元素
			this.error = validator.verifyZero();
			if(null != this.error){
				break;
			}
			// 28.2.验证columns中是不是都是String
			this.error = validator.verifyStringNodes();
			if(null != this.error){
				break;
			}
		}
		return null == this.error;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
