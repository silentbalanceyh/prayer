package com.prayer.schema.json;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Error.message;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Log.peError;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.KeysNameSpecificationException;
import com.prayer.exception.schema.MultiForFKPolicyException;
import com.prayer.exception.schema.PatternNotMatchException;
import com.prayer.util.JsonKit;
import com.prayer.util.cv.SystemEnum.KeyCategory;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class KeysEnsurer implements InternalEnsurer {
    // ~ Static Fields =======================================
    /** **/
    private static final ConcurrentMap<String, String> REGEX_MAP = new ConcurrentHashMap<>();
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(KeysEnsurer.class);
    /** **/
    private static final String MT_MSG = "Multi = \"true\", Size > \"1\".";
    /** **/
    private static final String MF_MSG = "Multi = \"false\", Size == \"1\".";
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
    @PreValidateThis
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
        // 4.验证重复键
        validateKeysDuplicated();
        interrupt();
        // 5.验证columns属性的相关信息
        validateColumns();
        interrupt();
        // 6.验证multi和columns的关系
        validateMulti();
        interrupt();
        // 7.验证multi和FK的定义关系
        validateMultiForFK();
        interrupt();
        // 8.验证name和category是否规范
        validateKeysNameSpec();
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
            return false;
        }
        // 25.2.验证元素是否为JsonObject
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
    private boolean validateKeysRequired() {
        // 26.验证__keys__中的属性
        final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
        int idx = 0;
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
                    message("D10000.FKIDX", idx, node.path(Attributes.K_NAME).asText()));
            // new JObjectValidator(node,message("D10000.FKIDX", idx,
            // node.path(Attributes.K_NAME).asText()));
            // 26.1.验证__keys__中的Json对象的Required属性
            this.error = validator.verifyRequired(Attributes.K_NAME, Attributes.K_MULTI, Attributes.K_CATEGORY,
                    Attributes.K_COLUMNS);
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
    private boolean validateKeysPattern() {
        // 27.验证__keys__中的属性的模式匹配
        final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
        int idx = 0;
        outer: while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
                    message("D10000.FKIDX", idx, node.path(Attributes.K_NAME).asText()));
            // new JObjectValidator(node, message("D10000.FKIDX", idx,
            // node.path(Attributes.K_NAME).asText()));
            // 27.1.模式匹配
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
    private boolean validateKeysDuplicated() {
        // 27.2.验证重复键
        this.error = validator.verifyKeysDuplicated(Attributes.K_NAME);
        if (null != this.error) {
            return false;
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateKeysNameSpec() {
        // 30.验证Keys的命名规范
        final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final KeyCategory category = fromStr(KeyCategory.class, node.path(Attributes.K_CATEGORY).asText());
            final String name = node.path(Attributes.K_NAME).asText();
            if (KeyCategory.PrimaryKey == category && !name.startsWith("PK")) {
                this.error = instance(KeysNameSpecificationException.class.getName(), getClass(), name,
                        category.toString());
                // this.error = new KeysNameSpecificationException(getClass(),
                // name, category.toString());
            } else if (KeyCategory.ForeignKey == category && !name.startsWith("FK")) {
                this.error = instance(KeysNameSpecificationException.class.getName(), getClass(), name,
                        category.toString());
                // this.error = new KeysNameSpecificationException(getClass(),
                // name, category.toString());
            } else if (KeyCategory.UniqueKey == category && !name.startsWith("UK")) {
                this.error = instance(KeysNameSpecificationException.class.getName(), getClass(), name,
                        category.toString());
                // this.error = new KeysNameSpecificationException(getClass(),
                // name, category.toString());
            }
            if (null != this.error) {
                peError(LOGGER, this.error);
                break;
            }
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
            final ArrayNode columns = JsonKit.fromJObject(node.path(Attributes.K_COLUMNS));
            final JArrayValidator validator = instance(JArrayValidator.class.getName(), columns, Attributes.K_COLUMNS);
            // new JArrayValidator(columns, Attributes.K_COLUMNS);
            // 28.1.验证columns中是否有元素
            this.error = validator.verifyZero();
            if (null != this.error) {
                break;
            }
            // 28.2.验证columns中是不是都是String
            this.error = validator.verifyStringNodes();
            if (null != this.error) {
                break;
            }
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateMulti() {
        // 29.根据Multi验证columns的length
        final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final ArrayNode columns = JsonKit.fromJObject(node.path(Attributes.K_COLUMNS));
            final Boolean isMulti = node.path(Attributes.K_MULTI).asBoolean();
            if (isMulti && columns.size() <= 1) {
                // 29.1.multi = true, Size > 1
                this.error = instance(PatternNotMatchException.class.getName(), getClass(), Attributes.K_COLUMNS,
                        "Size: " + columns.size(), MT_MSG);
                // new PatternNotMatchException(getClass(),
                // Attributes.K_COLUMNS, "Size: " + columns.size(),MT_MSG);
            } else if (!isMulti && columns.size() > 1) {
                // 29.2.multi = false, Size = 1
                this.error = instance(PatternNotMatchException.class.getName(), getClass(), Attributes.K_COLUMNS,
                        "Size: " + columns.size(), MF_MSG);
                // new PatternNotMatchException(getClass(),
                // Attributes.K_COLUMNS, "Size: " + columns.size(),MF_MSG);
            }
            if (null != this.error) {
                peError(LOGGER, this.error);
                break;
            }
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateMultiForFK() {
        // 29.3.multi = true
        final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final Boolean isMulti = node.path(Attributes.K_MULTI).asBoolean();
            if (isMulti) {
                final KeyCategory category = fromStr(KeyCategory.class, node.path(Attributes.K_CATEGORY).asText());
                if (KeyCategory.ForeignKey == category) {
                    final String name = node.path(Attributes.K_NAME).asText();
                    this.error = instance(MultiForFKPolicyException.class.getName(), getClass(), name);
                    // new MultiForFKPolicyException(getClass(), name);
                    peError(LOGGER, this.error);
                    break;
                }
            }
        }
        return null == this.error;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
