package com.prayer;

import static com.prayer.util.Generator.uuid;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.exception.AbstractMetadataException;
import com.prayer.kernel.Value;
import com.prayer.model.type.BinaryType;
import com.prayer.model.type.BooleanType;
import com.prayer.model.type.DataType;
import com.prayer.model.type.DateType;
import com.prayer.model.type.DecimalType;
import com.prayer.model.type.IntType;
import com.prayer.model.type.JsonType;
import com.prayer.model.type.LongType;
import com.prayer.model.type.ScriptType;
import com.prayer.model.type.StringType;
import com.prayer.model.type.XmlType;
import com.prayer.util.Instance;

/**
 * 
 * @author Lang
 *
 */
public final class Assistant { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(Assistant.class);
	/** 测试用枚举 **/
	public static enum TestEnum{
		TEST1,TEST2,TEST3
	}

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/** **/
	public static Value<?> generate(final DataType type,final boolean isUpdate) throws AbstractMetadataException { // NOPMD
		Value<?> ret = null;
		switch (type) {
		case INT:
			ret = new IntType(22);
			break;
		case LONG:
			ret = new LongType(1000L);
			break;
		case BOOLEAN:
			ret = new BooleanType(true);
			break;
		case STRING:
			ret = new StringType(uuid());
			break;
		case DATE:
			ret = new DateType(new Date());
			break;
		case JSON:
			ret = new JsonType("{}");
			break;
		case SCRIPT:
			ret = new ScriptType("var i = 0;");
			break;
		case XML:
			ret = new XmlType("<node></node>");
			break;
		case DECIMAL:
			ret = new DecimalType(BigDecimal.valueOf(3.14));
			break;
		case BINARY:
			ret = new BinaryType("lang.yu@hp.com".getBytes());
			break;
		default:
			break;
		}
		return ret;
	}

	/** 生成字符串集合 **/
	public static Set<String> set(final int size) {
		final Set<String> retSet = new TreeSet<>();
		if (0 < size) {
			for (int idx = 0; idx < size; idx++) {
				retSet.add("Set" + idx);
			}
		}
		return retSet;
	}
	/** 生成字符串数组 **/
	public static String[] array(final int size){
		return set(size).toArray(Constants.T_STR_ARR);
	}

	/** 创建一个新的实例的方法 **/
	public static <T> T instance(final Class<?> clazz, final Object... params) {
		return Instance.instance(clazz.getName(), params);
	}

	/** 创建无参数实例 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T instance(final Class<?> clazz) {
		T ret = null;
		Constructor con;
		try {
			con = clazz.getDeclaredConstructor();
			if (!con.isAccessible()) {
				con.setAccessible(true);
			}
			ret = (T) con.newInstance();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException exp) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[JVM] Test Scope !", exp);
			}
		}
		return ret;
	}

	/**
	 * 
	 * @param collection
	 * @param separator
	 * @param appendEnd
	 * @return
	 */
	public static String join(final Collection<String> collection, final char separator, final boolean appendEnd) {
		final StringBuilder retStr = new StringBuilder();
		if (Constants.ONE == collection.size()) {
			retStr.append(collection.iterator().next());
		} else {
			for (final String item : collection) {
				if (null != item) {
					retStr.append(item).append(separator);
				}
			}
			if (!appendEnd) {
				retStr.deleteCharAt(retStr.length() - 1);
			}
		}
		return retStr.toString();
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Assistant() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
