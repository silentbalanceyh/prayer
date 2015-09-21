package com.prayer.schema.json.internal;

import static com.prayer.util.Error.message;
import static com.prayer.util.Instance.instance;

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
 * @see
 */
@Guarded
final class FieldsEnsurer implements InternalEnsurer {
    // ~ Static Fields =======================================
    /** **/
    private static final ConcurrentMap<String, String> REGEX_MAP = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    /** **/
    private transient AbstractSchemaException error;
    /** **/
    @NotNull
    private transient final ArrayNode fieldsNode;
    /** **/
    @NotNull
    private transient final JArrayValidator validator;

    // ~ Static Block ========================================
    /** Put Regex **/
    static {
        REGEX_MAP.put(Attributes.F_NAME, Attributes.RGX_F_NAME);
        REGEX_MAP.put(Attributes.F_TYPE, Attributes.RGX_F_TYPE);
        REGEX_MAP.put(Attributes.F_COL_NAME, Attributes.RGX_F_COL_NAME);
        REGEX_MAP.put(Attributes.F_COL_TYPE, Attributes.RGX_F_COL_TYPE);
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param fieldsNode
     */
    @PostValidateThis
    public FieldsEnsurer(@NotNull final ArrayNode fieldsNode) {
        this.fieldsNode = fieldsNode;
        this.error = null; // NOPMD
        this.validator = new JArrayValidator(this.fieldsNode, Attributes.R_FIELDS);
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
        validateFieldsAttr();
        interrupt();
        // 2.验证每个字段属性
        validateFieldRequired();
        interrupt();
        // 3.验证每个字段属性的Pattern
        validateFieldPattern();
        interrupt();
        // 4.验证重复字段属性
        validateDuplicated();
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
    private boolean validateFieldsAttr() {
        // 12.验证Zero长度异常的属性节点
        this.error = validator.verifyZero();
        if (null != this.error) {
            return false;
        }
        // 13.验证是否所有元素都为JsonObject
        this.error = validator.verifyJObjectNodes();
        if (null != this.error) {
            return false;
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateFieldRequired() {
        // 14.验证__fields__字段元素
        final Iterator<JsonNode> nodeIt = this.fieldsNode.iterator();
        int idx = 0;
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
                    message("D10000.FIDX", idx, node.path(Attributes.F_NAME).asText()));
            // new JObjectValidator(node,message("D10000.FIDX", idx,
            // node.path(Attributes.F_NAME).asText()));
            // 14.1.验证每一个__fields__中的Required元素
            this.error = validator.verifyRequired(Attributes.F_NAME, Attributes.F_TYPE, Attributes.F_COL_NAME,
                    Attributes.F_COL_TYPE);
            if (null != this.error) {
                break;
            }
            idx++;
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateFieldPattern() {
        // 14.验证__fields__字段元素
        final Iterator<JsonNode> nodeIt = this.fieldsNode.iterator();
        int idx = 0;
        outer: while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
                    message("D10000.FIDX", idx, node.path(Attributes.F_NAME).asText()));
            // new JObjectValidator(node, message("D10000.FIDX", idx,
            // node.path(Attributes.F_NAME).asText()));
            // 14.2.验证每一个__fields__中的Required元素的格式
            for (final String attr : REGEX_MAP.keySet()) {
                this.error = validator.verifyPattern(attr, REGEX_MAP.get(attr));
                if (null != this.error) {
                    break outer;
                }
            }
            idx++;
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateDuplicated() {
        // 15.验证重复属性name
        this.error = validator.verifyDuplicated(Attributes.F_NAME);
        if (null != this.error) {
            return false;
        }
        // 16.验证重复列columnName属性
        this.error = validator.verifyDuplicated(Attributes.F_COL_NAME);
        if (null != this.error) {
            return false;
        }
        return null == this.error;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
