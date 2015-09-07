package com.prayer.bus.impl; // NOPMD

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.bus.ConfigService;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.vx.RouteModel;
import com.prayer.model.h2.vx.VerticleModel;
import com.prayer.schema.dao.RouteDao;
import com.prayer.schema.dao.VerticleDao;
import com.prayer.schema.dao.impl.RouteDaoImpl;
import com.prayer.schema.dao.impl.VerticleDaoImpl;
import com.prayer.util.JsonKit;
import com.prayer.util.StringKit;

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

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public ConfigSevImpl() {
		this.verticleDao = singleton(VerticleDaoImpl.class);
		this.routeDao = singleton(RouteDaoImpl.class);
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

	/** 将数据从Json文件导入到H2数据库中 **/
	@Override
	@PreValidateThis
	public ServiceResult<ConcurrentMap<String, VerticleChain>> importVerticles(
			@NotNull @NotBlank @NotEmpty final String jsonPath) {
		// 1.构造响应数据
		final ServiceResult<ConcurrentMap<String, VerticleChain>> result = new ServiceResult<>();
		// 2.从Json中读取List
		List<VerticleModel> dataList = new ArrayList<>();
		try {
			// 特殊的Type引用，用于序列化
			final TypeReference<List<VerticleModel>> typeRef = new TypeReference<List<VerticleModel>>() {
			};
			dataList = JsonKit.fromFile(typeRef, jsonPath);
		} catch (AbstractSystemException ex) {
			info(LOGGER, "[E-BUS] (Verticles) Serialization error when reading data from file : file = " + jsonPath,
					ex);
			result.setResponse(null, ex);
		}
		// 3.批量创建
		try {
			this.verticleDao.insert(dataList.toArray(new VerticleModel[] {}));
		} catch (AbstractTransactionException ex) {
			info(LOGGER, "[E-BUS] (Verticles) Data accessing error !", ex);
			result.setResponse(null, ex);
		}
		// 4.返回最终的Result信息
		if (ResponseCode.SUCCESS == result.getResponseCode() && Constants.RC_SUCCESS == result.getErrorCode()) {
			result.setResponse(this.extractVerticles(dataList), null);
		}
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
		result.setResponse(this.extractVerticles(verticles), null);
		// 4.返回最终结果
		return result;
	}

	/** 删除所有的配置信息 **/
	@Override
	@PreValidateThis
	public ServiceResult<Boolean> purgeVerticles() {
		// 1.构造响应数据
		final ServiceResult<Boolean> result = new ServiceResult<>();
		// 2.调用删除方法
		try {
			this.verticleDao.clear();
			result.setResponse(Boolean.TRUE, null);
		} catch (AbstractTransactionException ex) {
			info(LOGGER, "[E-BUS] (Verticles) Data accessing error !", ex);
			result.setResponse(Boolean.FALSE, ex);
		}
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
		result.setResponse(this.extractRoutes(routes), null);
		// 4.返回最终结果
		return result;
	}

	/** 从Json中导入数据到H2数据库 **/
	@Override
	@PreValidateThis
	public ServiceResult<ConcurrentMap<String, List<RouteModel>>> importRoutes(
			@NotNull @NotBlank @NotEmpty final String jsonPath) {
		// 1.构造响应数据
		final ServiceResult<ConcurrentMap<String, List<RouteModel>>> result = new ServiceResult<>();
		// 2.从Json中读取List
		List<RouteModel> dataList = new ArrayList<>();
		try {
			// 特殊的Type引用，用于序列化
			final TypeReference<List<RouteModel>> typeRef = new TypeReference<List<RouteModel>>() {
			};
			dataList = JsonKit.fromFile(typeRef, jsonPath);
		} catch (AbstractSystemException ex) {
			info(LOGGER, "[E-BUS] (Routes) Serialization error when reading data from file : file = " + jsonPath, ex);
			result.setResponse(null, ex);
		}
		// 3.批量创建
		try {
			this.routeDao.insert(dataList.toArray(new RouteModel[] {}));
		} catch (AbstractTransactionException ex) {
			info(LOGGER, "[E-BUS] (Routes) Data accessing error !", ex);
			result.setResponse(null, ex);
		}
		// 4.返回最终的Result信息
		if (ResponseCode.SUCCESS == result.getResponseCode() && Constants.RC_SUCCESS == result.getErrorCode()) {
			result.setResponse(this.extractRoutes(dataList), null);
		}
		return result;
	}

	/** 删除所有的配置信息 **/
	@Override
	@PreValidateThis
	public ServiceResult<Boolean> purgeRoutes() {
		// 1.构造响应数据
		final ServiceResult<Boolean> result = new ServiceResult<>();
		// 2.调用删除方法
		try {
			this.routeDao.clear();
			result.setResponse(Boolean.TRUE, null);
		} catch (AbstractTransactionException ex) {
			info(LOGGER, "[E-BUS] (Routes) Data accessing error !", ex);
			result.setResponse(Boolean.FALSE, ex);
		}
		return result;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private ConcurrentMap<String, List<RouteModel>> extractRoutes(final List<RouteModel> dataList) {
		// 1.构造结果
		final ConcurrentMap<String, List<RouteModel>> retMap = new ConcurrentHashMap<>();
		// 2.遍历结果集
		dataList.stream().filter(item -> StringKit.isNonNil(item.getParent())).forEach(item -> {
			// 3.1.获取某个Parent下的List
			List<RouteModel> list = retMap.get(item.getParent());
			// 3.2.判断是否获取到
			if (null == list) {
				list = new ArrayList<>(); // NOPMD
			}
			// 3.3.添加对象到List中
			list.add(item);
			// 3.4.完成过后将内容重新推回到Map中
			retMap.put(item.getParent(), list);
		});
		return retMap;
	}

	private ConcurrentMap<String, VerticleChain> extractVerticles(final List<VerticleModel> dataList) {
		// 1.构造结果
		final ConcurrentMap<String, VerticleChain> retMap = new ConcurrentHashMap<>();
		// 2.遍历结果集
		dataList.stream().filter(item -> StringKit.isNonNil(item.getGroup())).forEach(item -> {
			// 3.1.获取某个Group的VerticleChain
			VerticleChain chain = retMap.get(item.getGroup());
			// 3.2.判断是否获取到，没获取到就重新获取
			if (null == chain) {
				chain = new VerticleChain(item.getGroup()); // NOPMD
			}
			// 3.3.修改chain引用
			chain.addVerticle(item);
			// 3.4.完成过后添加到Map中
			retMap.put(item.getGroup(), chain);
		});
		// 4.返回最终过滤结果
		return retMap;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
