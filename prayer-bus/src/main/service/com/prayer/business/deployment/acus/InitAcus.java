package com.prayer.business.deployment.acus;

import static com.prayer.util.debug.Log.info;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.constant.log.InfoKey;
import com.prayer.exception.database.DataAccessException;
import com.prayer.exception.database.OperationNotSupportException;
import com.prayer.facade.business.instantor.deployment.acus.DeployAcus;
import com.prayer.facade.constant.Constants.EXTENSION;
import com.prayer.facade.constant.Symbol;
import com.prayer.fantasm.business.deployment.acus.AbstractEntityAcus;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.database.PEMeta;

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
public class InitAcus extends AbstractEntityAcus implements DeployAcus {
    // ~ Static Fields =======================================
    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(InitAcus.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
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
        info(LOGGER, InfoKey.INF_META_INIT, dftFile);
        /** 3.这里调用的是initialize，所以传入哪个Entity都无所谓，所以传入最不容易变化的Meta **/
        final boolean exeRet = this.accessor(PEMeta.class).initialize(dftFile);
        if (!exeRet) {
            throw new DataAccessException(getClass(), "Metadata Initialize : Sql = " + dftFile);
        }
        return exeRet;
    }

    /**
     * 删除数据接口，对于SQL类型不可支持
     */
    @Override
    public boolean purge() throws AbstractException {
        throw new OperationNotSupportException(getClass(), "InitAcus -> purge()");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
