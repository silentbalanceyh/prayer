package com.test.prayer.meta.data.type;

import static org.junit.Assert.assertEquals;
import jodd.mutable.MutableInteger;

import org.junit.Test;

import com.prayer.meta.data.type.IntType;

public class IntTestCase {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	private final Integer IN_DATA = new Integer(0);
	private final MutableInteger IN_DATA2 = new MutableInteger(0);
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	
	@Test
	public void testInt1(){
		final IntType data = new IntType();
		assertEquals("[T] IntType -> 0", IN_DATA, data.getValue());		
	}
	
	public void testInt2(){
		final IntType data = new IntType(IN_DATA);
		data.setValue(IN_DATA);
		assertEquals("[T] IntType -> 0", IN_DATA, data.getValue());
	}
	
	public void testInt3(){
		final IntType data = new IntType("0");
	//	assertEquals("[T] IntType -> String", data.init(IN_DATA), data.getValue());
	}
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================

}
