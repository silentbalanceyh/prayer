package com.prayer.model.crucial; // NOPMD

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.instantor.schema.MilieuBllor;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.exception.database.ColumnInvalidException;
import com.prayer.exception.database.FieldInvalidException;
import com.prayer.exception.database.SchemaNotFoundException;
import com.prayer.facade.business.instantor.schema.EnvInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.crucial.Transducer.V;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractTransactionException;
import com.prayer.model.meta.database.PEField;
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

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class DataRecord implements Record { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DataRecord.class);
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
    private transient Schema _schema; // NOPMD
    /** 访问H2的Schema数据层接口 **/
    @NotNull
    private transient final EnvInstantor instantor = singleton(MilieuBllor.class);
    /** 当前Record中的数据 **/
    @NotNull
    private transient final ConcurrentMap<String, Value<?>> data = new ConcurrentHashMap<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param _identifier
     */
    @PostValidateThis
    public DataRecord(@AssertFieldConstraints(RULE_ID) final String identifier) throws AbstractDatabaseException {
        this._identifier = identifier.trim();
        try {
            this._schema = this.instantor.get(identifier);
        } catch (AbstractTransactionException ex) {
            this._schema = null; // NOPMD
            peError(LOGGER, ex);
        }
        if (null == this._schema) {
            throw new SchemaNotFoundException(getClass(), identifier);
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void set(@AssertFieldConstraints(RULE_ID) final String name, @InstanceOf(Value.class) final Value<?> value)
            throws AbstractDatabaseException {
        this.verifyField(name);
        this.data.put(name, value);
    }

    /** **/
    @Override
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
    public Value<?> column(@AssertFieldConstraints(RULE_ID) final String column) throws AbstractDatabaseException {
        this.verifyColumn(column);
        final PEField colInfo = this._schema.getColumn(column);
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
    public Set<String> columns() {
        return this._schema.getColumns();
    }

    /** 获取当前Record的Schema定义 **/
    @Override
    @NotNull
    @InstanceOfAny(MetaPolicy.class)
    public MetaPolicy policy() {
        return this._schema.getPolicy();
    }

    /** 获取表名 **/
    @Override
    @NotNull
    public String table() {
        return this._schema.getTable();
    }

    /** **/
    @Override
    @NotNull
    public String toField(@AssertFieldConstraints(RULE_ID) final String column) throws AbstractDatabaseException {
        this.verifyColumn(column);
        final PEField field = this._schema.getColumn(column);
        return field.getName();
    }

    /** **/
    @Override
    @NotNull
    public String toColumn(@AssertFieldConstraints(RULE_ID) final String field) throws AbstractDatabaseException {
        this.verifyField(field);
        final PEField column = this._schema.getField(field);
        return column.getColumnName();
    }

    /** **/
    @Override
    @NotNull
    @MinSize(1)
    public ConcurrentMap<String, Value<?>> idKV() {
        final List<PEField> pkFields = this._schema.getPrimaryKeys();
        final ConcurrentMap<String, Value<?>> retMap = new ConcurrentHashMap<>();
        for (final PEField field : pkFields) {
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
    public List<PEField> idschema() {
        return this._schema.getPrimaryKeys();
    }

    /** **/
    @Override
    @NotNull
    @MinSize(1)
    public ConcurrentMap<String, DataType> fields() {
        final ConcurrentMap<String, DataType> retMap = new ConcurrentHashMap<>();
        for (final String name : this._schema.fieldNames()) {
            retMap.put(name, this._schema.getField(name).getType());
        }
        return retMap;
    }

    /** **/
    @Override
    @NotNull
    @MinSize(1)
    public ConcurrentMap<String, DataType> columnTypes() {
        final ConcurrentMap<String, DataType> retMap = new ConcurrentHashMap<>();
        for (final String name : this._schema.fieldNames()) {
            retMap.put(this._schema.getField(name).getColumnName(), this._schema.getField(name).getType());
        }
        return retMap;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private Value<?> createValue(final String name, final String value) throws AbstractDatabaseException {
        final DataType type = this._schema.getField(name).getType();
        return V.get().getValue(type, value);
    }

    private void verifyField(final String name) throws AbstractDatabaseException {
        if (!this._schema.fieldNames().contains(name)) {
            throw new FieldInvalidException(getClass(), name, this._schema.identifier());
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
        // 1.当前Record的ID：
        retStr.append(this.identifier());
        for (final String col : this.columns()) {
            try {
                // 2.当前Record的<Field:Column>=<Value:DataType>
                final String field = this.toField(col);
                retStr.append('<').append(field).append(':').append(col).append('>');
                retStr.append('=');
                final DataType type = this._schema.getField(field).getType();
                final String value = null == this.column(col) ? "" : this.column(col).toString();
                retStr.append('<').append(value).append(':').append(type).append(">,");
            } catch (AbstractDatabaseException ex) {
                peError(LOGGER, ex);
            }
        }
        return retStr.toString();
    }

    /** **/
    @Override
    public String seqname() {
        return this._schema.meta().getSeqName();
    }
}
