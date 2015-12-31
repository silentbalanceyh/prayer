package com.prayer.dao.impl.builder;    // NOPMD


import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.info;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.dao.AbstractBuilder;
import com.prayer.constant.Constants;
import com.prayer.constant.SqlSegment;
import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.constant.SystemEnum.StatusFlag;
import com.prayer.constant.log.DebugKey;
import com.prayer.exception.database.NullableAddException;
import com.prayer.exception.database.NullableAlterException;
import com.prayer.exception.database.UniqueAddException;
import com.prayer.exception.database.UniqueAlterException;
import com.prayer.model.h2.schema.FieldModel;
import com.prayer.model.h2.schema.KeyModel;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * Oracle的元数据生成器
 * @author huar
 *
 */
@Guarded
public class OracleBuilder extends AbstractBuilder implements SqlSegment { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(OracleBuilder.class);
	/** 针对整个系统的表统计管理 **/
	private static final ConcurrentMap<String, Boolean> TB_COUNT_MAP = new ConcurrentHashMap<>();
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public OracleBuilder(@NotNull @InstanceOfAny(GenericSchema.class) final GenericSchema schema) {
		super(schema);
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
			return false;
		} else {
			final String sql = genCreateSql();
			debug(LOGGER, DebugKey.INFO_SQL_STMT, sql);
			final int respCode = this.getContext().execute(sql, null);
			// EXIST：新表创建成功过后添加缓存
			final boolean ret = Constants.RC_SUCCESS == respCode;
			if (ret) {
				TB_COUNT_MAP.put(this.getTable(), Boolean.TRUE);
				// for oracle case
				this.createSeq();
			}
			return ret;
		}
	}

	@Override
	@PreValidateThis
	public boolean existTable() {
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

	/**
	 * 传入参数schema是最新从H2中拿到的元数据信息
	 */
	@Override
	@PreValidateThis
	public boolean syncTable(@NotNull final GenericSchema schema) {
		final boolean exist = this.existTable();
		if (exist) {
			final String sql = this.genUpdateSql(schema);
			debug(LOGGER, DebugKey.INFO_SQL_STMT, sql);
			//final int respCode = this.getContext().execute(sql, null);
			final int respCode = this.getContext().executeBatch(sql);
			return Constants.RC_SUCCESS == respCode;
		} else {
		    return false;
		}
	}


    /* (non-Javadoc)
     * @see com.prayer.facade.kernel.Builder#purgeTable()
     */
    @Override
    @PreValidateThis
    public boolean purgeTable() {
        final boolean exist = this.existTable();
        if (exist) {
            final String sql = MessageFormat.format(TB_DROP, this.getTable());
            debug(LOGGER, DebugKey.INFO_SQL_STMT, sql);
            final int respCode = this.getContext().execute(sql, null);
            // EXIST：删除成功过后移除缓存
            final boolean ret = Constants.RC_SUCCESS == respCode;
            if (ret && TB_COUNT_MAP.containsKey(this.getTable())) {
                TB_COUNT_MAP.remove(this.getTable());
                // for oracle case
    			this.removeSeq();
            }
            return Constants.RC_SUCCESS == respCode;
        } else {
            return false;
        }
    }

	/* (non-Javadoc)
	 * @see com.prayer.facade.kernel.builder.AbstractBuilder#lengthTypes()
	 */
	@Override
	protected String[] lengthTypes() {
		return Arrays.copyOf(OracleHelper.LENGTH_TYPES, OracleHelper.LENGTH_TYPES.length);
	}

	/* (non-Javadoc)
	 * @see com.prayer.facade.kernel.builder.AbstractBuilder#precisionTypes()
	 */
	@Override
	protected String[] precisionTypes() {
		return Arrays.copyOf(OracleHelper.PRECISION_TYPES, OracleHelper.PRECISION_TYPES.length);
	}

	/* (non-Javadoc)
	 * @see com.prayer.facade.kernel.builder.AbstractBuilder#nullRows(java.lang.String)
	 */
	@Override
	protected Long nullRows(final String column) {
		return this.getContext().count(OracleHelper.getSqlNull(this.getTable(), column));
	}
	
    /**
     * 
     * @return
     */
    @Override
    protected Long uniqueRows(@NotNull @NotBlank @NotEmpty final String column){
    	return this.getContext().count(OracleHelper.getSqlUnique(this.getTable(), column));
    }
	
	@Override
	protected String genAlterColumns(@NotNull final FieldModel field) {
		
		// 0. oracle dedicated handle for clob/blob fields, remove/add instead of modify (ORA-22859: invalid modification of columns)
		if (this.getColType(field).equalsIgnoreCase("CLOB") || this.getColType(field).equalsIgnoreCase("BLOB"))
		{
			return "";
		}	
		// 1.初始化缓冲区
		final StringBuilder sql = new StringBuilder();
		// 2.填充模板的第二部分
		sql.append(MODIFY).append(SPACE).append(BRACKET_SL).append(this.genColumnLine(field)).append(BRACKET_SR);
		// 3.返回最终语句
		return MessageFormat.format(TB_ALTER, this.getTable(), sql.toString());
	}
	
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private String genUpdateSql(final GenericSchema schema) {
		// 1.清除SQL行
		this.getSqlLines().clear();
		// 没有表存在的时候：Fix Issue: Invalid object name 'XXXXX'.
		Long rows = 0L;
		if (this.existTable()) {
			rows = this.getRows();
		}
		// 2.清除约束
		{
			final Collection<String> csSet = this.getCSNames();
			for (final String name : csSet) {
				addSqlLine(this.genDropConstraints(name));
			}
		}
		final ConcurrentMap<StatusFlag, Collection<String>> statusMap = this.getStatusMap(schema);
		// 3.删除列操作
		{
			final Collection<String> columns = statusMap.get(StatusFlag.DELETE);
			for (final String column : columns) {
				addSqlLine(this.genDropColumns(column));
			}
		}
		// 4.添加列操作
		{
			this.genAddColumnLines(statusMap, schema, rows);
		}
		// 5.修改列操作
		{
			this.genAlterColumnLines(statusMap, schema, rows);
		}
		// 6.添加约束
		{
			final Collection<KeyModel> keys = schema.getKeys().values();
			for (final KeyModel key : keys) {
				if (KeyCategory.ForeignKey == key.getCategory()) {
					final String column = key.getColumns().get(Constants.ZERO);
					final FieldModel field = schema.getColumn(column);
					addSqlLine(this.genAddCsLine(key, field));
				} else {
					addSqlLine(this.genAddCsLine(key, null));
				}
			}
		}
		// oracle批量处理时，语句间必须加分号以及换行
		//return StringKit.join(this.getSqlLines(), SEMICOLON, true);
		return StringKit.join(this.getSqlLines(), SEMICOLON + NEW_LINE);
	}

	private void genAlterColumnLines(final ConcurrentMap<StatusFlag, Collection<String>> statusMap,
			final GenericSchema schema, final long rows) {
		final Collection<String> columns = statusMap.get(StatusFlag.UPDATE);
		for (final String column : columns) {
			final FieldModel field = schema.getColumn(column);
			if (Constants.ZERO == rows) {
				addSqlLine(this.genAlterColumns(field));
			} else if (Constants.ZERO < rows) {
                // NOT NULL在有数据信息的情况不可变更
                final Long nullRows = this.nullRows(column);
                if (Constants.ZERO < nullRows && !field.isNullable()) {
                    this.setError(new NullableAlterException(getClass(), field.getColumnName(), // NOPMD
                            this.getTable()));
                    break;
                }
                // UNIQUE在有数据信息的时候如果本身出现了Duplicated的值（非Unique的，那么会抛出异常）
                final Long uniqueRows = this.uniqueRows(column);
                if (Constants.ZERO < uniqueRows && field.isUnique()) {
                    this.setError(new UniqueAlterException(getClass(), field.getColumnName(), this.getTable()));
                    break;
                }
                addSqlLine(this.genAlterColumns(field));
            }
		}
	}

	private void genAddColumnLines(final ConcurrentMap<StatusFlag, Collection<String>> statusMap,
			final GenericSchema schema, final long rows) {
		final Collection<String> columns = statusMap.get(StatusFlag.ADD);
		for (final String column : columns) {
			final FieldModel field = schema.getColumn(column);
			if (Constants.ZERO == rows) {
				addSqlLine(this.genAddColumns(field));
			} else {
                // NOT NULL不可以针对有数据的，ORA-01758
                if (!field.isNullable()) {
                    this.setError(new NullableAddException(getClass(), field.getColumnName(), this.getTable())); // NOPMD
                    break;
                }
                
                // oracle中无此限制，但按平台统一处理
                // UNIQUE不可以针对数据行超过1行的
                if (Constants.ONE < rows && field.isUnique()) {
                    this.setError(new UniqueAddException(getClass(), field.getColumnName(), this.getTable()));// NOPMD
                    break;
                }
                addSqlLine(this.genAddColumns(field));
            }
		}
	}

	private ConcurrentMap<StatusFlag, Collection<String>> getStatusMap(final GenericSchema schema) {
		// 获取新列集合
		final Collection<String> newColumns = schema.getColumns();
		// 获取旧列集合
		final String sql = OracleHelper.getSqlColumns(this.getTable());
		final Collection<String> oldColumns = this.getContext().select(sql, OracleHelper.COL_TB_COLUMN);
		return this.getColumnStatus(oldColumns, newColumns);
	}

	private Collection<String> getCSNames() {
		final String sql = OracleHelper.getSqlConstraints(this.getTable());
		return this.getContext().select(sql, OracleHelper.COL_TB_CONSTRAINT);
	}
	
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
            final List<String> fkeys = this.genForeignKeys();
            for (final String fkeySql : fkeys) {
                addSqlLine(fkeySql);
            }
		}
		// 5.生成最终SQL语句
		return MessageFormat.format(TB_CREATE, this.getTable(), StringKit.join(this.getSqlLines(), COMMA));
	}
	
	private void genPrimaryKeyLines() {
		final MetaPolicy policy = this.getSchema().getMeta().getPolicy();
		/*
		if (MetaPolicy.INCREMENT == policy) {
			addSqlLine(this.genIdentityLine(this.getSchema().getMeta()));
			// this.getSqlLines().add(this.genIdentityLine(getSchema().getMeta()));
		} else
		*/
		if (MetaPolicy.COLLECTION == policy) {
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
	
	/**
	 * Remove related sequence
	 */
	private void removeSeq() {
		String removeSeqSql;
		final MetaPolicy policy = this.getSchema().getMeta().getPolicy();
		if (MetaPolicy.INCREMENT == policy) {
			// check if is not existed
			final String sql = OracleHelper.getSeqExist(this
					.getSchema().getMeta().getSeqName());
			final Long counter = this.getContext().count(sql);
			if (0 == counter)
			{
				info(LOGGER, "[I] Location: removeSeq(), Result : already is not existed");
			}
			else
			{
				removeSeqSql = MessageFormat.format(SEQ_REMOVE, this
						.getSchema().getMeta().getSeqName());
				final int respCode = this.getContext().execute(removeSeqSql, null);
				final String respStr = (Constants.RC_SUCCESS == respCode ? ResponseCode.SUCCESS.toString()
						: ResponseCode.FAILURE.toString());
				info(LOGGER, "[I] Location: removeSeq(), Result : " + respStr);
			}
		}
	}
	
	/**
	 * Create related sequence
	 */
	private void createSeq() {
		String createSeqSql;
		final MetaPolicy policy = this.getSchema().getMeta().getPolicy();
		if (MetaPolicy.INCREMENT == policy) {
			// check if is existed
			final String sql = OracleHelper.getSeqExist(this
					.getSchema().getMeta().getSeqName());
			final Long counter = this.getContext().count(sql);
			if (0 == counter)
			{
				createSeqSql = MessageFormat.format(SEQ_CREATE, this
						.getSchema().getMeta().getSeqName(),
						// fill with fixed values(min, max) for the time being
						"1", "999999999", this.getSchema().getMeta().getSeqInit(),
						this.getSchema().getMeta().getSeqStep());
				final int respCode = this.getContext().execute(createSeqSql, null);
				final String respStr = (Constants.RC_SUCCESS == respCode ? ResponseCode.SUCCESS.toString()
						: ResponseCode.FAILURE.toString());
				info(LOGGER, "[I] Location: createSeq(), Result : " + respStr);
			}
			else
			{
				info(LOGGER, "[I] Location: createSeq(), Result : already existed");
			}
		}
	}
	
	/*
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
	*/
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
