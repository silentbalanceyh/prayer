package com.prayer.db.conn.impl;

import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.i.jdbc.MetadataConn;
import com.prayer.model.bus.Metadata;
import com.prayer.util.DatabaseKit;
import com.prayer.util.cv.Resources;

import jodd.util.StringPool;

/**
 * 
 *
 * @author Lang
 * @see
 */
public class MetadataTestCase {     // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MetadataTestCase.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient final MetadataConn metaConn = singleton("com.prayer.dao.impl.jdbc.MetadataConnImpl");

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Test
    public void testMetadata() {
        final Metadata metadata = this.metaConn.getMetadata();
        assertNotNull("[T] Metadata is null.", metadata);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[TD] Metadata = " + metadata);
        }
    }

    /** **/
    @Test
    public void testVersionFlag() {
        final Metadata metadata = this.metaConn.getMetadata();
        final String versionFlag = DatabaseKit.getDatabaseVersion(metadata.getProductName(),metadata
                .getProductVersion());
        assertEquals("[T] Version flag is not match.", versionFlag,
                metadata.getVersionFlag());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[TD] Version flag = " + metadata.getVersionFlag());
        }
    }

    /** **/
    @Test
    public void testSqlFile() {
        final Metadata metadata = this.metaConn.getMetadata();
        final String versionFlag = DatabaseKit.getDatabaseVersion(metadata.getProductName(),metadata
                .getProductVersion());
        assertEquals("[T] Sql filename is not match.",
                metadata.getDatabaseCategory() + versionFlag,
                metadata.getSqlFile());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[TD] Sql filename = " + metadata.getSqlFile());
        }
    }
    /**
     * 
     */
    @Test
    public void testSqlExec(){
        final Metadata metadata = this.metaConn.getMetadata();
        final String scriptFile = Resources.DB_SQL_DIR + metadata.getSqlFile() + StringPool.DOT + "sql";
        final boolean ret = this.metaConn.loadSqlFile(Resources.class.getResourceAsStream(scriptFile));
        assertTrue("[T] Executed failure.",ret);
    }
    /**
     * 
     */
    @Test
    public void testH2Init(){
        final String scriptFile = Resources.DB_SQL_DIR + MetadataConn.H2_SQL;
        final boolean ret = this.metaConn.initMeta(Resources.class.getResourceAsStream(scriptFile));
        assertTrue("[T] Executed meta init failure.",ret);
    }
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================
}
