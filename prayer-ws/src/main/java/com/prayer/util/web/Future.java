package com.prayer.util.web;

import static com.prayer.util.debug.Log.peError;

import javax.net.ssl.SSLEngineResult.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HttpHeaders;
import com.prayer.constant.Resources;
import com.prayer.exception.web.BodyParamDecodingException;
import com.prayer.exception.web._500InternalServerErrorException;
import com.prayer.exception.web._405MethodNotAllowedException;
import com.prayer.exception.web.NotAuthorizationException;
import com.prayer.exception.web.RequiredParamMissingException;
import com.prayer.exception.web._404UriSpecificationMissingException;
import com.prayer.facade.constant.Constants;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.web.Responsor;
import com.prayer.model.web.StatusCode;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
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
public final class Future {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Future.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /**
     * 
     * @param clazz
     * @param context
     * @param error
     */
    public static void error400(@NotNull final Class<?> clazz, @NotNull final RoutingContext context,
            @NotNull final AbstractWebException error) {
        final Responsor responsor = Responsor.failure(StatusCode.BAD_REQUEST, error);
        peError(LOGGER,error);
        context.put(Constants.KEY.CTX_RESPONSOR, responsor);
        context.fail(responsor.getStatus().code());
    }

    /**
     * 
     * @param clazz
     * @param context
     * @param errorCode
     * @param params
     */
    public static void error400(@NotNull final Class<?> clazz, @NotNull final RoutingContext context,
            @NotNull final int errorCode, final Object... params) {
        AbstractWebException error = null;
        if (-30010 == errorCode) {
            error = new BodyParamDecodingException(clazz, params[0].toString());
        } else if (-30001 == errorCode) {
            error = new RequiredParamMissingException(clazz, params[0].toString(), params[1].toString(),
                    params[2].toString());
        }
        final Responsor responsor = Responsor.failure(StatusCode.BAD_REQUEST, error);
        peError(LOGGER,error);
        context.put(Constants.KEY.CTX_RESPONSOR, responsor);
        context.fail(responsor.getStatus().code());
    }

    /**
     * 
     * @param clazz
     * @param context
     * @param path
     */
    public static void error404(@NotNull final Class<?> clazz, @NotNull final RoutingContext context,
            @NotNull @NotBlank @NotEmpty final String path) {
        final AbstractWebException error = new _404UriSpecificationMissingException(clazz, path);
        final Responsor responsor = Responsor.failure(StatusCode.NOT_FOUND, error);
        peError(LOGGER,error);
        context.put(Constants.KEY.CTX_RESPONSOR, responsor);
        context.fail(responsor.getStatus().code());
    }

    /**
     * 
     * @param clazz
     * @param context
     * @param method
     */
    public static void error405(@NotNull final Class<?> clazz, @NotNull final RoutingContext context,
            @NotNull final HttpMethod method) {
        final AbstractWebException error = new _405MethodNotAllowedException(clazz, method.toString());
        final Responsor responsor = Responsor.failure(StatusCode.METHOD_NOT_ALLOWED, error);
        peError(LOGGER,error);
        context.put(Constants.KEY.CTX_RESPONSOR, responsor);
        context.fail(responsor.getStatus().code());
    }

    /**
     * 
     * @param clazz
     * @param context
     */
    public static void error500(@NotNull final Class<?> clazz, @NotNull final RoutingContext context) {
        final AbstractWebException error = new _500InternalServerErrorException(clazz);
        final Responsor responsor = Responsor.error(error);
        peError(LOGGER,error);
        context.put(Constants.KEY.CTX_RESPONSOR, responsor);
        context.fail(responsor.getStatus().code());
    }

    /**
     * 
     * @param clazz
     * @param context
     */
    public static void error401(@NotNull final Class<?> clazz, @NotNull final RoutingContext context) {
        final AbstractWebException error = new NotAuthorizationException(clazz, context.request().path());
        final Responsor responsor = Responsor.failure(StatusCode.UNAUTHORIZED, error);
        peError(LOGGER,error);
        context.put(Constants.KEY.CTX_RESPONSOR, responsor);
        context.fail(responsor.getStatus().code());
    }

    /**
     * 
     * @param clazz
     * @param context
     * @param message
     */
    public static void error401(@NotNull final Class<?> clazz, @NotNull final RoutingContext context,
            @NotNull final String message) {
        final AbstractWebException error = new NotAuthorizationException(clazz, context.request().path());
        final Responsor responsor = Responsor.failure(StatusCode.UNAUTHORIZED, error, message);
        peError(LOGGER,error);
        context.put(Constants.KEY.CTX_RESPONSOR, responsor);
        context.fail(responsor.getStatus().code());
    }

    /**
     * 
     * @param response
     * @param content
     */
    public static void success(@NotNull final HttpServerResponse response, @NotNull final String content) {
        final int length = content.getBytes(Resources.SYS_ENCODING).length;
        response.setStatusCode(StatusCode.OK.code());
        response.setStatusMessage(Status.OK.toString());
        response.putHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=" + Resources.SYS_ENCODING.toString());
        response.putHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(length));
        response.end(content, Resources.SYS_ENCODING.name());
        response.close();
    }

    /**
     * 
     * @param response
     * @param content
     * @param statusCode
     * @param statusMsg
     */
    public static void failure(@NotNull final HttpServerResponse response, @NotNull final String content,
            final int statusCode, @NotNull final String statusMsg) {
        final int length = content.getBytes(Resources.SYS_ENCODING).length;
        response.setStatusCode(statusCode);
        response.setStatusMessage(statusMsg);
        response.putHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=" + Resources.SYS_ENCODING.toString());
        response.putHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(length));
        response.end(content, Resources.SYS_ENCODING.name());
        response.close();
    }
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Future(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
