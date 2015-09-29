package com.prayer.assistant;

import javax.net.ssl.SSLEngineResult.Status;

import com.google.common.net.HttpHeaders;
import com.prayer.constant.Resources;
import com.prayer.model.bus.web.StatusCode;

import io.vertx.core.http.HttpServerResponse;
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
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
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
