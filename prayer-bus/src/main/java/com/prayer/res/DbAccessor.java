package com.prayer.res;

import java.text.MessageFormat;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

import com.prayer.util.PropertyLoader;

/**
 * 数据库配置工具类
 *
 * @author Lang
 * @see
 */
@Guarded
public final class DbAccessor {
	// ~ Static Fields =======================================
	/**
	 * 属性文件加载器
	 */
	private static final PropertyLoader LOADER;
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/** **/
	static {
		LOADER = Resources.getLoader();
	}

	// ~ Static Methods ======================================
	/**
	 * 获取默认建模方式
	 * @param strategy
	 * @return
	 */
	@NotNull
	public static String strategy(
			@NotNull @NotEmpty @NotBlank final String strategy) {
		return LOADER.getString(MessageFormat.format(PropKeys.SMA_STRATEGY_KEY,
				strategy));
	}
	/**
	 * 获取连接池实现方式
	 * @return
	 */
	@NotNull
	public static String pool(){
		final String dbPool = LOADER.getString(PropKeys.DB_POOL);
		return null == dbPool ? Classes.DBP_BONECP: dbPool;
	}
	/**
	 * 获取Strategy中的Context实现类
	 * @return
	 */
	@NotNull
	public static String context(){
		return Classes.CTX_CONTEXT;
	}
	/**
	 * 获取数据库访问实现类
	 * @param category
	 * @return
	 */
	@NotNull
	public static String dbDao(final String category){
		String rDao = null;
		switch (category) {
		case Constants.DF_MSSQL:
			rDao = Classes.DAO_MSSQL;
			break;
		case Constants.DF_MYSQL:
			rDao = Classes.DAO_MYSQL;
			break;
		case Constants.DF_ORACLE:
			rDao = Classes.DAO_ORACLE;
			break;
		case Constants.DF_PGSQL:
			rDao = Classes.DAO_PGSQL;
			break;
		default:
			rDao = Resources.DB_R_DAO;
			break;
		}
		return rDao;
	}
	/**
	 * 获取安全相关的访问实现类
	 * @param category
	 * @return
	 */
	@NotNull
	public static String secDao(
			@NotNull @NotEmpty @NotBlank final String category){
		String rDao = null;
		switch (category) {
		case Constants.DF_MSSQL:
			rDao = Classes.DAO_OAUTH_MSSQL;
			break;
		case Constants.DF_MYSQL:
			break;
		case Constants.DF_ORACLE:
			break;
		case Constants.DF_PGSQL:
			break;
		default:
			rDao = Resources.DB_SEC_O_DAO;
			break;
		}
		return rDao;
	}
	/**
	 * 获取数据库Metadata的Builder
	 * @param category
	 * @return
	 */
	@NotNull
	public static String metaBuilder(
			@NotNull @NotEmpty @NotBlank final String category){
		String builder = null;
		switch (category) {
		case Constants.DF_MSSQL:
			builder = Classes.BLD_MSSQL;
			break;
		case Constants.DF_MYSQL:
			builder = Classes.BLD_MYSQL;
			break;
		case Constants.DF_ORACLE:
			builder = Classes.BLD_ORACLE;
			break;
		case Constants.DF_PGSQL:
			builder = Classes.BLD_PGSQL;
			break;
		default:
			builder = Resources.DB_BUILDER;
			break;
		}
		return builder;
	}
	// ~ Constructors ========================================
	private DbAccessor() {

	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
