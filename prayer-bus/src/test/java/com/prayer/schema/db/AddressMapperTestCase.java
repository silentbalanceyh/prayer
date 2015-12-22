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
import com.prayer.facade.mapper.AddressMapper;
import com.prayer.model.h2.vertx.AddressModel;
import com.prayer.util.cv.Resources;

/**
 * 
 * @author Lang
 *
 */
public class AddressMapperTestCase extends AbstractMapperCase<AddressModel, String> { // NOPMD{
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressMapperTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
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

    // ~ Static Methods ======================================
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
        return AddressMapper.class;
    }

    /** **/
    @Override
    public AddressModel instance() {
        return new AddressModel();
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testSelectByClass() {
        final AddressMapper mapper = (AddressMapper) this.getMapper();
        final AddressModel entity = this.insertTs(false).get(0);
        // 插入一条心数据
        final String workClass = entity.getWorkClass();
        // 重新从数据库中读取数据
        final AddressModel targetT = mapper.selectByClass(workClass);
        info(getLogger(), "[TD] (SelectByClass) Selected by workClass = " + workClass);
        assertEquals("[E] (SelectByClass) Entity in database must be the same as inserted one !", entity, targetT);
        // 删除插入的新数据
        mapper.deleteById(targetT.getUniqueId());
        this.session().commit();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
