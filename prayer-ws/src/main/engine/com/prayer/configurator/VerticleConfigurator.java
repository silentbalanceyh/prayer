package com.prayer.configurator;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.debug.Log.error;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.impl.oob.ConfigSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.bus.ConfigService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.vertx.VerticleModel;

import io.vertx.core.DeploymentOptions;
import jodd.util.StringUtil;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class VerticleConfigurator {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VerticleConfigurator.class);
    /** 系统核心缓存，因为这个类只会在配置时使用，则这个变量保存了H2中所有的信息 **/
    private static final ConcurrentMap<String, List<VerticleModel>> DATA_MAP = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    /** 访问H2元数据的业务逻辑层 **/
    @NotNull
    private transient final ConfigService service;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 公共构造函数 **/
    @PostValidateThis
    public VerticleConfigurator() {
        this.service = singleton(ConfigSevImpl.class);
        this.initDataMap();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    /**
     * 从H2中读取所有的发布数据：异步Queue
     * 
     * @return
     */
    @PreValidateThis
    public Map<String, DeploymentOptions> readConfig() {
        // 1.加载缓存
        this.initDataMap();
        // 2.读取结果
        final Map<String, DeploymentOptions> retMap = new LinkedHashMap<>(); // NOPMD
        for (final String key : DATA_MAP.keySet()) {
            retMap.putAll(this.getConfig(DATA_MAP.get(key)));
        }
        return retMap;
    }

    /**
     * 从H2中按照Group名称读取某个Group下的发布数据
     */
    @PreValidateThis
    public Map<String, DeploymentOptions> readConfig(@NotNull @NotEmpty @NotBlank final String group) {
        // 1.加载缓存
        this.initDataMap();
        // 2.读取结果
        final Map<String, DeploymentOptions> retMap = new LinkedHashMap<>(); // NOPMD
        retMap.putAll(this.getConfig(DATA_MAP.get(group)));
        return retMap;
    }
    // ~ Private Methods =====================================

    private void initDataMap() {
        if (DATA_MAP.isEmpty()) {
            final ServiceResult<ConcurrentMap<String, List<VerticleModel>>> result = this.service.findVerticles();
            if (ResponseCode.SUCCESS == result.getResponseCode()) {
                DATA_MAP.putAll(result.getResult());
            } else {
                error(LOGGER, "There are some errors when reading deployment options from H2");
            }
        }
    }

    private Map<String, DeploymentOptions> getConfig(final List<VerticleModel> rawList) {
        final Map<String, DeploymentOptions> retMap = new LinkedHashMap<>(); // NOPMD
        for (final VerticleModel item : rawList) {
            retMap.put(item.getName().getName(), this.getOptions(item));
        }
        return retMap;
    }

    private DeploymentOptions getOptions(final VerticleModel rawData) {
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
