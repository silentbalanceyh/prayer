package com.prayer.assistant;

import static com.prayer.assistant.WebLogger.info;

import javax.net.ssl.SSLEngineResult.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HttpHeaders;
import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.exception.AbstractWebException;
import com.prayer.exception.web.InternalServerErrorException;
import com.prayer.exception.web.NotAuthorizationException;
import com.prayer.model.web.Responsor;
import com.prayer.model.web.StatusCode;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
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
     */
    public static void error500(@NotNull final Class<?> clazz, @NotNull final RoutingContext context) {
        final AbstractWebException error = new InternalServerErrorException(clazz);
        final Responsor responsor = Responsor.error(error);
        info(LOGGER, WebLogger.E_ERROR_HTTP, StatusCode.INTERNAL_SERVER_ERROR.status(),
                StatusCode.INTERNAL_SERVER_ERROR.toString(), error.getErrorMessage());
        context.put(Constants.KEY.CTX_ERROR, responsor);
        context.fail(responsor.getStatus().status());
    }

    /**
     * 
     * @param clazz
     * @param context
     */
    public static void error401(@NotNull final Class<?> clazz, @NotNull final RoutingContext context) {
        final AbstractWebException error = new NotAuthorizationException(clazz, context.request().path());
        final Responsor responsor = Responsor.failure(StatusCode.UNAUTHORIZED, error);
        info(LOGGER, WebLogger.E_ERROR_HTTP, StatusCode.UNAUTHORIZED.status(), StatusCode.UNAUTHORIZED.toString(),
                error.getErrorMessage());
        context.put(Constants.KEY.CTX_ERROR, responsor);
        context.fail(responsor.getStatus().status());
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
        info(LOGGER, WebLogger.E_ERROR_HTTP, StatusCode.UNAUTHORIZED.status(), StatusCode.UNAUTHORIZED.toString(),
                error.getErrorMessage());
        context.put(Constants.KEY.CTX_ERROR, responsor);
        context.fail(responsor.getStatus().status());
    }

    /**
     * 
     * @param response
     * @param content
     */
    public static void success(@NotNull final HttpServerResponse response, @NotNull final String content) {
        final int length = content.getBytes(Resources.SYS_ENCODING).length;
        response.setStatusCode(StatusCode.OK.status());
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
        response.write(content, Resources.SYS_ENCODING.name());
        response.end();
        response.close();
    }
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
