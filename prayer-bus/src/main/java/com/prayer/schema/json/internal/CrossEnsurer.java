package com.prayer.schema.json.internal;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Error.debug;
import static com.prayer.util.Instance.instance;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.ColumnsMissingException;
import com.prayer.exception.schema.FKNotOnlyOneException;
import com.prayer.exception.schema.MultiForPKPolicyException;
import com.prayer.exception.schema.PKNotOnlyOneException;
import com.prayer.exception.schema.WrongTimeAttrException;
import com.prayer.util.JsonKit;

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
final class CrossEnsurer implements InternalEnsurer {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(CrossEnsurer.class);
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
    public CrossEnsurer(@NotNull final ArrayNode keysNode, @NotNull final ArrayNode fieldsNode,
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
    @PreValidateThis
    public void validate() throws AbstractSchemaException {
        // 1.验证PK的Policy和Multi的冲突
        validateMetaPKPolicy();
        interrupt();
        // 2.主键必须在__keys__中唯一定义异常
        validatePKOnlyOne();
        interrupt();
        // 3.外键如果存在则在__keys__中只能出现0次或者1次
        validateFKOnlyOne();
        interrupt();
        // 4.检查columns中的列是否在__fields__中定义过
        validateColumnMissing();
        interrupt();
        // 5.检查columns中的定义和__fields__中定义相同
        validateAttrConflict();
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
    private boolean validateAttrConflict() {
        // 35.验证__keys__和__fields__中的属性定义
        final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final Boolean isMulti = node.path(Attributes.K_MULTI).asBoolean();
            final KeyCategory category = fromStr(KeyCategory.class, node.path(Attributes.K_CATEGORY).asText());
            final ArrayNode columns = JsonKit.fromJObject(node.path(Attributes.K_COLUMNS));
            if (isMulti) {
                if (KeyCategory.PrimaryKey == category) {
                    countAttr(columns, Attributes.F_PK, Boolean.TRUE, category);
                } else if (KeyCategory.UniqueKey == category) {
                    countAttr(columns, Attributes.F_UNIQUE, Boolean.FALSE, category);
                }
            } else {
                if (KeyCategory.PrimaryKey == category) {
                    countAttr(columns, Attributes.F_PK, Boolean.TRUE, category);
                } else if (KeyCategory.UniqueKey == category) {
                    countAttr(columns, Attributes.F_UNIQUE, Boolean.TRUE, category);
                } else {
                    countAttr(columns, Attributes.F_FK, Boolean.TRUE, category);
                }
            }
            if (null != this.error) {
                break;
            }
        }
        return null == this.error;
    }

    /**
     * 
     * @param columns
     * @param attr
     * @param value
     * @param category
     * @return
     */
    private void countAttr(final ArrayNode columns, final String attr, final Object value, final KeyCategory category) {
        int occurs = 0;
        final Iterator<JsonNode> nodeIt = columns.iterator();
        while (nodeIt.hasNext()) {
            final String colName = nodeIt.next().asText();
            final JsonNode fieldNode = this.findNode(colName);
            occurs += JsonKit.occursAttr(fieldNode, attr, value, true);
        }
        if (occurs != columns.size()) {
            this.error = instance(WrongTimeAttrException.class.getName(), getClass(), attr, value.toString(),
                    String.valueOf(columns.size()), String.valueOf(occurs), category.toString());
            debug(LOGGER, getClass(), "D10024", this.error, attr, value, category, columns.size(), occurs);
        }
    }

    /**
     * 根据columnName找Node
     * 
     * @param colValue
     * @return
     */
    private JsonNode findNode(final String colValue) {
        final Iterator<JsonNode> nodeIt = this.fieldsNode.iterator();
        JsonNode retNode = null;
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final String nodeColValue = node.path(Attributes.F_COL_NAME).asText();
            if (StringUtil.equals(colValue, nodeColValue)) {
                retNode = node;
                break;
            }
        }
        return retNode;
    }

    /**
     * 
     * @return
     */
    private boolean validateColumnMissing() {
        // 34.验证columns中出现过的列是否是__fields__中定义的合法列
        final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
        int idx = 0;
        outer: while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final ArrayNode columns = JsonKit.fromJObject(node.path(Attributes.K_COLUMNS));
            final Iterator<JsonNode> columnIt = columns.iterator();
            while (columnIt.hasNext()) {
                final String colName = columnIt.next().asText();
                final int occurs = JsonKit.occursAttr(this.fieldsNode, Attributes.F_COL_NAME, colName, true);
                if (0 == occurs) {
                    final String keyName = node.path(Attributes.K_NAME).asText();
                    this.error = instance(ColumnsMissingException.class.getName(), getClass(), colName, keyName);
                    // new ColumnsMissingException(getClass(), colName,
                    // keyName);
                    debug(LOGGER, getClass(), "D10023", this.error, keyName, colName, occurs, idx);
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
    private boolean validatePKOnlyOne() {
        // 32.验证Keys中的PrimaryKey只能有一个
        final int occurs = JsonKit.occursAttr(this.keysNode, Attributes.K_CATEGORY, KeyCategory.PrimaryKey, true);
        if (Constants.ONE != occurs) {
            this.error = new PKNotOnlyOneException(getClass());
            debug(LOGGER, getClass(), "D10009", this.error, KeyCategory.PrimaryKey, occurs);
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateFKOnlyOne() {
        // 33.验证Keys中的ForeignKey如果存在只能有一个
        final int occurs = JsonKit.occursAttr(this.keysNode, Attributes.K_CATEGORY, KeyCategory.ForeignKey, true);
        if (Constants.ONE < occurs) {
            this.error = new FKNotOnlyOneException(getClass());
            debug(LOGGER, getClass(), "D10016", this.error, KeyCategory.ForeignKey, occurs);
        }
        return null == this.error;
    }

    /**
     * 
     * @return
     */
    private boolean validateMetaPKPolicy() {    // NOPMD
        // 31.验证Keys中的PrimaryKey对应的Policy
        final Iterator<JsonNode> nodeIt = this.keysNode.iterator();
        int idx = 0;
        while (nodeIt.hasNext()) {
            final JsonNode node = nodeIt.next();
            final KeyCategory category = fromStr(KeyCategory.class, node.path(Attributes.K_CATEGORY).asText());
            if (KeyCategory.PrimaryKey != category) {
                idx++;
                continue;
            }
            final Boolean isMulti = node.path(Attributes.K_MULTI).asBoolean();
            if (isMulti && MetaPolicy.COLLECTION != this.pkPolicy) {
                this.error = instance(MultiForPKPolicyException.class.getName(), getClass(), this.pkPolicy.toString(),
                        isMulti.toString());
                // new MultiForPKPolicyException(getClass(),
                // this.pkPolicy.toString(), isMulti.toString());
            } else if (!isMulti && MetaPolicy.ASSIGNED != this.pkPolicy && MetaPolicy.GUID != this.pkPolicy
                    && MetaPolicy.INCREMENT != this.pkPolicy) {
                this.error = instance(MultiForPKPolicyException.class.getName(), getClass(), this.pkPolicy.toString(),
                        isMulti.toString());
            }
            if (null != this.error) {
                debug(LOGGER, getClass(), "D10022", this.error, this.pkPolicy, isMulti, idx);
                break;
            }
        }
        return null == this.error;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
