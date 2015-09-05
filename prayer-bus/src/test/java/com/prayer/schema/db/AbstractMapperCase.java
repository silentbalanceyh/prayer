package com.prayer.schema.db;

import java.io.Serializable;

import org.junit.Test;
import org.slf4j.Logger;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractMapperCase<T,ID extends Serializable> {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	/** 子类需要实现的抽象方法 **/
	public abstract Logger getLogger();
	/** 获取Mapper的类信息 **/
	public abstract Class<?> getMapper();
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** 插入方法的测试 **/
	@Test
	public void testInsert(){
		
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
