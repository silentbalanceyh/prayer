package com.prayer.business.impl.deployment;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.Locale;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Accessors;
import com.prayer.constant.Constants.EXTENSION;
import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.constant.log.InfoKey;
import com.prayer.exception.database.DataAccessException;
import com.prayer.facade.business.deployment.DeployService;
import com.prayer.facade.business.deployment.SchemaService;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.ServiceResult;
import com.prayer.util.io.IOKit;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class DeployBllor implements DeployService {
    // ~ Static Fields =======================================
    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployBllor.class);
    // ~ Instance Fields =====================================
    /** SQL才会使用的内容 **/
    @NotNull
    @InstanceOf(JdbcConnection.class)
    private transient final JdbcConnection connection; // NOPMD
    /** 主要用于Deploy **/
    @NotNull
    private transient final MetadataDeployer deployer; // NOPMD
    /** Schema Service **/
    @NotNull
    private transient final SchemaService service;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /**
     * 发布用的连接初始化
     */
    @PostValidateThis
    public DeployBllor() {
        this.connection = singleton(Accessors.connection());
        this.deployer = singleton(MetadataDeployer.class);
        this.service = singleton(SchemaBllor.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Override
    public ServiceResult<Boolean> initialize(@NotNull @NotBlank @NotEmpty final String sqlfile) {
        /** 1.文件名计算 **/
        String dftFile = null;
        if (sqlfile.toLowerCase(Locale.getDefault()).endsWith(".sql")) {
            dftFile = Resources.OOB_DATA_FOLDER + "/sql/" + sqlfile;
        } else {
            dftFile = Resources.OOB_DATA_FOLDER + "/sql/" + sqlfile + Symbol.DOT + EXTENSION.SQL;
        }
        /** 2.使用Reader **/
        final ServiceResult<Boolean> ret = new ServiceResult<>();
        info(LOGGER, InfoKey.INF_META_INIT, dftFile);
        final boolean exeRet = this.connection.executeSql(IOKit.getFile(dftFile));
        if (exeRet) {
            ret.success(exeRet);
        } else {
            ret.failure(new DataAccessException(getClass(), "Metadata Initialize : Sql = " + dftFile));
        }
        return ret;
    }

    /** **/
    @Override
    public ServiceResult<Boolean> manoeuvre(@NotNull @NotBlank @NotEmpty final String folder) {
        final ServiceResult<Boolean> ret = new ServiceResult<>();
        try {
            /** 1.执行数据库Schema部分的Deploy **/
            this.deploySchema(folder);
        } catch (AbstractException ex) {
            ret.failure(ex);
        }
        return ret;
    }

    /** **/
    @Override
    public ServiceResult<Boolean> purge() {
        ServiceResult<Boolean> ret = new ServiceResult<>();
        try {
            /** 1.执行数据库Schema部分的Purege **/
            ret = this.service.purge();
            if (ret.success()) {

            } else {
                throw ret.getServiceError();
            }
        } catch (AbstractException ex) {
            ret.failure(ex);
        }
        return ret;
    }
    // ~ Private Methods =====================================

    private void deploySchema(final String folder) throws AbstractException {
        /** 0.文件目录准备 **/
        final String dftFile = folder + "/schema";
        /** 1.构建Schema文件的顺序 **/
        final ConcurrentMap<Integer, String> ordMap = this.deployer.buildOrdMap(dftFile);
        /** 2.创建顺序按照从大到小 **/
        final int size = ordMap.size();
        for (int idx = size; idx > 0; idx--) {
            /** 3.根据文件名读取Schema **/
            final String file = ordMap.get(idx);
            /** 4.同步当前Schema **/
            this.importSchema(file);
        }
        info(LOGGER, "[I] Schema deployment executed successfully under : " + dftFile);
    }

    private void importSchema(final String file) throws AbstractException {
        /** 1.从文件导入Schema **/
        ServiceResult<Schema> schema = this.service.importSchema(file);
        if (schema.success()) {
            /** 3.执行业务数据库同步 **/
            schema = this.service.syncMetadata(schema.getResult());
        }
        /** 4.执行完成过后有Error **/
        if (!schema.success()) {
            throw schema.getServiceError();
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
