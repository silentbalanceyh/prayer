package com.prayer.handler.web;

import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.constant.Constants;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.bus.web.StatusCode;
import com.prayer.model.h2.vx.RuleModel;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ConversionHandler implements Handler<RoutingContext> {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ConversionHandler.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient ConfigService service;

	// ~ Static Block ========================================
	/** 创建方法 **/
	public static ConversionHandler create() {
		return new ConversionHandler();
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public ConversionHandler() {
		this.service = singleton(ConfigSevImpl.class);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================

	/** **/
	@Override
	public void handle(@NotNull final RoutingContext routingContext) {
		System.out.println("Conversion");
		// 1.从Context中提取参数信息
		final String uriId = routingContext.get(Constants.VX_CTX_URI_ID);
		final HttpServerResponse response = routingContext.response();
		// 2.查找Convertors的数据
		final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = this.service.findConvertors(uriId);
		final RestfulResult webRet = new RestfulResult(StatusCode.OK);
		// 3.执行Dispatcher功能
		routingContext.next();
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
