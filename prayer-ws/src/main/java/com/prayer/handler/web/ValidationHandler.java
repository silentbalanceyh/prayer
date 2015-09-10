package com.prayer.handler.web;

import static com.prayer.util.Error.debug;
import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractWebException;
import com.prayer.exception.web.InternalServerErrorException;
import com.prayer.exception.web.ValidationFailureException;
import com.prayer.exception.web.ValidatorInvalidException;
import com.prayer.exception.web.ValidatorNotFoundException;
import com.prayer.kernel.Value;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.bus.web.StatusCode;
import com.prayer.model.h2.vx.RuleModel;
import com.prayer.uca.WebValidator;
import com.prayer.util.Instance;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 【Step 2】参数的第二部检查 -> Validator/Convertor
 * 
 * @author Lang
 *
 */
@Guarded
public class ValidationHandler implements Handler<RoutingContext> {

	// ~ Static Fields =======================================

	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationHandler.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient ConfigService service;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public ValidationHandler() {
		this.service = singleton(ConfigSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public void handle(final RoutingContext routingContext) {

		// 1.从Context中提取参数信息
		final String uriId = routingContext.get(Constants.VX_CTX_URI_ID);
		final HttpServerResponse response = routingContext.response();
		// info(LOGGER, "1.Get URI ID from Context: uri = " + uriId);

		// 2.获取当前路径下的Validator的数据
		final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = this.service.findValidators(uriId);
		final RestfulResult webRet = new RestfulResult(StatusCode.OK);
		// info(LOGGER, "2.Found ServiceResult: result = " + result);
		// 3.如果获取到值
		AbstractWebException error = this.requestDispatch(result, webRet, routingContext);
		// info(LOGGER, "3.Dispatched Error: error = " + error);
		if (null == error) {
			// SUCCESS -->
			routingContext.next();
		} else {
			response.setStatusCode(webRet.getStatusCode().status());
			response.setStatusMessage(webRet.getErrorMessage());
			// 触发错误信息
			info(LOGGER, "RestfulResult = " + webRet);
			routingContext.put(Constants.VX_CTX_ERROR, webRet);
			routingContext.fail(webRet.getStatusCode().status());
		}
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private AbstractWebException requestDispatch(final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result,
			final RestfulResult webRef, final RoutingContext context) {
		AbstractWebException error = null;
		final JsonObject params = context.get(Constants.VX_CTX_PARAMS);
		if (ResponseCode.SUCCESS == result.getResponseCode()) {
			final ConcurrentMap<String, List<RuleModel>> dataMap = result.getResult();
			// 遍历每一个字段
			for (final String field : params.fieldNames()) {
				final String value = params.getString(field);
				final List<RuleModel> validators = dataMap.get(field);
				// 验证当前字段信息
				error = this.validateField(field, value, validators);
				// 400 Bad Request
				if (null != error) {
					webRef.setResponse(null, StatusCode.BAD_REQUEST, error);
					break;
				}
			}
		} else {
			// 500 Internal Server
			error = new InternalServerErrorException(getClass());
			webRef.setResponse(null, StatusCode.INTERNAL_SERVER_ERROR, error);
		}
		if (null == error) {
			webRef.setResponse(null, StatusCode.OK, error);
		}
		return error;
	}

	private AbstractWebException validateField(final String name, final String value,
			final List<RuleModel> validators) {
		AbstractWebException error = null;
		// Fix: Null Pointer，因为validators是从Map中取得的，所以必须判断是否为null
		if (null != validators && !validators.isEmpty()) {
			for (final RuleModel validator : validators) {
				error = this.validateField(name, value, validator);
				if (null != error) {
					break;
				}
			}
		}
		return error;
	}

	private AbstractWebException validateField(final String paramName, final String paramValue,
			final RuleModel ruleModel) {
		AbstractWebException error = null;
		try {
			// 1.验证Validator是否存在
			final String validatorCls = ruleModel.getComponentClass();
			this.checkValidator(validatorCls);
			// 2.从value中提取值信息
			final String typeCls = ruleModel.getType().getClassName();
			final Value<?> value = instance(typeCls, paramValue);
			// 3.提取配置信息
			final JsonObject config = ruleModel.getConfig();
			// 4.验证结果
			final WebValidator validator = instance(validatorCls);
			final boolean ret = validator.validate(paramName, value, config);
			// 5.验证失败，特殊的Exception
			if (!ret) {
				error = new ValidationFailureException(ruleModel.getErrorMessage());
			}
		} catch (AbstractWebException ex) {
			error = ex;
		}
		// TODO: Debug
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return error;
	}

	private void checkValidator(final String className) throws AbstractWebException {
		// 1.检查是否存在这个类
		Class<?> clazz = Instance.clazz(className);
		if (null == clazz) {
			final AbstractWebException error = new ValidatorNotFoundException(getClass(), className);
			debug(LOGGER, "SYS.VX.CLASS", error, "Validator", className);
			throw error;
		} else {
			// 2.递归检索接口
			final List<Class<?>> interfaces = Instance.interfaces(className);
			boolean flag = false;
			for (final Class<?> item : interfaces) {
				if (item == WebValidator.class) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				final AbstractWebException error = new ValidatorInvalidException(getClass(), className);
				debug(LOGGER, "SYS.VX.INVALID", error, "Validator", className);
				throw error;
			}
		}
	}

	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
