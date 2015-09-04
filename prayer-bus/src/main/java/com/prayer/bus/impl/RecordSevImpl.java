package com.prayer.bus.impl;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.RecordService;
import com.prayer.kernel.Record;
import com.prayer.model.bus.ServiceResult;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RecordSevImpl implements RecordService{
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordSevImpl.class);
	/** **/
	private static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName("nashorn");
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	@Override
	public ServiceResult<Record> saveRecord(String jsonContent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult<Record> removeRecord(String jsonContent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult<List<Record>> queryRecord(String jsonContent) {
		// TODO Auto-generated method stub
		return null;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
