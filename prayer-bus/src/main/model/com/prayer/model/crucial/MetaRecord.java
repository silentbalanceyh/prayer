package com.prayer.model.crucial; // NOPMD

import static com.prayer.util.Calculator.index;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.reservoir;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.exception.database.ColumnInvalidException;
import com.prayer.exception.database.FieldInvalidException;
import com.prayer.exception.database.SchemaNotFoundException;
import com.prayer.facade.kernel.Transducer.V;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
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
import net.sf.oval.guard.Pre;

/**
 * 特别注意columns()和columnTypes()两个方法并不是重复方法，columns()是TreeSet，有一个默认顺序，而columnTypes
 * ()只是一个哈希表
 * 
 * @author Lang
 *
 */
@Guarded
public class MetaRecord implements Record { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaRecord.class);
    /** **/
    private static final ConcurrentMap<String, MetaRaw> META_MAP = new ConcurrentHashMap<>();

    /** 前置条件 **/
    private static final String PRE_M_READER_CON = "_this.raw != null";
    /** 数据前置条件 **/
    private static final String PRE_DATA_CON = "_this.data != null";
    /** **/
    private static final String RULE_ID = "_identifier";
    // ~ Instance Fields =====================================
    /** 全局标识符 **/
    @NotNull
    @NotBlank
    @NotEmpty
    private transient final String _identifier; // NOPMD
    /** Meta信息读取器 **/
    @NotNull
    private transient final MetaRaw raw;
    /** 当前Record中的数据 **/
    private transient final ConcurrentMap<String, Value<?>> data;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param identifier
     */
    @PostValidateThis
    public MetaRecord(@AssertFieldConstraints(RULE_ID) final String identifier) throws AbstractDatabaseException {
        // 1.连接操作
        this._identifier = identifier;
        // 2.Meta的Serializer池化处理
        this.raw = reservoir(META_MAP, identifier, MetaRaw.class, identifier);
        if (null == this.raw) {
            throw new SchemaNotFoundException(getClass(), identifier);
        }
        // 3.数据Map信息
        this.data = new ConcurrentHashMap<>();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    @NotNull
    @InstanceOfAny(MetaPolicy.class)
    @Pre(expr = PRE_M_READER_CON, lang = Constants.LANG_GROOVY)
    public MetaPolicy policy() {
        MetaPolicy policy = null;
        try {
            policy = this.raw.readPolicy();
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
        }
        return policy;
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_M_READER_CON, lang = Constants.LANG_GROOVY)
    public String table() {
        String table = null;
        try {
            table = this.raw.readTable();
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
        }
        return table;
    }

    /** **/
    @Override
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_M_READER_CON, lang = Constants.LANG_GROOVY)
    public ConcurrentMap<String, Value<?>> idKV() throws AbstractDatabaseException {
        final ConcurrentMap<String, Value<?>> retMap = new ConcurrentHashMap<>();
        for (final PEField field : this.idschema()) {
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
    @Pre(expr = PRE_M_READER_CON, lang = Constants.LANG_GROOVY)
    public List<PEField> idschema() {
        List<PEField> schemata = new ArrayList<>();
        try {
            schemata = MetaHelper.extractIds(this.raw);
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
        }
        return schemata;
    }

    /** **/
    @Override
    @InstanceOf(Value.class)
    @Pre(expr = PRE_DATA_CON + " && " + PRE_M_READER_CON, lang = Constants.LANG_GROOVY)
    public Value<?> column(@AssertFieldConstraints(RULE_ID) final String column) throws AbstractDatabaseException {
        this.verifyColumn(column);
        final String field = this.toField(column);
        return this.get(field);
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_M_READER_CON, lang = Constants.LANG_GROOVY)
    public Set<String> columns() {
        // 注意Column的顺序，这里使用的是TreeSet
        final Set<String> retSet = new TreeSet<>();
        try {
            retSet.addAll(this.raw.readColumns());
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
        }
        return retSet;
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_M_READER_CON, lang = Constants.LANG_GROOVY)
    public ConcurrentMap<String, DataType> columnTypes() {
        ConcurrentMap<String, DataType> retMap = new ConcurrentHashMap<>();
        try {
            retMap = MetaHelper.extractColumnTypes(this.raw);
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
        }
        return retMap;
    }

    /** **/
    @Override
    @Pre(expr = PRE_DATA_CON, lang = Constants.LANG_GROOVY)
    public void set(@AssertFieldConstraints(RULE_ID) final String name, @InstanceOf(Value.class) final Value<?> value)
            throws AbstractDatabaseException {
        this.verifyField(name);
        this.data.put(name, value);
    }

    /** **/
    @Override
    @Pre(expr = PRE_DATA_CON, lang = Constants.LANG_GROOVY)
    public void set(@AssertFieldConstraints(RULE_ID) final String name, final String value)
            throws AbstractDatabaseException {
        this.verifyField(name);
        final int idx = index(this.raw.readNames(), name);
        final DataType type = this.raw.readTypes().get(idx);
        final Value<?> wrapperValue = V.get().getValue(type, value);
        this.set(name, wrapperValue);
    }

    /** **/
    @Override
    @InstanceOf(Value.class)
    @Pre(expr = PRE_DATA_CON, lang = Constants.LANG_GROOVY)
    public Value<?> get(@AssertFieldConstraints(RULE_ID) final String name) throws AbstractDatabaseException {
        this.verifyField(name);
        return this.data.get(name);
    }

    /**
     * 
     */
    @Override
    @NotNull
    @Pre(expr = PRE_M_READER_CON, lang = Constants.LANG_GROOVY)
    public String identifier() {
        return this._identifier;
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_M_READER_CON, lang = Constants.LANG_GROOVY)
    public String toField(@AssertFieldConstraints(RULE_ID) final String column) throws AbstractDatabaseException {
        // 1.检查Column是否存在
        this.verifyColumn(column);
        // 2.获取Field
        final int idx = index(this.raw.readColumns(), column);
        return -1 == idx ? Constants.EMPTY_STR : this.raw.readNames().get(idx);
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_M_READER_CON, lang = Constants.LANG_GROOVY)
    public String toColumn(@AssertFieldConstraints(RULE_ID) final String field) throws AbstractDatabaseException {
        // 1.检查Field是否存在
        this.verifyField(field);
        // 2.获取Column
        final int idx = index(this.raw.readNames(), field);
        return -1 == idx ? Constants.EMPTY_STR : this.raw.readColumns().get(idx);
    }

    /**
     * 
     */
    @Override
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_M_READER_CON, lang = Constants.LANG_GROOVY)
    public ConcurrentMap<String, DataType> fields() {
        ConcurrentMap<String, DataType> retMap = new ConcurrentHashMap<>();
        try {
            retMap = MetaHelper.extractTypes(this.raw);
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
        }
        return retMap;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void verifyField(final String name) throws AbstractDatabaseException {
        if (!this.raw.readNames().contains(name)) {
            throw new FieldInvalidException(getClass(), name, this._identifier);
        }
    }

    private void verifyColumn(final String column) throws AbstractDatabaseException {
        if (!this.raw.readColumns().contains(column)) {
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
                final int idx = index(this.raw.readNames(), field);
                final DataType type = this.raw.readTypes().get(idx);
                final String value = null == this.column(col) ? "" : this.column(col).toString();
                retStr.append('<').append(value).append(':').append(type).append(">,");
            } catch (AbstractDatabaseException ex) {
                peError(LOGGER, ex);
            }
        }
        return retStr.toString();
    }

    /** Sequence Name返回null，因为Metadata只能是GUID，无Seq Name名称 **/
    @Override
    public String seqname() {
        return null;
    }
}
