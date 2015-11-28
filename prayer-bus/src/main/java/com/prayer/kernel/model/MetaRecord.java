package com.prayer.kernel.model;

import static com.prayer.util.Calculator.index;
import static com.prayer.util.Error.debug;
import static com.prayer.util.Error.info;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.database.ColumnInvalidException;
import com.prayer.exception.database.FieldInvalidException;
import com.prayer.kernel.i.Record;
import com.prayer.kernel.i.Value;
import com.prayer.kernel.model.Transducer.V;
import com.prayer.model.schema.FieldModel;
import com.prayer.model.type.DataType;
import com.prayer.model.type.StringType;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.MetaPolicy;

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
public class MetaRecord implements Record {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaRecord.class);
    /** 前置条件 **/
    private static final String PRE_CONNECTOR_CON = "_this._connector != null";
    /** 数据前置条件 **/
    private static final String PRE_DATA_CON = "_this.data != null";

    // ~ Instance Fields =====================================
    /** 全局标识符 **/
    @NotNull
    @NotBlank
    @NotEmpty
    private transient final String _identifier;
    /** Metadata 连接器 **/
    @NotNull
    private transient MetaConnector _connector;
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
    public MetaRecord(@AssertFieldConstraints("_identifier") final String identifier) {
        // 1.连接操作
        try {
            this._connector = MetaConnector.connect(identifier);
        } catch (AbstractSystemException ex) {
            this._connector = null; // NOPMD
            debug(LOGGER, getClass(), "D20006", ex, identifier);
            // TODO: Debug
            ex.printStackTrace();
        }
        // 连接成功
        this._identifier = this._connector.identifier();
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
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public MetaPolicy policy() {
        return this.connector().policy();
    }

    /** **/
    @Override
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public ConcurrentMap<String, Value<?>> idKV() throws AbstractDatabaseException {
        final ConcurrentMap<String, Value<?>> retMap = new ConcurrentHashMap<>();
        for (final FieldModel field : this.idschema()) {
            try {
                if (null != this.column(field.getColumnName())) {
                    retMap.put(field.getColumnName(), this.column(field.getColumnName()));
                } else {
                    // 默认String为主键替换Null的默认ID
                    retMap.put(field.getColumnName(), new StringType(Constants.EMPTY_STR));
                }
            } catch (AbstractDatabaseException ex) {
                info(LOGGER, ex.getErrorMessage());
            }
        }
        return retMap;
    }

    /** **/
    @Override
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public List<FieldModel> idschema() {
        return this.connector().idschema();
    }

    /** **/
    @Override
    @InstanceOf(Value.class)
    @Pre(expr = PRE_DATA_CON + " && " + PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public Value<?> column(String column) throws AbstractDatabaseException {
        this.verifyColumn(column);
        final String field = this.toField(column);
        return this.get(field);
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public String table() {
        return this.connector().table();
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public Set<String> columns() {
        // 注意Column的顺序，这里使用的是TreeSet
        final Set<String> retSet = new TreeSet<>();
        final Set<String> oldCols = this.connector().columns().keySet();
        retSet.addAll(oldCols);
        return retSet;
    }

    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public ConcurrentMap<String, DataType> columnTypes() {
        return this.connector().columns();
    }

    /** **/
    @Override
    @Pre(expr = PRE_DATA_CON, lang = Constants.LANG_GROOVY)
    public void set(@AssertFieldConstraints("_identifier") final String name,
            @InstanceOf(Value.class) final Value<?> value) throws AbstractDatabaseException {
        this.verifyField(name);
        this.data.put(name, value);
    }

    /** **/
    @Override
    @Pre(expr = PRE_DATA_CON, lang = Constants.LANG_GROOVY)
    public void set(@AssertFieldConstraints("_identifier") final String name, final String value)
            throws AbstractDatabaseException {
        this.verifyField(name);
        final DataType type = this.connector().fields().get(name);
        final Value<?> wrapperValue = V.get().getValue(type, value);
        this.set(name, wrapperValue);
    }

    /** **/
    @Override
    @InstanceOf(Value.class)
    @Pre(expr = PRE_DATA_CON, lang = Constants.LANG_GROOVY)
    public Value<?> get(@AssertFieldConstraints("_identifier") final String name) throws AbstractDatabaseException {
        this.verifyField(name);
        return this.data.get(name);
    }

    /**
     * 
     */
    @Override
    @NotNull
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public String identifier() {
        return this.connector().identifier();
    }

    @Override
    @NotNull
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public String toField(String column) throws AbstractDatabaseException {
        // 1.检查Column是否存在
        this.verifyColumn(column);
        // 2.获取Field
        final int idx = index(this.connector().getColumnList(), column);
        return -1 == idx ? Constants.EMPTY_STR : this.connector().getFieldList().get(idx);
    }

    @Override
    @NotNull
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public String toColumn(String field) throws AbstractDatabaseException {
        // 1.检查Field是否存在
        this.verifyField(field);
        // 2.获取Column
        final int idx = index(this.connector().getFieldList(), field);
        return -1 == idx ? Constants.EMPTY_STR : this.connector().getColumnList().get(idx);
    }

    /**
     * 
     */
    @Override
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public ConcurrentMap<String, DataType> fields() {
        return this.connector().fields();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private MetaConnector connector() {
        return this._connector;
    }

    private void verifyField(final String name) throws AbstractDatabaseException {
        if (!this.connector().fields().keySet().contains(name)) {
            throw new FieldInvalidException(getClass(), name, this._connector.identifier());
        }
    }

    private void verifyColumn(final String column) throws AbstractDatabaseException {
        if (!this.connector().columns().keySet().contains(column)) {
            throw new ColumnInvalidException(getClass(), column, this.table());
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
