package com.prayer.kernel.validator;

import static com.prayer.util.Error.info;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractMetadataException;
import com.prayer.exception.metadata.ValidatorConflictException;
import com.prayer.kernel.Validator;
import com.prayer.kernel.Value;
import com.prayer.model.type.DataType;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class MinLengthValidator implements Validator {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(MinLengthValidator.class);
	/** 符合该验证器的属性 **/
	// TODO: 等待加入Binary验证
	private static final DataType[] T_REQUIRED = new DataType[] { DataType.STRING, DataType.XML, DataType.JSON,
			DataType.SCRIPT };

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public boolean validate(@NotNull final Value<?> value, @NotNull @Size(min = 1, max = 1) final Object... params)
			throws AbstractMetadataException {
		// 类型冲突
		if (!Arrays.asList(T_REQUIRED).contains(value.getDataType())) {
			throw new ValidatorConflictException(getClass(), value.getDataType().toString(), "minLength");
		}
		boolean ret = false;
		if (null != params[0]) {
			final int length = value.literal().length();
			final int minLength = Integer.parseInt(params[0].toString());
			if (minLength <= length) {
				ret = true;
			} else {
				ret = false;
			}
		} else {
			info(LOGGER, "[E] Param[0] is null and execution error!");
		}
		return ret;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
