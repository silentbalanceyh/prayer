package com.prayer.bus.deploy.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.bus.deploy.AddressDPService;
import com.prayer.exception.AbstractSystemException;
import com.prayer.model.h2.vx.AddressModel;
import com.prayer.schema.dao.impl.AddressDaoImpl;
import com.prayer.util.JsonKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class AddressDPSevImpl extends AbstractDPSevImpl<AddressModel, String> implements AddressDPService{	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(AddressDPSevImpl.class);
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public Class<?> getDaoClass() {
		return AddressDaoImpl.class;
	}

	/** 获取Logger **/
	@Override
	public Logger getLogger() {
		return LOGGER;
	}
	/** T Array **/
	@Override
	public AddressModel[] getArrayType(){
		return new AddressModel[]{};
	}

	/** **/
	@Override
	public List<AddressModel> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath)
			throws AbstractSystemException {
		final TypeReference<List<AddressModel>> typeRef = new TypeReference<List<AddressModel>>() {
		};
		return JsonKit.fromFile(typeRef, jsonPath);
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
