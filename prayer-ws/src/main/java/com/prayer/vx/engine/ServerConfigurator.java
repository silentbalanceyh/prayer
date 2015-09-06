package com.prayer.vx.engine;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.util.PropertyKit;

import io.vertx.core.http.HttpServerOptions;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ServerConfigurator {
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
	public HttpServerOptions getOptions(){
		HttpServerOptions options = new HttpServerOptions();
		if(null != this.LOADER){
			// Basic Options
			options.setPort(this.LOADER.getInt("server.port"));
			options.setHost(this.LOADER.getString("server.host"));
			// Whether support compression
			options.setCompressionSupported(this.LOADER.getBoolean("server.compression.support"));
			options.setAcceptBacklog(this.LOADER.getInt("server.accept.backlog"));
			options.setClientAuthRequired(this.LOADER.getBoolean("server.client.auth.required"));
		}else{
			info(LOGGER, "[I-VX] Server property file has not been initialized successfully !");
		}
		return options;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
