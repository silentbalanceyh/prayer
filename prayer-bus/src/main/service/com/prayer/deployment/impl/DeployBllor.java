package com.prayer.deployment.impl;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SystemEnum.Acus;
import com.prayer.facade.deployment.DeployService;
import com.prayer.facade.deployment.SchemaService;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.ServiceResult;

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
    /** **/
    @NotNull
    private transient final AcusSelector selector; // NOPMD
    /** **/
    @NotNull
    private transient final SchemaService service; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /**
     * 发布用的连接初始化
     */
    @PostValidateThis
    public DeployBllor() {
        this.selector = singleton(AcusSelector.class);
        this.service = singleton(SchemaBllor.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Override
    public ServiceResult<Boolean> initialize(@NotNull @NotBlank @NotEmpty final String sqlfile) {
        final ServiceResult<Boolean> ret = new ServiceResult<>();
        try {
            this.selector.selectors(Acus.SQL).deploy(sqlfile, null);
            ret.success(Boolean.TRUE);
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
            ret.failure(ex);
        }
        return ret;
    }

    /** **/
    @Override
    public ServiceResult<Boolean> manoeuvre(@NotNull @NotBlank @NotEmpty final String folder) {
        final ServiceResult<Boolean> ret = new ServiceResult<>();
        try {
            /** 1.执行数据库Schema部分的Deploy **/
            this.selector.selectors(Acus.SCHEMA).deploy(folder, null);

            ret.success(Boolean.TRUE);
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
            ret.failure(ex);
        }
        return ret;
    }

    /** **/
    @Override
    public ServiceResult<Boolean> purge() {
        ServiceResult<Boolean> ret = new ServiceResult<>();
        /** 1.执行数据库Schema部分的Purege **/
        ret = this.service.purge();
        
        return ret;
    }
    // ~ Private Methods =====================================

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
