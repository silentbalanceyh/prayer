package com.prayer.bus.impl;	// NOPMD

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.bus.DeployService;
import com.prayer.bus.SchemaService;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.exception.system.DeploymentException;
import com.prayer.kernel.model.GenericSchema;
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
import com.prayer.util.IOKit;
import com.prayer.util.JsonKit;

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
public class DeploySevImpl implements DeployService, OOBPaths {
	// ~ Static Fields =======================================

	/** 日志记录器 **/
	private static final Logger LOGGER = LoggerFactory.getLogger(DeploySevImpl.class);

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
	/** **/
	@NotNull
	private transient final SchemaService schemaService;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 */
	@PostValidateThis
	public DeploySevImpl() {
		this.verticleDao = singleton(VerticleDaoImpl.class);
		this.routeDao = singleton(RouteDaoImpl.class);
		this.uriDao = singleton(UriDaoImpl.class);
		this.schemaService = singleton(SchemaSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================

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
			result.setResponse(Extractor.extractVerticles(dataList), null);
		}
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
			result.setResponse(Extractor.extractRoutes(dataList), null);
		}
		return result;
	}

	/** 从Json中导入数据到H2数据库 **/
	@Override
	@PreValidateThis
	public ServiceResult<ConcurrentMap<String, UriModel>> importUris(
			@NotNull @NotBlank @NotEmpty final String jsonPath) {
		// 1.构造响应数据
		final ServiceResult<ConcurrentMap<String, UriModel>> result = new ServiceResult<>();
		// 2.从Json中读取List
		List<UriModel> dataList = new ArrayList<>();
		try {
			// 特殊的Type引用，用于序列化
			final TypeReference<List<UriModel>> typeRef = new TypeReference<List<UriModel>>() {
			};
			dataList = JsonKit.fromFile(typeRef, jsonPath);
		} catch (AbstractSystemException ex) {
			info(LOGGER, "[E-BUS] (Uris) Serialization error when reading data from file : file = " + jsonPath, ex);
			result.setResponse(null, ex);
		}
		// 3.批量创建
		try {
			this.uriDao.insert(dataList.toArray(new UriModel[] {}));
		} catch (AbstractTransactionException ex) {
			info(LOGGER, "[E-BUS] (Uris) Data accessing error !", ex);
			result.setResponse(null, ex);
		}
		// 4.返回最终的Result信息
		if (ResponseCode.SUCCESS == result.getResponseCode() && Constants.RC_SUCCESS == result.getErrorCode()) {
			result.setResponse(Extractor.extractUris(dataList), null);
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

	/** 删除Uris的配置信息 **/
	@Override
	@PreValidateThis
	public ServiceResult<Boolean> purgeUris() {
		// 1.构造响应数据
		final ServiceResult<Boolean> result = new ServiceResult<>();
		// 2.调用删除方法
		try {
			this.uriDao.clear();
			result.setResponse(Boolean.TRUE, null);
		} catch (AbstractTransactionException ex) {
			info(LOGGER, "[E-BUS] (Uris) Data accessing error !", ex);
			result.setResponse(Boolean.FALSE, ex);
		}
		return result;
	}

	/**
	 * 
	 */
	@Override
	public ServiceResult<Boolean> deployPrayerData() {
		final ServiceResult<Boolean> result = new ServiceResult<>();
		// 1.删除原始的EVX_VERTICLE表中数据
		ServiceResult<?> ret = this.purgeVerticles();
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.importVerticles(VX_VERTICLE);
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.purgeRoutes();
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.importRoutes(VX_ROUTES);
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()){
			ret = this.purgeUris();
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()){
			ret = this.importUris(VX_URI);
		}
		// 2.导入OOB中的Schema定义
		{
			final List<String> jsonPathList = this.getSchemaFiles();
			jsonPathList.forEach(jsonPath -> {
				ServiceResult<GenericSchema> syncRet = this.schemaService.syncSchema(SCHEMA_FOLDER + jsonPath);
				info(LOGGER, "[I] Read new schema data : " + syncRet.getResult());
				syncRet = this.schemaService.syncMetadata(syncRet.getResult());
				if (ResponseCode.SUCCESS != syncRet.getResponseCode()) {
					info(LOGGER, "[E] Schema Importing met Error : " + syncRet.getErrorMessage());
				}
			});
		}
		// 最终结果
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			result.setResponse(Boolean.TRUE, null);
			info(LOGGER, "[I-DP] Deployment Successfully ! ");
		} else {
			final AbstractException exp = new DeploymentException(getClass());
			result.setResponse(Boolean.FALSE, exp);
			info(LOGGER, "[E-DP] Error met ! ", exp);
		}
		return result;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private List<String> getSchemaFiles() {
		final URL uri = IOKit.getURL(SCHEMA_FOLDER);
		final List<String> retList = new ArrayList<>();
		if (null != uri) {
			final File folder = new File(uri.getPath());
			if (folder.isDirectory()) {
				Arrays.asList(folder.listFiles()).forEach(file -> {
					retList.add(file.getName());
				});
			}
		}
		return retList;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
