package com.prayer.bus.impl;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.DeployService;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractException;
import com.prayer.exception.system.DeploymentException;
import com.prayer.model.bus.ServiceResult;

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
	private transient final ConfigService cfgService;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 */
	@PostValidateThis
	public DeploySevImpl() {
		this.cfgService = singleton(ConfigSevImpl.class);
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
		ServiceResult<?> ret = this.cfgService.purgeVerticles();
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.cfgService.importVerticles(VX_VERTICLE);
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.cfgService.purgeRoutes();
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			ret = this.cfgService.importRoutes(VX_ROUTES);
		}
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			result.setResponse(Boolean.TRUE, null);
		} else {
			final AbstractException exp = new DeploymentException(getClass());
			result.setResponse(Boolean.FALSE, exp);
			info(LOGGER, "[E-DP] Error met ! ", exp);
		}
		return result;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
