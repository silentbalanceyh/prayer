package com.prayer.bus.impl;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.DeployService;
import com.prayer.bus.SchemaService;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractException;
import com.prayer.exception.system.DeploymentException;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.IOKit;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

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
	/** **/
	@NotNull
	private transient final ConfigService configService;
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
		this.configService = singleton(ConfigSevImpl.class);
		this.schemaService = singleton(SchemaSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public ServiceResult<Boolean> deployPrayerData() {
		final ServiceResult<Boolean> result = new ServiceResult<>();
		// 1.删除原始的EVX_VERTICLE表中数据
		ServiceResult<?> ret = this.configService.purgeVerticles();
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.configService.importVerticles(VX_VERTICLE);
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.configService.purgeRoutes();
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.configService.importRoutes(VX_ROUTES);
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
