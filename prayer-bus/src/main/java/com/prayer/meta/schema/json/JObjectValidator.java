package com.prayer.meta.schema.json;

import static com.prayer.util.JsonKit.isAttrIn;
import static com.prayer.util.JsonKit.occursAttr;
import static com.prayer.util.sys.Converter.toStr;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jodd.util.StringUtil;
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
import com.prayer.exception.schema.InvalidValueException;
import com.prayer.exception.schema.OptionalAttrMorEException;
import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.exception.schema.SameConflictException;
import com.prayer.exception.schema.UnsupportAttrException;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
final class JObjectValidator {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JObjectValidator.class);
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
	public JObjectValidator(@NotNull final JsonNode verifiedNode) {
		this(verifiedNode, null);
	}

	/** **/
	@PostValidateThis
	public JObjectValidator(@NotNull final JsonNode verifiedNode,
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
		AbstractSchemaException retExp = null;
		for (final String attr : attrs) {
			final int occurs = occursAttr(this.verifiedNode, attr);
			if (0 == occurs) {
				retExp = new RequiredAttrMissingException(getClass(), attr); // NOPMD
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("[ERR-10001] ==> Error-10001 ( Location : "
							+ this.name + " [" + attr + "] )", retExp);
				}
			}
		}
		return retExp;
	}

	/**
	 * -10020：两个属性是否不等，如果不等返回true，如果相等则报错
	 * 
	 * @param attrLeft
	 * @param attrRight
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyNotSame(
			@NotNull @NotEmpty @NotBlank final String attrLeft,
			@NotNull @NotEmpty @NotBlank final String attrRight) {
		final JsonNode leftNode = this.verifiedNode.path(attrLeft);
		final JsonNode rightNode = this.verifiedNode.path(attrRight);
		AbstractSchemaException retExp = null;
		if (StringUtil.equals(leftNode.asText(), rightNode.asText())) {
			retExp = new SameConflictException(getClass(), attrLeft, attrRight);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[ERR-10020] ==> Error-10020 ( Attribute '"
						+ attrLeft + "' and '" + attrRight
						+ "' are same value and they are conflicts.", retExp);
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
		if (!attrNode.isArray() && attrNode.isContainerNode()) {
			retExp = new AttrJsonTypeException(getClass(), attr);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[ERR-10002] ==> Error-10002 ( Array/Location : "
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
				LOGGER.debug("[ERR-10002] ==> Error-10002 ( Object/Location : "
						+ this.name + " )", retExp);
			}
		}
		return retExp;
	}

	/**
	 * -10017: 支持的属性验证，出现了穿入属性之外的属性则抛异常
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
				LOGGER.debug("[ERR-10017] ==> Error-10017 ( Location: "
						+ this.name + " )", retExp);
			}
		}
		return retExp;
	}

	/**
	 * Error-10003
	 * 
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
		final String value = attrNode.asText();
		final Matcher matcher = pattern.matcher(value);

		AbstractSchemaException retExp = null;
		if (!matcher.matches()) {
			retExp = new PatternNotMatchException(getClass(), attr, value,
					regexStr);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[ERR-10003] ==> Error-10003 ( Location: "
						+ this.name + " )", retExp);
			}
		}
		return retExp;
	}

	/**
	 * Error-10004：【单属性检查】属性必须同时存在但丢失，不考虑Required
	 * 
	 * @param attrs
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyMissing(
			@MinLength(1) final String... attrs) {
		AbstractSchemaException retExp = null;
		for (final String attr : attrs) {
			final int occurs = occursAttr(this.verifiedNode, attr);
			if (0 == occurs) {
				retExp = new OptionalAttrMorEException(getClass(), attr, // NOPMD
						"Missing");
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("[ERR-10004] ==> Error-10004 ( Location: "
							+ this.name + " ) Missing, ", retExp);
				}
				break;
			}
		}
		return retExp;
	}

	/**
	 * Error-10004：【单属性检查】属性必须同时不存在但存在了，不考虑Required
	 * 
	 * @param attrs
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyExisting(
			@MinLength(1) final String... attrs) {
		AbstractSchemaException retExp = null;
		for (final String attr : attrs) {
			final int occurs = occursAttr(this.verifiedNode, attr);
			if (0 < occurs) {
				retExp = new OptionalAttrMorEException(getClass(), attr, // NOPMD
						"Existing");
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("[ERR-10004] ==> Error-10004 ( Location: "
							+ this.name + " ) Existing, ", retExp);
				}
				break;
			}
		}
		return retExp;
	}

	/**
	 * Error-10005：【单属性检查】如果存在则Pass，不存在这个值就错
	 * 
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyIn(
			@NotNull @NotBlank @NotEmpty final String attr,
			@MinLength(1) final String... values) {
		AbstractSchemaException retExp = null;
		if (!isAttrIn(this.verifiedNode, attr, values)) {
			retExp = new InvalidValueException(getClass(), attr, // NOPMD
					toStr(values), this.verifiedNode.path(attr).asText(), "");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[ERR-10005] ==> Error-10005 ( Location: "
						+ this.name + " ) In, ", retExp);
			}
		}
		return retExp;
	}

	/**
	 * Error-10005：【单属性检查】如果不存在则Pass，存在这个值就错
	 * 
	 * @param attr
	 * @param values
	 * @return
	 */
	@PreValidateThis
	public AbstractSchemaException verifyNotIn(
			@NotNull @NotBlank @NotEmpty final String attr,
			@MinLength(1) final String... values) {
		AbstractSchemaException retExp = null;
		if (isAttrIn(this.verifiedNode, attr, values)) {
			retExp = new InvalidValueException(getClass(), attr, // NOPMD
					toStr(values), this.verifiedNode.path(attr).asText(), "n't");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[ERR-10005] ==> Error-10005 ( Location: "
						+ this.name + " ) NotIn, ", retExp);
			}
		}
		return retExp;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
