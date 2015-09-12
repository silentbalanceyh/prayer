package com.prayer.bus.impl;

import static com.prayer.util.Instance.singleton;

import org.junit.Test;

import com.prayer.bus.RecordService;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class ScriptEngineTestCase {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	private transient RecordService service = singleton(RecordSevImpl.class);
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	@Test
	public void saveRecord() {
		final JsonObject params = new JsonObject(
				"{\"identifier\" : \"sec.account\",\"data\" : {\"password\" : \"E559D4DA17DD1C17BE86FCF49E60E322\",\"username\" : \"lang.yu@hp.com\"},\"method\" : \"POST\",\"uri\" : \"/oauth/authorize\",\"session.id\" : \"98ff9876-41c1-480a-9cca-1125816268fd\",\"script\" : \"js.post.oauth.authorize\"}");
		service.save(params);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
