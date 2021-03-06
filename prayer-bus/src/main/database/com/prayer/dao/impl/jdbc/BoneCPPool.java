package com.prayer.dao.impl.jdbc;

import static com.prayer.util.Instance.reservoir;

import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import com.jolbox.bonecp.BoneCPDataSource;
import com.prayer.base.dao.AbstractDbPool;
import com.prayer.constant.MemoryPool;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.Pre;

/**
 * Bone CP连接池
 *
 * @author Lang
 * @see
 */
@Guarded
public class BoneCPPool extends AbstractDbPool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * SQL数据源实例，维持一个实例：OVal不支持静态变量field的约束
     */
    private transient DataSource dataSource;

    // ~ Constructors ========================================
    /**
     * 默认构造函数
     */
    public BoneCPPool() {
        super();
    }

    // ~ Static Block ========================================
    /**
     * 传入数据库种类的构造函数
     * 
     * @param category
     */
    public BoneCPPool(@NotNull @NotEmpty @NotBlank final String category) {
        super(category);
    }

    // ~ Override Methods ====================================
    /**
     * 获取数据源引用
     */
    @Override
    @InstanceOfAny(BoneCPDataSource.class)
    @Pre(expr = "_this.dataSource != null", lang = "groovy")
    public BoneCPDataSource getDataSource() {
        return (BoneCPDataSource) dataSource;
    }

    /**
     * JDBC基本属性
     */
    @Override
    @Pre(expr = "_this.category != null", lang = "groovy")
    protected void initJdbc() {
        this.getDataSource().setDriverClass(this.getLoader().getString(this.getCategory() + ".jdbc.driver"));
        this.getDataSource().setJdbcUrl(this.getLoader().getString(this.getCategory() + ".jdbc.url"));
        this.getDataSource().setUsername(this.getLoader().getString(this.getCategory() + ".jdbc.username"));
        this.getDataSource().setPassword(this.getLoader().getString(this.getCategory() + ".jdbc.password"));
    }

    /**
     * 初始化数据源
     */
    @Override
    @Pre(expr = "_this.category != null", lang = "groovy")
    protected void initDataSource() {
        if (null == dataSource) {
            /**
             * 池化数据源的信息，默认： Internal: H2 = DataSource 可配置的外围的：MSSQL = DataSource
             */
            dataSource = reservoir(MemoryPool.POOL_DATA_SOURCE, this.getCategory(), BoneCPDataSource.class);
        }
    }

    /**
     * 连接池属性
     */
    @Override
    protected void initPool() {
        this.getDataSource().setAcquireIncrement(this.getLoader().getInt("bonecp.acquire.increment"));
        this.getDataSource().setAcquireRetryDelay(this.getLoader().getLong("bonecp.acquire.retry.delay"),
                TimeUnit.MILLISECONDS);
        this.getDataSource().setIdleMaxAgeInSeconds(this.getLoader().getLong("bonecp.idle.max.age"));
        this.getDataSource()
                .setIdleConnectionTestPeriodInSeconds(this.getLoader().getLong("bonecp.idle.connection.test.period"));
        this.getDataSource().setPartitionCount(this.getLoader().getInt("bonecp.partition.count"));
        this.getDataSource().setStatementsCacheSize(this.getLoader().getInt("bonecp.statements.cache.size"));
        this.getDataSource().setMinConnectionsPerPartition(this.getLoader().getInt("bonecp.min.conn.per.partition"));
        this.getDataSource().setMaxConnectionsPerPartition(this.getLoader().getInt("bonecp.max.conn.per.partition"));
        this.getDataSource().setPoolStrategy(this.getLoader().getString("bonecp.pool.strategy"));
    }
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================
}
