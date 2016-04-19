package com.prayer.model.crucial;

import static com.prayer.util.Calculator.index;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.clazz;
import static com.prayer.util.reflection.Instance.reservoir;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.resource.InceptBus;
import com.prayer.util.resource.DatumLoader;
import com.prayer.util.string.StringKit;

/**
 * 根据Metadata的类名、字段名得到对应的列名
 * 
 * @author Lang
 *
 */
public class MetaColumns {
	// ~ Static Fields =======================================
	/** 从实体类名到Identifier的映射 **/
	public static final ConcurrentMap<Class<?>, String> ENTITY_MAP = new ConcurrentHashMap<>();
	/** 日志记录器 **/
	private static final Logger LOGGER = LoggerFactory.getLogger(MetaColumns.class);
	/** 元数据资源文件 **/
	private static final Inceptor LOADER = InceptBus.build(Point.Schema.class);
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	static {
		/** 从属性文件中加载对应的信息 **/
		final Properties prop = DatumLoader.getLoader(LOADER.getFile());
		prop.keySet().stream().filter(item -> item.toString().endsWith(".instance")).forEach(item -> {
			final String literal = item.toString();
			final String identifier = literal.split("\\.")[Constants.IDX];
			if (StringKit.isNonNil(identifier)) {
				final Class<?> key = clazz(prop.getProperty(literal));
				if (null != key) {
					ENTITY_MAP.put(key, identifier);
				}
			}
		});
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 通过池化单例创建MetaRaw
	 * 
	 * @return
	 */
	public static String column(final Class<?> entityCls, final String field) {
		/** 1.从系统中读取MetaRaw **/
		final String identifier = ENTITY_MAP.get(entityCls);
		MetaRaw raw = null;
		if (StringKit.isNonNil(identifier)) {
			raw = reservoir(identifier, MetaRaw.class, identifier);
		}
		/** 2.读取对应的列信息 **/
		String ret = null;
		try {
			final List<String> names = raw.readNames();
			final List<String> columns = raw.readColumns();
			ret = columns.get(index(names, field));
		} catch (AbstractDatabaseException ex) {
			peError(LOGGER, ex);
		}
		return ret;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
