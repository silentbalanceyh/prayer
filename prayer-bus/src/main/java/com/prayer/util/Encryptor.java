package com.prayer.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.prayer.constant.Constants;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Encryptor {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param inputValue
	 * @param algorithm
	 * @return
	 */
	public static String encryptMD5(@NotNull @NotBlank @NotEmpty final String inputValue) {
		String retValue = null;
		try {
			final MessageDigest digester = MessageDigest.getInstance("MD5");
			final byte[] source = inputValue.getBytes();
			digester.update(source);
			final byte[] middle = digester.digest(); // MD5计算结果是一个128位长整数
			// 16个字节表示
			final char[] middleStr = new char[16 * 2]; // 每个16进制数使用两个字符

			// 16进制数需要32个字符
			int position = 0; // 转换结果中的字符位置
			for (int idx = 0; idx < 16; idx++) {
				// 16进制字符转换
				final byte byte0 = middle[idx]; // 取第idx个字节
				middleStr[position++] = Constants.UTI_HEX_ARR[byte0 >>> 4 & 0xF]; // 取字节中高4位
				middleStr[position++] = Constants.UTI_HEX_ARR[byte0 & 0xF]; // 取字节中高4位
			}
			retValue = new String(middleStr);

		} catch (NoSuchAlgorithmException ex) {
			retValue = inputValue;
		}
		return retValue;
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Encryptor() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
