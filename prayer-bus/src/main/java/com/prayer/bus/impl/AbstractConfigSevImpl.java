package com.prayer.bus.impl;

import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;

import com.prayer.bus.SchemaService;
import com.prayer.bus.deploy.RouteDPService;
import com.prayer.bus.deploy.UriDPService;
import com.prayer.bus.deploy.ValidatorDPService;
import com.prayer.bus.deploy.VerticleDPService;
import com.prayer.bus.impl.deploy.RouteDPSevImpl;
import com.prayer.bus.impl.deploy.UriDPSevImpl;
import com.prayer.bus.impl.deploy.ValidatorDPSevImpl;
import com.prayer.bus.impl.deploy.VerticleDPSevImpl;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractConfigSevImpl {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** 访问H2的EVX_VERTICLE接口 **/
	@NotNull
	private transient final VerticleDPService verticleService;
	/** 访问H2的EVX_ROUTE接口 **/
	@NotNull
	private transient final RouteDPService routeService;
	/** 访问H2的EVX_URI接口 **/
	@NotNull
	private transient final UriDPService uriService;
	/** 访问H2的EVX_PVRULE接口 **/
	@NotNull
	private transient final ValidatorDPService validatorService;
	/** **/
	@NotNull
	private transient final SchemaService schemaService;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public AbstractConfigSevImpl() {
		this.verticleService = singleton(VerticleDPSevImpl.class);
		this.routeService = singleton(RouteDPSevImpl.class);
		this.uriService = singleton(UriDPSevImpl.class);
		this.validatorService = singleton(ValidatorDPSevImpl.class);
		this.schemaService = singleton(SchemaSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	/** 日志记录器 **/
	public abstract Logger getLogger();

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	/**
	 * @return the verticleService
	 */
	public VerticleDPService getVerticleService() {
		return verticleService;
	}

	/**
	 * @return the routeService
	 */
	public RouteDPService getRouteService() {
		return routeService;
	}

	/**
	 * @return the uriService
	 */
	public UriDPService getUriService() {
		return uriService;
	}

	/**
	 * @return the validatorService
	 */
	public ValidatorDPService getValidatorService() {
		return validatorService;
	}

	/**
	 * @return the schemaService
	 */
	public SchemaService getSchemaService() {
		return schemaService;
	}
	// ~ hashCode,equals,toString ============================

}
