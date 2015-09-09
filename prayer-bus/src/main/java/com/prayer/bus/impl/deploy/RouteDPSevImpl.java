package com.prayer.bus.impl.deploy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.bus.deploy.RouteDPService;
import com.prayer.exception.AbstractSystemException;
import com.prayer.model.h2.vx.RouteModel;
import com.prayer.schema.dao.impl.RouteDaoImpl;
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
public class RouteDPSevImpl extends AbstractDPSevImpl<RouteModel, String>implements RouteDPService {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RouteDPSevImpl.class);

	// ~ Instance Fields =====================================

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public Class<?> getDaoClass() {
		return RouteDaoImpl.class;
	}

	/** 获取Logger **/
	@Override
	public Logger getLogger() {
		return LOGGER;
	}
	/** T Array **/
	@Override
	public RouteModel[] getArrayType(){
		return new RouteModel[]{};
	}

	/** **/
	@Override
	public List<RouteModel> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath)
			throws AbstractSystemException {
		final TypeReference<List<RouteModel>> typeRef = new TypeReference<List<RouteModel>>() {
		};
		return JsonKit.fromFile(typeRef, jsonPath);
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
