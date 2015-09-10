package com.prayer.uca.validator;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractWebException;
import com.prayer.exception.web.ValidatorConfigErrorException;
import com.prayer.kernel.Value;
import com.prayer.uca.WebValidator;

import io.vertx.core.json.JsonObject;
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
public class LengthValidator implements WebValidator {
	// ~ Static Fields =======================================
	/** 最小值 **/
	private final static String MIN_LENGTH_KEY = "minLength";
	/** 最大值 **/
	private final static String MAX_LENGTH_KEY = "maxLength";

	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(LengthValidator.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public boolean validate(@NotNull @NotBlank @NotEmpty final String paramName, @NotNull final Value<?> value,
			@NotNull final JsonObject config) throws AbstractWebException {
		// DEBUG: info(LOGGER,"[D-VX] Paramters ( paramName =" + paramName + ", value = " + value.literal() + ", config = " + config.encodePrettily());
		// 1.检查Validator提供的配置信息
		this.interrupt(paramName, config);
		// 2.检查值
		final Integer minLength = Extractor.getNumber(config,MIN_LENGTH_KEY);
		final Integer maxLength = Extractor.getNumber(config,MAX_LENGTH_KEY);
		return ValidatorUtil.verifyLength(value.literal(), minLength, maxLength);
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private void interrupt(final String name, final JsonObject config) throws AbstractWebException {
		AbstractWebException error = null;
		final Integer minLength = Extractor.getNumber(config, MIN_LENGTH_KEY);
		if (null == minLength) {
			error = new ValidatorConfigErrorException(getClass(), name, getClass().getName(),
					"minLength = " + config.getString(MIN_LENGTH_KEY));
			info(LOGGER, "[D-VX] Config Error: " + error.getErrorMessage());
			throw error;
		}
		final Integer maxLength = Extractor.getNumber(config, MAX_LENGTH_KEY);
		if (null == maxLength) {
			error = new ValidatorConfigErrorException(getClass(), name, getClass().getName(),
					"maxLength = " + config.getString(MAX_LENGTH_KEY));
			info(LOGGER, "[D-VX] Config Error: " + error.getErrorMessage());
			throw error;
		}
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
