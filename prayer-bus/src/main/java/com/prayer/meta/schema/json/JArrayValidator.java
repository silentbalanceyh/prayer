package com.prayer.meta.schema.json;

import java.util.Iterator;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.AttrJsonTypeException;
import com.prayer.exception.schema.AttrZeroException;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
final class JArrayValidator {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JArrayValidator.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotBlank
	@NotEmpty
	private transient String name; // NOPMD
	/** **/
	@NotNull
	private transient ArrayNode verifiedNodes;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public JArrayValidator(@NotNull final ArrayNode verifiedNodes) {
		this(verifiedNodes, null);
	}

	/** **/
	@PostValidateThis
	public JArrayValidator(@NotNull final ArrayNode verifiedNodes,
			@NotEmpty @NotBlank final String name) {
		this.verifiedNodes = verifiedNodes;
		if (null == name) {
			this.name = "ROOT";
		} else {
			this.name = name;
		}
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * -10006：当前属性节点长度为0异常
	 * 
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyZero() {
		AbstractSchemaException retExp = null;
		if (this.verifiedNodes.size() <= 0) {
			retExp = new AttrZeroException(getClass(), this.name);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] ==> Error-10006 ( Location: " + this.name
						+ " )", retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10002：每个元素必须是JsonObject类型
	 * 
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyPojoNodes() {
		AbstractSchemaException retExp = null;
		final Iterator<JsonNode> nodeIt = this.verifiedNodes.iterator();
		while (nodeIt.hasNext()) {
			final JsonNode item = nodeIt.next();
			if (!item.isContainerNode()) {
				retExp = new AttrJsonTypeException(getClass(),"value = " + item.toString()); // NOPMD
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(
							"[E] ==> Error-10002 ( Location: "
									+ item.toString() + " )", retExp);
				}
				break;
			}
		}
		return retExp;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
