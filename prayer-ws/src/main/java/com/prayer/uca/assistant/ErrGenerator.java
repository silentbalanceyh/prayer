package com.prayer.uca.assistant;

import static com.prayer.util.Instance.instance;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
	/** Status Code: 404,501 etc. **/
	public static final String STATUS_CODE = "statusCode";
	/** Status Code Error: NOT_FOUND, INTERNAL_ERROR etc. **/
	public static final String ERROR = "error";
	/** Error Message: Exception description **/
	public static final String ERROR_MSG = "errorMessage";
	/** SUCCESS/FAILURE/ERROR **/
	public static final String RESPONSE = "response";
	/** Error Map **/
	private static final ConcurrentMap<Integer, String> ERR_MAP = new ConcurrentHashMap<>();

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/** **/
	static {
		ERR_MAP.put(-30001, RequiredParamMissingException.class.getName());
	}

	// ~ Static Methods ======================================
	/** **/
	@NotNull
	public static AbstractWebException error404(@NotNull final RestfulResult webRef, @NotNull final Class<?> clazz,
			@NotNull @NotBlank @NotEmpty final String path) {
		final AbstractWebException error = new UriSpecificationMissingException(clazz, path);
		webRef.setResponse(StatusCode.NOT_FOUND, error);
		webRef.setResult(produceResult(webRef));
		return error;
	}

	/** **/
	@NotNull
	public static AbstractWebException error405(@NotNull final RestfulResult webRef, @NotNull final Class<?> clazz,
			@NotNull final HttpMethod method) {
		final AbstractWebException error = new MethodNotAllowedException(clazz, method.toString());
		webRef.setResponse(StatusCode.METHOD_NOT_ALLOWED, error);
		webRef.setResult(produceResult(webRef));
		return error;
	}

	/** **/
	@NotNull
	public static AbstractWebException error500(@NotNull final RestfulResult webRef, @NotNull final Class<?> clazz) {
		final AbstractWebException error = new InternalServerErrorException(clazz);
		webRef.setResponse(StatusCode.METHOD_NOT_ALLOWED, error);
		webRef.setResult(produceResult(webRef));
		return error;
	}

	/** **/
	@NotNull
	public static AbstractWebException error400(@NotNull final RestfulResult webRef, @NotNull final Class<?> clazz,
			final int errorCode, final Object... params) {
		final String className = ERR_MAP.get(errorCode);
		final AbstractWebException error = instance(className, params);
		webRef.setResponse(StatusCode.BAD_REQUEST, error);
		webRef.setResult(produceResult(webRef));
		return error;
	}

	/** **/
	@NotNull
	public static RestfulResult error400(@NotNull final AbstractWebException error) {
		final RestfulResult webRet = RestfulResult.create();
		webRet.setResponse(StatusCode.BAD_REQUEST, error);
		webRet.setResult(produceResult(webRet));
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
