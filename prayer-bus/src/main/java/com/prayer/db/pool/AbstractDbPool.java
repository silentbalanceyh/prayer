package com.prayer.db.pool;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prayer.res.cv.Resources;
import com.prayer.util.PropertyKit;

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
	 * Spring JDBC Template实例：jdbc这个变量没有执行Pre的@NotNull约束，因为构造函数调用了子类中的两个方法，
	 * 而这个变量使用了@NotNull过后会导致PreValidateThis的构造失败
	 */
	protected transient JdbcTemplate jdbc;
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
			// 初始化Template
			this.jdbc = new JdbcTemplate(this.getDataSource());
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
	@Pre(expr = "_this.jdbc != null", lang = "groovy")
	public JdbcTemplate getJdbc() {
		return this.jdbc;
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
	@Pre(expr = "_this.loader != null", lang = "groovy")
	public PropertyKit getLoader() {
		return this.LOADER;
	}
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
