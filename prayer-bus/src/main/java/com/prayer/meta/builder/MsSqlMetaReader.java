package com.prayer.meta.builder;

import java.text.MessageFormat;

import com.prayer.constant.Constants;
import com.prayer.db.conn.JdbcContext;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;


/**
 * SQL Server元数据读取器
 * 
 * @author Lang
 *
 */
@Guarded
final class MsSqlMetaReader {
	// ~ Static Fields =======================================
	/** 类型后跟Length，类似：{ VARCHAR(256) } **/
	public static final String[] LENGTH_TYPES = new String[] { "CHAR", "VARCHAR", "NCHAR", "NVARCHAR" };
	/** 类型后边跟精度，类似：{ DECIMAL(2,4) } **/
	public static final String[] PRECISION_TYPES = new String[] { "DECIMAL" };
	/** SQL Server 特殊关键字 **/
	public static final String IDENTITY = "IDENTITY";

	/** 检查表是否存在 **/
	private final static String SQL_TB_EXIST = "SELECT COUNT(name) FROM dbo.SYSOBJECTS WHERE ID = OBJECT_ID(N''{0}'') AND OBJECTPROPERTY(ID, ''IsTable'') = 1";
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient final JdbcContext context;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public MsSqlMetaReader(@NotNull final JdbcContext context) {
		this.context = context;
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	@Pre(expr = "_this.content != null", lang = Constants.LANG_GROOVY)
	public Long countTable(@NotNull final String tableName) {
		final String sql = MessageFormat.format(SQL_TB_EXIST, tableName);
		return this.context.count(sql);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	/**
	 * 
	 * @return
	 */
	@NotNull
	public JdbcContext getContext(){
		return this.context;
	}
	// ~ hashCode,equals,toString ============================
}
