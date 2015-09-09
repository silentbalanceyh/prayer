package com.prayer.vx.validator.impl;

import com.prayer.exception.AbstractWebException;
import com.prayer.kernel.Value;
import com.prayer.vx.validator.WebValidator;

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
		final Integer minLength = config.getInteger(MIN_LENGTH_KEY);
		
		return false;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
