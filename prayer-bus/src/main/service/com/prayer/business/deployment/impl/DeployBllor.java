package com.prayer.business.deployment.impl;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SystemEnum.Acus;
import com.prayer.facade.business.instantor.deployment.DeployInstantor;
import com.prayer.fantasm.exception.AbstractException;

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
public class DeployBllor implements DeployInstantor {
    // ~ Static Fields =======================================
    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployBllor.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final AcusSelector selector; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /**
     * 发布用的连接初始化
     */
    @PostValidateThis
    public DeployBllor() {
        this.selector = singleton(AcusSelector.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Override
    public boolean initialize(@NotNull @NotBlank @NotEmpty final String sqlfile) throws AbstractException {
        this.selector.selectors(Acus.INIT).deploy(sqlfile);
        return true;
    }

    /** **/
    @Override
    public boolean manoeuvre(@NotNull @NotBlank @NotEmpty final String folder, final boolean schema)
            throws AbstractException {
        /** 1.执行数据库Schema部分的Deploy **/
        if (schema) {
            this.selector.selectors(Acus.SCHEMA).deploy(folder);
        }
        /** 2.执行Vertx的Deploy **/
        this.selector.selectors(Acus.VERTX).deploy(folder);
        /** 3.执行Uri的Deploy **/
        this.selector.selectors(Acus.URI).deploy(folder);
        info(LOGGER, "[DP] Final -> All metadata have been deployed successfully !");
        return true;
    }

    /** **/
    @Override
    public boolean purge(final boolean schema) throws AbstractException {
        /** 1.执行数据库Schema部分的Purge **/
        if (schema) {
            this.selector.selectors(Acus.SCHEMA).purge();
        }
        /** 2.执行Vertx的Purge **/
        this.selector.selectors(Acus.VERTX).purge();
        /** 3.执行Script的Purge **/
        this.selector.selectors(Acus.SCRIPT).purge();
        /** 4.执行Uri的Purge **/
        this.selector.selectors(Acus.URI).purge();
        info(LOGGER, "[PG] Final -> All metadata have been purged successfully !");
        return true;
    }
    // ~ Private Methods =====================================

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
