package com.prayer.handler.web;

import static com.prayer.uca.assistant.WebLogger.error;
import static com.prayer.uca.assistant.WebLogger.info;
import static com.prayer.util.Error.debug;
import static com.prayer.util.Instance.singleton;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.ParamType;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractException;
import com.prayer.exception.AbstractWebException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.uca.assistant.HttpErrHandler;
import com.prayer.uca.assistant.WebLogger;
import com.prayer.util.StringKit;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 【Step 1】参数的基本检查，访问接口的第一步
 * 
 * @author Lang
 *
 */
@Guarded
public class RouterHandler implements Handler<RoutingContext> {

	// ~ Static Fields =======================================

	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RouterHandler.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient final ConfigService service;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/** 创建方法 **/
	public static RouterHandler create() {
		return new RouterHandler();
	}

	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public RouterHandler() {
		this.service = singleton(ConfigSevImpl.class);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================

	/**
	 * 
	 */
	@Override
	public void handle(@NotNull final RoutingContext routingContext) {
		info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), Constants.VX_OD_ROUTER);
		// 1.获取请求Request和相应Response引用
		final HttpServerRequest request = routingContext.request();

		// 2.从系统中按URI读取接口规范
		final ServiceResult<ConcurrentMap<HttpMethod, UriModel>> result = this.service.findUri(request.path());
		final RestfulResult webRet = RestfulResult.create();

		// 3.请求转发，去除掉Error过后的信息
		AbstractException error = this.requestDispatch(result, webRet, routingContext);

		// 4.根据Error设置
		if (null == error) {
			// SUCCESS -->
			// 5.1.保存UriModel到Context中
			final UriModel uri = result.getResult().get(request.method());
			routingContext.put(Constants.VX_CTX_URI, uri);
			// 6.1.设置参数到Context中提供给下一个Handler处理
			final JsonObject params = extractParams(routingContext, uri);
			routingContext.put(Constants.VX_CTX_PARAMS, params);
			routingContext.next();
		} else {
			// 5.2.保存RestfulResult到Context中
			routingContext.put(Constants.VX_CTX_ERROR, webRet);
			routingContext.fail(webRet.getStatusCode().status());
		}
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private JsonObject extractParams(final RoutingContext context, final UriModel uri) {
		JsonObject retJson = new JsonObject();
		final HttpServerRequest request = context.request();
		if (ParamType.QUERY == uri.getParamType()) {
			retJson.clear();
			final MultiMap params = request.params();
			final Iterator<Map.Entry<String, String>> kvPair = params.iterator();
			while (kvPair.hasNext()) {
				final Map.Entry<String, String> entity = kvPair.next();
				retJson.put(decodeURL(entity.getKey()), decodeURL(entity.getValue()));
			}
		} else if (ParamType.FORM == uri.getParamType()) {
			retJson.clear();
			final MultiMap params = request.formAttributes();
			final Iterator<Map.Entry<String, String>> kvPair = params.iterator();
			while (kvPair.hasNext()) {
				final Map.Entry<String, String> entity = kvPair.next();
				retJson.put(decodeURL(entity.getKey()), decodeURL(entity.getValue()));
			}
		} else {
			retJson = new JsonObject(decodeURL(context.getBodyAsJson().encodePrettily()));
		}
		return retJson;
	}

	private AbstractWebException requestDispatch(final ServiceResult<ConcurrentMap<HttpMethod, UriModel>> result,
			final RestfulResult webRef, final RoutingContext context) {
		AbstractWebException error = null;
		final HttpServerRequest request = context.request();
		if (ResponseCode.SUCCESS == result.getResponseCode()) {
			final ConcurrentMap<HttpMethod, UriModel> uriMap = result.getResult();
			if (uriMap.isEmpty()) {
				// 404 Resources Not Found
				error = HttpErrHandler.error404(webRef, getClass(), request.path());
			} else {
				final UriModel uriSpec = uriMap.get(request.method());
				if (null != uriSpec) {
					final String errParam = getErrorParam(uriSpec, context);
					if (null != errParam) {
						if (errParam.equals("DECODE")) {
							error = HttpErrHandler.error400E30010(webRef, getClass(), request.path());
						} else {
							error = HttpErrHandler.error400E30001(webRef, getClass(), request.path(),
									uriSpec.getParamType().toString(), errParam);
						}
					}
				} else {
					// 405 Method Not Allowed
					error = HttpErrHandler.error405(webRef, getClass(), request.method());
				}
			}
		} else {
			// 500 Internal Server
			error = HttpErrHandler.error500(webRef, getClass());
		}
		return error;
	}

	// 参数规范的处理流程
	private String getErrorParam(final UriModel uri, final RoutingContext context) {
		String retParam = null;
		final List<String> paramList = uri.getRequiredParam();
		final HttpServerRequest request = context.request();
		if (ParamType.QUERY == uri.getParamType()) {
			// 从Query String中获取参数
			final MultiMap params = request.params();
			for (final String param : paramList) {
				if (StringKit.isNil(params.get(param))) {
					retParam = param;
					break;
				}
			}
		} else if (ParamType.FORM == uri.getParamType()) {
			// 从Form中获取参数
			final MultiMap params = request.formAttributes();
			for (final String param : paramList) {
				if (StringKit.isNil(params.get(param))) {
					retParam = param;
					break;
				}
			}
		} else {
			// 从Body中直接获取参数
			try {
				final JsonObject params = context.getBodyAsJson();
				for (final String param : paramList) {
					if (StringKit.isNil(params.getString(param))) {
						retParam = param;
						break;
					}
				}
			} catch (DecodeException ex) {
				retParam = "DECODE";
				error(LOGGER, WebLogger.E_COMMON_EXP, ex.toString());
			}

		}
		return retParam;
	}

	private String decodeURL(final String inputValue) {
		String ret = inputValue;
		try {
			ret = URLDecoder.decode(inputValue, Resources.SYS_ENCODING);
		} catch (UnsupportedEncodingException ex) {
			debug(LOGGER, "JVM.ENCODING", ex, Resources.SYS_ENCODING);
		}
		return ret;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}