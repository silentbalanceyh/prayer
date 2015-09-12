package com.prayer.handler.web;

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
import com.prayer.exception.web.ConvertorMultiException;
import com.prayer.kernel.Value;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.h2.vx.RuleModel;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.uca.WebConvertor;
import com.prayer.uca.assistant.ErrGenerator;
import com.prayer.uca.assistant.Interruptor;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ConversionHandler implements Handler<RoutingContext> {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ConversionHandler.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient ConfigService service;

	// ~ Static Block ========================================
	/** 创建方法 **/
	public static ConversionHandler create() {
		return new ConversionHandler();
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public ConversionHandler() {
		this.service = singleton(ConfigSevImpl.class);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================

	/** **/
	@Override
	public void handle(@NotNull final RoutingContext routingContext) {
		info(LOGGER, "[VX-I] Handler : " + getClass().getName() + ", Order : " + Constants.VX_OD_CONVERTOR);
		// 1.从Context中提取参数信息
		final UriModel uri = routingContext.get(Constants.VX_CTX_URI);
		// 2.查找Convertors的数据
		final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = this.service
				.findConvertors(uri.getUniqueId());
		final RestfulResult webRet = RestfulResult.create();
		// 3.执行Dispatcher功能
		AbstractWebException error = this.requestDispatch(result, webRet, routingContext);
		if (null == error) {
			// SUCCESS ->
			routingContext.next();
		} else {
			// 触发错误信息
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
			try {
				final JsonObject updatedParams = new JsonObject();
				for (final String field : params.fieldNames()) {
					final String value = params.getString(field);
					updatedParams.put(field, value);
					final List<RuleModel> convertors = dataMap.get(field);
					// Convertor不可以有多个
					if (null != convertors) {
						if (Constants.ONE < convertors.size()) {
							error = new ConvertorMultiException(getClass(), field);
							break;
						} else if (Constants.ONE == convertors.size()) {
							final RuleModel convertor = convertors.get(Constants.ZERO);
							final String cvRet = this.convertField(field, value, convertor);
							updatedParams.put(field, cvRet);
						}
					}
				}
				// 将更新过后的参数放到RoutingContext中
				context.put(Constants.VX_CTX_PARAMS, updatedParams);
			} catch (AbstractWebException ex) {
				error = ex;
			}
			// TODO: Debug
			catch (Exception ex) {
				ex.printStackTrace();
			}
			if (null != error) {
				final RestfulResult webRet = ErrGenerator.error400(error);
				webRef.copyFrom(webRet);
			}
		} else {
			// 500 Internal Server
			error = ErrGenerator.error500(webRef, getClass());
		}
		return error;
	}

	private String convertField(final String paramName, final String paramValue, final RuleModel ruleModel)
			throws AbstractWebException {
		// 1.验证Convertor是否合法
		final String convertorCls = ruleModel.getComponentClass();
		Interruptor.interruptClass(getClass(), convertorCls, "Convertor");
		Interruptor.interruptImplements(getClass(), convertorCls, WebConvertor.class);
		// 2.提取Convertor中的
		final String typeCls = ruleModel.getType().getClassName();
		final Value<?> value = instance(typeCls, paramValue);
		// 3.提取配置信息
		final JsonObject config = ruleModel.getConfig();
		// 4.执行转换
		final WebConvertor convertor = instance(convertorCls);
		final Value<?> ret = convertor.convert(paramName, value, config);
		// 5.最终返回literal，转换失败的时候使用原值
		return null == ret ? paramValue : ret.literal();
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
