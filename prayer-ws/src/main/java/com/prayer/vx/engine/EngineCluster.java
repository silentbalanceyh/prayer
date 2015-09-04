package com.prayer.vx.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.util.PropertyKit;

/**
 * 
 * @author Lang
 *
 */
public class EngineCluster {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(EngineCluster.class);
	// ~ Instance Fields =====================================
	/** 读取全局vertx的属性配置 **/
	private transient final PropertyKit LOADER = new PropertyKit(getClass(), "/vertx.properties");
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
