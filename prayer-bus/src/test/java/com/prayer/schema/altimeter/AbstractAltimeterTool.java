package com.prayer.schema.altimeter;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.fail;

import org.slf4j.Logger;

import com.prayer.builder.MetadataBuilder;
import com.prayer.constant.Accessors;
import com.prayer.constant.Resources;
import com.prayer.dao.impl.schema.CommuneImporter;
import com.prayer.dao.impl.schema.SchemaDalor;
import com.prayer.database.pool.impl.jdbc.JdbcConnImpl;
import com.prayer.facade.builder.Builder;
import com.prayer.facade.constant.DBConstants;
import com.prayer.facade.dao.schema.Importer;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.Altimeter;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.exception.AbstractTransactionException;
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
public abstract class AbstractAltimeterTool {
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
    /** 对于后期验证重要 **/
    @NotNull
    private transient Builder builder;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public AbstractAltimeterTool() {
        this.importer = singleton(CommuneImporter.class);
        this.dao = singleton(SchemaDalor.class);
        this.builder = singleton(MetadataBuilder.class);
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
        return reservoir(Resources.DB_CATEGORY, Accessors.validator());
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
            /** 3.Prepared最后一步是刷Schema到Database中 **/
            if (null != this.validator().verifyTable(schema.getTable())) {
                this.builder.synchronize(schema);
            }
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
        String table = null;
        String identifier = null;
        try {
            /** 1.读取Schema信息 **/
            final Schema schema = importer.read(dataFile);
            if (null != schema) {
                table = schema.getTable();
                identifier = schema.identifier();
                /** 2.执行更新验证流程 **/
                final Altimeter altimeter = singleton(SchemaAltimeter.class, this.dao);
                /** 3.验证 **/
                altimeter.verify(schema);
            }
        } catch (AbstractException ex) {
            peError(getLogger(), ex);
            // 抛出异常之前就直接删除表信息
            {
                this.purgeTable(table);
                try {
                    this.dao.delete(identifier);
                    // 特殊的验证ID，准备数据中读取的
                    this.dao.delete("tst.mod.dao101");
                } catch (AbstractTransactionException ei) {
                    peError(getLogger(), ei);
                }
            }
            if (ex instanceof AbstractSchemaException) {
                throw (AbstractSchemaException) ex;
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
