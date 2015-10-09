package com.prayer.server;

import static com.prayer.util.WebLogger.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
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

    /** Api Remote 地址 **/
    public String getEndPoint() {
        return LOADER.getString("server.endpoint");
    }
    /**
     * 
     * @return
     */
    public HttpServerOptions getOptions() {
        final HttpServerOptions options = new HttpServerOptions();
        if (null == this.LOADER) {
            error(LOGGER, "Server property loader has not been initialized successfully !");
        } else {
            // Basic Options
            options.setPort(this.LOADER.getInt("server.client.port"));
            options.setHost(this.LOADER.getString("server.client.host"));
            // Whether support compression
            options.setCompressionSupported(this.LOADER.getBoolean("server.compression.support"));
            options.setAcceptBacklog(this.LOADER.getInt("server.accept.backlog"));
        }
        return options;
    }
    // ~ Private Methods =====================================

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
