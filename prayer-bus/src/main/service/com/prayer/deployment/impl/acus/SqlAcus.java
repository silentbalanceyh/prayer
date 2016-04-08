package com.prayer.deployment.impl.acus;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Accessors;
import com.prayer.constant.Resources;
import com.prayer.constant.log.InfoKey;
import com.prayer.exception.database.DataAccessException;
import com.prayer.exception.database.OperationNotSupportException;
import com.prayer.facade.constant.Constants.EXTENSION;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.deployment.acus.DeployAcus;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.ServiceResult;
import com.prayer.util.io.IOKit;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 1.执行初始化脚本的发布器
 * 
 * @author Lang
 *
 */
@Guarded
public class SqlAcus implements DeployAcus {
    // ~ Static Fields =======================================
    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlAcus.class);
    // ~ Instance Fields =====================================
    /** SQL才会使用的内容 **/
    @NotNull
    @InstanceOf(JdbcConnection.class)
    private transient final JdbcConnection connection; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /**
     * 
     */
    public SqlAcus() {
        this.connection = singleton(Accessors.connection());
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 发布接口
     * 
     * @param filename
     */
    @Override
    public boolean deploy(@NotNull @NotEmpty @NotBlank final String filename) throws AbstractException {
        /** 1.文件名计算 **/
        String dftFile = null;
        if (filename.toLowerCase(Locale.getDefault()).endsWith(".sql")) {
            dftFile = Resources.OOB_DATA_FOLDER + "/sql/" + filename;
        } else {
            dftFile = Resources.OOB_DATA_FOLDER + "/sql/" + filename + Symbol.DOT + EXTENSION.SQL;
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
        return ret.getResult();
    }

    /**
     * 删除数据接口，对于SQL类型不可支持
     */
    @Override
    public boolean purge() throws AbstractException {
        throw new OperationNotSupportException(getClass(), "SqlAcus -> purge()");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
