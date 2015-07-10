package com.prayer.meta.schema.json;

import static com.prayer.util.JsonKit.occursAttr;
import static com.prayer.util.sys.Converter.fromStr;

import java.util.Iterator;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.MultiForPKPolicyException;
import com.prayer.exception.schema.PKNotOnlyOneException;
import com.prayer.mod.sys.SystemEnum.KeyCategory;
import com.prayer.mod.sys.SystemEnum.MetaPolicy;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class CrossEnsurer implements InternalEnsurer {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CrossEnsurer.class);
	// ~ Instance Fields =====================================
	/** **/
	private transient AbstractSchemaException error;
	/** **/
	@NotNull
	private transient final ArrayNode fieldsNode;
	/** **/
	@NotNull
	private transient final ArrayNode keysNode;
	/** **/
	@NotNull
	private transient final MetaPolicy pkPolicy;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param keysNode
	 * @param fieldsNode
	 */
	@PostValidateThis
	public CrossEnsurer(@NotNull final ArrayNode keysNode,
			@NotNull final ArrayNode fieldsNode,
			@NotNull final MetaPolicy policy) {
		this.keysNode = keysNode;
		this.fieldsNode = fieldsNode;
		this.pkPolicy = policy;
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 * @throws AbstractSchemaException
	 */
	@Override
	public void validate() throws AbstractSchemaException {
		// 1.验证PK的Policy和Multi的冲突
		validateMetaPKPolicy();
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

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	/**
	 * 
	 * @return
	 */
	private boolean validatePKOnlyOne() {
		// 32.验证Keys中的PrimaryKey只能有一个
		final int occurs = occursAttr(this.keysNode, Attributes.K_CATEGORY,
				KeyCategory.PrimaryKey, true);
		if(1 != occurs){	// NOPMD
			this.error = new PKNotOnlyOneException(getClass());
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("[ERR" + this.error.getErrorCode() + "] ==> Primary Key definition redundancy.",this.error);
			}
		}
		return null == this.error;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateMetaPKPolicy() {
		// 31.验证Keys中的PrimaryKey对应的Policy
		final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
		int count = 0;
		while (nodeIt.hasNext()) {
			final JsonNode node = nodeIt.next();
			final KeyCategory category = fromStr(KeyCategory.class,
					node.path(Attributes.K_CATEGORY).asText());
			if (KeyCategory.PrimaryKey != category) {
				count++;
				continue;
			}
			final Boolean isMulti = node.path(Attributes.K_MULTI).asBoolean();
			if (isMulti && MetaPolicy.COLLECTION != this.pkPolicy) {
				this.error = new MultiForPKPolicyException(getClass(), // NOPMD
						this.pkPolicy.toString(), isMulti.toString());
			} else if (!isMulti
					&& (MetaPolicy.ASSIGNED != this.pkPolicy
							|| MetaPolicy.GUID != this.pkPolicy || MetaPolicy.INCREMENT != this.pkPolicy)) {
				this.error = new MultiForPKPolicyException(getClass(), // NOPMD
						this.pkPolicy.toString(), isMulti.toString());
			}
			if (null != this.error) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("[ERR" + this.error.getErrorCode()
							+ "] ==> ( Location: index = " + count
							+ ", policy = " + this.pkPolicy.toString()
							+ ", multi=" + isMulti + " )", this.error);
				}
				break;
			}
		}
		return null == this.error;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
