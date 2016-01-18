package com.prayer.bus.impl.oob; // NOPMD

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.ComponentType;
import com.prayer.facade.bus.ConfigService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.meta.vertx.PEAddress;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEScript;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.model.meta.vertx.PEVerticle;
import com.prayer.util.bus.ResultExtractor;

import io.vertx.core.http.HttpMethod;
import net.sf.oval.constraint.InstanceOfAny;
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
public class ConfigSevImpl implements ConfigService {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 访问H2的EVX_VERTICLE接口 **/
    @NotNull
    private transient final ConfigDaoManager manager;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public ConfigSevImpl() {
        this.manager = singleton(ConfigDaoManager.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 读取的特殊方法，无异常信息抛出。 **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<List<PEVerticle>> findVerticles(@NotNull @NotBlank @NotEmpty final String group) {
        final ServiceResult<List<PEVerticle>> result = new ServiceResult<>();
        final List<PEVerticle> chain = this.manager.getVerticleDao().getByGroup(group);
        return result.success(chain);
    }

    /** 读取数据库中所有的VerticleModel配置信息 **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ConcurrentMap<String, List<PEVerticle>>> findVerticles() {
        // 1.构造响应数据
        final ServiceResult<ConcurrentMap<String, List<PEVerticle>>> result = new ServiceResult<>();
        // 2.读取所有的VerticleModel相关信息
        final List<PEVerticle> verticles = this.manager.getVerticleDao().getAll();
        // 3.返回最终结果
        return result.success(ResultExtractor.extractList(verticles, "group"));
    }

    /** 读取主路由下的子路由 **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<List<PERoute>> findRoutes(@NotNull @NotBlank @NotEmpty final String parent) {
        // 1.构造响应数据
        final ServiceResult<List<PERoute>> result = new ServiceResult<>();
        // 2.读取所有的RouteMode相关信息
        final List<PERoute> routes = this.manager.getRouteDao().getByParent(parent);
        // 3.返回最终结果
        return result.success(routes);
    }

    /** 读取所有路由信息 **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ConcurrentMap<String, List<PERoute>>> findRoutes() {
        // 1.构造响应数据
        final ServiceResult<ConcurrentMap<String, List<PERoute>>> result = new ServiceResult<>();
        // 2.读取所有的RouteMode相关信息
        final List<PERoute> routes = this.manager.getRouteDao().getAll();
        // 3.设置返回结果
        final ConcurrentMap<String, List<PERoute>> listRet = ResultExtractor.extractList(routes, "parent");
        // 4.返回最终结果
        return result.success(listRet);
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ConcurrentMap<HttpMethod, PEUri>> findUri(@NotNull @NotBlank @NotEmpty final String uri) {
        // 1.构造响应数据
        final ServiceResult<ConcurrentMap<HttpMethod, PEUri>> result = new ServiceResult<>();
        // 2.调用读取方法
        final List<PEUri> ret = this.manager.getUriDao().getByUri(uri);
        // 3.设置响应信息
        final ConcurrentMap<HttpMethod, PEUri> retMap = ResultExtractor.extractUris(ret);
        return result.success(retMap);
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ConcurrentMap<String, List<PERule>>> findValidators(
            @NotNull @NotBlank @NotEmpty final String uriId) {
        // 1.构造响应数据
        final ServiceResult<ConcurrentMap<String, List<PERule>>> result = new ServiceResult<>();
        // 2.调用读取方法
        final List<PERule> ret = this.manager.getRuleDao().getByUriAndCom(uriId, ComponentType.VALIDATOR);
        // 3.抽取结果
        final ConcurrentMap<String, List<PERule>> listRet = ResultExtractor.extractList(ret, "name");
        return result.success(listRet);
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ConcurrentMap<String, List<PERule>>> findDependants(
            @NotNull @NotBlank @NotEmpty final String uriId) {
        // 1.构造响应数据
        final ServiceResult<ConcurrentMap<String, List<PERule>>> result = new ServiceResult<>();
        // 2.调用读取方法
        final List<PERule> ret = this.manager.getRuleDao().getByUriAndCom(uriId, ComponentType.DEPENDANT);
        // 3.设置响应结果
        final ConcurrentMap<String, List<PERule>> listRet = ResultExtractor.extractList(ret, "name");
        return result.success(listRet);
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ConcurrentMap<String, List<PERule>>> findConvertors(
            @NotNull @NotBlank @NotEmpty final String uriId) {
        // 1.构造响应数据
        final ServiceResult<ConcurrentMap<String, List<PERule>>> result = new ServiceResult<>();
        // 2.调用读取方法
        final List<PERule> ret = this.manager.getRuleDao().getByUriAndCom(uriId, ComponentType.CONVERTOR);
        // 3.设置响应结果
        final ConcurrentMap<String, List<PERule>> listRet = ResultExtractor.extractList(ret, "name");
        return result.success(listRet);
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<PEAddress> findAddress(@NotNull final Class<?> workClass) {
        // 1.构造响应数据
        final ServiceResult<PEAddress> result = new ServiceResult<>();
        // 2.调用读取方法
        final PEAddress ret = this.manager.getAddressDao().getByClass(workClass);
        // 3.设置最终响应结果
        return result.success(ret);
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<PEScript> findScript(@NotNull @NotBlank @NotEmpty final String name) {
        // 1.构造响应数据
        final ServiceResult<PEScript> result = new ServiceResult<>();
        // 2.调用读取方法
        final PEScript ret = this.manager.getScriptDao().getByName(name);
        // 3.设置最终响应结果
        return result.success(ret);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
