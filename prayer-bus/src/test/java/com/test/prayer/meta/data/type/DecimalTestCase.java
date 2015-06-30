package com.test.prayer.meta.data.type;

import java.math.BigDecimal;

import org.junit.Test;

import com.prayer.meta.DataType;
import com.prayer.meta.data.type.DecimalType;

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author Lang
 * @see
 */
public class DecimalTestCase {	// NOPMD
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	private static final BigDecimal IN_DATA = BigDecimal.valueOf(0.00);

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	@Test
	public void testDecimal1() {
		final DecimalType data = new DecimalType();
		assertEquals("[T] DecimalType -> 0.00", IN_DATA, data.getValue());
	}

	/** **/
	@Test
	public void testDecimal2() {
		final BigDecimal data1 = new BigDecimal(0.00);		// NOPMD
		final DecimalType data2 = new DecimalType(data1);
		assertEquals("[T] DecimalType -> BigDecimal", data1, data2.getValue());
	}

	/** **/
	@Test
	public void testDecimal3() {
		final DecimalType data1 = new DecimalType("1.00");	// NOPMD
		final DecimalType data2 = new DecimalType("1.00");
		assertEquals("[T] DecimalType -> String", data1.getValue(),
				data2.getValue());
	}

	/** **/
	@Test
	public void testDecimal4() {
		final BigDecimal data1 = new BigDecimal(0.00);		// NOPMD
		final DecimalType data2 = new DecimalType();
		data2.setValue(data1);
		assertEquals("[T] test setValue -> BigDecimal", data1, data2.getValue());
	}

	/** **/
	@Test
	public void testDecimal5() {
		final DecimalType data = new DecimalType();
		assertEquals("[T] test getType", BigDecimal.class, data.getType());
	}

	/** **/
	@Test
	public void testDecimal6() {
		final DecimalType data = new DecimalType();
		assertEquals("[T] test getDataType", DataType.DECIMAL,
				data.getDataType());
	}

	/** **/
	@Test
	public void testDecimal7() {
		final DecimalType data1 = new DecimalType();
		final DecimalType data2 = new DecimalType("1.00");
		data1.setValue("1.00");
		assertEquals("[T] test setValue -> String", data2.getValue(),
				data1.getValue());
	}

	/** **/
	@Test
	public void testDecimal8() {
		final DecimalType data1 = new DecimalType(IN_DATA);
		final DecimalType data2 = new DecimalType(IN_DATA);
		assertEquals("[T] test toString", data1.toString(), data2.toString());
	}

	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================

}
