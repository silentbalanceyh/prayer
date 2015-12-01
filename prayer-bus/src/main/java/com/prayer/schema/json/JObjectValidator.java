package com.prayer.schema.json;    // NOPMD

import static com.prayer.util.Converter.toStr;
import static com.prayer.util.Error.debug;
import static com.prayer.util.Instance.instance;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.DuplicatedTablesException;
import com.prayer.exception.schema.InvalidValueException;
import com.prayer.exception.schema.JsonTypeConfusedException;
import com.prayer.exception.schema.OptionalAttrMorEException;
import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.exception.schema.UnsupportAttrException;
import com.prayer.util.JsonKit;
import com.prayer.util.cv.Constants;

import jodd.util.StringUtil;
import net.sf.oval.constraint.AssertFieldConstraints;
import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
final class JObjectValidator {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JObjectValidator.class);
    /** **/
    private static final String EXP_PRE_CON = "_this.verifiedNode != null";
    // ~ Instance Fields =====================================
    /** **/
    @NotBlank
    @NotEmpty
    private transient String name; // NOPMD
    /** **/
    @NotNull
    private transient final JsonNode verifiedNode;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /** **/
    @PostValidateThis
    public JObjectValidator(@NotNull final JsonNode verifiedNode, @AssertFieldConstraints("name") final String name) {
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
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyRequired(@MinLength(1) final String... attrs) {
        AbstractSchemaException retExp = null;
        for (final String attr : attrs) {
            final int occurs = JsonKit.occursAttr(this.verifiedNode, attr);
            if (0 == occurs) {
                retExp = instance(RequiredAttrMissingException.class.getName(), getClass(), attr);
                // retExp = new RequiredAttrMissingException(getClass(), attr);
                debug(LOGGER, getClass(), "D10001", retExp, this.name, attr);
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
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyNotSame(@NotNull @NotEmpty @NotBlank final String attrLeft,
            @NotNull @NotEmpty @NotBlank final String attrRight) {
        final JsonNode leftNode = this.verifiedNode.path(attrLeft);
        final JsonNode rightNode = this.verifiedNode.path(attrRight);
        AbstractSchemaException retExp = null;
        if (StringUtil.equals(leftNode.asText(), rightNode.asText())) {
            retExp = new DuplicatedTablesException(getClass(), attrLeft, attrRight);
            debug(LOGGER, getClass(), "D10020", retExp, this.name, attrLeft, leftNode.asText(), attrRight,
                    rightNode.asText());
        }
        return retExp;
    }

    /**
     * -10002: Array，当前节点某属性节点是Array就合法，否则异常
     * 
     * @param attr
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyJArray(@NotNull @NotEmpty @NotBlank final String attr) {
        final JsonNode attrNode = this.verifiedNode.path(attr);

        AbstractSchemaException retExp = null;
        if (!attrNode.isArray() && attrNode.isContainerNode()) {
            retExp = new JsonTypeConfusedException(getClass(), attr);
            debug(LOGGER, getClass(), "D10002.ARR", retExp, this.name, attr);
        }
        return retExp;
    }

    /**
     * -10002: Object，当前节点某属性节点是Object就合法，否则异常
     * 
     * @param attr
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyJObject(@NotNull @NotEmpty @NotBlank final String attr) {
        final JsonNode attrNode = this.verifiedNode.path(attr);

        AbstractSchemaException retExp = null;
        if (attrNode.isArray()) {
            retExp = new JsonTypeConfusedException(getClass(), attr);
            debug(LOGGER, getClass(), "D10002.OBJ", retExp, this.name, attr);
        }
        return retExp;
    }

    /**
     * -10017: 支持的属性验证，出现了穿入属性之外的属性则抛异常
     * 
     * @param attrs
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyUnsupported(@MinLength(1) final String... attrs) {
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
            debug(LOGGER, getClass(), "D10017", retExp, this.name, toStr(unsupportedSet));
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
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyPattern(@NotNull @NotEmpty @NotBlank final String attr,
            @NotNull @NotEmpty @NotBlank final String regexStr) {
        final JsonNode attrNode = this.verifiedNode.path(attr);
        final Pattern pattern = Pattern.compile(regexStr);
        final String value = attrNode.asText();
        final Matcher matcher = pattern.matcher(value);

        AbstractSchemaException retExp = null;
        if (!matcher.matches()) {
            retExp = new PatternNotMatchException(getClass(), attr, value, regexStr);
            debug(LOGGER, getClass(), "D10003", retExp, this.name, attr, value, regexStr);
        }
        return retExp;
    }

    /**
     * Error-10004：【单属性检查】属性必须同时存在但丢失，不考虑Required
     * 
     * @param attrs
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyMissing(@MinLength(1) final String... attrs) {
        AbstractSchemaException retExp = null;
        for (final String attr : attrs) {
            final int occurs = JsonKit.occursAttr(this.verifiedNode, attr);
            if (0 == occurs) {
                retExp = instance(OptionalAttrMorEException.class.getName(), getClass(), attr, "Missing");
                // retExp = new OptionalAttrMorEException(getClass(), attr,
                // "Missing");
                debug(LOGGER, getClass(), "D10004.MIS", retExp, this.name, this.name, attr);
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
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyExisting(@MinLength(1) final String... attrs) {
        AbstractSchemaException retExp = null;
        for (final String attr : attrs) {
            final int occurs = JsonKit.occursAttr(this.verifiedNode, attr);
            if (0 < occurs) {
                retExp = instance(OptionalAttrMorEException.class.getName(), getClass(), attr, "Existing");
                // new OptionalAttrMorEException(getClass(), attr, "Existing");
                debug(LOGGER, getClass(), "D10004.EXT", retExp, this.name, this.name, attr);
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
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyIn(@NotNull @NotBlank @NotEmpty final String attr,
            @MinLength(1) final String... values) {
        AbstractSchemaException retExp = null;
        if (!JsonKit.isAttrIn(this.verifiedNode, attr, values)) {
            retExp = new InvalidValueException(getClass(), attr, toStr(values), this.verifiedNode.path(attr).asText(),
                    "");
            debug(LOGGER, getClass(), "D10005.IN", retExp, this.name, this.name, attr, toStr(values));
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
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyNotIn(@NotNull @NotBlank @NotEmpty final String attr,
            @MinLength(1) final String... values) {
        AbstractSchemaException retExp = null;
        if (JsonKit.isAttrIn(this.verifiedNode, attr, values)) {
            retExp = new InvalidValueException(getClass(), attr, toStr(values), this.verifiedNode.path(attr).asText(),
                    "n't");
            debug(LOGGER, getClass(), "D10005.NIN", retExp, this.name, this.name, attr, toStr(values));
        }
        return retExp;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}