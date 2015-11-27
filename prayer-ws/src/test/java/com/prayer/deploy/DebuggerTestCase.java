package com.prayer.deploy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.prayer.bus.security.BasicAuthService;
import com.prayer.bus.security.impl.BasicAuthSevImpl;
import com.prayer.bus.std.RecordService;
import com.prayer.bus.std.impl.RecordSevImpl;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.IOKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class DebuggerTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    private transient RecordService service = new RecordSevImpl();
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
        Process proc = Runtime.getRuntime().exec("tasklist /FI \"imagename eq chrome.exe\"");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            if(line.indexOf("4204") >= 0){
                System.out.println(line);
                Pattern pattern = Pattern.compile("(\\d+)(,\\d+)*\\s(K|k)");
                Matcher matcher = pattern.matcher(line);
                if(matcher.find()){
                    Long kb = Long.parseLong(matcher.group().replace(",", "").replace("K", "").trim());
                    kb = kb / 1024;
                    System.out.println(kb);
                    break;
                }
            }
        }
    }
}
