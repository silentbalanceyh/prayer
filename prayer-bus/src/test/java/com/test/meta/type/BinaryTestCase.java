package com.test.meta.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.prayer.model.type.BinaryType;
import com.prayer.model.type.DataType;

/**
 * 
 *
 * @author Lang
 * @see
 */
public class BinaryTestCase { 	// NOPMD
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	private static final String IN_DATA = "lang.yu@hp.com";
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	@Test
	public void testBinary1(){
		// BinaryType data = new BinaryType(null); 编译器错误，不可以这样调用
		final BinaryType data = new BinaryType();
		assertEquals("[T] BinaryType -> 1024 ",1024,data.getValue().length);
	}
	/** **/
	@Test
	public void testBinary2(){
		final BinaryType data = new BinaryType(12);
		assertEquals("[T] BinaryType -> 12 ",12,data.getValue().length);
	}
	/** **/
	@Test
	public void testBinary3(){
		final BinaryType data = new BinaryType(IN_DATA);
		assertArrayEquals(IN_DATA.getBytes(),data.getValue());
	}
	/** **/
	@Test
	public void testBinary4(){
		final BinaryType data = new BinaryType();
		data.setValue(IN_DATA);
		assertArrayEquals(IN_DATA.getBytes(),data.getValue());
	}
	/** **/
	@Test
	public void testBinary5(){
		final BinaryType data = new BinaryType();
		data.setValue(IN_DATA.getBytes());
		assertArrayEquals(IN_DATA.getBytes(),data.getValue());
	}
	/** **/
	@Test
	public void testBinary6(){
		final BinaryType data = new BinaryType();
		assertEquals("[T] Test getType.",Arrays.copyOf(new byte[0],0).getClass(),data.getType());
	}
	/** **/
	@Test
	public void testBinary7(){
		final BinaryType data = new BinaryType();
		assertEquals("[T] Test getDataType.",DataType.BINARY,data.getDataType());
	}
	/** **/
	@Test
	public void testBinary8(){
		final BinaryType data1 = new BinaryType(IN_DATA);
		final BinaryType data2 = new BinaryType(IN_DATA);
		assertEquals("[T] Test hashCode.",data1.hashCode(),data2.hashCode());
	}
	/** **/
	@Test
	public void testBinary9(){
		final BinaryType data1 = new BinaryType(IN_DATA);
		final BinaryType data2 = new BinaryType(IN_DATA);
		assertEquals("[T] Test toString.",data1.toString(),data2.toString());
	}
	/** **/
	@Test
	public void testBinary10(){
		final BinaryType data1 = new BinaryType(IN_DATA);
		final BinaryType data2 = new BinaryType(IN_DATA);
		final boolean ret = data1.equals(data2);
		assertTrue("[T] Test equals.",ret);
	}
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
