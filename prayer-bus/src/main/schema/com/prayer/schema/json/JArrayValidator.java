package com.prayer.schema.json; // NOPMD

import static com.prayer.util.Instance.instance;
import static com.prayer.util.debug.Log.peError;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.exception.schema.DuplicatedAttrException;
import com.prayer.exception.schema.DuplicatedColumnException;
import com.prayer.exception.schema.DuplicatedKeyException;
import com.prayer.exception.schema.FKAttrTypeException;
import com.prayer.exception.schema.FKColumnTypeException;
import com.prayer.exception.schema.JsonTypeConfusedException;
import com.prayer.exception.schema.PKColumnTypePolicyException;
import com.prayer.exception.schema.PKMissingException;
import com.prayer.exception.schema.PKPolicyConflictException;
import com.prayer.exception.schema.SubtableWrongException;
import com.prayer.exception.schema.ZeroLengthException;
import com.prayer.util.io.JsonKit;

import net.sf.oval.constraint.AssertFieldConstraints;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 
 * @author Yu
 *
 */
@Guarded
final class JArrayValidator { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JArrayValidator.class);
    /** **/
    private static final String EXP_PRE_CON = "_this.verifiedNodes != null";
    // ~ Instance Fields =====================================
    /** **/
    @NotBlank
    @NotEmpty
    private transient String name; // NOPMD
    /** **/
    @NotNull
    private transient final ArrayNode verifiedNodes;

    /** **/
    private transient String table; // Optional Attribute

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /** **/
    @PostValidateThis
    public JArrayValidator(@NotNull final ArrayNode verifiedNodes, @AssertFieldConstraints("name") final String name) {
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
     * 
     * @param table
     */
    public void setTable(@NotNull @NotBlank @NotEmpty final String table) {
        this.table = table;
    }

    /**
     * -10006：当前属性节点长度为0异常
     * 
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyZero() {
        AbstractSchemaException retExp = null;
        if (this.verifiedNodes.size() <= 0) {
            retExp = new ZeroLengthException(getClass(), this.name);
            peError(LOGGER, retExp);
        }
        return retExp;
    }

    /**
     * -10015：外键数据类型问题
     * 
     * @param attr
     * @param filter
     * @param regexStr
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyFkColumnType(@NotNull @NotBlank @NotEmpty final String attr,
            @NotNull final ConcurrentMap<String, Object> filter, @NotNull @NotBlank @NotEmpty final String regexStr) {
        AbstractSchemaException retExp = null;
        final List<JsonNode> fkNodes = JsonKit.findNodes(this.verifiedNodes, filter);
        final Iterator<JsonNode> fkNIt = fkNodes.iterator();
        // Regex
        final Pattern pattern = Pattern.compile(regexStr);
        while (fkNIt.hasNext()) {
            final JsonNode node = fkNIt.next();
            final String value = node.path(attr).asText();
            final Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                retExp = instance(FKColumnTypeException.class.getName(), getClass(), Attributes.F_COL_TYPE);
                // new FKColumnTypeException(getClass(), Attributes.F_COL_TYPE);
                peError(LOGGER, retExp);
                break;
            }
        }
        return retExp;
    }

    /**
     * -10014：外键属性类型异常
     * 
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyFkType(@NotNull @NotBlank @NotEmpty final String attr,
            @NotNull final ConcurrentMap<String, Object> filter, @MinLength(1) final String... values) {
        AbstractSchemaException retExp = null;
        final List<JsonNode> fkNodes = JsonKit.findNodes(this.verifiedNodes, filter);
        final Iterator<JsonNode> fkNIt = fkNodes.iterator();
        while (fkNIt.hasNext()) {
            final JsonNode node = fkNIt.next();
            if (!JsonKit.isAttrIn(node, attr, values)) {
                retExp = instance(FKAttrTypeException.class.getName(), getClass(), attr,
                        node.path(Attributes.F_TYPE).asText());
                // new FKAttrTypeException(getClass(), attr,
                // node.path(Attributes.F_TYPE).asText());
                peError(LOGGER, retExp);
                break;
            }
        }
        return retExp;
    }

    /**
     * -10002：每个元素必须是JsonObject类型
     * 
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyJObjectNodes() {
        AbstractSchemaException retExp = null;
        final Iterator<JsonNode> nodeIt = this.verifiedNodes.iterator();
        while (nodeIt.hasNext()) {
            final JsonNode item = nodeIt.next();
            if (!item.isContainerNode() || item.isArray()) {
                retExp = instance(JsonTypeConfusedException.class.getName(), getClass(), "Value = " + item.toString());
                // new JsonTypeConfusedException(getClass(), "value = " +
                // item.toString());
                peError(LOGGER, retExp);
                break;
            }
        }
        return retExp;
    }

    /**
     * -10002
     * 
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyStringNodes() {
        AbstractSchemaException retExp = null;
        final Iterator<JsonNode> nodeIt = this.verifiedNodes.iterator();
        while (nodeIt.hasNext()) {
            final JsonNode item = nodeIt.next();
            if (item.isContainerNode() || !item.isTextual()) {
                retExp = instance(JsonTypeConfusedException.class.getName(), getClass(), "Value = " + item.toString());
                // new JsonTypeConfusedException(getClass(), "value = " +
                // item.toString());
                peError(LOGGER, retExp);
                break;
            }
        }
        return retExp;
    }

    /**
     * 
     * @param policy
     * @param filter
     * @param expectedValues
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyPKeyPolicyType(@NotNull final MetaPolicy policy,
            @NotNull final ConcurrentMap<String, Object> filter, @MinLength(1) final String... expectedValues) {
        final List<JsonNode> pkList = JsonKit.findNodes(this.verifiedNodes, filter);
        AbstractSchemaException retExp = null;
        for (final JsonNode jsonNode : pkList) {
            if (!JsonKit.isAttrIn(jsonNode, Attributes.F_TYPE, expectedValues)) {
                retExp = instance(PKColumnTypePolicyException.class.getName(), getClass(), policy.toString(),
                        jsonNode.path(Attributes.F_TYPE).asText());
                // new PKColumnTypePolicyException(getClass(),
                // policy.toString(),jsonNode.path(Attributes.F_TYPE).asText());
                peError(LOGGER, retExp);
                break;
            }
        }
        return retExp;
    }

    /**
     * -10011：Policy != COLLECTION
     * 
     * @param attr
     * @param policy
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyPKeyNonCOPolicy(@NotNull @NotBlank @NotEmpty final String attr,
            @NotNull final MetaPolicy policy) {
        final int occurs = JsonKit.occursAttr(this.verifiedNodes, attr, Boolean.TRUE, false);
        AbstractSchemaException retExp = null;
        if (Constants.ONE != occurs) {
            retExp = new PKPolicyConflictException(getClass(), policy.toString(), this.table);
            peError(LOGGER, retExp);
        }
        return retExp;
    }

    /**
     * -10011：Policy == COLLECTION
     * 
     * @param attr
     * @param policy
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyPKeyCOPolicy(@NotNull @NotBlank @NotEmpty final String attr,
            @NotNull final MetaPolicy policy) {
        final int occurs = JsonKit.occursAttr(this.verifiedNodes, attr, Boolean.TRUE, false);
        AbstractSchemaException retExp = null;
        if (Constants.ONE >= occurs) {
            retExp = new PKPolicyConflictException(getClass(), policy.toString(), this.table);
            peError(LOGGER, retExp);
        }
        return retExp;
    }

    /**
     * -10013：subtable不合法
     * 
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyRelMissing(@NotNull @NotBlank @NotEmpty final String attr, final Object value,
            @Min(1) final int minOccurs) {
        // Pre Condition：假设attr是存在的，即上边verifyMissing函数已经验证通过
        final int occurs = JsonKit.occursAttr(this.verifiedNodes, attr, value, false);
        AbstractSchemaException retExp = null;
        if (minOccurs > occurs) {
            retExp = new SubtableWrongException(getClass());
            peError(LOGGER, retExp);
        }
        return retExp;
    }

    /**
     * -10010：验证主键定义，至少有一个primarykey属性为true
     * 
     * @param attr
     * @param value
     * @param minOccurs
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyPKeyMissing(@NotNull @NotBlank @NotEmpty final String attr, final Object value,
            @Min(1) final int minOccurs) {
        // Pre Condition：假设attr是存在的，即上边verifyMissing函数已经验证通过
        final int occurs = JsonKit.occursAttr(this.verifiedNodes, attr, value, false);

        AbstractSchemaException retExp = null;
        if (minOccurs > occurs) {
            retExp = new PKMissingException(getClass(), this.table);
            peError(LOGGER, retExp);
        }
        return retExp;
    }

    /**
     * -10018
     * 
     * @param attr
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyKeysDuplicated(@NotNull @NotBlank @NotEmpty final String attr) {
        // Occurs的计算不能使用，attrExpStr的计算存在，所以不可使用Occurs计算
        final Set<String> setValues = new HashSet<>();
        final Iterator<JsonNode> nodeIt = this.verifiedNodes.iterator();
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final String value = node.path(attr).asText();
            final int start = setValues.size();
            setValues.add(value);
            if (start == setValues.size()) {
                break;
            }
        }

        AbstractSchemaException retExp = null;
        if (setValues.size() < this.verifiedNodes.size()) {
            retExp = new DuplicatedKeyException(getClass(), attr);
            peError(LOGGER, retExp);
        }
        return retExp;
    }

    /**
     * -10007/-10008：重名属性验证
     * 
     * @param attr
     * @return
     */
    @Pre(expr = EXP_PRE_CON, lang = Constants.LANG_GROOVY)
    public AbstractSchemaException verifyDuplicated(@NotNull @NotBlank @NotEmpty final String attr) {
        // Occurs的计算不能使用，attrExpStr的计算存在，所以不可使用Occurs计算
        final Set<String> setValues = new HashSet<>();
        final Iterator<JsonNode> nodeIt = this.verifiedNodes.iterator();
        String attrExpStr = null;
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final String value = node.path(attr).asText();
            final int start = setValues.size();
            setValues.add(value);
            if (start == setValues.size()) {
                attrExpStr = value;
                break;
            }
        }

        AbstractSchemaException retExp = null;
        if (setValues.size() < this.verifiedNodes.size()) {
            // 特殊判断，主要针对columnName字段的信息
            if (attr.equals(Attributes.F_COL_NAME)) {
                retExp = new DuplicatedColumnException(getClass(), attrExpStr);
                peError(LOGGER, retExp);
            } else {
                retExp = new DuplicatedAttrException(getClass(), attrExpStr);
                peError(LOGGER, retExp);
            }
        }
        return retExp;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
