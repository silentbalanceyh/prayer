package com.prayer.bus.impl; // NOPMD

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.impl.util.Extractor;
import com.prayer.constant.SystemEnum.ComponentType;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.script.ScriptModel;
import com.prayer.model.h2.vx.AddressModel;
import com.prayer.model.h2.vx.RouteModel;
import com.prayer.model.h2.vx.RuleModel;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.model.h2.vx.VerticleModel;
import com.prayer.schema.dao.AddressDao;
import com.prayer.schema.dao.RouteDao;
import com.prayer.schema.dao.RuleDao;
import com.prayer.schema.dao.ScriptDao;
import com.prayer.schema.dao.UriDao;
import com.prayer.schema.dao.VerticleDao;
import com.prayer.schema.dao.impl.AddressDaoImpl;
import com.prayer.schema.dao.impl.RouteDaoImpl;
import com.prayer.schema.dao.impl.RuleDaoImpl;
import com.prayer.schema.dao.impl.ScriptDaoImpl;
import com.prayer.schema.dao.impl.UriDaoImpl;
import com.prayer.schema.dao.impl.VerticleDaoImpl;

import io.vertx.core.http.HttpMethod;
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
	/** 日志记录器 **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigSevImpl.class);
	// ~ Instance Fields =====================================
	/** 访问H2的EVX_VERTICLE接口 **/
	@NotNull
	private transient final VerticleDao verticleDao;
	/** 访问H2的EVX_ROUTE接口 **/
	@NotNull
	private transient final RouteDao routeDao;
	/** 访问H2的EVX_URI接口 **/
	@NotNull
	private transient final UriDao uriDao;
	/** 访问H2的EVX_PVRULE接口 **/
	@NotNull
	private transient final RuleDao ruleDao;
	/** 访问H2的EVX_ADDRESS接口 **/
	@NotNull
	private transient final AddressDao addressDao;
	/** **/
	@NotNull
	private transient final ScriptDao scriptDao;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public ConfigSevImpl() {
		this.verticleDao = singleton(VerticleDaoImpl.class);
		this.routeDao = singleton(RouteDaoImpl.class);
		this.uriDao = singleton(UriDaoImpl.class);
		this.ruleDao = singleton(RuleDaoImpl.class);
		this.addressDao = singleton(AddressDaoImpl.class);
		this.scriptDao = singleton(ScriptDaoImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** 读取的特殊方法，无异常信息抛出。 **/
	@Override
	@PreValidateThis
	public ServiceResult<VerticleChain> findVerticles(@NotNull @NotBlank @NotEmpty final String group) {
		final ServiceResult<VerticleChain> result = new ServiceResult<>();
		final VerticleChain chain = this.verticleDao.getByGroup(group);
		result.setResponse(chain, null);
		return result;
	}

	/** 读取数据库中所有的VerticleModel配置信息 **/
	@Override
	@PreValidateThis
	public ServiceResult<ConcurrentMap<String, VerticleChain>> findVerticles() {
		// 1.构造响应数据
		final ServiceResult<ConcurrentMap<String, VerticleChain>> result = new ServiceResult<>();
		// 2.读取所有的VerticleModel相关信息
		final List<VerticleModel> verticles = this.verticleDao.getAll();
		// 3.设置返回结果
		result.setResponse(Extractor.extractVerticles(verticles), null);
		// 4.返回最终结果
		return result;
	}

	/** 读取主路由下的子路由 **/
	@Override
	@PreValidateThis
	public ServiceResult<List<RouteModel>> findRoutes(@NotNull @NotBlank @NotEmpty final String parent) {
		// 1.构造响应数据
		final ServiceResult<List<RouteModel>> result = new ServiceResult<>();
		// 2.读取所有的RouteMode相关信息
		final List<RouteModel> routes = this.routeDao.getByParent(parent);
		// 3.设置返回结果
		result.setResponse(routes, null);
		// 4.返回最终结果
		return result;
	}

	/** 读取所有路由信息 **/
	@Override
	@PreValidateThis
	public ServiceResult<ConcurrentMap<String, List<RouteModel>>> findRoutes() {
		// 1.构造响应数据
		final ServiceResult<ConcurrentMap<String, List<RouteModel>>> result = new ServiceResult<>();
		// 2.读取所有的RouteMode相关信息
		final List<RouteModel> routes = this.routeDao.getAll();
		// 3.设置返回结果
		final ConcurrentMap<String, List<RouteModel>> listRet = Extractor.extractList(routes, "parent");
		result.setResponse(listRet, null);
		// 4.返回最终结果
		return result;
	}

	/** **/
	@Override
	@PreValidateThis
	public ServiceResult<UriModel> findUri(@NotNull @NotBlank @NotEmpty final String uri,
			@NotNull final HttpMethod method) {
		// 1.构造响应数据
		final ServiceResult<UriModel> result = new ServiceResult<>();
		// 2.调用读取方法
		final UriModel ret = this.uriDao.getByUri(uri, method);
		// 3.设置响应信息
		result.setResponse(ret, null);
		return result;
	}

	/** **/
	@Override
	@PreValidateThis
	public ServiceResult<ConcurrentMap<String, List<RuleModel>>> findValidators(
			@NotNull @NotBlank @NotEmpty final String uriId) {
		// 1.构造响应数据
		final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = new ServiceResult<>();
		// 2.调用读取方法
		List<RuleModel> ret = this.ruleDao.getByUri(uriId);
		// 3.设置响应结果
		ret = ret.stream().filter(item -> ComponentType.VALIDATOR == item.getComponentType())
				.collect(Collectors.toList());
		info(LOGGER, "Validator Size : " + ret.size() + ", uriId = " + uriId);
		final ConcurrentMap<String, List<RuleModel>> listRet = Extractor.extractList(ret, "name");
		result.setResponse(listRet, null);
		return result;
	}

	/** **/
	@Override
	@PreValidateThis
	public ServiceResult<ConcurrentMap<String, List<RuleModel>>> findConvertors(
			@NotNull @NotBlank @NotEmpty final String uriId) {
		// 1.构造响应数据
		final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = new ServiceResult<>();
		// 2.调用读取方法
		List<RuleModel> ret = this.ruleDao.getByUri(uriId);
		// 3.设置响应结果
		ret = ret.stream().filter(item -> ComponentType.CONVERTOR == item.getComponentType())
				.collect(Collectors.toList());
		info(LOGGER, "Convertor Size : " + ret.size() + ", uriId = " + uriId);
		final ConcurrentMap<String, List<RuleModel>> listRet = Extractor.extractList(ret, "name");
		result.setResponse(listRet, null);
		return result;
	}

	/** **/
	@Override
	@PreValidateThis
	public ServiceResult<AddressModel> findAddress(@NotNull final Class<?> workClass) {
		// 1.构造响应数据
		final ServiceResult<AddressModel> result = new ServiceResult<>();
		// 2.调用读取方法
		final AddressModel ret = this.addressDao.getByClass(workClass.getName());
		// 3.设置最终响应结果
		result.setResponse(ret, null);
		return result;
	}

	/** **/
	@Override
	@PreValidateThis
	public ServiceResult<ScriptModel> findScript(@NotNull @NotBlank @NotEmpty final String name) {
		// 1.构造响应数据
		final ServiceResult<ScriptModel> result = new ServiceResult<>();
		// 2.调用读取方法
		final ScriptModel ret = this.scriptDao.getByName(name);
		// 3.设置最终响应结果
		result.setResponse(ret, null);
		return result;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
