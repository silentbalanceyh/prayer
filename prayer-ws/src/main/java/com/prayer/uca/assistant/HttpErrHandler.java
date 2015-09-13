package com.prayer.uca.assistant;

import static com.prayer.uca.assistant.WebLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.exception.AbstractWebException;
import com.prayer.exception.web.BodyParamDecodingException;
import com.prayer.exception.web.InternalServerErrorException;
import com.prayer.exception.web.MethodNotAllowedException;
import com.prayer.exception.web.RequiredParamMissingException;
import com.prayer.exception.web.UriSpecificationMissingException;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.bus.web.StatusCode;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class HttpErrHandler {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpErrHandler.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/** **/
	@NotNull
	public static void handle500Error(@NotNull final Class<?> clazz, @NotNull final RoutingContext context) {
		final RestfulResult webRet = RestfulResult.create();
		error500(webRet, clazz);
		context.put(Constants.VX_CTX_ERROR, webRet);
		context.fail(webRet.getStatusCode().status());
	}

	/** **/
	@NotNull
	public static AbstractWebException error404(@NotNull final RestfulResult webRef, @NotNull final Class<?> clazz,
			@NotNull @NotBlank @NotEmpty final String path) {
		final AbstractWebException error = new UriSpecificationMissingException(clazz, path);
		webRef.setResponse(StatusCode.NOT_FOUND, error);
		webRef.setResult(produceResult(webRef));
		info(LOGGER, WebLogger.E_ERROR_HTTP, StatusCode.NOT_FOUND.status(), StatusCode.NOT_FOUND.toString(),
				error.getErrorMessage());
		return error;
	}

	/** **/
	@NotNull
	public static AbstractWebException error405(@NotNull final RestfulResult webRef, @NotNull final Class<?> clazz,
			@NotNull final HttpMethod method) {
		final AbstractWebException error = new MethodNotAllowedException(clazz, method.toString());
		webRef.setResponse(StatusCode.METHOD_NOT_ALLOWED, error);
		webRef.setResult(produceResult(webRef));
		info(LOGGER, WebLogger.E_ERROR_HTTP, StatusCode.METHOD_NOT_ALLOWED.status(),
				StatusCode.METHOD_NOT_ALLOWED.toString(), error.getErrorMessage());
		return error;
	}

	/** **/
	@NotNull
	public static AbstractWebException error500(@NotNull final RestfulResult webRef, @NotNull final Class<?> clazz) {
		final AbstractWebException error = new InternalServerErrorException(clazz);
		webRef.setResponse(StatusCode.INTERNAL_SERVER_ERROR, error);
		webRef.setResult(produceResult(webRef));
		info(LOGGER, WebLogger.E_ERROR_HTTP, StatusCode.INTERNAL_SERVER_ERROR.status(),
				StatusCode.INTERNAL_SERVER_ERROR.toString(), error.getErrorMessage());
		return error;
	}

	/** **/
	@NotNull
	public static AbstractWebException error400E30001(@NotNull final RestfulResult webRef,
			@NotNull final Class<?> clazz, @NotNull @NotBlank @NotEmpty final String path,
			@NotNull @NotBlank @NotEmpty final String paramType, @NotNull @NotBlank @NotEmpty final String paramName) {
		final AbstractWebException error = new RequiredParamMissingException(clazz, path, paramType, paramName);
		webRef.setResponse(StatusCode.BAD_REQUEST, error);
		webRef.setResult(produceResult(webRef));
		info(LOGGER, WebLogger.E_ERROR_HTTP, StatusCode.BAD_REQUEST.status(), StatusCode.BAD_REQUEST.toString(),
				error.getErrorMessage());
		return error;
	}

	/** **/
	@NotNull
	public static AbstractWebException error400E30010(@NotNull final RestfulResult webRef,
			@NotNull final Class<?> clazz, @NotNull @NotBlank @NotEmpty final String path) {
		final AbstractWebException error = new BodyParamDecodingException(clazz, path);
		webRef.setResponse(StatusCode.BAD_REQUEST, error);
		webRef.setResult(produceResult(webRef));
		info(LOGGER, WebLogger.E_ERROR_HTTP, StatusCode.BAD_REQUEST.status(), StatusCode.BAD_REQUEST.toString(),
				error.getErrorMessage());
		return error;
	}

	/** **/
	@NotNull
	public static RestfulResult error400(@NotNull final AbstractWebException error) {
		final RestfulResult webRet = RestfulResult.create();
		webRet.setResponse(StatusCode.BAD_REQUEST, error);
		webRet.setResult(produceResult(webRet));
		info(LOGGER, WebLogger.E_ERROR_HTTP, StatusCode.BAD_REQUEST.status(), StatusCode.BAD_REQUEST.toString(),
				error.getErrorMessage());
		return webRet;
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private static JsonObject produceResult(final RestfulResult webRef) {
		// Error Object
		final JsonObject response = new JsonObject();
		response.put(Constants.STATUS_CODE, webRef.getStatusCode().status());
		response.put(Constants.ERROR, webRef.getStatusCode().toString());
		response.put(Constants.ERROR_MSG, webRef.getErrorMessage());
		response.put(Constants.RESPONSE, webRef.getResponseCode().toString());
		return response;
	}

	private HttpErrHandler() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
