package com.prayer.util.sys;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import jodd.util.MathUtil;
import net.sf.oval.constraint.Min;
import net.sf.oval.guard.Guarded;

/**
 * 
 *
 * @author Lang
 * @see
 */
@Guarded
public final class Generator {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/**
	 * 生成一个UUID大写的键值
	 * @return
	 */
	public static String uuid(){
		return UUID.randomUUID().toString().toUpperCase(Locale.getDefault());
	}
	/**
	 * 生成一个Bool值
	 * @return
	 */
	public static boolean bool(){
		final int number = number(100);
		return MathUtil.isEven(number);
	}
	/**
	 * 生成一个从1到range的随机整数
	 * @param range
	 * @return
	 */
	public static int number(@Min(0) final int range){
		return index(range) + 1;
	}
	/**
	 * 生成一个随机索引，比number数小1
	 * @param range
	 * @return
	 */
	public static int index(@Min(1) final int range){
		final Random rand = new Random();
		return rand.nextInt(range);
	}
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Get/Set =============================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Generator(){}
	// ~ hashCode,equals,toString ============================
}
