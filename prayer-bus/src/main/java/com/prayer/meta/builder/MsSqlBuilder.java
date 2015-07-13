package com.prayer.meta.builder;

import static com.prayer.util.Error.info;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.mod.meta.FieldModel;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.mod.meta.KeyModel;
import com.prayer.mod.meta.MetaModel;
import com.prayer.mod.meta.SystemEnum.KeyCategory;
import com.prayer.mod.meta.SystemEnum.MetaPolicy;
import com.prayer.util.StringKit;

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
public class MsSqlBuilder extends AbstractMetaBuilder {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(MsSqlBuilder.class);
	/** 类型后跟Length，类似：{ VARCHAR(256) } **/
	private static final String[] LENGTH_TYPES = new String[] { "CHAR", "VARCHAR", "NCHAR", "NVARCHAR" };
	/** 类型后边跟精度，类似：{ DECIMAL(2,4) } **/
	private static final String[] PRECISION_TYPES = new String[] { "DECIMAL" };
	/** SQL Server 特殊关键字 **/
	private static final String IDENTITY = "IDENTITY";

	/** 检查表是否存在 **/
	private final static String SQL_TB_EXIST = "SELECT COUNT(name) FROM dbo.SYSOBJECTS WHERE ID = OBJECT_ID(N''{0}'') AND OBJECTPROPERTY(ID, ''IsTable'') = 1";

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
		return Arrays.copyOf(LENGTH_TYPES, LENGTH_TYPES.length);
	}

	/**
	 * 
	 */
	@Override
	protected String[] precisionTypes() {
		return Arrays.copyOf(PRECISION_TYPES, PRECISION_TYPES.length);
	}

	/**
	 * 创建新的数据表
	 */
	@Override
	public boolean createTable() {
		final String sql = genCreateSql();
		final int affectedRows = this.getContext().execute(sql);
		info(LOGGER, "[I] Location: createTable(), Affected Rows: " + affectedRows);
		return 0 < affectedRows;
	}

	/**
	 * 检查数据表是否存在
	 */
	@Override
	public boolean existTable() {
		final String sql = MessageFormat.format(SQL_TB_EXIST, this.getTable());
		final long counter = this.getContext().count(sql);
		info(LOGGER, "[I] Location: existTable(), Table Counter Result: " + counter);
		return 0 < counter;
	}

	@Override
	public void syncTable() {
		// TODO Auto-generated method stub
	}

	/** **/
	@Override
	public boolean purgeTable() {
		final String sql = MessageFormat.format(TB_DROP, this.getTable());
		final int affectedRows = this.getContext().execute(sql);
		info(LOGGER, "[I] Location: purgeTable(), Affected Rows: " + affectedRows);
		return 0 < affectedRows;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private String genCreateSql() {
		// 1.主键定义行
		{
			this.genPrimaryKeyLines();
		}
		// 2.字段定义行
		{
			final Collection<FieldModel> fields = this.getFields();
			for (final FieldModel field : fields) {
				if (!field.isPrimaryKey()) {
					this.getSqlLines().add(this.genColumnLine(field));
				}
			}
		}
		// 3.添加Unique/Primary Key约束
		{
			final MetaPolicy policy = getSchema().getMeta().getPolicy();
			final Collection<KeyModel> keys = this.getKeys();
			for (final KeyModel key : keys) {
				// INCREMENT已经在前边生成过主键行了，不需要在生成
				if (KeyCategory.PrimaryKey == key.getCategory() && MetaPolicy.INCREMENT == policy) {
					continue;
				}
				this.getSqlLines().add(this.genKeyLine(key));
			}
		}
		// 4.添加Foreign Key约束
		{
			this.getSqlLines().add(this.genForeignKey());
		}
		// 5.生成最终SQL语句
		return MessageFormat.format(TB_CREATE, this.getTable(), StringKit.join(this.getSqlLines(), COMMA));
	}

	private void genPrimaryKeyLines() {
		final MetaPolicy policy = getSchema().getMeta().getPolicy();
		if (MetaPolicy.INCREMENT == policy) {
			this.getSqlLines().add(this.genIdentityLine(getSchema().getMeta()));
		} else if (MetaPolicy.COLLECTION == policy) {
			final List<FieldModel> pkFields = this.getPrimaryKeys();
			for (final FieldModel field : pkFields) {
				this.getSqlLines().add(this.genColumnLine(field));
			}
		} else {
			final FieldModel field = this.getPrimaryKeys().get(Constants.ZERO);
			this.getSqlLines().add(this.genColumnLine(field));
		}
	}

	private String genIdentityLine(final MetaModel meta) {
		final StringBuilder pkSql = new StringBuilder();
		final FieldModel field = this.getPrimaryKeys().get(Constants.ZERO);
		// 1.1.主键字段和数据类型
		final String columnType = DB_TYPES.get(field.getColumnType());

		// 2.字段名、数据类型
		pkSql.append(field.getColumnName()).append(SPACE).append(columnType).append(PRIMARY).append(SPACE).append(KEY)
				.append(SPACE).append(IDENTITY).append(BRACKET_SL).append(meta.getSeqInit()).append(COMMA)
				.append(meta.getSeqStep()).append(BRACKET_SR);
		return pkSql.toString();
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
