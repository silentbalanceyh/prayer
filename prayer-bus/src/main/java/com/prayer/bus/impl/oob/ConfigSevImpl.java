package com.prayer.bus.impl.oob; // NOPMD

import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.prayer.facade.bus.ConfigService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.vertx.AddressModel;
import com.prayer.model.vertx.RouteModel;
import com.prayer.model.vertx.RuleModel;
import com.prayer.model.vertx.ScriptModel;
import com.prayer.model.vertx.UriModel;
import com.prayer.model.vertx.VerticleModel;
import com.prayer.util.bus.ResultExtractor;
import com.prayer.util.cv.SystemEnum.ComponentType;

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
    public ServiceResult<VerticleChain> findVerticles(@NotNull @NotBlank @NotEmpty final String group) {
        final ServiceResult<VerticleChain> result = new ServiceResult<>();
        final VerticleChain chain = this.manager.getVerticleDao().getByGroup(group);
        return result.success(chain);
    }

    /** 读取数据库中所有的VerticleModel配置信息 **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ConcurrentMap<String, VerticleChain>> findVerticles() {
        // 1.构造响应数据
        final ServiceResult<ConcurrentMap<String, VerticleChain>> result = new ServiceResult<>();
        // 2.读取所有的VerticleModel相关信息
        final List<VerticleModel> verticles = this.manager.getVerticleDao().getAll();
        // 3.返回最终结果
        return result.success(ResultExtractor.extractVerticles(verticles));
    }

    /** 读取主路由下的子路由 **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<List<RouteModel>> findRoutes(@NotNull @NotBlank @NotEmpty final String parent) {
        // 1.构造响应数据
        final ServiceResult<List<RouteModel>> result = new ServiceResult<>();
        // 2.读取所有的RouteMode相关信息
        final List<RouteModel> routes = this.manager.getRouteDao().getByParent(parent);
        // 3.返回最终结果
        return result.success(routes);
    }

    /** 读取所有路由信息 **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ConcurrentMap<String, List<RouteModel>>> findRoutes() {
        // 1.构造响应数据
        final ServiceResult<ConcurrentMap<String, List<RouteModel>>> result = new ServiceResult<>();
        // 2.读取所有的RouteMode相关信息
        final List<RouteModel> routes = this.manager.getRouteDao().getAll();
        // 3.设置返回结果
        final ConcurrentMap<String, List<RouteModel>> listRet = ResultExtractor.extractList(routes, "parent");
        // 4.返回最终结果
        return result.success(listRet);
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ConcurrentMap<HttpMethod, UriModel>> findUri(@NotNull @NotBlank @NotEmpty final String uri) {
        // 1.构造响应数据
        final ServiceResult<ConcurrentMap<HttpMethod, UriModel>> result = new ServiceResult<>();
        // 2.调用读取方法
        final List<UriModel> ret = this.manager.getUriDao().getByUri(uri);
        // 3.设置响应信息
        final ConcurrentMap<HttpMethod, UriModel> retMap = ResultExtractor.extractUris(ret);
        return result.success(retMap);
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ConcurrentMap<String, List<RuleModel>>> findValidators(
            @NotNull @NotBlank @NotEmpty final String uriId) {
        // 1.构造响应数据
        final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = new ServiceResult<>();
        // 2.调用读取方法
        List<RuleModel> ret = this.manager.getRuleDao().getByUri(uriId);
        // 3.设置响应结果
        ret = ret.stream().filter(item -> ComponentType.VALIDATOR == item.getComponentType())
                .collect(Collectors.toList());
        final ConcurrentMap<String, List<RuleModel>> listRet = ResultExtractor.extractList(ret, "name");
        return result.success(listRet);
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ConcurrentMap<String, List<RuleModel>>> findConvertors(
            @NotNull @NotBlank @NotEmpty final String uriId) {
        // 1.构造响应数据
        final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = new ServiceResult<>();
        // 2.调用读取方法
        List<RuleModel> ret = this.manager.getRuleDao().getByUri(uriId);
        // 3.设置响应结果
        ret = ret.stream().filter(item -> ComponentType.CONVERTOR == item.getComponentType())
                .collect(Collectors.toList());
        final ConcurrentMap<String, List<RuleModel>> listRet = ResultExtractor.extractList(ret, "name");
        return result.success(listRet);
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<AddressModel> findAddress(@NotNull final Class<?> workClass) {
        // 1.构造响应数据
        final ServiceResult<AddressModel> result = new ServiceResult<>();
        // 2.调用读取方法
        final AddressModel ret = this.manager.getAddressDao().getByClass(workClass.getName());
        // 3.设置最终响应结果
        return result.success(ret);
    }

    /** **/
    @Override
    @PreValidateThis
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<ScriptModel> findScript(@NotNull @NotBlank @NotEmpty final String name) {
        // 1.构造响应数据
        final ServiceResult<ScriptModel> result = new ServiceResult<>();
        // 2.调用读取方法
        final ScriptModel ret = this.manager.getScriptDao().getByName(name);
        // 3.设置最终响应结果
        return result.success(ret);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
