package com.prayer.fantasm.dao.database;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.constant.Resources;
import com.prayer.database.pool.impl.jdbc.JdbcConnImpl;
import com.prayer.facade.dao.DatabaseDao;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.model.business.Metadata;
import com.prayer.util.jdbc.DatabaseKit;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;

/**
 * Metadata元数据信息
 * @author Lang
 *
 */
public abstract class AbstractDatabaseDao implements DatabaseDao {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    @NotNull
    @InstanceOf(JdbcConnection.class)
    private transient final JdbcConnection connection; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    
    public AbstractDatabaseDao(){
        this.connection = singleton(JdbcConnImpl.class);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 通过Discovery分析出来的数据库的文件名，其他所有特殊数据库文件名由此而来
     */
    @Override
    public String getFile() {
        final Metadata metadata = this.connection.getMetadata(Resources.DB_CATEGORY);
        String retFile = DatabaseKit.getDatabaseVersion(metadata.getProductName(), metadata.getProductVersion());
        return Resources.DB_CATEGORY + retFile;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
