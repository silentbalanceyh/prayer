package com.prayer.plugin.ibatis;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Accessors;
import com.prayer.constant.Resources;
import com.prayer.exception.database.MetadataNotSupportException;
import com.prayer.facade.constant.DBConstants;
import com.prayer.facade.pool.JdbcPool;
import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * 将iBatis的连接池连接到BoneCP的连接池中，目前支持的JDBC数据库就H2，在整个Prayer中，只有元数据才会连接到iBatis
 * 
 * @author Lang
 *
 */
public class PEDataSourceFactory implements DataSourceFactory {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEDataSourceFactory.class);
    /** 支持的Database，SQL类型的Metadata Database **/
    private static final String[] DB_SUPPORTED = new String[] { DBConstants.CATEGORY_H2 };
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
                /**
                 * 传入DB_POOL实现类，以及Metadata Database的类型
                 */
                final JdbcPool pool = singleton(Accessors.pool(), Resources.META_CATEGORY);
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
