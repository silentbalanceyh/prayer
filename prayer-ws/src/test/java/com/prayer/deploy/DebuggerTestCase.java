package com.prayer.deploy;

import com.prayer.bus.std.RecordService;
import com.prayer.bus.std.impl.RecordSevImpl;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class DebuggerTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    private transient RecordService service = new RecordSevImpl();
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    public void testSelect(){
        final JsonObject params = new JsonObject("{\"identifier\":\"RP$identifier\",\"filters\":[],\"method\":\"GET\",\"data\":{\"name\":\"username\",\"identifier\":\"sec.account\",\"value\":\"lang.yu\"},\"script\":\"js.api.get.util.unique\"}");
        ServiceResult<JsonArray> ret = service.find(params);
        System.out.println(ret.getResult().encode());
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
