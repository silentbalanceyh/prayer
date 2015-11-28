package com.prayer.kernel.model;

import static com.prayer.util.Calculator.index;
import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.reservoir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.MetaCounterException;
import com.prayer.exception.system.MetaTypeWrongException;
import com.prayer.exception.system.MetadataDefMissingException;
import com.prayer.exception.system.SchemaNotFoundException;
import com.prayer.model.schema.FieldModel;
import com.prayer.model.type.DataType;
import com.prayer.util.Converter;
import com.prayer.util.PropertyKit;
import com.prayer.util.StringKit;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.MemoryPool;
import com.prayer.util.cv.Resources;
import com.prayer.util.cv.SystemEnum.MetaPolicy;

import jodd.util.StringUtil;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 从MetaRecord到Resource中资源文件定义的Metadata的连接过程
 * 
 * @author Lang
 *
 */
@Guarded
public class MetaConnector {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaConnector.class);
    /** 元数据资源文件 **/
    private static final PropertyKit LOADER = new PropertyKit(MetaConnector.class, Resources.META_CFG_FILE);
    /** 前置条件 **/
    private static final String PRE_ID_CON = "_this._identifier != null";
    /** 元数据Global ID集合 **/
    private static final Set<String> ID_SET = new HashSet<>();
    /** H2中的数据类型集合 **/
    private static final Set<String> DBTYPES = new HashSet<>(Arrays.asList(Constants.H2_TYPES));
    // ~ Instance Fields =====================================
    /** 全局标识符 **/
    private transient final String _identifier;
    /** 当前Model所有字段集合 **/
    private transient final List<String> nameList = new ArrayList<>();
    /** 当前Meta Model中所有字段的类型集合 **/
    private transient final List<DataType> typeList = new ArrayList<>();
    /** 当前Model中所有列集合 **/
    private transient final List<String> columnList = new ArrayList<>();
    /** 当前Model中所有的列类型集合 **/
    private transient final List<String> colTypeList = new ArrayList<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param identifier
     * @return
     */
    @NotNull
    public static MetaConnector connect(@NotNull @NotBlank @NotEmpty final String identifier)
            throws AbstractSystemException {
        return reservoir(MemoryPool.POOL_CONNECTOR, identifier, MetaConnector.class, identifier);
    }

    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    private MetaConnector(final String identifier) throws AbstractSystemException {
        // 1.检查Global ID是否配置
        initIdentifier(identifier);
        // 2.检查成功针对identifer中的数据进行赋值，如果检查不成功会抛出异常
        this._identifier = identifier;
        // 3.检查identifier成功，初始化所有的List列表
        initDefinitions();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 获取主键的Policy策略
     * 
     * @return
     */
    @NotNull
    @Pre(expr = PRE_ID_CON, lang = Constants.LANG_GROOVY)
    public MetaPolicy policy() {
        return Converter.fromStr(MetaPolicy.class, LOADER.getString(this._identifier + ".meta.policy"));
    }

    /**
     * 获取当前Global ID
     * 
     * @return
     */
    @NotNull
    @NotBlank
    @NotEmpty
    @Pre(expr = PRE_ID_CON, lang = Constants.LANG_GROOVY)
    public String identifier() {
        return this._identifier;
    }

    /** **/
    @NotNull
    @MinSize(1)
    @Pre(expr = "_this.columnList != null && !_this.columnList.isEmpty() && _this.typeList != null && !_this.typeList.isEmpty()", lang = Constants.LANG_GROOVY)
    public ConcurrentMap<String, DataType> columns() {
        final ConcurrentMap<String, DataType> fields = new ConcurrentHashMap<>();
        int size = this.columnList.size();
        for (int idx = 0; idx < size; idx++) {
            fields.put(this.columnList.get(idx), this.typeList.get(idx));
        }
        return fields;
    }

    @NotNull
    @MinSize(1)
    public List<FieldModel> idschema() {
        final List<String> ids = fromStr(LOADER.getString(this.identifier() + ".ids"), ",");
        final List<FieldModel> ret = new ArrayList<>();
        for (final String id : ids) {
            ret.add(this.getPrimaryKey(id));
        }
        return ret;
    }

    /**
     * 
     * @return
     */
    @NotNull
    @Pre(expr = PRE_ID_CON, lang = Constants.LANG_GROOVY)
    public String table() {
        return LOADER.getString(this._identifier + ".meta.table");
    }

    /**
     * 
     * @return
     */
    @NotNull
    @MinSize(1)
    @Pre(expr = "_this.nameList != null && !_this.nameList.isEmpty() && _this.typeList != null && !_this.typeList.isEmpty()", lang = Constants.LANG_GROOVY)
    public ConcurrentMap<String, DataType> fields() {
        final ConcurrentMap<String, DataType> fields = new ConcurrentHashMap<>();
        int size = this.nameList.size();
        for (int idx = 0; idx < size; idx++) {
            fields.put(this.nameList.get(idx), this.typeList.get(idx));
        }
        return fields;
    }

    /**
     * 
     * @return
     */
    @NotNull
    @MinSize(1)
    @Pre(expr = "_this.nameList != null && !_this.nameList.isEmpty()", lang = Constants.LANG_GROOVY)
    public List<String> getFieldList() {
        return this.nameList;
    }

    /**
     * 
     * @return
     */
    @NotNull
    @MinSize(1)
    @Pre(expr = "_this.columnList != null && !_this.columnList.isEmpty()", lang = Constants.LANG_GROOVY)
    public List<String> getColumnList() {
        return this.columnList;
    }

    // ~ Private Methods =====================================

    private FieldModel getPrimaryKey(final String id) {
        final FieldModel pKeySchema = new FieldModel();
        // 1.Field Name
        pKeySchema.setName(id);
        // 2.Basic Information
        final int idx = index(this.nameList, id);
        pKeySchema.setColumnName(this.columnList.get(idx));
        pKeySchema.setColumnType(this.colTypeList.get(idx));
        pKeySchema.setType(this.typeList.get(idx));
        // 3.Extension Info
        pKeySchema.setPrimaryKey(true);
        pKeySchema.setUnique(true);
        pKeySchema.setNullable(false);
        pKeySchema.setSubTable(false);
        pKeySchema.setForeignKey(false);
        return pKeySchema;
    }

    private void initDefinitions() throws AbstractSystemException {
        // 1.Field Definitions
        if (null != this.nameList && this.nameList.isEmpty()) {
            this.nameList.addAll(fromStr(LOADER.getString(this.identifier() + ".field.names"), ","));
            if (this.nameList.isEmpty()) {
                throw new MetadataDefMissingException(getClass(), Resources.META_CFG_FILE,
                        this.identifier() + ".field.names");
            }
        }
        // 2.Type Definitions
        if (null != this.typeList && this.typeList.isEmpty()) {
            final List<String> typeLiteral = fromStr(LOADER.getString(this.identifier() + ".field.types"), ",");
            if (typeLiteral.isEmpty()) {
                throw new MetadataDefMissingException(getClass(), Resources.META_CFG_FILE,
                        this.identifier() + ".field.types");
            } else {
                for (final String literal : typeLiteral) {
                    if (StringKit.isNonNil(literal)) {
                        final DataType type = DataType.fromString(literal);
                        if (null == type) {
                            throw new MetaTypeWrongException(getClass(), literal, Resources.META_CFG_FILE,
                                    this.identifier() + ".field.types");
                        }
                        this.typeList.add(type);
                    }
                }
            }
        }
        // 3.Column Definitions
        if (null != this.columnList && this.columnList.isEmpty()) {
            this.columnList.addAll(fromStr(LOADER.getString(this.identifier() + ".column.names"), ","));
            if (this.columnList.isEmpty()) {
                throw new MetadataDefMissingException(getClass(), Resources.META_CFG_FILE,
                        this.identifier() + ".column.names");
            }
        }
        // 4.Column Type Definitions
        if (null != this.colTypeList && this.colTypeList.isEmpty()) {
            final List<String> typeLiteral = fromStr(LOADER.getString(this.identifier() + ".column.types"), ",");
            if (typeLiteral.isEmpty()) {
                throw new MetadataDefMissingException(getClass(), Resources.META_CFG_FILE,
                        this.identifier() + ".column.types");
            } else {
                for (final String literal : typeLiteral) {
                    if (StringKit.isNonNil(literal)) {
                        if (DBTYPES.contains(literal)) {
                            this.colTypeList.add(literal);
                        } else {
                            throw new MetaTypeWrongException(getClass(), literal, Resources.META_CFG_FILE,
                                    this.identifier() + ".column.types");
                        }
                    }
                }
            }
        }
        // 5.Final Checking for Size
        int size = this.nameList.size();
        if (size != this.typeList.size()) {
            throw new MetaCounterException(getClass(), size, "Field Type", this.typeList.size());
        }
        if (size != this.columnList.size()) {
            throw new MetaCounterException(getClass(), size, "Column", this.columnList.size());
        }
        if (size != this.colTypeList.size()) {
            throw new MetaCounterException(getClass(), size, "Column Type", this.colTypeList.size());
        }
    }

    private void initIdentifier(final String identifier) throws SchemaNotFoundException {
        info(LOGGER, "[I] Checking virtual schema from Property files : identifier = " + identifier);
        if (null != ID_SET && ID_SET.isEmpty()) {
            ID_SET.addAll(fromStr(LOADER.getString("h2.meta.ids"), ","));
        }
        if (!ID_SET.contains(identifier)) {
            throw new SchemaNotFoundException(getClass(), identifier);
        }
    }

    private List<String> fromStr(final String content, final String delimiter) {
        final List<String> dataSet = new ArrayList<>();
        if (null != content) {
            final String[] dataArr = StringUtil.split(content, delimiter);
            dataSet.addAll(Arrays.asList(dataArr));
        }
        return dataSet;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
