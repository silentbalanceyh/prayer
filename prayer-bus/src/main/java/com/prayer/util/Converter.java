package com.prayer.util;

import static com.prayer.util.Error.info;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;

import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 * @see
 */
@Guarded
public final class Converter {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param sets
	 * @return
	 */
	@NotNull
	@NotEmpty
	@NotBlank
	public static String toStr(@NotNull @MinSize(1) final Set<String> sets) {
		return toStr(sets.toArray(Constants.T_STR_ARR));
	}

	/**
	 * 
	 * @param setArr
	 * @return
	 */
	@NotNull
	@NotEmpty
	@NotBlank
	public static String toStr(@NotNull @MinLength(1) final String... setArr) {
		final StringBuilder retStr = new StringBuilder();
		for (int i = 0; i < setArr.length; i++) {
			retStr.append(setArr[i]);
			if (i < setArr.length - 1) {
				retStr.append(Symbol.COMMA);
			}
		}
		return retStr.toString();
	}

	/**
	 * 
	 * @param clob
	 * @return
	 */
	public static String toStr(@NotNull final Clob clob) {
		String retStr = null;
		try {
			final Reader reader = clob.getCharacterStream();
			final char[] charArr = new char[(int) clob.length()];
			reader.read(charArr);
			reader.close();
			retStr = new String(charArr);
		} catch (SQLException ex) {
			info(LOGGER, "[E] Clob to string invalid. ", ex);
		} catch (IOException ex) {
			info(LOGGER, "[E] IO Exception. ", ex);
		}
		return retStr;
	}

	/**
	 * 
	 * @param clazz
	 * @param inputStr
	 * @return
	 */
	public static <T extends Enum<T>> T fromStr(@NotNull final Class<T> clazz,
			@NotNull @NotBlank @NotEmpty final String inputStr) {
		T retEnum = null;
		try {
			retEnum = Enum.valueOf(clazz, inputStr);
		} catch (IllegalArgumentException ex) {
			info(LOGGER, "[E] Enum value invalid: " + inputStr);
		}
		return retEnum;
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Converter() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
