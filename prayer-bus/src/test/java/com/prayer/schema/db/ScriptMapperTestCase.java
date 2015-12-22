package com.prayer.schema.db;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.info;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.facade.mapper.ScriptMapper;
import com.prayer.model.h2.vertx.ScriptModel;
import com.prayer.util.cv.Resources;

/**
 * Uri的特殊测试用例
 * 
 * @author Lang
 *
 */
public class ScriptMapperTestCase extends AbstractMapperCase<ScriptModel, String> { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptMapperTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     */
    @BeforeClass // NOPMD
    public static void setUp() {
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
        return ScriptMapper.class;
    }

    /** **/
    @Override
    public ScriptModel instance() {
        return new ScriptModel();
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testSelectByName() {
        final ScriptMapper mapper = (ScriptMapper) this.getMapper();
        final ScriptModel entity = this.insertTs(false).get(0);
        // 插入一条心数据
        final String name = entity.getName();
        // 重新从数据库中读取数据
        final ScriptModel targetT = mapper.selectByName(name);
        info(getLogger(), "[TD] (SelectByName) Selected by name = " + name);
        assertEquals("[E] (SelectByName) Entity in database must be the same as inserted one !", entity, targetT);
        // 删除插入的新数据
        mapper.deleteById(targetT.getUniqueId());
        this.session().commit();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
