package com.prayer.bus.impl; // NOPMD

import static com.prayer.util.Error.info;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.DeployService;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractException;
import com.prayer.exception.system.DeploymentException;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.util.IOKit;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
@SuppressWarnings("unchecked")
public class DeploySevImpl extends AbstractConfigSevImpl implements DeployService, OOBPaths {
	// ~ Static Fields =======================================

	/** 日志记录器 **/
	private static final Logger LOGGER = LoggerFactory.getLogger(DeploySevImpl.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public Logger getLogger() {
		return LOGGER;
	}

	/**
	 * 
	 */
	@Override
	public ServiceResult<Boolean> deployPrayerData() {
		final ServiceResult<Boolean> result = new ServiceResult<>();
		// 1.删除原始的EVX_VERTICLE表中数据
		ServiceResult<?> ret = this.getVerticleService().purgeData();
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.getVerticleService().importVerticles(VX_VERTICLE);
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.getRouteService().purgeData();
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.getRouteService().importToList(VX_ROUTES);
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.getValidatorService().purgeData();
			// 必须先PurgeValidators
			ret = this.getUriService().purgeData();
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.getUriService().importToList(VX_URI);
			// URI中的Param参数List
			final List<UriModel> uriModels = (List<UriModel>) ret.getResult();
			for (final UriModel model : uriModels) {
				final String paramFile = VX_URI_PARAM
						+ model.getUri().substring(1, model.getUri().length()).replaceAll("/",".") + ".json";
				this.getValidatorService().importValidators(paramFile, model);
			}
		}
		// 2.导入OOB中的Schema定义
		{
			final List<String> jsonPathList = this.getSchemaFiles();
			jsonPathList.forEach(jsonPath -> {
				ServiceResult<GenericSchema> syncRet = this.getSchemaService().syncSchema(SCHEMA_FOLDER + jsonPath);
				info(LOGGER, "[I] Read new schema data : " + syncRet.getResult());
				syncRet = this.getSchemaService().syncMetadata(syncRet.getResult());
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
