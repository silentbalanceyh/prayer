package com.prayer.schema.db;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.info;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.facade.mapper.MetaMapper;
import com.prayer.model.h2.schema.MetaModel;
import com.prayer.util.cv.Resources;

/**
 * 
 * @author Lang
 *
 */
public class MetaMapperTestCase extends AbstractMapperCase<MetaModel, String> { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaMapperTestCase.class);

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
        return MetaMapper.class;
    }

    /** **/
    @Override
    public MetaModel instance() {
        return new MetaModel();
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testSelectByModel() {
        final MetaMapper mapper = (MetaMapper) this.getMapper();
        final MetaModel entity = this.insertTs(false).get(0);
        // 插入一条新数据
        final String namespace = entity.getNamespace();
        final String name = entity.getName();
        // 重新从数据库中读取数据
        final MetaModel targetT = mapper.selectByModel(namespace, name);
        info(getLogger(), "[TD] (SelectByModel) Selected by namespace = " + namespace + ",name = " + name);
        assertEquals("[E] (SelectByModel) Entity in database must be the same as inserted one !", entity, targetT);
        // 删除插入的数据
        mapper.deleteById(targetT.getUniqueId());
        this.session().commit();
    }

    /** **/
    @Test
    public void testSelectByGlobalId() {
        final MetaMapper mapper = (MetaMapper) this.getMapper();
        final MetaModel entity = this.insertTs(false).get(0);
        // 插入一条新数据
        final String globalId = entity.getGlobalId();
        // 重新从数据库中读取数据
        final MetaModel targetT = mapper.selectByGlobalId(globalId);
        info(getLogger(), "[TD] (SelectByGlobalId) Selected by globalId = " + globalId);
        assertEquals("[E] (SelectByGlobalId) Entity in database must be the same as inserted one !", entity, targetT);
        // 删除插入的数据
        mapper.deleteById(targetT.getUniqueId());
        this.session().commit();
    }

    /** **/
    @Test
    public void testDeleteByModel() {
        final MetaMapper mapper = (MetaMapper) this.getMapper();
        final MetaModel entity = this.insertTs(false).get(0);
        // 插入一条新数据
        final String namespace = entity.getNamespace();
        final String name = entity.getName();
        mapper.deleteByModel(namespace, name);
        this.session().commit();
        // 重新从数据库中读取数据
        final MetaModel targetT = mapper.selectByModel(namespace, name);
        assertNull("[E] (DeleteByModel) Entity in database has been deleted by model successfully !", targetT);
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
