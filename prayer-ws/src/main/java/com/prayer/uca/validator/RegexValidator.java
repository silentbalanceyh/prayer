package com.prayer.uca.validator;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractWebException;
import com.prayer.exception.web.ValidatorConfigErrorException;
import com.prayer.exception.web.ValidatorConfigMissingException;
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
public class RegexValidator implements WebValidator{
	// ~ Static Fields =======================================
	/** Regex Expression **/
	private final static String REGEX_KEY = "regex";
	
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RegexValidator.class);
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================

	/** **/
	@Override
	public boolean validate(@NotNull @NotBlank @NotEmpty final String paramName, @NotNull final Value<?> value,
			@NotNull final JsonObject config) throws AbstractWebException {
		// 1.检查Validator提供的配置信息
		this.interrupt(paramName, config);
		// 2.检查值
		final String regex = Extractor.getString(config, REGEX_KEY);
		// 3.检查Pattern
		return ValidatorUtil.verifyPattern(value.literal(), regex);
	}
	
	private void interrupt(final String name, final JsonObject config) throws AbstractWebException{
		AbstractWebException error = null;
		if(!config.containsKey(REGEX_KEY)){
			error = new ValidatorConfigMissingException(getClass(),name,getClass().getName(),REGEX_KEY);
			info(LOGGER, "[D-VX] Config Error: " + error.getErrorMessage());
			throw error;
		}
		final String regexStr = Extractor.getString(config, REGEX_KEY);
		if(null == regexStr){
			error = new ValidatorConfigErrorException(getClass(), name, getClass().getName(),
					"regex = " + config.getString(REGEX_KEY));
			info(LOGGER, "[D-VX] Config Error: " + error.getErrorMessage());
			throw error;
		}
	}
	
	
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
