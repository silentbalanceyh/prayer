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
public class MsSqlBuilder extends AbstractMetaBuilder implements SqlSegment {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(MsSqlBuilder.class);

	// ~ Instance Fields =====================================
	/** 从当前数据库读取到的Schema **/
	private transient GenericSchema databaseSchema;

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
	 * 创建新的数据表
	 */
	@Override
	public boolean createTable() {
		final String sql = genCreateSql();
		final int respCode = this.getContext().execute(sql);
		final String respStr = (Constants.RC_SUCCESS == respCode ? SUCCESS : FAILURE);
		info(LOGGER, "[I] Location: createTable(), Result : " + respStr);
		return Constants.RC_SUCCESS == respCode;
	}

	/**
	 * 检查数据表是否存在
	 */
	@Override
	public boolean existTable() {
		final String sql = MsSqlHelper.getSqlTableExist(this.getDatabase(), this.getTable());
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
		final int respCode = this.getContext().execute(sql);
		final String respStr = (Constants.RC_SUCCESS == respCode ? SUCCESS : FAILURE);
		info(LOGGER, "[I] Location: purgeTable(), Result : " + respStr);
		return Constants.RC_SUCCESS == respCode;
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
				// INCREMENT已经在前边生成过主键行了，不需要重新生成
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
		final String columnType = SqlStatement.DB_TYPES.get(field.getColumnType());

		// 2.字段名、数据类型，SQL Server独有：NAME INT PRIMARY KEY IDENTITY
		pkSql.append(field.getColumnName()).append(SPACE).append(columnType).append(PRIMARY).append(SPACE).append(KEY)
				.append(SPACE).append(MsSqlHelper.IDENTITY).append(BRACKET_SL).append(meta.getSeqInit()).append(COMMA)
				.append(meta.getSeqStep()).append(BRACKET_SR);
		return pkSql.toString();
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
