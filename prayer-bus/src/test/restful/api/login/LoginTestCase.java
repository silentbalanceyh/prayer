package api.login;

import static com.prayer.util.reflection.Instance.singleton;

import org.junit.Test;

import com.prayer.business.endpoint.DataMessager;
import com.prayer.facade.business.endpoint.DataStubor;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.behavior.ActResponse;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
// URI: /api/login
// Route: /api/login
public class LoginTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient DataStubor stubor = singleton(DataMessager.class);
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Test
    public void testStubor() throws AbstractException{
        final ActResponse ret = stubor.get(new JsonObject(IOKit.getContent("rest/api.login/data.json")));
        System.out.println(ret.getResult());
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
