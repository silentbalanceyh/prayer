package com.prayer.handler.web;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.model.bus.web.RestfulResult;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.ErrorHandler;

/**
 * 
 * @author Lang
 *
 */
public class FailureHandler implements ErrorHandler {

	// ~ Static Fields =======================================

	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(FailureHandler.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 直接从Context中获取处理结果
	 */
	@Override
	public void handle(final RoutingContext context) {
		// 1.从Context中获取处理结果
		final RestfulResult webRet = (RestfulResult) context.get(Constants.VX_CTX_ERROR);
		// 2.包装Error信息生成统一的Error格式
		final JsonObject retData = new JsonObject();
		// Fix: 防止Failure的空指针异常
		if (null != webRet) {
			retData.put("statusCode", webRet.getStatusCode().status());
			retData.put("error", webRet.getStatusCode().toString());
			retData.put("errorMessage", webRet.getErrorMessage());
			retData.put("response", webRet.getResponseCode().toString());
		}
		// 3.必须加入长度信息，处理Body部分内容
		final String content = retData.encodePrettily();
		// 5.处理响应信息
		final HttpServerResponse response = context.response();
		// TODO: 后期需要改动，测试因为使用浏览器，暂时使用这种
		response.putHeader("Context-Type", "application/json;charset=" + Resources.SYS_ENCODING);
		response.putHeader("Content-Length", String.valueOf(content.getBytes().length));
		try {
			response.setStatusCode(retData.getInteger("statusCode"));
			response.setStatusMessage(retData.getString("error"));
		} catch (Exception ex) {
			info(LOGGER, "[E-VX] Error Occurs.", ex);
		}
		response.write(content, Resources.SYS_ENCODING);
		response.end();
		response.close();
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
