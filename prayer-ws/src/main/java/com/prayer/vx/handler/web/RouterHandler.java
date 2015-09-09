package com.prayer.vx.handler.web;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ParamType;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractException;
import com.prayer.exception.AbstractWebException;
import com.prayer.exception.web.InternalServerErrorException;
import com.prayer.exception.web.MethodNotAllowedException;
import com.prayer.exception.web.RequiredParamMissingException;
import com.prayer.exception.web.UriSpecificationMissingException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.bus.web.StatusCode;
import com.prayer.model.h2.vx.UriModel;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
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
	public void handle(final RoutingContext routingContext) {
		// 1.获取请求Request和相应Response引用
		final HttpServerRequest request = routingContext.request();
		final HttpServerResponse response = routingContext.response();

		// 2.从系统中按URI读取接口规范
		final ServiceResult<UriModel> result = this.service.findUri(request.path());
		final RestfulResult webRet = new RestfulResult(StatusCode.OK);

		// 3.请求转发，去除掉Error过后的信息
		AbstractException error = this.requestDispatch(result, webRet, routingContext);

		// 4.根据Error设置
		if (null == error) {
			// SUCCESS -->
			// 5.1.保存UriModel的数据库ID
			final UriModel uri = result.getResult();
			routingContext.put(Constants.VX_CTX_URI_ID, uri.getUniqueId());

			// 6.1.设置参数到Context中提供给下一个Handler处理
			final JsonObject params = extractParams(routingContext, uri);
			routingContext.put(Constants.VX_CTX_PARAMS, params);
			routingContext.next();
		} else {
			response.setStatusCode(webRet.getStatusCode().status());
			response.setStatusMessage(webRet.getErrorMessage());

			// 5.2.保存RestfulResult到Context中
			info(LOGGER, "RestfulResult = " + webRet);
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
				retJson.put(entity.getKey(), entity.getValue());
			}
		} else if (ParamType.FORM == uri.getParamType()) {
			retJson.clear();
			final MultiMap params = request.formAttributes();
			final Iterator<Map.Entry<String, String>> kvPair = params.iterator();
			while (kvPair.hasNext()) {
				final Map.Entry<String, String> entity = kvPair.next();
				retJson.put(entity.getKey(), entity.getValue());
			}
		} else {
			retJson = context.getBodyAsJson();
		}
		return retJson;
	}

	private AbstractWebException requestDispatch(final ServiceResult<UriModel> result, final RestfulResult webRef,
			final RoutingContext context) {
		AbstractWebException error = null;
		final HttpServerRequest request = context.request();
		if (ResponseCode.SUCCESS == result.getResponseCode()) {
			final UriModel uriSpec = result.getResult();
			if (null == uriSpec) {
				// 404 Resources Not Found
				error = new UriSpecificationMissingException(getClass(), request.path());
				webRef.setResponse(null, StatusCode.NOT_FOUND, error);
			} else {
				if (uriSpec.getMethod() == request.method()) {
					error = requestParams(uriSpec, context);
					if (null != error) {
						// 400 Bad Request
						webRef.setResponse(null, StatusCode.BAD_REQUEST, error);
					}
				} else {
					// 405 Method Not Allowed
					error = new MethodNotAllowedException(getClass(), request.method().toString());
					webRef.setResponse(null, StatusCode.METHOD_NOT_ALLOWED, error);
				}
			}
		} else {
			// 500 Internal Server
			error = new InternalServerErrorException(getClass());
			webRef.setResponse(null, StatusCode.INTERNAL_SERVER_ERROR, error);
		}
		return error;
	}

	// 参数规范的处理流程
	private AbstractWebException requestParams(final UriModel uri, final RoutingContext context) {
		AbstractWebException error = null;
		final List<String> paramList = uri.getRequiredParam();
		final HttpServerRequest request = context.request();
		if (ParamType.QUERY == uri.getParamType()) {
			// 从Query String中获取参数
			final MultiMap params = request.params();
			for (final String param : paramList) {
				if (null == params.get(param)) {
					error = new RequiredParamMissingException(getClass(), request.path(), uri.getParamType().toString(),
							param);
					break;
				}
			}
		} else if (ParamType.FORM == uri.getParamType()) {
			// 从Form中获取参数
			final MultiMap params = request.formAttributes();
			for (final String param : paramList) {
				if (null == params.get(param)) {
					error = new RequiredParamMissingException(getClass(), request.path(), uri.getParamType().toString(),
							param);
					break;
				}
			}
		} else {
			// 从Body中直接获取参数
			final JsonObject params = context.getBodyAsJson();
			for (final String param : paramList) {
				if (null == params.getString(param)) {
					error = new RequiredParamMissingException(getClass(), request.path(), uri.getParamType().toString(),
							param);
					break;
				}
			}
		}
		return error;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
