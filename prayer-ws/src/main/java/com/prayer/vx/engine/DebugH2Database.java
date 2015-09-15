package com.prayer.vx.engine;

import static com.prayer.util.Instance.singleton;

import com.prayer.exception.AbstractException;
/**
 * 
 * @author Lang
 *
 */
public final class DebugH2Database {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/** **/
	public static void main(final String... args) throws AbstractException{
		final H2DatabaseServer server = singleton(H2DatabaseServer.class);
		if (server.start()) {
			boolean ret = server.checkLocks();
			if (!ret) {
				// 1.初始化元数据
				ret = server.initMetadata();
				// 2.创建锁文件
				ret = ret && server.createLocks();
			}
		}
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private DebugH2Database(){}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
