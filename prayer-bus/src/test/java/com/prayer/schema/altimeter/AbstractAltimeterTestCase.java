package com.prayer.schema.altimeter;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.fail;

import org.slf4j.Logger;

import com.prayer.constant.Accessors;
import com.prayer.constant.DBConstants;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.Resources;
import com.prayer.dao.impl.schema.CommuneImporter;
import com.prayer.dao.impl.schema.SchemaDaoImpl;
import com.prayer.database.pool.impl.jdbc.JdbcConnImpl;
import com.prayer.facade.dao.schema.Importer;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.Altimeter;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.schema.common.SchemaAltimeter;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractAltimeterTestCase {
    // ~ Static Fields =======================================
    /** Schema Root **/
    protected static final String SCHEMA_ROOT = "/schema/data/json/advanced/";
    // ~ Instance Fields =====================================
    /** 导入器 **/
    @NotNull
    private transient Importer importer;
    /** Schema数据访问层 **/
    @NotNull
    private transient SchemaDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public AbstractAltimeterTestCase() {
        this.importer = singleton(CommuneImporter.class);
        this.dao = singleton(SchemaDaoImpl.class);
    }

    // ~ Abstract Methods ====================================
    /** 获取日志器 **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected void executeAltimeter(final String caseFolder) throws AbstractSchemaException {
        // 1.准备数据
        boolean result = this.preparedData(caseFolder);
        // 2.准备数据成功后就可以执行二次验证了
        if (result) {
            this.altimeterVerify(caseFolder);
        }
    }

    /** 删除表 **/
    protected void purgeTable(final String table) {
        // TODO: 目前只有SQL模式才检查
        if (Resources.DB_MODE.equals(DBConstants.MODE_SQL)) {
            if (null == validator().verifyTable(table)) {
                this.connection().executeBatch("DROP TABLE " + table + ";");
            }
        }
    }

    /** **/
    protected DataValidator validator() {
        return reservoir(MemoryPool.POOL_VALIDATOR, Resources.DB_CATEGORY, Accessors.validator());
    }

    /** **/
    protected void failure(final Object object) {
        fail("[T] Failure assert : " + object);
    }

    /** **/
    protected JdbcConnection connection() {
        return singleton(JdbcConnImpl.class);
    }

    // ~ Private Methods =====================================
    /** **/
    private boolean preparedData(final String caseFolder) {
        final String dataFile = SCHEMA_ROOT + caseFolder + "/prepared.json";
        boolean result = false;
        try {
            /** 1.读取Schema信息，从Json到H2的准备 **/
            final Schema schema = importer.read(dataFile);
            /** 2.不执行Advanced验证导入H2 **/
            this.dao.save(schema);
            result = true;
        } catch (AbstractException ex) {
            peError(getLogger(), ex);
            result = false;
        }
        return result;
    }

    /** **/
    private void altimeterVerify(final String caseFolder) throws AbstractSchemaException {
        final String dataFile = SCHEMA_ROOT + caseFolder + "/data.json";
        try {
            /** 1.读取Schema信息 **/
            final Schema schema = importer.read(dataFile);
            if (null != schema) {
                /** 2.执行更新验证流程 **/
                final Altimeter altimeter = singleton(SchemaAltimeter.class, this.dao);
                /** 3.验证 **/
                altimeter.verify(schema);
            }
        } catch (AbstractException ex) {
            peError(getLogger(), ex);
            if (ex instanceof AbstractSchemaException) {
                throw (AbstractSchemaException) ex;
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}