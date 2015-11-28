package com.prayer.schema.db;

import static com.prayer.util.Instance.singleton;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.facade.mapper.RuleMapper;
import com.prayer.model.vertx.RuleModel;
import com.prayer.util.cv.Resources;

/**
 * 
 * @author Lang
 *
 */
public class RuleMapperTestCase extends AbstractMapperCase<RuleModel, String> { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleMapperTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     */
    @BeforeClass        // NOPMD
    public static void setUp(){
        /** **/
        final MetadataConn metaConn = singleton(MetadataConnImpl.class);
        final String scriptFile = Resources.DB_SQL_DIR + MetadataConn.H2_SQL;
        metaConn.initMeta(Resources.class.getResourceAsStream(scriptFile));
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** **/
    @Override
    public Class<?> getMapperClass() {
        return RuleMapper.class;
    }

    /** **/
    @Override
    public RuleModel instance() {
        return new RuleModel();
    }

    /** **/
    @Override
    public String[] filterFields() {
        return new String[] { "refUriId" };
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
