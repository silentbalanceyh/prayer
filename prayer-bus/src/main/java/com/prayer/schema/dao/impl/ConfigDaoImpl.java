package com.prayer.schema.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.vertx.DataAccessException;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.vx.VerticleModel;
import com.prayer.schema.dao.ConfigDao;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ConfigDaoImpl implements ConfigDao{
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigDaoImpl.class);
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================

	@Override
	public VerticleModel insert(VerticleModel... verticle) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VerticleModel update(VerticleModel... verticle) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(String uniqueId) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteByName(String name) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VerticleChain getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VerticleModel getByClass(String clazz) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
