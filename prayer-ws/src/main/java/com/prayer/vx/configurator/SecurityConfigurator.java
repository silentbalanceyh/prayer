package com.prayer.vx.configurator;

import static com.prayer.uca.assistant.WebLogger.error;
import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.instance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.constant.SystemEnum.SecurityMode;
import com.prayer.exception.AbstractWebException;
import com.prayer.security.provider.BasicAuth;
import com.prayer.uca.assistant.Interruptor;
import com.prayer.uca.assistant.WebLogger;
import com.prayer.util.PropertyKit;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class SecurityConfigurator {
	// ~ Static Fields =======================================
	/** Provider Implementation Key **/
	private static final String PROVIDER = "provider.impl";
	// ~ Instance Fields =====================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfigurator.class);
	/** Server Config File **/
	private transient final PropertyKit LOADER = new PropertyKit(SecurityConfigurator.class, Resources.SEV_CFG_FILE);
	/** **/
	@NotNull
	private transient final SecurityMode mode;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public SecurityConfigurator() {
		this.mode = fromStr(SecurityMode.class, this.LOADER.getString("server.security.mode"));
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * Security Mode
	 * 
	 * @return
	 */
	public SecurityMode getMode() {
		return this.mode;
	}

	/**
	 * 获取Security的配置信息
	 * 
	 * @return
	 */
	public JsonObject getSecurityOptions() {
		JsonObject options = new JsonObject();
		switch (this.mode) {
		case BASIC:
			options = getBasicOptions();
			break;
		case DIGEST:
			options = getDigestOptions();
			break;
		case OAUTH2:
			options = getOAuth2Options();
			break;
		default:
			break;
		}
		return options;
	}

	/**
	 * 获取Provider引用
	 * 
	 * @return
	 */
	public AuthProvider getProvider() {
		AuthProvider provider = null;
		try {
			final String providerCls = this.getSecurityOptions().getString(PROVIDER);
			Interruptor.interruptClass(getClass(), providerCls, "AuthProvider");
			Interruptor.interruptImplements(getClass(), providerCls, AuthProvider.class);
			provider = instance(providerCls);
		} catch (AbstractWebException ex) {
			error(LOGGER, WebLogger.E_COMMON_EXP, ex.getErrorMessage());
		}
		return provider;
	}
	// ~ Private Methods =====================================

	/** Basic Options **/
	private JsonObject getBasicOptions() {
		final JsonObject options = new JsonObject();
		final String prefix = SecurityMode.BASIC.toString();
		// 固定属性
		options.put(PROVIDER, this.LOADER.getString(prefix + Symbol.DOT + PROVIDER));
		options.put(BasicAuth.DFT_REALM, this.LOADER.getString(prefix + Symbol.DOT + BasicAuth.DFT_REALM));
		// 可定义的动态属性
		options.put(BasicAuth.DFT_SCHEMA_ID, this.LOADER.getString(prefix + Symbol.DOT + BasicAuth.DFT_SCHEMA_ID));
		options.put(BasicAuth.DFT_USER_ACCOUNT,
				this.LOADER.getString(prefix + Symbol.DOT + BasicAuth.DFT_USER_ACCOUNT));
		options.put(BasicAuth.DFT_USER_EMAIL, this.LOADER.getString(prefix + Symbol.DOT + BasicAuth.DFT_USER_EMAIL));
		options.put(BasicAuth.DFT_USER_MOBILE, this.LOADER.getString(prefix + Symbol.DOT + BasicAuth.DFT_USER_MOBILE));
		options.put(BasicAuth.DFT_USER_PWD, this.LOADER.getString(prefix + Symbol.DOT + BasicAuth.DFT_USER_PWD));
		return options;
	}

	/** Digest Options **/
	private JsonObject getDigestOptions() {
		return null;
	}

	/** OAuth2 Options **/
	private JsonObject getOAuth2Options() {
		return null;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
