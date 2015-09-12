package com.prayer.uca.assistant;

import static com.prayer.util.Error.info;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractWebException;
import com.prayer.exception.web.InternalServerErrorException;
import com.prayer.exception.web.MethodNotAllowedException;
import com.prayer.exception.web.RequiredParamMissingException;
import com.prayer.exception.web.UriSpecificationMissingException;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.bus.web.StatusCode;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
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
public final class ErrGenerator {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrGenerator.class);
	/** Status Code: 404,501 etc. **/
	public static final String STATUS_CODE = "statusCode";
	/** Status Code Error: NOT_FOUND, INTERNAL_ERROR etc. **/
	public static final String ERROR = "error";
	/** Error Message: Exception description **/
	public static final String ERROR_MSG = "errorMessage";
	/** SUCCESS/FAILURE/ERROR **/
	public static final String RESPONSE = "response";
	/** Error Http **/
	private static final String ERROR_HTTP = "[VX-E] Error Code : {0} -> {1}, error = {2}";

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/** **/
	@NotNull
	public static AbstractWebException error404(@NotNull final RestfulResult webRef, @NotNull final Class<?> clazz,
			@NotNull @NotBlank @NotEmpty final String path) {
		final AbstractWebException error = new UriSpecificationMissingException(clazz, path);
		webRef.setResponse(StatusCode.NOT_FOUND, error);
		webRef.setResult(produceResult(webRef));
		info(LOGGER, MessageFormat.format(ERROR_HTTP, StatusCode.NOT_FOUND.status(), StatusCode.NOT_FOUND.toString(),
				error));
		return error;
	}

	/** **/
	@NotNull
	public static AbstractWebException error405(@NotNull final RestfulResult webRef, @NotNull final Class<?> clazz,
			@NotNull final HttpMethod method) {
		final AbstractWebException error = new MethodNotAllowedException(clazz, method.toString());
		webRef.setResponse(StatusCode.METHOD_NOT_ALLOWED, error);
		webRef.setResult(produceResult(webRef));
		info(LOGGER, MessageFormat.format(ERROR_HTTP, StatusCode.METHOD_NOT_ALLOWED.status(),
				StatusCode.METHOD_NOT_ALLOWED.toString(), error));
		return error;
	}

	/** **/
	@NotNull
	public static AbstractWebException error500(@NotNull final RestfulResult webRef, @NotNull final Class<?> clazz) {
		final AbstractWebException error = new InternalServerErrorException(clazz);
		webRef.setResponse(StatusCode.INTERNAL_SERVER_ERROR, error);
		webRef.setResult(produceResult(webRef));
		info(LOGGER, MessageFormat.format(ERROR_HTTP, StatusCode.INTERNAL_SERVER_ERROR.status(),
				StatusCode.INTERNAL_SERVER_ERROR.toString(), error));
		return error;
	}

	/** **/
	@NotNull
	public static AbstractWebException error400E30001(@NotNull final RestfulResult webRef,
			@NotNull final Class<?> clazz, @NotNull @NotBlank @NotEmpty final String path,
			@NotNull @NotBlank @NotEmpty final String paramType, @NotNull @NotBlank @NotEmpty final String paramName) {
		AbstractWebException error = new RequiredParamMissingException(clazz, path, paramType, paramName);
		webRef.setResponse(StatusCode.BAD_REQUEST, error);
		webRef.setResult(produceResult(webRef));
		info(LOGGER, MessageFormat.format(ERROR_HTTP, StatusCode.BAD_REQUEST.status(),
				StatusCode.BAD_REQUEST.toString(), error));
		return error;
	}

	/** **/
	@NotNull
	public static RestfulResult error400(@NotNull final AbstractWebException error) {
		final RestfulResult webRet = RestfulResult.create();
		webRet.setResponse(StatusCode.BAD_REQUEST, error);
		webRet.setResult(produceResult(webRet));
		info(LOGGER, MessageFormat.format(ERROR_HTTP, StatusCode.BAD_REQUEST.status(),
				StatusCode.BAD_REQUEST.toString(), error));
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
		response.put(STATUS_CODE, webRef.getStatusCode().status());
		response.put(ERROR, webRef.getStatusCode().toString());
		response.put(ERROR_MSG, webRef.getErrorMessage());
		response.put(RESPONSE, webRef.getResponseCode().toString());
		return response;
	}

	private ErrGenerator() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
