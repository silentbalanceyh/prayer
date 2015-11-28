package com.prayer.schema.db;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.facade.mapper.RouteMapper;
import com.prayer.model.vertx.RouteModel;
import com.prayer.util.cv.Resources;

/**
 * 
 * @author Lang
 *
 */
public class RouteMapperTestCase extends AbstractMapperCase<RouteModel, String> { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteMapperTestCase.class);

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
        return RouteMapper.class;
    }

    /** **/
    @Override
    public RouteModel instance() {
        return new RouteModel();
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testSelectByPath() {
        final RouteMapper mapper = (RouteMapper) this.getMapper();
        final RouteModel entity = this.insertTs(false).get(0);
        // 插入一条心数据
        final String parent = entity.getParent();
        final String path = entity.getPath();
        // 重新从数据库中读取数据
        final RouteModel targetT = mapper.selectByPath(parent, path);
        info(getLogger(), "[TD] (SelectByPath) Selected by parent = " + parent + ", path = " + path);
        assertEquals("[E] (SelectByPath) Entity in database must be the same as inserted one !", entity, targetT);
        // 删除插入的新数据
        mapper.deleteById(targetT.getUniqueId());
        this.session().commit();
    }

    /** **/
    @Test
    public void testSelectByParent() {
        final RouteMapper mapper = (RouteMapper) this.getMapper();
        final List<RouteModel> entities = this.getTs(null);
        // 重新设置父路径
        for (int idx = 0; idx < 15; idx++) {
            entities.get(idx).setParent("/auth");
        }
        mapper.batchInsert(entities);
        this.session().commit();
        // 从数据库中读取15条数据
        final List<RouteModel> targetTs = mapper.selectByParent("/auth");
        info(getLogger(), "[TD] (SelectByParent) Selected by parent = /auth, Size = " + targetTs.size());
        final boolean flag = 15 <= targetTs.size();
        assertTrue("[E] (SelectByParent) Selected size must be the same as expected !", flag);
        // 删除插入的数据
        final List<String> ids = new ArrayList<>();
        for (final RouteModel entity : entities) {
            ids.add(entity.getUniqueId());
        }
        mapper.batchDelete(ids);
        this.session().commit();
    }

    // ~ OOB Deployment ======================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
