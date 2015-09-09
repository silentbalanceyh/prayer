package com.prayer.vx.handler.web;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
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
public class PreFailureHandler implements ErrorHandler {

	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(PreFailureHandler.class);

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
		retData.put("statusCode", webRet.getStatusCode().status());
		retData.put("error", webRet.getStatusCode().toString());
		retData.put("errorMessage", webRet.getErrorMessage());
		// 3.必须加入长度信息，处理Body部分内容
		final String content = retData.encodePrettily();
		// 4.日志记录
		info(LOGGER, webRet.getErrorMessage() + ", HttpStatus = " + webRet.getStatusCode().toString());
		// 5.处理响应信息
		final HttpServerResponse response = context.response();
		// TODO: 后期需要改动，测试因为使用浏览器，暂时使用这种
		response.putHeader("Context-Type", "text/plain");
		response.putHeader("Content-Length", String.valueOf(content.length()));
		response.setStatusCode(webRet.getStatusCode().status());
		response.setStatusMessage(webRet.getErrorMessage());
		response.write(content);
		response.end();
		response.close();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
