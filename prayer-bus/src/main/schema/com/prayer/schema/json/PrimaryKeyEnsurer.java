package com.prayer.schema.json;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.exception.schema.PKNullableConflictException;
import com.prayer.exception.schema.PKUniqueConflictException;
import com.prayer.model.type.DataType;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.AssertFieldConstraints;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
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
final class PrimaryKeyEnsurer implements InternalEnsurer {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PrimaryKeyEnsurer.class);
    /** **/
    private static final ConcurrentMap<String, Object> PK_FILTER = new ConcurrentHashMap<>();
    /** GUID **/
    private static final String[] PK_GUID_STR = new String[] { DataType.STRING.toString() };
    /** INCREMENT **/
    private static final String[] PK_INCREMENT_STR = new String[] { DataType.INT.toString(), DataType.LONG.toString() };
    /** ASSIGNED, COLLECTION **/
    private static final String[] PK_STR = new String[] { DataType.INT.toString(), DataType.LONG.toString(),
            DataType.STRING.toString() };
    // ~ Instance Fields =====================================
    /** **/
    private transient AbstractSchemaException error;
    /** **/
    @NotNull
    private transient final ArrayNode fieldsNode;
    
    /** **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient final String table; // NOPMD
    /** **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient final String policyStr; // NOPMD
    /** **/
    @NotNull
    private transient final JArrayValidator validator;

    // ~ Static Block ========================================
    /** Put Filter **/
    static {
        PK_FILTER.put(Attributes.F_PK, Boolean.TRUE);
    }

    // ~ Static Methods ======================================

    // ~ Constructors ========================================
    /**
     * 
     * @param fieldsNode
     * @param policyStr
     */
    @PostValidateThis
    public PrimaryKeyEnsurer(@NotNull final ArrayNode fieldsNode, @AssertFieldConstraints("policyStr") final String policyStr,
            @AssertFieldConstraints("table") final String table) {
        this.fieldsNode = fieldsNode;
        this.policyStr = policyStr;
        this.table = table;
        this.validator = new JArrayValidator(this.fieldsNode, Attributes.R_FIELDS);
        // 数据表Inject过程
        this.validator.setTable(table);
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
        // 1.验证Primary Key是否定义
        validatePKeyMissing();
        interrupt();
        // 2.policy是否COLLECTION
        validateDispatcher();
        interrupt();
        // 3.policy如果为非COLLECTION，主键必须unique为true
        validatePKUniqueConflict();
        interrupt();
        // 4.policy如果为非COLLECTION，主键必须nullable为false
        validatePKNullableConflict();
        interrupt();
    }

    /**
     * 
     * @throws AbstractSchemaException
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
    private boolean validatePKNullableConflict() {
        // 18.4.主键的nullable一旦出现必须为false
        final Iterator<JsonNode> nodeIt = this.fieldsNode.iterator();
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final Boolean isPKey = node.path(Attributes.F_PK).asBoolean();
            if (isPKey && StringKit.isNonNil(node.path(Attributes.F_NULLABLE).asText())) {
                final Boolean isNull = node.path(Attributes.F_NULLABLE).asBoolean();
                if (isNull) {
                    this.error = instance(PKNullableConflictException.class.getName(), getClass(),
                            node.path(Attributes.F_NAME).asText());
                    // this.error = new PKNullableConflictException(getClass(),
                    // node.path(Attributes.F_NAME).asText());
                    peError(LOGGER, this.error);
                    break;
                }
            }
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validatePKUniqueConflict() {
        final MetaPolicy policy = MetaPolicy.valueOf(policyStr);
        // 18.3.主键的unique一旦出现默认必须为true
        if (MetaPolicy.COLLECTION == policy) {
            return true;
        }
        final Iterator<JsonNode> nodeIt = this.fieldsNode.iterator();
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final Boolean isPkey = node.path(Attributes.F_PK).asBoolean();
            if (isPkey && StringKit.isNonNil(node.path(Attributes.F_UNIQUE).asText())) {
                final Boolean isUnique = node.path(Attributes.F_UNIQUE).asBoolean();
                if (!isUnique) {
                    this.error = instance(PKUniqueConflictException.class.getName(), getClass(),
                            node.path(Attributes.F_NAME).asText());
                    // this.error = new PKUniqueConflictException(getClass(),
                    // node.path(Attributes.F_NAME).asText());
                    peError(LOGGER, this.error);
                    break;
                }
            }
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateDispatcher() {    // NOPMD
        final MetaPolicy policy = MetaPolicy.valueOf(policyStr);
        // 18.属性policy顶层检查，是否COLLECTION
        if (MetaPolicy.COLLECTION == policy) {
            // 18.1.1.属性：policy == COLLECTION
            this.error = this.validator.verifyPKeyCOPolicy(Attributes.F_PK, policy);
            if (null != this.error) {
                return false;
            }
        } else {
            // 18.2.1.属性：policy != COLLECTION
            this.error = this.validator.verifyPKeyNonCOPolicy(Attributes.F_PK, policy);
            if (null != this.error) {
                return false;
            }
            if (MetaPolicy.GUID == policy) {
                // 18.2.2.1.对应：policy == GUID
                this.error = this.validator.verifyPKeyPolicyType(policy, PK_FILTER, PK_GUID_STR);
            } else if (MetaPolicy.INCREMENT == policy) {
                // 18.2.2.2.对应：policy == INCREMENT
                this.error = this.validator.verifyPKeyPolicyType(policy, PK_FILTER, PK_INCREMENT_STR);
            }
            if (null != this.error) {
                return false;
            }
        }
        // 18.1.2.policy == COLLECTION
        // 18.2.2.3.policy == ASSIGNED
        if ((MetaPolicy.ASSIGNED == policy || MetaPolicy.COLLECTION == policy) && null == this.error) {
            this.error = this.validator.verifyPKeyPolicyType(policy, PK_FILTER, PK_STR);
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validatePKeyMissing() {
        // // 17.1.验证主键是否存在：primarykey
        // this.error = this.validator.verifyPKeyRequired(Attributes.F_PK);
        // if (null != this.error) {
        // return false;
        // }
        // 17.2.验证主键定义primarykey的值是不是至少有一个为true
        this.error = this.validator.verifyPKeyMissing(Attributes.F_PK, Boolean.TRUE, 1);
        if (null != this.error) {
            return false;
        }
        return null == this.error;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
