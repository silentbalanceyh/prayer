/**
 * 
 */
package com.prayer.kernel.builder;


import static com.prayer.util.Error.info;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.SqlSegment;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.h2.FieldModel;
import com.prayer.model.h2.KeyModel;
import com.prayer.model.h2.MetaModel;
import com.prayer.util.StringKit;

import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * @author huar
 *
 */
@Guarded
public class OracleBuilder extends AbstractBuilder implements SqlSegment {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MsSqlBuilder.class);
    /** 针对整个系统的表统计管理 **/
    private static final ConcurrentMap<String, Boolean> TB_COUNT_MAP = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public OracleBuilder(GenericSchema schema) {
        super(schema);
        // TODO Auto-generated constructor stub
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 创建新的数据表
     */
    @Override
    @PreValidateThis
    public boolean createTable() {
        final boolean exist = this.existTable();
        if (exist) {
            info(LOGGER, "[I] Location: createTable(), Table existing : " + this.getTable());
            return false;
        } else {
            final String sql = genCreateSql();
            info(LOGGER, "[I] Sql: " + sql);
            final int respCode = this.getContext().execute(sql, null);
            final String respStr = (Constants.RC_SUCCESS == respCode ? ResponseCode.SUCCESS.toString()
                    : ResponseCode.FAILURE.toString());
            info(LOGGER, "[I] Location: createTable(), Result : " + respStr);
            // EXIST：新表创建成功过后添加缓存
            final boolean ret = Constants.RC_SUCCESS == respCode;
            if (ret) {
                TB_COUNT_MAP.put(this.getTable(), Boolean.TRUE);
            }
            return ret;
        }
    }

    @Override
    @PreValidateThis
    public boolean existTable() {
        // TODO Auto-generated method stub
        // 缓存统计结果
        Boolean exist = TB_COUNT_MAP.get(this.getTable());
        // 理论上这个Hash表中只可能有TRUE的值，没有FALSE
        if (null == exist || !exist) {
            final String sql = OracleHelper.getSqlTableExist(this.getTable());
            final Long counter = this.getContext().count(sql);
            exist = 0 < counter;
            // EXIST：直接判断添加缓存
            if (exist) {
                TB_COUNT_MAP.put(this.getTable(), Boolean.TRUE);
            }
        }
        return exist;
    }

    /* (non-Javadoc)
     * @see com.prayer.kernel.Builder#syncTable(com.prayer.kernel.model.GenericSchema)
     */
    @Override
    public boolean syncTable(GenericSchema schema) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.prayer.kernel.Builder#purgeTable()
     */
    @Override
    @PreValidateThis
    public boolean purgeTable() {
        final boolean exist = this.existTable();
        if (exist) {
            final String sql = MessageFormat.format(TB_DROP, this.getTable());
            final int respCode = this.getContext().execute(sql, null);
            final String respStr = (Constants.RC_SUCCESS == respCode ? ResponseCode.SUCCESS.toString()
                    : ResponseCode.FAILURE.toString());
            info(LOGGER, "[I] Location: purgeTable(), Result : " + respStr);
            // EXIST：删除成功过后移除缓存
            final boolean ret = Constants.RC_SUCCESS == respCode;
            if (ret && TB_COUNT_MAP.containsKey(this.getTable())) {
                TB_COUNT_MAP.remove(this.getTable());
            }
            return Constants.RC_SUCCESS == respCode;
        } else {
            info(LOGGER, "[I] Location: purgeTable(), Table does not exist : " + this.getTable());
            return false;
        }
    }

    /* (non-Javadoc)
     * @see com.prayer.kernel.builder.AbstractBuilder#lengthTypes()
     */
    @Override
    protected String[] lengthTypes() {
        // TODO Auto-generated method stub
        return Arrays.copyOf(OracleHelper.LENGTH_TYPES, OracleHelper.LENGTH_TYPES.length);
    }

    /* (non-Javadoc)
     * @see com.prayer.kernel.builder.AbstractBuilder#precisionTypes()
     */
    @Override
    protected String[] precisionTypes() {
        // TODO Auto-generated method stub
        return Arrays.copyOf(OracleHelper.PRECISION_TYPES, OracleHelper.PRECISION_TYPES.length);
    }

    /* (non-Javadoc)
     * @see com.prayer.kernel.builder.AbstractBuilder#nullRows(java.lang.String)
     */
    @Override
    protected Long nullRows(String column) {
        // TODO Auto-generated method stub
        return null;
    }
    
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private String genCreateSql() {
        // 1.主键定义行
        this.getSqlLines().clear();
        {
            this.genPrimaryKeyLines();
        }
        // 2.字段定义行
        {
            final Collection<FieldModel> fields = this.getSchema().getFields().values();
            for (final FieldModel field : fields) {
                if (!field.isPrimaryKey()) {
                    addSqlLine(this.genColumnLine(field));
                    // this.getSqlLines().add(this.genColumnLine(field));
                }
            }
        }
        // 3.添加Unique/Primary Key约束
        {
            final Collection<KeyModel> keys = this.getSchema().getKeys().values();
            for (final KeyModel key : keys) {
                // INCREMENT已经在前边生成过主键行了，不需要重新生成
                addSqlLine(this.genKeyLine(key));
                // this.getSqlLines().add(this.genKeyLine(key));
            }
        }
        // 4.添加Foreign Key约束
        {
            addSqlLine(this.genForeignKey());
        }
        // 5.生成最终SQL语句
        return MessageFormat.format(TB_CREATE, this.getTable(), StringKit.join(this.getSqlLines(), COMMA));
    }
    
    private void genPrimaryKeyLines() {
        final MetaPolicy policy = this.getSchema().getMeta().getPolicy();
        if (MetaPolicy.INCREMENT == policy) {
            addSqlLine(this.genIdentityLine(this.getSchema().getMeta()));
            // this.getSqlLines().add(this.genIdentityLine(getSchema().getMeta()));
        } else if (MetaPolicy.COLLECTION == policy) {
            final List<FieldModel> pkFields = this.getSchema().getPrimaryKeys();
            for (final FieldModel field : pkFields) {
                addSqlLine(this.genColumnLine(field));
                // this.getSqlLines().add(this.genColumnLine(field));
            }
        } else {
            final FieldModel field = this.getSchema().getPrimaryKeys().get(Constants.ZERO);
            addSqlLine(this.genColumnLine(field));
            // this.getSqlLines().add(this.genColumnLine(field));
        }
    }
    
    private String genIdentityLine(final MetaModel meta) {
        final StringBuilder pkSql = new StringBuilder();
        final FieldModel field = this.getSchema().getPrimaryKeys().get(Constants.ZERO);
        // 1.1.主键字段和数据类型
        final String columnType = SqlDdlStatement.DB_TYPES.get(field.getColumnType());

        // 2.字段名、数据类型，SQL Server独有：NAME INT PRIMARY KEY IDENTITY
        pkSql.append(field.getColumnName()).append(SPACE).append(columnType).append(SPACE).append(MsSqlHelper.IDENTITY)
                .append(BRACKET_SL).append(meta.getSeqInit()).append(COMMA).append(meta.getSeqStep())
                .append(BRACKET_SR);
        return pkSql.toString();
    }
    
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
