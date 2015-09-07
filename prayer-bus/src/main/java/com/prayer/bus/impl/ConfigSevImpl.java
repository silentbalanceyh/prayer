package com.prayer.bus.impl; // NOPMD

import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.bus.ConfigService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.vx.RouteModel;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.model.h2.vx.VerticleModel;
import com.prayer.schema.dao.RouteDao;
import com.prayer.schema.dao.UriDao;
import com.prayer.schema.dao.VerticleDao;
import com.prayer.schema.dao.impl.RouteDaoImpl;
import com.prayer.schema.dao.impl.UriDaoImpl;
import com.prayer.schema.dao.impl.VerticleDaoImpl;

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
	private transient final VerticleDao verticleDao;
	/** 访问H2的EVX_ROUTE接口 **/
	@NotNull
	private transient final RouteDao routeDao;
	/** 访问H2的EVX_URI接口 **/
	@NotNull
	private transient final UriDao uriDao;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public ConfigSevImpl() {
		this.verticleDao = singleton(VerticleDaoImpl.class);
		this.routeDao = singleton(RouteDaoImpl.class);
		this.uriDao = singleton(UriDaoImpl.class);
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
		result.setResponse(Extractor.extractRoutes(routes), null);
		// 4.返回最终结果
		return result;
	}

	/** **/
	@Override
	@PreValidateThis
	public ServiceResult<UriModel> findUri(@NotNull @NotBlank @NotEmpty final String uri) {
		// 1.构造响应数据
		final ServiceResult<UriModel> result = new ServiceResult<>();
		// 2.调用读取方法
		final UriModel ret = this.uriDao.getByUri(uri);
		// 3.设置响应信息
		result.setResponse(ret, null);
		return result;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
