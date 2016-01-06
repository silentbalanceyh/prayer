package com.prayer.schema.db;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.debug.Log.info;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.facade.mapper.UriMapper;
import com.prayer.model.vertx.UriModel;

/**
 * Uri的特殊测试用例
 * @author Lang
 *
 */
public class UriMapperTestCase extends AbstractMapperCase<UriModel, String>{    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UriMapperTestCase.class);
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
        final String scriptFile = Resources.META_INIT_SQL;
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
        return UriMapper.class;
    }

    /** **/
    @Override
    public UriModel instance() {
        return new UriModel();
    }
    // ~ Methods =============================================
    /** **/
    @Test
    public void testSelectByUri(){
        final UriMapper mapper = (UriMapper) this.getMapper();
        final UriModel entity = this.insertTs(false).get(0);
        // 插入一条心数据
        final String uri = entity.getUri();
        // 重新从数据库中读取数据
        final UriModel targetT = mapper.selectByUriAndMethod(uri,entity.getMethod());
        info(getLogger(), "[TD] (SelectByUri) Selected by uri = " + uri);
        assertEquals("[E] (SelectByUri) Entity in database must be the same as inserted one !", entity, targetT);
        // 删除插入的新数据
        mapper.deleteById(targetT.getUniqueId());
        this.session().commit();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
