package com.prayer.meta.builder;

import static com.prayer.util.Error.info;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.exception.builder.NullableAddException;
import com.prayer.exception.builder.NullableAlterException;
import com.prayer.mod.SystemEnum.KeyCategory;
import com.prayer.mod.SystemEnum.MetaPolicy;
import com.prayer.mod.SystemEnum.ResponseCode;
import com.prayer.mod.meta.FieldModel;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.mod.meta.KeyModel;
import com.prayer.mod.meta.MetaModel;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * SQL Server的元数据生成器
 * 
 * @author Lang
 *
 */
@Guarded
public class MsSqlBuilder extends AbstractMetaBuilder implements SqlSegment {	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(MsSqlBuilder.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public MsSqlBuilder(@NotNull final GenericSchema schema) {
		super(schema);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	protected String[] lengthTypes() {
		return Arrays.copyOf(MsSqlHelper.LENGTH_TYPES, MsSqlHelper.LENGTH_TYPES.length);
	}

	/**
	 * 
	 */
	@Override
	protected String[] precisionTypes() {
		return Arrays.copyOf(MsSqlHelper.PRECISION_TYPES, MsSqlHelper.PRECISION_TYPES.length);
	}

	/**
	 * 
	 * @param column
	 * @return
	 */
	@Override
	protected Long nullRows(@NotNull @NotBlank @NotEmpty final String column) {
		return this.getContext().count(MsSqlHelper.getSqlNull(this.getMetadata().getTable(), column));
	}

	/**
	 * 创建新的数据表
	 */
	@Override
	public boolean createTable() {
		final String sql = genCreateSql();
		final int respCode = this.getContext().execute(sql);
		final String respStr = (Constants.RC_SUCCESS == respCode ? ResponseCode.SUCCESS.toString()
				: ResponseCode.FAILURE.toString());
		info(LOGGER, "[I] Location: createTable(), Result : " + respStr);
		return Constants.RC_SUCCESS == respCode;
	}

	/**
	 * 检查数据表是否存在
	 */
	@Override
	public boolean existTable() {
		final String sql = MsSqlHelper.getSqlTableExist(this.getMetadata().getTable());
		final long counter = this.getContext().count(sql);
		info(LOGGER, "[I] Location: existTable(), Table Counter Result: " + counter);
		return 0 < counter;
	}

	/**
	 * 传入参数schema是最新从H2中拿到的元数据信息
	 */
	@Override
	public boolean syncTable(@NotNull final GenericSchema schema) {
		final String sql = this.genUpdateSql(schema);
		info(LOGGER, "[I] Sql: " + sql);
		final int respCode = this.getContext().execute(sql);
		final String respStr = (Constants.RC_SUCCESS == respCode ? ResponseCode.SUCCESS.toString()
				: ResponseCode.FAILURE.toString());
		info(LOGGER, "[I] Location: syncTable(GenericSchema), Result : " + respStr);
		return Constants.RC_SUCCESS == respCode;
	}

	/** **/
	@Override
	public boolean purgeTable() {
		final String sql = MessageFormat.format(TB_DROP, this.getMetadata().getTable());
		final int respCode = this.getContext().execute(sql);
		final String respStr = (Constants.RC_SUCCESS == respCode ? ResponseCode.SUCCESS.toString()
				: ResponseCode.FAILURE.toString());
		info(LOGGER, "[I] Location: purgeTable(), Result : " + respStr);
		return Constants.RC_SUCCESS == respCode;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private String genUpdateSql(final GenericSchema schema) {
		// 1.清除SQL行
		this.getSqlLines().clear();
		final Long rows = this.getRows();
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
		return StringKit.join(this.getSqlLines(), SEMICOLON, true);
	}

	private void genAlterColumnLines(final ConcurrentMap<StatusFlag, Collection<String>> statusMap,
			final GenericSchema schema, final long rows) {
		final Collection<String> columns = statusMap.get(StatusFlag.UPDATE);
		for (final String column : columns) {
			final FieldModel field = schema.getColumn(column);
			if (Constants.ZERO == rows) {
				addSqlLine(this.genAlterColumns(field));
			} else if (Constants.ZERO < rows) {
				final Long nullRows = this.nullRows(column);
				if (Constants.ZERO == nullRows) {
					addSqlLine(this.genAlterColumns(field));
				} else {
					if (field.isNullable()) {
						addSqlLine(this.genAlterColumns(field));
					} else {
						this.setError(new NullableAlterException(getClass(), field.getColumnName(), // NOPMD
								this.getMetadata().getTable()));
					}
				}
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
				if (field.isNullable()) {
					addSqlLine(this.genAddColumns(field));
				} else {
					this.setError(
							new NullableAddException(getClass(), field.getColumnName(), this.getMetadata().getTable())); // NOPMD
				}
			}
		}
	}

	private ConcurrentMap<StatusFlag, Collection<String>> getStatusMap(final GenericSchema schema) {
		// 获取新列集合
		final Collection<String> newColumns = GenericSchema.getColumns(schema.getFields().values());
		// 获取旧列集合
		final String sql = MsSqlHelper.getSqlColumns(this.getMetadata().getTable());
		final Collection<String> oldColumns = this.getContext().select(sql, MsSqlHelper.COL_TB_COLUMN);
		return this.getColumnStatus(oldColumns, newColumns);
	}

	private Collection<String> getCSNames() {
		final String sql = MsSqlHelper.getSqlConstraints(this.getMetadata().getTable());
		return this.getContext().select(sql, MsSqlHelper.COL_TB_CONSTRAINT);
	}

	private String genCreateSql() {
		// 1.主键定义行
		this.getSqlLines().clear();
		{
			this.genPrimaryKeyLines();
		}
		// 2.字段定义行
		{
			final Collection<FieldModel> fields = this.getMetadata().getFields();
			for (final FieldModel field : fields) {
				if (!field.isPrimaryKey()) {
					addSqlLine(this.genColumnLine(field));
					// this.getSqlLines().add(this.genColumnLine(field));
				}
			}
		}
		// 3.添加Unique/Primary Key约束
		{
			final Collection<KeyModel> keys = this.getMetadata().getKeys();
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
		return MessageFormat.format(TB_CREATE, this.getMetadata().getTable(),
				StringKit.join(this.getSqlLines(), COMMA));
	}

	private void genPrimaryKeyLines() {
		final MetaPolicy policy = getMetadata().getSchema().getMeta().getPolicy();
		if (MetaPolicy.INCREMENT == policy) {
			addSqlLine(this.genIdentityLine(getMetadata().getSchema().getMeta()));
			// this.getSqlLines().add(this.genIdentityLine(getSchema().getMeta()));
		} else if (MetaPolicy.COLLECTION == policy) {
			final List<FieldModel> pkFields = this.getMetadata().getPrimaryKeys();
			for (final FieldModel field : pkFields) {
				addSqlLine(this.genColumnLine(field));
				// this.getSqlLines().add(this.genColumnLine(field));
			}
		} else {
			final FieldModel field = this.getMetadata().getPrimaryKeys().get(Constants.ZERO);
			addSqlLine(this.genColumnLine(field));
			// this.getSqlLines().add(this.genColumnLine(field));
		}
	}

	private String genIdentityLine(final MetaModel meta) {
		final StringBuilder pkSql = new StringBuilder();
		final FieldModel field = this.getMetadata().getPrimaryKeys().get(Constants.ZERO);
		// 1.1.主键字段和数据类型
		final String columnType = SqlStatement.DB_TYPES.get(field.getColumnType());

		// 2.字段名、数据类型，SQL Server独有：NAME INT PRIMARY KEY IDENTITY
		pkSql.append(field.getColumnName()).append(SPACE).append(columnType).append(SPACE).append(MsSqlHelper.IDENTITY)
				.append(BRACKET_SL).append(meta.getSeqInit()).append(COMMA).append(meta.getSeqStep())
				.append(BRACKET_SR);
		return pkSql.toString();
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
