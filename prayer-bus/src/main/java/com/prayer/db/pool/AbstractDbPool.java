package com.prayer.db.pool;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.util.PropertyKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * Jdbc连接池类
 *
 * @author Lang
 * @see
 */
@Guarded
public abstract class AbstractDbPool {
	// ~ Static Fields =======================================
	/**
	 * 数据源的Mapping
	 */
	public static final ConcurrentMap<String, DataSource> DS_POOL = new ConcurrentHashMap<>();
	// ~ Instance Fields =====================================
	/**
	 * 数据库的种类： MSSQL,PGSQL,ORACLE,MYSQL,MONGODB
	 */
	@NotNull
	@NotEmpty
	@NotBlank
	protected transient String category; // NOPMD
	/**
	 * 资源加载器
	 */
	@NotNull
	protected transient final PropertyKit LOADER = new PropertyKit(getClass(), Resources.DB_CFG_FILE);

	// ~ Constructors ========================================
	/**
	 * 默认构造函数
	 */
	@PostValidateThis
	protected AbstractDbPool() {
		this(Resources.DB_CATEGORY);
	}

	/**
	 * 构造函数
	 * 
	 * @param category
	 */
	@PostValidateThis
	protected AbstractDbPool(@NotNull @NotEmpty @NotBlank final String category) {
		synchronized (getClass()) {
			this.category = category;
			// 初始化数据源
			this.initDataSource();
			// 初始化jdbc基础属性
			this.initJdbc();
			// 初始化连接池属性
			this.initPool();
		}
	}

	// ~ Abstract Methods ====================================
	/**
	 * 初始化数据源
	 */
	protected abstract void initDataSource();

	/**
	 * JDBC属性设置
	 */
	protected abstract void initJdbc();

	/**
	 * 连接池属性设置
	 */
	protected abstract void initPool();

	/**
	 * 返回数据源DataSource的引用
	 * 
	 * @return
	 */
	protected abstract DataSource getDataSource();

	// ~ Methods =============================================
	/**
	 * 返回JdbcTemplate引用
	 * 
	 * @return
	 */
	public JdbcTemplate getJdbc() {
		return new JdbcTemplate(this.getDataSource());
	}

	/**
	 * 返回数据库类型
	 * 
	 * @return
	 */
	@NotNull
	public String getCategory() {
		return this.category;
	}

	/**
	 * 返回资源加载器，子类使用
	 * 
	 * @return
	 */
	@Pre(expr = "_this.loader != null", lang = Constants.LANG_GROOVY)
	public PropertyKit getLoader() {
		return this.LOADER;
	}
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
