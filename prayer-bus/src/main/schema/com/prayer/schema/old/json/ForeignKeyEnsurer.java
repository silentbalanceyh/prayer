package com.prayer.schema.old.json;

import static com.prayer.util.debug.Error.message;
import static com.prayer.util.io.JsonKit.findNodes;
import static com.prayer.util.reflection.Instance.instance;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.exception.schema.FKReferenceSameException;
import com.prayer.exception.schema.JKeyConstraintInvalidException;
import com.prayer.exception.schema.JTColumnNotExistingException;
import com.prayer.exception.schema.JTColumnTypeInvalidException;
import com.prayer.model.type.DataType;
import com.prayer.util.io.JsonKit;

import jodd.util.StringUtil;
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
@Deprecated
final class ForeignKeyEnsurer implements InternalEnsurer {
    // ~ Static Fields =======================================
    /** **/
    private static final ConcurrentMap<String, Object> FK_FILTER = new ConcurrentHashMap<>();
    /** **/
    private static final ConcurrentMap<String, String> REGEX_MAP = new ConcurrentHashMap<>();
    /** PK_TYPE **/
    private static final String[] PK_STR = new String[] { DataType.INT.toString(), DataType.LONG.toString(),
            DataType.STRING.toString() };
    /** **/
    private static final String RGX_REFID_CTYPE = "(INT|LONG|STRING)[0-9]*";
    // ~ Instance Fields =====================================
    /** **/
    private transient AbstractSchemaException error;
    /** **/
    @NotNull
    private transient final ArrayNode fieldsNode;
    /** **/
    @NotNull
    private transient final JsonNode metaNode;
    /** **/
    @NotNull
    private transient final JArrayValidator validator;
    // ~ Static Block ========================================

    // ~ Static Methods ======================================
    /** **/
    static {
        /** Put Filter **/
        FK_FILTER.put(Attributes.F_FK, Boolean.TRUE);
        /** Put Regex **/
        REGEX_MAP.put(Attributes.F_REF_TABLE, Attributes.RGX_F_REF_TABLE);
        REGEX_MAP.put(Attributes.F_REF_ID, Attributes.RGX_F_REF_ID);
    }

    // ~ Constructors ========================================
    /**
     * 
     * @param fieldsNode
     */
    @PostValidateThis
    public ForeignKeyEnsurer(@NotNull final JsonNode metaNode, @NotNull final ArrayNode fieldsNode) {
        this.metaNode = metaNode;
        this.fieldsNode = fieldsNode;
        this.error = null; // NOPMD
        this.validator = new JArrayValidator(this.fieldsNode, Attributes.R_FIELDS);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Override
    @PreValidateThis
    public void validate() throws AbstractSchemaException {
        // 1.外键基础属性验证
        validateForeignKey();
        interrupt();
        // 2.外键类型定义验证
        validateFKType();
        interrupt();
        // 3.外键表是否存在
        validateFKTargetTable();
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

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 
     * @return
     */
    private boolean validateFKType() {
        // 21.1.验证外键的类型
        this.error = this.validator.verifyFkType(Attributes.F_TYPE, FK_FILTER, PK_STR);
        if (null != this.error) {
            return false;
        }
        // 21.2.验证外键的数据库列类型
        this.error = this.validator.verifyFkColumnType(Attributes.F_COL_TYPE, FK_FILTER, RGX_REFID_CTYPE);
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateFKTargetTable() {
        // 因为foreignkey = true的节点一定存在，上层验证过
        // 21.3.外键表属性验证
        final List<JsonNode> fkNodes = findNodes(this.fieldsNode, FK_FILTER);
        Iterator<JsonNode> fkNIt = fkNodes.iterator();
        int idx = 0;
        // 21.4.Foreign Key问题
        final Set<String> fkSet = new HashSet<>();

        while (fkNIt.hasNext()) {
            final JsonNode node = fkNIt.next();
            final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
                    message("D10000.FKIDX", idx, node.path(Attributes.F_NAME).asText()));

            final String table = node.path(Attributes.F_REF_TABLE).asText();
            if (fkSet.contains(table)) {
                this.error = new FKReferenceSameException(getClass(), node.path(Attributes.F_REF_ID).asText());
            }else{
                // 不包含就加进去
                fkSet.add(table);
            }
            /**
             * 如果外键关联的时候关联表和本表一致，那么这种情况不需要检查真实数据库中外键表是否存在
             */
            if (this.skipFKey(node)) {
                if (checkFKFromJson(node)) {
                    break;
                } else {
                    continue;
                }
            } else {
                if (this.checkFKFromDB(validator)) {
                    break;
                }
            }
        }
        if (null != this.error) {
            return false;
        }
        return null == this.error;
    }

    /**
     * 从非数据库层面检查外键规范
     * 
     * @param validator
     * @return
     */
    private boolean checkFKFromJson(final JsonNode node) {
        // 21.3.2.1.验证外键表是否存在，因为已经是引用本表，所以不需要考虑这种情况
        final String colName = node.path(Attributes.F_REF_ID).asText();
        final String table = node.path(Attributes.F_REF_TABLE).asText();

        // 21.3.2.2.验证外键表对应的字段是否存在，在Json中查找是否包含了R_REF_ID的字段
        final ConcurrentMap<String, Object> filter = new ConcurrentHashMap<>();
        filter.put(Attributes.F_COL_NAME, colName);
        // 是否包含了列名操作
        List<JsonNode> nodeList = JsonKit.findNodes(this.fieldsNode, filter);
        if (nodeList.isEmpty()) {
            this.error = new JTColumnNotExistingException(getClass(), table, colName);
            return true;
        } else {
            // 21.3.2.3.验证外键字段的约束是否OK
            filter.put(Attributes.F_PK, Boolean.TRUE);
            nodeList = JsonKit.findNodes(this.fieldsNode, filter);
            if (nodeList.isEmpty()) {
                this.error = new JKeyConstraintInvalidException(getClass(), table, colName);
                return true;
            }
            // 21.3.2.4.验证外键字段的类型是否OK
            final String fkType = node.path(Attributes.F_TYPE).asText();
            final String tgType = nodeList.get(Constants.ZERO).path(Attributes.F_TYPE).asText();
            if (!StringUtil.equals(fkType, tgType)) {
                this.error = new JTColumnTypeInvalidException(getClass(), table, colName, fkType);
                return true;
            }
        }

        return false;
    }

    /**
     * 从数据库层面检查外键规范
     * 
     * @param validator
     * @return
     */
    private boolean checkFKFromDB(final JObjectValidator validator) {
        // 21.3.1.1.验证外键表是否存在
        this.error = validator.verifyTableExisting(Attributes.F_REF_TABLE);
        if (null != this.error) {
            return true;
        }
        // 21.3.1.2.验证外键表对应字段是否存在
        this.error = validator.verifyColumnExisting(Attributes.F_REF_TABLE, Attributes.F_REF_ID);
        if (null != this.error) {
            return true;
        }
        // 21.3.1.3.验证外键对应字段的约束是否OK
        this.error = validator.verifyInvalidConstraints(Attributes.F_REF_TABLE, Attributes.F_REF_ID);
        if (null != this.error) {
            return true;
        }
        // 21.3.1.4.验证外键对应的字段的类型是否OK
        this.error = validator.verifyInvalidType(Attributes.F_REF_TABLE, Attributes.F_REF_ID, Attributes.F_COL_TYPE);
        if (null != this.error) {
            return true;
        }
        /**
         * 没有错误的时候则返回false
         */
        return false;
    }

    /**
     * 如果外键引用的是本表则不需要检查是否存在，因为目前正在创建本表
     * 
     * @param current
     * @param refNode
     * @return
     */
    private boolean skipFKey(final JsonNode refNode) {
        boolean ret = false;
        final String table = this.metaNode.path(Attributes.M_TABLE).asText();
        final String refTable = refNode.path(Attributes.F_REF_TABLE).asText();
        if (StringUtil.equals(table.trim(), refTable.trim())) {
            ret = true;
        }
        return ret;
    }

    /**
     * 
     * @return
     */
    private boolean validateForeignKey() {
        // foreignkey = true的节点一定存在，在上层验证过
        // 20.外键验证
        final List<JsonNode> fkNodes = findNodes(this.fieldsNode, FK_FILTER);
        Iterator<JsonNode> fkNIt = fkNodes.iterator();
        int idx = 0;
        while (fkNIt.hasNext()) {
            final JsonNode node = fkNIt.next();
            final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
                    message("D10000.FKIDX", idx, node.path(Attributes.F_NAME).asText()));
            // 20.1.验证foreignkey = true的节点必须包含两个特殊可选属性
            this.error = validator.verifyMissing(Attributes.F_REF_ID, Attributes.F_REF_TABLE);
            if (null != this.error) {
                break;
            }
            idx++;
        }
        if (null != this.error) {
            return false;
        }
        // 重置进行第二轮验证
        fkNIt = fkNodes.iterator();
        idx = 0;
        while (fkNIt.hasNext()) {
            final JsonNode node = fkNIt.next();
            final JObjectValidator validator = instance(JObjectValidator.class.getName(), node,
                    message("D10000.FKIDX", idx, node.path(Attributes.F_NAME).asText()));
            // 20.2.验证foreignkey = true的节点中特殊可选属性
            for (final String attr : REGEX_MAP.keySet()) {
                this.error = validator.verifyPattern(attr, REGEX_MAP.get(attr));
                if (null != this.error) {
                    break;
                }
            }
            idx++;
        }
        return null == this.error;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
