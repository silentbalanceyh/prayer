package com.prayer.plugin.ibatis;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.debug.Log.peError;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.dao.AbstractDbPool;
import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.Resources;
import com.prayer.dao.impl.jdbc.BoneCPPool;
import com.prayer.exception.database.MetadataNotSupportException;

/**
 * 将iBatis的连接池连接到BoneCP的连接池中
 * 
 * @author Lang
 *
 */
public class BoneCPDataSourceFactory implements DataSourceFactory {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(BoneCPDataSourceFactory.class);
    /** 支持的Database，SQL类型的Metadata Database **/
    private static final String[] DB_SUPPORTED = new String[] { "H2" };
    // ~ Instance Fields =====================================
    /**
     * SQL数据库连接池
     */
    private transient DataSource dataSource;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 返回当前的DataSource
     * 
     */
    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    @Override
    public void setProperties(final Properties props) {
        // 因为属性已经在DbPool中绑定好了，所以这里没有必要读取props
        try {
            final Set<String> supports = new HashSet<>(Arrays.asList(DB_SUPPORTED));
            if (supports.contains(Resources.META_CATEGORY)) {
                final AbstractDbPool pool = singleton(BoneCPPool.class, Resources.META_CATEGORY);
                this.dataSource = pool.getDataSource();
            } else {
                throw new MetadataNotSupportException(getClass(), Resources.META_CATEGORY, supports);
            }
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
        } catch (Exception ex) {
            jvmError(LOGGER, ex);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
