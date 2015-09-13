package com.prayer.vx.configurator;

import static com.prayer.uca.assistant.WebLogger.error;
import static com.prayer.uca.assistant.WebLogger.info;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.uca.assistant.WebLogger;
import com.prayer.util.PropertyKit;

import io.vertx.core.http.HttpServerOptions;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ServerConfigurator {	// NOPMD
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
	// ~ Private Methods =====================================
	/** 获取Http Server Options的选项信息 **/
	public HttpServerOptions getOptions() {
		final HttpServerOptions options = new HttpServerOptions();
		if (null == this.LOADER) {
			error(LOGGER, "Server property file has not been initialized successfully !");
		} else {
			// Basic Options
			final int port = this.LOADER.getInt("server.port");
			final String host = this.LOADER.getString("server.host");
			options.setPort(port);
			options.setHost(host);
			info(LOGGER, WebLogger.I_SERVER_INFO, host, port);
			// Whether support compression
			options.setCompressionSupported(this.LOADER.getBoolean("server.compression.support"));
			options.setAcceptBacklog(this.LOADER.getInt("server.accept.backlog"));
			options.setClientAuthRequired(this.LOADER.getBoolean("server.client.auth.required"));
		}
		return options;
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
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
