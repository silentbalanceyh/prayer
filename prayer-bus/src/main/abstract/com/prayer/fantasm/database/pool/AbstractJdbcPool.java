package com.prayer.fantasm.database.pool;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import com.prayer.constant.Resources;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.database.pool.JdbcPool;
import com.prayer.util.io.PropertyKit;

import net.sf.oval.constraint.AssertFieldConstraints;
import net.sf.oval.constraint.InstanceOfAny;
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
public abstract class AbstractJdbcPool implements JdbcPool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 数据库的种类： MSSQL,PGSQL,ORACLE,MYSQL,MONGODB,H2
     */
    @NotNull
    @NotEmpty
    @NotBlank
    protected transient String category; // NOPMD
    /**
     * 资源加载器
     */
    @NotNull
    @InstanceOfAny(PropertyKit.class)
    protected transient final PropertyKit LOADER = new PropertyKit(getClass(), Resources.DB_CFG_FILE); // NOPMD

    // ~ Constructors ========================================
    /**
     * 默认构造函数
     */
    @PostValidateThis
    protected AbstractJdbcPool() {
        this(Resources.DB_CATEGORY);
    }

    /**
     * 构造函数
     * 
     * @param category
     */
    @PostValidateThis
    protected AbstractJdbcPool(@AssertFieldConstraints("category") final String category) {
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
    public abstract DataSource getDataSource();

    // ~ Methods =============================================
    /**
     * 返回JdbcTemplate引用
     * 
     * @return
     */
    public JdbcOperations getExecutor() {
        return new JdbcTemplate(this.getDataSource());
    }

    /**
     * 返回数据库类型
     * 
     * @return
     */
    @NotNull
    @Pre(expr = "_this.category != null", lang = Constants.LANG_GROOVY)
    @Override
    public String getCategory() {
        return this.category;
    }

    /**
     * 返回资源加载器，子类使用
     * 
     * @return
     */
    @Pre(expr = "_this.loader != null", lang = Constants.LANG_GROOVY)
    @Override
    public PropertyKit getLoader() {
        return this.LOADER;
    }
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================
}
