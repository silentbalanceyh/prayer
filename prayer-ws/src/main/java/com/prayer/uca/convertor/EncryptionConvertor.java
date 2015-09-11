package com.prayer.uca.convertor;

import com.prayer.exception.AbstractWebException;
import com.prayer.kernel.Value;
import com.prayer.model.type.StringType;
import com.prayer.uca.WebConvertor;
import com.prayer.uca.assistant.Extractor;
import com.prayer.uca.assistant.Interruptor;
import com.prayer.util.Encryptor;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 最终转换的类型是固定的
 * 
 * @author Lang
 *
 */
public class EncryptionConvertor implements WebConvertor {
	// ~ Static Fields =======================================
	/** 加密使用的算法 **/
	private final static String ALGORITHM_KEY = "algorithm";

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	@Override
	public Value<?> convert(@NotNull @NotBlank @NotEmpty final String name, @NotNull final Value<?> value,
			@NotNull final JsonObject config) throws AbstractWebException {
		// 1.检查加密模块使用的算法
		Interruptor.interruptRequired(getClass(), name, config, ALGORITHM_KEY);
		Interruptor.interruptStringConfig(getClass(), name, config, ALGORITHM_KEY);
		// 2.检查值
		final String algorithm = Extractor.getString(config, ALGORITHM_KEY);
		final String encrypted = this.encrypt(value.literal(), algorithm);
		// 3.返回Value<?>，固定返回StringType
		return new StringType(encrypted);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private String encrypt(final String inputVal, final String algorithm) {
		String retValue = null;
		switch (algorithm) {
		case "MD5":
			retValue = Encryptor.encryptMD5(inputVal);
			break;
		default:
			retValue = inputVal;
			break;
		}
		return retValue;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
