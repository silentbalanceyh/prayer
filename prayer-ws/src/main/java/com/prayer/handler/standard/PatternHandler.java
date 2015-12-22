package com.prayer.handler.standard;

import static com.prayer.util.Log.debug;

import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.util.cv.Constants;
import com.prayer.util.cv.Symbol;
import com.prayer.util.cv.log.DebugKey;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class PatternHandler implements Handler<RoutingContext> {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PatternHandler.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext context) {
        debug(LOGGER, DebugKey.WEB_HANDLER, getClass().getName());
        // 1.Request Extract
        final HttpServerRequest request = context.request();
        // Process URI
        final StringBuilder path = new StringBuilder(request.path());
        final Iterator<Entry<String, String>> it = request.params().entries().iterator();
        while (it.hasNext()) {
            final Entry<String, String> item = it.next();
            final int start = path.indexOf(item.getValue());
            if (0 <= start) {
                final int end = start + item.getValue().length();
                path.replace(start, end, Symbol.COLON + item.getKey());
            }
        }
        // 注意放入到Context中的数据类型，path即使是StringBuilder的类型，但也会导致ClassCastException的异常
        context.put(Constants.KEY.CTX_FINAL_URL, path.toString());
        context.next();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
