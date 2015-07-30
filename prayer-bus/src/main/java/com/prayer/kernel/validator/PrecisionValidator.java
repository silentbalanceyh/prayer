package com.prayer.kernel.validator;

import static com.prayer.util.Error.info;

import java.math.BigDecimal;

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
 * 浮点数精度验证
 * 
 * @author Lang
 *
 */
@Guarded
final class PrecisionValidator implements Validator {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(PrecisionValidator.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public boolean validate(@NotNull final Value<?> value, @NotNull @Size(min = 2, max = 2) final Object... params)
			throws AbstractMetadataException {
		// 类型冲突
		if (DataType.DECIMAL != value.getDataType()) {
			throw new ValidatorConflictException(getClass(), value.getDataType().toString(), "precision");
		}
		boolean ret = false;
		if (null != params[0] && null != params[1]) {
			final BigDecimal currentValue = (BigDecimal) value.getValue();
			if (currentValue.scale() == Integer.parseInt(params[0].toString())
					&& currentValue.precision() <= Integer.parseInt(params[1].toString())) {
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
	public static void main(String args[]) {
		BigDecimal big1 = new BigDecimal("3.145");
		BigDecimal big2 = new BigDecimal("35");
		System.out.println(big1.precision());
		System.out.println(big1.scale());
		System.out.println(big2.precision());
		System.out.println(big2.scale());
	}
}
