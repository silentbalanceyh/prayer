package com.prayer.bus.impl.deploy;

import static com.prayer.util.Error.debug;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.dao.impl.schema.ScriptDaoImpl;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.facade.bus.deploy.ScriptDPService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vertx.ScriptModel;
import com.prayer.util.IOKit;
import com.prayer.util.JsonKit;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ScriptDPSevImpl extends AbstractDPSevImpl<ScriptModel, String>implements ScriptDPService { // NOPMD
	// ~ Static Fields =======================================

	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ScriptDPSevImpl.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public Class<?> getDaoClass() {
		return ScriptDaoImpl.class;
	}

	/** 获取Logger **/
	@Override
	public Logger getLogger() {
		return LOGGER;
	}

	/** T Array **/
	@Override
	public ScriptModel[] getArrayType() {
		return new ScriptModel[] {};
	}

	/** **/
	@Override
	public List<ScriptModel> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath)
			throws AbstractSystemException {
		final TypeReference<List<ScriptModel>> typeRef = new TypeReference<List<ScriptModel>>() {
		};
		return JsonKit.fromFile(typeRef, jsonPath);
	}

	/** **/
	@PreValidateThis
	@Override
	public ServiceResult<List<ScriptModel>> importToList(@NotNull @NotBlank @NotEmpty final String jsonPath) {
		// 1.构造响应结果
		final ServiceResult<List<ScriptModel>> result = new ServiceResult<>();
		// 2.从Json中读取List
		List<ScriptModel> dataList = new ArrayList<>();
		try {
			dataList = this.readJson(jsonPath);
		} catch (AbstractSystemException ex) {
			debug(getLogger(), "SYS.KIT.SER", ex, jsonPath);
			result.error(ex);
		}
		// 3.脚本内容读取
		for (final ScriptModel script : dataList) {
			if (null != script.getContent()) {
				script.setContent(IOKit.getContent(script.getContent()));
			} else {
				final String path = script.getName().replaceAll("\\.", "/");
				script.setContent(IOKit.getContent("deploy/oob/" + path + ".js"));
			}
		}
		// 4.批量创建
		try {
			this.getDao().insert(dataList.toArray(getArrayType()));
		} catch (AbstractTransactionException ex) {
			debug(getLogger(), "SYS.KIT.DP", ex);
			result.error(ex);
		}
		if (ResponseCode.SUCCESS == result.getResponseCode() && Constants.RC_SUCCESS == result.getErrorCode()) {
			result.success(dataList);
		}
		return result;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}