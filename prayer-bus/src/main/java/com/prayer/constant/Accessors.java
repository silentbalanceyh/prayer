package com.prayer.constant;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 数据库配置工具类
 *
 * @author Lang
 * @see
 */
@Guarded
public final class Accessors {
	// ~ Static Fields =======================================
	/** 默认值 **/
	private static final String DEFAULT_DB_POOL = "com.prayer.db.pool.BoneCPPool";
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================

	// ~ Static Methods ======================================
	/**
	 * 获取连接池实现方式
	 * 
	 * @return
	 */
	@NotNull
	public static String pool() {
		return null == Resources.DB_POOL ? DEFAULT_DB_POOL : Resources.DB_POOL;
	}

	// ~ Constructors ========================================
	private Accessors() {

	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
