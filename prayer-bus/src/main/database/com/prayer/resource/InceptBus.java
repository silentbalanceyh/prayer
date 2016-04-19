package com.prayer.resource;

import static com.prayer.util.reflection.Instance.reservoir;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.inceptor.DatabaseInceptor;
import com.prayer.resource.inceptor.DeployInceptor;
import com.prayer.resource.inceptor.DynamicInceptor;
import com.prayer.resource.inceptor.ErrorInceptor;
import com.prayer.resource.inceptor.InjectionInceptor;
import com.prayer.resource.inceptor.SchemaInceptor;
import com.prayer.resource.inceptor.SystemInceptor;
import com.prayer.resource.inceptor.TpInceptor;
import com.prayer.util.reflection.Instance;

/**
 * 用于初始化Inceptor用
 * 
 * @author Lang
 *
 */
public final class InceptBus {
	// ~ Static Fields =======================================
	/** **/
	private static ConcurrentMap<Class<?>, Inceptor> INCEPTORS = new ConcurrentHashMap<>();
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/** **/
	static {
		INCEPTORS.put(Point.System.class, Instance.instance(SystemInceptor.class));
		INCEPTORS.put(Point.Error.class, Instance.instance(ErrorInceptor.class));
		INCEPTORS.put(Point.Database.class, Instance.instance(DatabaseInceptor.class));
		INCEPTORS.put(Point.Injection.class, Instance.instance(InjectionInceptor.class));
		INCEPTORS.put(Point.Schema.class, Instance.instance(SchemaInceptor.class));
		INCEPTORS.put(Point.Deploy.class, Instance.instance(DeployInceptor.class));
		INCEPTORS.put(Point.TP.class, Instance.instance(TpInceptor.class));
		/** 特殊Inceptors **/
		INCEPTORS.put(Point.Jdbc.class, Instance.instance(DatabaseInceptor.class));
	}

	// ~ Static Methods ======================================
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static Inceptor build(final Class<?> clazz) {
		return INCEPTORS.get(clazz);
	}

	/**
	 * 
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static Inceptor build(final Class<?> clazz, final String key) {
		/** 1.读取Inceptor **/
		final Inceptor inceptor = INCEPTORS.get(clazz);
		/** 2.读取key中对应的配置路径 **/
		final String file = inceptor.getString(key);
		return reservoir(file, DynamicInceptor.class, file);
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private InceptBus() {
	};
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
