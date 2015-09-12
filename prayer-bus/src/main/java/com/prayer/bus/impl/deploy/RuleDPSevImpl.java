package com.prayer.bus.impl.deploy;

import static com.prayer.util.Error.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.bus.deploy.RuleDPService;
import com.prayer.bus.impl.util.ResultExtractor;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.model.h2.vx.RuleModel;
import com.prayer.schema.dao.impl.RuleDaoImpl;
import com.prayer.util.JsonKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public class RuleDPSevImpl extends AbstractDPSevImpl<RuleModel, String>implements RuleDPService {	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleDPSevImpl.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public Class<?> getDaoClass() {
		return RuleDaoImpl.class;
	}
	/** 获取Logger **/
	@Override
	public Logger getLogger() {
		return LOGGER;
	}
	
	/** T Array **/
	@Override
	public RuleModel[] getArrayType(){
		return new RuleModel[]{};
	}
	
	/** **/
	@Override
	public List<RuleModel> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath)
			throws AbstractSystemException {
		final TypeReference<List<RuleModel>> typeRef = new TypeReference<List<RuleModel>>() {
		};
		return JsonKit.fromFile(typeRef, jsonPath);
	}

	/** 设置Validator的导入 **/
	@Override
	public ServiceResult<ConcurrentMap<String, List<RuleModel>>> importRules(
			@NotNull @NotBlank @NotEmpty final String jsonPath, @NotNull final UriModel uri) {
		// 1.构造响应函数
		final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = new ServiceResult<>();
		// 2.从Json中读取ValidatorModel的列表
		List<RuleModel> dataList = new ArrayList<>();
		try {
			dataList = this.readJson(jsonPath);
		} catch (AbstractSystemException ex) {
			debug(getLogger(), "SYS.KIT.SER", ex);
			result.setResponse(null, ex);
		}
		try {
			// 3.设置RefID的值
			for (final RuleModel validator : dataList) {
				validator.setRefUriId(uri.getUniqueId());
			}
			this.getDao().insert(dataList.toArray(new RuleModel[] {}));
		} catch (AbstractTransactionException ex) {
			debug(getLogger(), "SYS.KIT.DP", ex);
			result.setResponse(null, ex);
		}
		// 4.返回最终的Result信息
		if (ResponseCode.SUCCESS == result.getResponseCode() && Constants.RC_SUCCESS == result.getErrorCode()) {
			final ConcurrentMap<String, List<RuleModel>> listRet = ResultExtractor.extractList(dataList, "refUriId");
			result.setResponse(listRet, null);
		}
		return result;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
