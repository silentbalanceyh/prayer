package com.prayer.vertx.config;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.business.configuration.impl.ConfigBllor;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.Intaker;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PEVerticle;

import io.vertx.core.DeploymentOptions;
import jodd.util.StringUtil;

/**
 * 【不可用单件模式】直接从H2读取数据
 * 
 * @author Lang
 *
 */
public class DeployOptsIntaker implements Intaker<ConcurrentMap<String, DeploymentOptions>> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient ConfigInstantor instantor = singleton(ConfigBllor.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public ConcurrentMap<String, DeploymentOptions> ingest() throws AbstractException {
        /** 1.从系统中读取verticles **/
        final ConcurrentMap<String, PEVerticle> verticles = instantor.verticles();
        /** 2.生成发布选项 **/
        final ConcurrentMap<String, DeploymentOptions> ret = new ConcurrentHashMap<>();
        for (final String name : verticles.keySet()) {
            ret.put(name, this.buildOpts(verticles.get(name)));
        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private DeploymentOptions buildOpts(final PEVerticle rawData) {
        final DeploymentOptions retOpts = new DeploymentOptions();
        // 1.Group的划分，对象构造就会有Group信息，所以rawData的group不可能为空
        if (!StringUtil.equals(Constants.VX_GROUP, rawData.getGroup())) {
            retOpts.setIsolationGroup(rawData.getGroup());
            retOpts.setIsolatedClasses(rawData.getIsolatedClasses());
        }
        // 2.设置额外的ClassPath
        retOpts.setExtraClasspath(rawData.getExtraCp());
        // 3.设置扩展属性
        retOpts.setConfig(rawData.getJsonConfig());
        // 4.设置基本属性
        retOpts.setInstances(rawData.getInstances());
        retOpts.setHa(rawData.isHa());
        retOpts.setWorker(rawData.isWorker());
        retOpts.setMultiThreaded(rawData.isMulti());
        return retOpts;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
