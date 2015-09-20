package com.prayer.bus.deploy.oob; // NOPMD

import static com.prayer.bus.util.BusLogger.error;
import static com.prayer.util.Instance.singleton;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.std.DeployService;
import com.prayer.bus.util.BusLogger;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.db.conn.MetadataConn;
import com.prayer.db.conn.impl.MetadataConnImpl;
import com.prayer.exception.AbstractException;
import com.prayer.exception.system.DeploymentException;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.util.IOKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
@SuppressWarnings("unchecked")
public class DeploySevImpl implements DeployService, OOBPaths { // NOPMD
	// ~ Static Fields =======================================

	/** 日志记录器 **/
	private static final Logger LOGGER = LoggerFactory.getLogger(DeploySevImpl.class);
	// ~ Instance Fields =====================================
	/** **/
	private transient final MetadataConn metaConn = singleton(MetadataConnImpl.class);
	/** **/
	private transient final DeploySevManager manager = singleton(DeploySevManager.class);

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	public Logger getLogger() {
		return LOGGER;
	}

	/**
	 * 
	 * @param scriptPath
	 * @return
	 */
	@Override
	public ServiceResult<Boolean> initH2Database(@NotNull @NotBlank @NotEmpty final String scriptPath) {
		// 1.执行Script脚本
		final boolean executedRet = this.metaConn.initMeta(IOKit.getFile(scriptPath));
		// 2.设置相应信息
		final ServiceResult<Boolean> ret = new ServiceResult<>();
		ret.setResponse(executedRet, null);
		return ret;
	}

	/**
	 * 
	 */
	@Override
	public ServiceResult<Boolean> deployPrayerData() { // NOPMD
		final ServiceResult<Boolean> result = new ServiceResult<>();
		// 1.EVX_VERTICLE
		ServiceResult<?> ret = this.manager.getVerticleService().purgeData();
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.manager.getVerticleService().importVerticles(VX_VERTICLE);
		} else {
			error(LOGGER, BusLogger.E_PROCESS_ERR, "Verticle Deploy", ret.getErrorMessage());
		}
		// 2.EVX_ROUTE
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.manager.getRouteService().purgeData();
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.manager.getRouteService().importToList(VX_ROUTES);
		}else {
			error(LOGGER, BusLogger.E_PROCESS_ERR, "Route Deploy", ret.getErrorMessage());
		}
		// 3.EVX_URI
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.manager.getRuleService().purgeData();
			// 必须先PurgeValidators
			ret = this.manager.getUriService().purgeData();
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.manager.getUriService().importToList(VX_URI);
			if(ResponseCode.SUCCESS == ret.getResponseCode()){
				// URI中的Param参数List
				final List<UriModel> uriModels = (List<UriModel>) ret.getResult();
				for (final UriModel model : uriModels) {
					// 4.EVX_RULE
					final String paramFile = VX_URI_PARAM + model.getMethod().toString().toLowerCase(Locale.getDefault())
							+ "/" + model.getUri().substring(1, model.getUri().length()).replaceAll("/", ".") + ".json";
					ret = this.manager.getRuleService().importRules(paramFile, model);
					if(ResponseCode.SUCCESS != ret.getResponseCode()){
						error(LOGGER, BusLogger.E_PROCESS_ERR, model.getUri() + " Rule Deploy", ret.getErrorMessage());
					}
				}
			}else {
				error(LOGGER, BusLogger.E_PROCESS_ERR, "Uri Deploy", ret.getErrorMessage());
			}
		}
		// 5.EVX_ADDRESS
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.manager.getAddressService().purgeData();
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.manager.getAddressService().importToList(VX_ADDRESS);
		}else {
			error(LOGGER, BusLogger.E_PROCESS_ERR, "Address Deploy", ret.getErrorMessage());
		}
		// 6.ENG_SCRIPT
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.manager.getScriptService().purgeData();
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.manager.getScriptService().importToList(VX_SCRIPT);
		}
		else {
			error(LOGGER, BusLogger.E_PROCESS_ERR, "Script Deploy", ret.getErrorMessage());
		}
		// 2.导入OOB中的Schema定义
		{
			final List<String> jsonPathList = this.getSchemaFiles();
			jsonPathList.forEach(jsonPath -> {
				ServiceResult<GenericSchema> syncRet = this.manager.getSchemaService()
						.syncSchema(SCHEMA_FOLDER + jsonPath);
				syncRet = this.manager.getSchemaService().syncMetadata(syncRet.getResult());
			});
		}
		// 最终结果
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			result.setResponse(Boolean.TRUE, null);
		} else {
			final AbstractException exp = new DeploymentException(getClass());
			result.setResponse(Boolean.FALSE, exp);
			error(LOGGER, BusLogger.E_DPDATA_ERR);
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
