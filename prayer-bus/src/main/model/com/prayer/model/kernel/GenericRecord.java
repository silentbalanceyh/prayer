package com.prayer.model.kernel; // NOPMD

import static com.prayer.util.debug.Log.peError;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.exception.database.ColumnInvalidException;
import com.prayer.exception.database.FieldInvalidException;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.kernel.Transducer.V;
import com.prayer.facade.kernel.Value;
import com.prayer.model.database.FieldModel;
import com.prayer.model.type.DataType;
import com.prayer.model.type.StringType;

import net.sf.oval.constraint.AssertFieldConstraints;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class GenericRecord implements Record { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericRecord.class);
    /** 前置条件 **/
    private static final String PRE_SCHEMA_CON = "_this._schema != null && _this.data != null";
    /** **/
    private static final String RULE_ID = "_identifier";
    // ~ Instance Fields =====================================
    /** 全局标识符 **/
    @NotNull
    @NotBlank
    @NotEmpty
    private transient final String _identifier; // NOPMD
    /** 和当前Record绑定的Schema引用 **/
    @NotNull
    private transient GenericSchema _schema; // NOPMD
    /** 当前Record中的数据 **/
    @NotNull
    private transient final ConcurrentMap<String, Value<?>> data;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param _identifier
     */
    @PostValidateThis
    public GenericRecord(@AssertFieldConstraints(RULE_ID) final String identifier) {
        this._identifier = identifier.trim();
        try {
            this._schema = SchemaLocator.getSchema(identifier.trim());
        } catch (AbstractSystemException ex) {
            this._schema = null; // NOPMD
            peError(LOGGER, ex);
        }
        this.data = new ConcurrentHashMap<>();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public void set(@AssertFieldConstraints(RULE_ID) final String name, @InstanceOf(Value.class) final Value<?> value)
            throws AbstractDatabaseException {
        this.verifyField(name);
        this.data.put(name, value);
    }

    /** **/
    @Override
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public void set(@AssertFieldConstraints(RULE_ID) final String name, final String value)
            throws AbstractDatabaseException {
        this.verifyField(name);
        // 为Filed赋值
        this.set(name, this.createValue(name, value));
    }

    /** **/
    @Override
    @InstanceOf(Value.class)
    public Value<?> get(@AssertFieldConstraints(RULE_ID) final String name) throws AbstractDatabaseException {
        this.verifyField(name);
        // 如果得到了null值，则直接重新赋值，修复Value<?>为null的问题
        Value<?> ret = this.data.get(name);
        if (null == ret) {
            this.data.put(name, this.createValue(name, null));
        }
        return this.data.get(name);
    }

    /** **/
    @Override
    @InstanceOf(Value.class)
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public Value<?> column(@AssertFieldConstraints(RULE_ID) final String column) throws AbstractDatabaseException {
        this.verifyColumn(column);
        final FieldModel colInfo = this._schema.getColumn(column);
        return this.get(colInfo.getName());
    }

    /** 获取全局标识符 **/
    @Override
    @NotNull
    public String identifier() {
        return this._identifier;
    }

    /** 获取数据库列集 **/
    @Override
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public Set<String> columns() {
        return this._schema.getColumns();
    }

    /** 获取当前Record的Schema定义 **/
    @Override
    @NotNull
    @InstanceOfAny(MetaPolicy.class)
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public MetaPolicy policy() {
        return this._schema.getMeta().getPolicy();
    }

    /** 获取表名 **/
    @Override
    @NotNull
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public String table() {
        return this._schema.getMeta().getTable();
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public String toField(@AssertFieldConstraints(RULE_ID) final String column) throws AbstractDatabaseException {
        this.verifyColumn(column);
        final FieldModel field = this._schema.getColumn(column);
        return field.getName();
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public String toColumn(@AssertFieldConstraints(RULE_ID) final String field) throws AbstractDatabaseException {
        this.verifyField(field);
        final FieldModel column = this._schema.getFields().get(field);
        return column.getColumnName();
    }

    /** **/
    @Override
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public ConcurrentMap<String, Value<?>> idKV() {
        final List<FieldModel> pkFields = this._schema.getPrimaryKeys();
        final ConcurrentMap<String, Value<?>> retMap = new ConcurrentHashMap<>();
        for (final FieldModel field : pkFields) {
            try {
                if (null == this.column(field.getColumnName())) {
                    // 默认String为主键替换Null的默认ID
                    retMap.put(field.getColumnName(), new StringType(Constants.EMPTY_STR)); // NOPMD
                } else {
                    retMap.put(field.getColumnName(), this.column(field.getColumnName()));
                }
            } catch (AbstractDatabaseException ex) {
                peError(LOGGER, ex);
            }
        }
        return retMap;
    }

    /** **/
    @Override
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public List<FieldModel> idschema() {
        return this._schema.getPrimaryKeys();
    }

    /** **/
    @Override
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public ConcurrentMap<String, DataType> fields() {
        final ConcurrentMap<String, DataType> retMap = new ConcurrentHashMap<>();
        for (final String name : this._schema.getFields().keySet()) {
            retMap.put(name, this._schema.getFields().get(name).getType());
        }
        return retMap;
    }

    /** **/
    @Override
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_SCHEMA_CON, lang = Constants.LANG_GROOVY)
    public ConcurrentMap<String, DataType> columnTypes() {
        final ConcurrentMap<String, DataType> retMap = new ConcurrentHashMap<>();
        for (final String name : this._schema.getFields().keySet()) {
            retMap.put(this._schema.getFields().get(name).getColumnName(),
                    this._schema.getFields().get(name).getType());
        }
        return retMap;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private Value<?> createValue(final String name, final String value) throws AbstractDatabaseException {
        final DataType type = this._schema.getFields().get(name).getType();
        return V.get().getValue(type, value);
    }

    private void verifyField(final String name) throws AbstractDatabaseException {
        if (!this._schema.getFields().containsKey(name)) {
            throw new FieldInvalidException(getClass(), name, this._schema.getIdentifier());
        }
    }

    private void verifyColumn(final String column) throws AbstractDatabaseException {
        if (!this._schema.getColumns().contains(column)) {
            throw new ColumnInvalidException(getClass(), column, this.table());
        }
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** 调试用方法 **/
    @Override
    public String toString() {
        final StringBuilder retStr = new StringBuilder(100);
        retStr.append("======================> : Data (Record)");
        for (final String col : this.columns()) {
            try {
                final String value = null == this.column(col) ? "" : this.column(col).toString();
                retStr.append(col).append(" : ").append(value).append(Symbol.COMMA);
            } catch (AbstractDatabaseException ex) {
                peError(LOGGER, ex);
            }
        }
        return retStr.toString();
    }

    /** **/
    @Override
    public String seqname() {
        return this._schema.getMeta().getSeqName();
    }
}
