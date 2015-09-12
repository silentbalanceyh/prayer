package com.prayer.vx.verticle;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.RecordService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.bus.impl.RecordSevImpl;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.web.RestfulResult;
import com.prayer.model.h2.vx.AddressModel;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public class RecordWorker extends AbstractVerticle {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordWorker.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient final ConfigService configSev;
	/** **/
	@NotNull
	private transient final RecordService recordSev;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public RecordWorker() {
		this.configSev = singleton(ConfigSevImpl.class);
		this.recordSev = singleton(RecordSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	@Override
	public void start() {
		// 1.获取当前Worker的类名
		final Class<?> workClass = getClass();
		info(LOGGER, "[VX-I] Verticle Worker : " + workClass.getName());
		// 2.获取元数据信息
		final ServiceResult<AddressModel> result = this.configSev.findAddress(workClass);
		if (ResponseCode.SUCCESS == result.getResponseCode()) {
			final AddressModel address = result.getResult();
			if (null != address) {
				// 3.从地址上消费Message
				final EventBus bus = vertx.eventBus();
				bus.consumer(address.getConsumerAddr(), message -> {
					final JsonObject params = (JsonObject) message.body();

					this.recordSev.queryRecord(params);

					final RestfulResult ret = RestfulResult.create();
					ret.setResult(new JsonObject("{\"result\":\"SUCCESS\"}"));
					message.reply(ret.getResult());
				});
			}
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
