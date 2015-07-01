package com.prayer.meta.schema.json;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.AttrJsonTypeException;
import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.exception.schema.UnsupportAttrException;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
class JsonSchemaValidator {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JsonSchemaValidator.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotBlank
	@NotEmpty
	private transient String name; // NOPMD
	/** **/
	@NotNull
	private transient JsonNode verifiedNode;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public JsonSchemaValidator(@NotNull final JsonNode verifiedNode) {
		this(verifiedNode, null);
	}

	/** **/
	@PostValidateThis
	public JsonSchemaValidator(@NotNull final JsonNode verifiedNode,
			@NotEmpty @NotBlank final String name) {
		this.verifiedNode = verifiedNode;
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
	 * -10001: 必要属性丢失
	 * 
	 * @param attrs
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyRequired(
			@MinLength(1) final String... attrs) {
		final Set<String> reqAttrs = new HashSet<>(Arrays.asList(attrs));
		final Iterator<String> fieldIt = this.verifiedNode.fieldNames();
		while (fieldIt.hasNext()) {
			reqAttrs.remove(fieldIt.next());
		}
		
		AbstractSchemaException retExp = null;
		if (!reqAttrs.isEmpty()) {
			retExp = new RequiredAttrMissingException(getClass(), reqAttrs);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] ==> Error-10001 ( Location : " + this.name
						+ " )", retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10002: Array，当前节点某属性节点是Array就合法，否则异常
	 * 
	 * @param attr
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyJArray(
			@NotNull @NotEmpty @NotBlank final String attr) {
		final JsonNode attrNode = this.verifiedNode.path(attr);

		AbstractSchemaException retExp = null;
		if (!attrNode.isArray()) {
			retExp = new AttrJsonTypeException(getClass(), attr);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] ==> Error-10002 ( Array/Location : "
						+ this.name + " )", retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10002: Object，当前节点某属性节点是Object就合法，否则异常
	 * 
	 * @param attr
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyJObject(
			@NotNull @NotEmpty @NotBlank final String attr) {
		final JsonNode attrNode = this.verifiedNode.path(attr);

		AbstractSchemaException retExp = null;
		if (attrNode.isArray()) {
			retExp = new AttrJsonTypeException(getClass(), attr);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] ==> Error-10002 ( Object/Location : "
						+ this.name + " )", retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10017: 不支持的属性验证
	 * 
	 * @param attrs
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyUnsupported(
			@MinLength(1) final String... attrs) {
		final Set<String> reqAttrs = new HashSet<>(Arrays.asList(attrs));
		final Set<String> unsupportedSet = new HashSet<>();
		final Iterator<String> fieldIt = this.verifiedNode.fieldNames();
		while (fieldIt.hasNext()) {
			final String verifiedAttr = fieldIt.next();
			if (!reqAttrs.contains(verifiedAttr)) {
				unsupportedSet.add(verifiedAttr);
			}
		}
		
		AbstractSchemaException retExp = null;
		if (!unsupportedSet.isEmpty()) {
			retExp = new UnsupportAttrException(getClass(), unsupportedSet);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] ==> Error-10017 ( Location: " + this.name
						+ " )", retExp);
			}
		}
		return retExp;
	}
	
	/**
	 * Error-10003
	 * @param attr
	 * @param regexStr
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyPattern(
			@NotNull @NotEmpty @NotBlank final String attr,
			@NotNull @NotEmpty @NotBlank final String regexStr) {
		final JsonNode attrNode = this.verifiedNode.path(attr);
		final Pattern pattern = Pattern.compile(regexStr);
		final String value = attrNode.textValue();
		final Matcher matcher = pattern.matcher(value);

		AbstractSchemaException retExp = null;
		if (!matcher.matches()) {
			retExp = new PatternNotMatchException(getClass(), attr, value,
					regexStr);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] ==> Error-10003 ( Location: " + this.name
						+ " )", retExp);
			}
		}
		return retExp;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
