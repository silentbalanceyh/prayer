package com.prayer.bus.impl.std;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.i.RecordService;
import com.prayer.dao.impl.record.RecordDaoImpl;
import com.prayer.kernel.model.GenericRecord;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RecordSevImpl extends AbstractSevImpl implements RecordService {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordSevImpl.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public RecordSevImpl(){
	    super(RecordDaoImpl.class,GenericRecord.class);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public Logger getLogger() {
		return LOGGER;
	}
	
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
