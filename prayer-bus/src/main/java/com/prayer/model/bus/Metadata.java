package com.prayer.model.bus;

import static com.prayer.util.Error.debug;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.util.DatabaseKit;

/**
 * 数据库元数据
 *
 * @author Lang
 * @see
 */
public class Metadata { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Metadata.class);
    // ~ Instance Fields =====================================
    /** 数据库产品名 **/
    private String productName;
    /** 数据库版本 **/
    private String productVersion;
    /** JDBC Driver名 **/
    private String driverName;
    /** JDBC Driver版本 **/
    private String driverVersion;
    /** 数据库连接用户名 **/
    private String username;
    /** 数据库种类 **/
    private String databaseCategory;
    /** 数据库初始化SQL文件路径 **/
    private String sqlFile;
    /** 数据库版本的Flag，类似：Oracle -> 10G,11R, SQL Server -> 2005, 2008 **/
    private String versionFlag;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param metadata
     */
    public Metadata(final DatabaseMetaData metadata, final String category) {
        try {
            this.productName = metadata.getDatabaseProductName();
            this.productVersion = metadata.getDatabaseProductVersion();
            this.driverName = metadata.getDriverName();
            this.driverVersion = metadata.getDriverVersion();
            this.username = metadata.getUserName();
            this.databaseCategory = category;
            this.initSqlFile();
        } catch (SQLException ex) {
            debug(LOGGER, "JVM.SQL", "public Metadata(DatabaseMetaData,String)", ex);
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Bean Get/Set ========================================
    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public void setProductName(final String productName) {
        this.productName = productName;
    }

    /**
     * @return the productVersion
     */
    public String getProductVersion() {
        return productVersion;
    }

    /**
     * @param productVersion
     *            the productVersion to set
     */
    public void setProductVersion(final String productVersion) {
        this.productVersion = productVersion;
    }

    /**
     * @return the driverName
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * @param driverName
     *            the driverName to set
     */
    public void setDriverName(final String driverName) {
        this.driverName = driverName;
    }

    /**
     * @return the driverVersion
     */
    public String getDriverVersion() {
        return driverVersion;
    }

    /**
     * @param driverVersion
     *            the driverVersion to set
     */
    public void setDriverVersion(final String driverVersion) {
        this.driverVersion = driverVersion;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the databaseCategory
     */
    public String getDatabaseCategory() {
        return databaseCategory;
    }

    /**
     * @param databaseCategory
     *            the databaseCategory to set
     */
    public void setDatabaseCategory(final String databaseCategory) {
        this.databaseCategory = databaseCategory;
    }

    /**
     * @return the initSqlFile
     */
    public String getSqlFile() {
        return sqlFile;
    }

    /**
     * @param initSqlFile
     *            the initSqlFile to set
     */
    public void setSqlFile(final String sqlFile) {
        this.sqlFile = sqlFile;
    }

    /**
     * @return the versionFlag
     */
    public String getVersionFlag() {
        return versionFlag;
    }

    /**
     * @param versionFlag
     *            the versionFlag to set
     */
    public void setVersionFlag(final String versionFlag) {
        this.versionFlag = versionFlag;
    }

    // ~ Private Methods =====================================

    private void initSqlFile() {
        this.versionFlag = DatabaseKit.getDatabaseVersion(this.productName, this.productVersion);
        this.sqlFile = this.databaseCategory + this.versionFlag;
    }

    // ~ hashCode,equals,toString ============================

    /**
     * 
     */
    @Override
    public String toString() {
        return "Metadata [productName=" + productName + ", productVersion=" + productVersion + ", driverName="
                + driverName + ", driverVersion=" + driverVersion + ", username=" + username + ", databaseCategory="
                + databaseCategory + ", initSqlFile=" + sqlFile + ", versionFlag=" + versionFlag + "]";
    }

    /** **/
    @Override
    public int hashCode() {
        final int prime = Constants.HASH_BASE;
        int result = 1;
        result = prime * result + ((databaseCategory == null) ? 0 : databaseCategory.hashCode());
        result = prime * result + ((driverName == null) ? 0 : driverName.hashCode());
        result = prime * result + ((productName == null) ? 0 : productName.hashCode());
        return result;
    }

    /** **/
    @Override
    public boolean equals(final Object obj) { // NOPMD
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Metadata other = (Metadata) obj;
        if (databaseCategory == null) {
            if (other.databaseCategory != null) {
                return false;
            }
        } else if (!databaseCategory.equals(other.databaseCategory)) {
            return false;
        }
        if (driverName == null) {
            if (other.driverName != null) {
                return false;
            }
        } else if (!driverName.equals(other.driverName)) {
            return false;
        }
        if (productName == null) {
            if (other.productName != null) {
                return false;
            }
        } else if (!productName.equals(other.productName)) {
            return false;
        }
        return true;
    }

}
