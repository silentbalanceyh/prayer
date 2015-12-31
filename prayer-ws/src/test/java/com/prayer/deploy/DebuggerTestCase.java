package com.prayer.deploy;

import static com.prayer.util.Instance.singleton;

import org.junit.Test;

import com.prayer.bus.impl.std.BasicAuthSevImpl;
import com.prayer.bus.impl.std.RecordSevImpl;
import com.prayer.facade.bus.BasicAuthService;
import com.prayer.facade.bus.RecordService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.Encryptor;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class DebuggerTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    private transient BasicAuthService secSev = new BasicAuthSevImpl();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    public void testSelect() {
        final JsonObject params = new JsonObject(
                "{\"method\":\"GET\",\"filters\":[],\"identifier\":\"sec.account\",\"script\":\"js.api.get.sec.login\",\"data\":{\"username\":\"user.not.found\"}}");
        ServiceResult<JsonArray> ret = secSev.find(params);
        System.out.println(ret.getResult().encode());
    }

    public void testFolder() {
        System.out.println(IOKit.listFiles("deploy/oob/vertx/route"));
    }

    @Test
    public void testProcess() throws Exception {

    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    public static void main(String args[]) throws Exception {
        final RecordService recordSev = singleton(RecordSevImpl.class);
        final String content = IOKit.getContent("deploy/oob/data/account.json");
        System.out.println(content);
        final JsonObject params = new JsonObject(content);
        final String password = Encryptor.encryptMD5(params.getJsonObject("data").getString("password"));
        params.getJsonObject("data").put("password",password);
        recordSev.save(params);
    }
}
