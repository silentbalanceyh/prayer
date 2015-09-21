package com.prayer.client.configurator;

import static com.prayer.assistant.WebLogger.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.util.PropertyKit;

import io.vertx.core.http.HttpServerOptions;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ServerConfigurator { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfigurator.class);
	/** Server Config File **/
	private transient final PropertyKit LOADER = new PropertyKit(ServerConfigurator.class, Constants.PROP_SERVER);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================

	/** 获取Http Server Options的选项信息 **/
	public HttpServerOptions getApiOptions() {
		return this.getOptions("server.api.port");
	}

	/** Web应用的选项信息 **/
	public HttpServerOptions getWebOptions() {
		return this.getOptions("server.web.port");
	}

	/** Api Remote 地址 **/
	public String getEndPoint() {
		final StringBuilder apiUrl = new StringBuilder();
		apiUrl.append("http://").append(LOADER.getString("server.host")).append(Symbol.COLON)
				.append(LOADER.getString("server.api.port")).append("/api");
		return apiUrl.toString();
	}

	// ~ Private Methods =====================================

	private HttpServerOptions getOptions(final String portKey) {
		final HttpServerOptions options = new HttpServerOptions();
		if (null == this.LOADER) {
			error(LOGGER, "Server property loader has not been initialized successfully !");
		} else {
			// Basic Options
			options.setPort(this.LOADER.getInt(portKey));
			options.setHost(this.LOADER.getString("server.host"));
			// Whether support compression
			options.setCompressionSupported(this.LOADER.getBoolean("server.compression.support"));
			options.setAcceptBacklog(this.LOADER.getInt("server.accept.backlog"));
			options.setClientAuthRequired(this.LOADER.getBoolean("server.client.auth.required"));
		}
		return options;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
