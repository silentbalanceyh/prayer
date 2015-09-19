package com.prayer.vx.configurator;

import static com.prayer.assistant.WebLogger.error;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
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
	private transient final PropertyKit LOADER = new PropertyKit(ServerConfigurator.class, Resources.SEV_CFG_FILE);

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

	/** H2 Database 创建 **/
	public Server getH2Database() throws SQLException {
		Server server = null;
		if (null == this.LOADER) {
			error(LOGGER, "H2 Database property file has not been initialized successfully !");
		} else {
			final int port = this.LOADER.getInt("h2.database.tcp.port");
			final boolean allowOthers = this.LOADER.getBoolean("h2.database.tcp.allow.others");
			final List<String> params = new ArrayList<>();
			params.add("-tcpPort");
			params.add(String.valueOf(port));
			if (allowOthers) {
				params.add("-tcpAllowOthers");
			}
			server = Server.createTcpServer(params.toArray(new String[] {}));
		}
		return server;
	}

	/** H2 Web Console **/
	public Server getH2WebConsole() throws SQLException {
		Server server = null;
		if (null == this.LOADER) {
			error(LOGGER, "H2 Web Console property file has not been initialized successfully !");
		} else {
			final int port = this.LOADER.getInt("h2.database.web.port");
			final boolean allowOthers = this.LOADER.getBoolean("h2.database.web.allow.others");
			final List<String> params = new ArrayList<>();
			params.add("-webPort");
			params.add(String.valueOf(port));
			if (allowOthers) {
				params.add("-webAllowOthers");
			}
			server = Server.createWebServer(params.toArray(new String[] {}));
		}
		return server;
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
