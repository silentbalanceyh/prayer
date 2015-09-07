package com.prayer.schema.db;

import static com.prayer.util.Generator.uuid;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class Generator {	// NOPMD
	// ~ Static Fields =======================================
	/**
	 * 针对对象中的信息随机设置
	 * 
	 * @param instance
	 * @param field
	 */
	public static void setValues(final Object instance, final Set<String> excludes) {
		final Class<?> clazz = instance.getClass();
		try {
			final Field[] fields = clazz.getDeclaredFields();
			for (final Field field : fields) {
				// ID不设置
				if (!Modifier.isStatic(field.getModifiers()) && !field.getName().equals("uniqueId")
						&& !excludes.contains(field.getName())) {
					final Class<?> type = field.getType();
					final Object value = generateValue(type, field.getName());
					if (!field.isAccessible()) {
						field.setAccessible(true);
					}
					field.set(instance, value);
				}
			}
		} catch (Exception ex) { // NOPMD
			ex.printStackTrace(); // NOPMD
		}
	}

	private static Object generateValue(final Class<?> type, final String name) {
		Object ret = null;
		final Random random = new Random();
		if (int.class == type || Integer.class == type) {
			ret = Integer.valueOf(random.nextInt(1000) + 1);
		} else if (long.class == type || Long.class == type) {
			ret = Long.valueOf(random.nextLong() + 1L);
		} else if (String.class == type) {
			// FIELD-<UUID>的格式
			ret = name.toUpperCase(Locale.getDefault()) + "-" + uuid();
		} else if (Date.class == type) {
			ret = new Date();
		} else if (boolean.class == type || Boolean.class == type) {
			ret = random.nextBoolean();
		} else if (List.class == type) {
			// 仅支持String类型
			final int length = random.nextInt(10);
			final List<String> list = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				list.add(uuid());
			}
			ret = list;
		} else if (null != type.getEnumConstants()) { // NOPMD
			final int length = type.getEnumConstants().length;
			ret = type.getEnumConstants()[random.nextInt(length - 1)];
		}
		return ret;
	}

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Generator() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
