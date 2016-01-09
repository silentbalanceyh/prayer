package com.prayer.schema.json;

import static com.prayer.util.Converter.fromStr;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.Category;
import com.prayer.constant.SystemEnum.Mapping;
import com.prayer.constant.SystemEnum.MetaPolicy;

import jodd.util.StringUtil;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
final class MetaEnsurer implements InternalEnsurer {
    // ~ Static Fields =======================================
    /** Meta Required **/
    private static final String[] M_REQUIRED = new String[] { Attributes.M_NAMESPACE, Attributes.M_NAME,
            Attributes.M_CATEGORY, Attributes.M_TABLE, Attributes.M_IDENTIFIER, Attributes.M_MAPPING,
            Attributes.M_POLICY };
    /** Meta Attributes **/
    private static final String[] M_ATTRS = new String[] { Attributes.M_NAMESPACE, Attributes.M_NAME,
            Attributes.M_CATEGORY, Attributes.M_TABLE, Attributes.M_IDENTIFIER, Attributes.M_MAPPING,
            Attributes.M_POLICY, // Required
            Attributes.M_SUB_KEY, Attributes.M_SUB_TABLE, // SubTable
            Attributes.M_SEQ_NAME, Attributes.M_SEQ_STEP, Attributes.M_SEQ_INIT // Sequence
    };
    /** **/
    private static final ConcurrentMap<String, String> REGEX_MAP = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    /** **/
    private transient AbstractSchemaException error;
    /** **/
    @NotNull
    private transient final JsonNode metaNode;
    /** **/
    @NotNull
    private transient final JObjectValidator validator;

    // ~ Static Block ========================================
    /** Put Regex **/
    static {
        REGEX_MAP.put(Attributes.M_NAME, Attributes.RGX_M_NAME);
        REGEX_MAP.put(Attributes.M_NAMESPACE, Attributes.RGX_M_NAMESPACE);
        REGEX_MAP.put(Attributes.M_CATEGORY, Attributes.RGX_M_CATEGORY);
        REGEX_MAP.put(Attributes.M_TABLE, Attributes.RGX_M_TABLE);
        REGEX_MAP.put(Attributes.M_IDENTIFIER, Attributes.RGX_M_IDENTIFITER);
        REGEX_MAP.put(Attributes.M_MAPPING, Attributes.RGX_M_MAPPING);
        REGEX_MAP.put(Attributes.M_POLICY, Attributes.RGX_M_POLICY);
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 单参构造函数
     * 
     * @param metaNode
     */
    @PostValidateThis
    public MetaEnsurer(@NotNull final JsonNode metaNode) {
        super();
        this.metaNode = metaNode;
        this.error = null; // NOPMD
        this.validator = new JObjectValidator(this.metaNode, Attributes.R_META);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    @PreValidateThis
    public void validate() throws AbstractSchemaException {
        // 1.验证root节点：__keys__, __meta__, __fields__
        validateMetaAttr();
        interrupt();

        // 2.验证子流程
        validateDispatcher();
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
    private boolean validateDispatcher() {
        final Category category = fromStr(Category.class,
                this.metaNode.path(Attributes.M_CATEGORY).textValue());
        final Mapping mapping = fromStr(Mapping.class, this.metaNode.path(Attributes.M_MAPPING).textValue());
        // Category
        switch (category) {
        case ENTITY: {
            // Mapping
            switch (mapping) {
            case PARTIAL: {
                return verifyCEntityPartial();
            }
            case DIRECT: {
                verifyCEntityDirect();
            }
                break;
            case COMBINATED: {
                verifyCEntityCombinated();
            }
                break;
            default: {
                this.error = validator.verifyPattern(Attributes.M_MAPPING, REGEX_MAP.get(Attributes.M_MAPPING));
            }
                break;
            }
        }
            break;
        case RELATION: {
            verifyCRelation();
        }
            break;
        default: {
            this.error = validator.verifyPattern(Attributes.M_CATEGORY, REGEX_MAP.get(Attributes.M_CATEGORY));
        }
            break;
        }
        // 单独验证 INCREMENT 类型，只有 PARTIAL会被Skip掉，直接发生了return的语句返回
        if (null == this.error) {
            validateIncrement();
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateIncrement() {
        // 8.如果policy为INCREMENT
        final MetaPolicy policy = fromStr(MetaPolicy.class, this.metaNode.path(Attributes.M_POLICY).asText());
        if (MetaPolicy.INCREMENT != policy) {
            return true;
        }
        // 9.如果是MSSQL以及MYSQL，直接返回true
        if (!StringUtil.equals(Resources.DB_CATEGORY, "MSSQL") && !StringUtil.equals(Resources.DB_CATEGORY, "MYSQL")) {
            // 9.2.1.使用了Sequence的情况，必须要验证seqname是否丢失
            this.error = validator.verifyMissing(Attributes.M_SEQ_NAME);
            if (null != this.error) {
                return false;
            }
            // 9.2.2.使用Sequence情况，验证seqname的格式
            this.error = validator.verifyPattern(Attributes.M_SEQ_NAME, Attributes.RGX_M_SEQ_NAME);
            if (null != this.error) {
                return false;
            }
        }
        // 10.如果是INCREMENT必须验证seqinit, seqstep属性是否存在
        this.error = validator.verifyMissing(Attributes.M_SEQ_INIT, Attributes.M_SEQ_STEP);
        if (null != this.error) {
            return false;
        }
        // 11.验证seqinit, seqstep的格式
        this.error = validator.verifyPattern(Attributes.M_SEQ_INIT, Attributes.RGX_M_SEQ_INIT);
        if (null != this.error) {
            return false;
        }
        this.error = validator.verifyPattern(Attributes.M_SEQ_STEP, Attributes.RGX_M_SEQ_STEP);
        if (null != this.error) {
            return false;
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateMetaAttr() {
        // 4.__meta__ Required
        this.error = validator.verifyRequired(M_REQUIRED);
        if (null != this.error) {
            return false;
        }
        // 5.__meta__ Exclude Unsupported Attributes
        this.error = validator.verifyUnsupported(M_ATTRS);
        if (null != this.error) {
            return false;
        }
        // 6.__meta__ Required Attribute Patterns
        for (final String attr : REGEX_MAP.keySet()) {
            this.error = validator.verifyPattern(attr, REGEX_MAP.get(attr));
            if (null != this.error) {
                return false;
            }
        }
        // 6.1.__meta__ 可选属性status
        this.error = validator.verifyOptionalPattern(Attributes.M_STATUS, Attributes.RGX_M_STATUS);
        if (null != this.error) {
            return false;
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean verifyCEntityCombinated() {
        // 7.4.1.category == ENTITY && mapping == COMBINATED
        this.error = this.validator.verifyMissing(Attributes.M_SUB_KEY, Attributes.M_SUB_TABLE);
        if (null != this.error) {
            return false;
        }
        // 7.4.2.__subtable__ Parttern
        this.error = validator.verifyPattern(Attributes.M_SUB_TABLE, Attributes.RGX_M_SUB_TABLE);
        if (null != this.error) {
            return false;
        }
        // 7.4.3.__subtable__ and __table__ conflicts
        this.error = validator.verifyNotSame(Attributes.M_TABLE, Attributes.M_SUB_TABLE);
        if (null != this.error) {
            return false;
        }
        // 7.4.4 __subtable__ 这张表是否存在的验证
        this.error = validator.verifyTableExisting(Attributes.M_SUB_TABLE);
        if (null != this.error) {
            return false;
        }
        // 7.4.5 __subtable__ & __subkey__ 验证字段是否存在
        this.error = validator.verifyColumnExisting(Attributes.M_SUB_TABLE, Attributes.M_SUB_KEY);
        if (null != this.error) {
            return false;
        }
        // 7.4.6.__subtable__ & __subkey__ 验证字段的约束是否OK？
        this.error = validator.verifyInvalidConstraints(Attributes.M_SUB_TABLE, Attributes.M_SUB_KEY);
        if (null != this.error) {
            return false;
        }
        // 7.4.7.__subtable__ & __subkey__ 验证字段的类型是否OK？

        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean verifyCEntityDirect() {
        // 7.3.1.category == ENTITY && mapping == DIRECT
        this.error = this.validator.verifyExisting(Attributes.M_SUB_KEY, Attributes.M_SUB_TABLE);
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean verifyCEntityPartial() {
        // 7.2.1.category == ENTITY && mapping == PARTIAL
        this.error = validator.verifyExisting(Attributes.M_SUB_KEY, Attributes.M_SUB_TABLE);
        if (null != this.error) {
            return false;
        }
        // 7.2.2.seqname, seqinit, seqstep Checking
        this.error = validator.verifyExisting(Attributes.M_SEQ_INIT, Attributes.M_SEQ_NAME, Attributes.M_SEQ_STEP);
        if (null != this.error) {
            return false;
        }
        // 7.2.3.policy in ASSIGNED
        this.error = validator.verifyIn(Attributes.M_POLICY, MetaPolicy.ASSIGNED.toString());
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean verifyCRelation() {
        // 7.1.1.category == RELATION
        this.error = validator.verifyExisting(Attributes.M_SUB_KEY, Attributes.M_SUB_TABLE);
        if (null != this.error) {
            return false;
        }
        // 7.1.2.mapping in DIRECT
        this.error = validator.verifyIn(Attributes.M_MAPPING, Mapping.DIRECT.toString());
        if (null != this.error) {
            return false;
        }
        // 7.1.3.policy not COLLECTION | ASSIGNED
        this.error = validator.verifyNotIn(Attributes.M_POLICY, MetaPolicy.ASSIGNED.toString());
        return null == this.error;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
