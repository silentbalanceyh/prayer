package com.prayer.bus.impl.deploy;

import static com.prayer.util.debug.Log.peError;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.base.bus.AbstractDPSevImpl;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.dao.impl.schema.ScriptDaoImpl;
import com.prayer.facade.bus.deploy.ScriptDPService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.vertx.PEScript;
import com.prayer.util.io.IOKit;
import com.prayer.util.io.JsonKit;

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
public class ScriptDPSevImpl extends AbstractDPSevImpl<PEScript, String>implements ScriptDPService { // NOPMD
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
	public PEScript[] getArrayType() {
		return new PEScript[] {};
	}

	/** **/
	@Override
	public List<PEScript> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath)
			throws AbstractSystemException {
		final TypeReference<List<PEScript>> typeRef = new TypeReference<List<PEScript>>() {
		};
		return JsonKit.fromFile(typeRef, jsonPath);
	}

	/** **/
	@PreValidateThis
	@Override
	public ServiceResult<List<PEScript>> importToList(@NotNull @NotBlank @NotEmpty final String jsonPath) {
		// 1.构造响应结果
		final ServiceResult<List<PEScript>> result = new ServiceResult<>();
		// 2.从Json中读取List
		List<PEScript> dataList = new ArrayList<>();
		try {
			dataList = this.readJson(jsonPath);
		} catch (AbstractSystemException ex) {
		    peError(getLogger(),ex);
			result.error(ex);
		}
		// 3.脚本内容读取
		for (final PEScript script : dataList) {
			if (null == script.getContent()) {
			    final String path = script.getName().replaceAll("\\.", "/");
                script.setContent(IOKit.getContent("deploy/oob/" + path + ".js"));
			} else {
                script.setContent(IOKit.getContent(script.getContent()));
			}
		}
		// 4.批量创建
		try {
			this.getDao().insert(dataList.toArray(getArrayType()));
		} catch (AbstractTransactionException ex) {
		    peError(getLogger(),ex);
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
