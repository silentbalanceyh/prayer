package com.prayer.schema.db;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.info;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.facade.mapper.VerticleMapper;
import com.prayer.model.h2.vertx.VerticleModel;
import com.prayer.util.cv.Resources;

/**
 * 
 * @author Lang
 *
 */
public class VerticleMapperTestCase extends AbstractMapperCase<VerticleModel, String> { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VerticleMapperTestCase.class);

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
        return VerticleMapper.class;
    }

    /** **/
    @Override
    public VerticleModel instance() {
        return new VerticleModel();
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testSelectByName() {
        final VerticleMapper mapper = (VerticleMapper) this.getMapper();
        final VerticleModel entity = this.insertTs(false).get(0);
        // 插入一条新数据
        final String name = entity.getName();
        // 从数据库中读取数据
        final VerticleModel targetT = mapper.selectByName(name);
        info(getLogger(), "[TD] (SelectByName) Selected by name = " + name);
        assertEquals("[E] (SelectByName) Entity in database must be the same as inserted one !", entity, targetT);
        // 删除插入的数据
        mapper.deleteById(entity.getUniqueId());
        this.session().commit();
    }

    /** **/
    @Test
    public void testSelectByGroup() {
        final VerticleMapper mapper = (VerticleMapper) this.getMapper();
        final List<VerticleModel> entities = this.getTs(null);
        // 重新设置Group值，前边17个整体值
        for (int idx = 0; idx < 17; idx++) {
            entities.get(idx).setGroup("TARGET_GROUP");
        }
        mapper.batchInsert(entities);
        this.session().commit();
        // 从数据库中读取17条TARGET_GROUP数据
        final List<VerticleModel> targetTs = mapper.selectByGroup("TARGET_GROUP");
        info(getLogger(), "[TD] (SelectByGroup) Selected by group = TARGET_GROUP, Size = " + targetTs.size());
        assertEquals("[E] (SelectByGroup) Selected size must be the same as expected !", 17, targetTs.size());
        // 删除插入的数据
        final List<String> ids = new ArrayList<>();
        for (final VerticleModel entity : entities) {
            ids.add(entity.getUniqueId());
        }
        mapper.batchDelete(ids);
        this.session().commit();
    }

    /** **/
    @Test
    public void testDeleteByName() {
        final VerticleMapper mapper = (VerticleMapper) this.getMapper();
        final VerticleModel entity = this.insertTs(false).get(0);
        // 插入一条新数据
        final String name = entity.getName();
        // 从数据库中读取数据
        mapper.deleteByName(name);
        this.session().commit();

        final VerticleModel targetT = mapper.selectByName(name);
        info(getLogger(), "[TD] (DeleteByName) Selected by name = " + name);
        assertNull("[E] (DeleteByName) Entity in database has been deleted successfully!", targetT);
    }
    // ~ OOB Deployment ======================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
