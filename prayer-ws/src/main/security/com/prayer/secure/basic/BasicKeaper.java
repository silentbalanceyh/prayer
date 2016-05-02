package com.prayer.secure.basic;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.secure.SecureKeaper;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.secure.model.Token;
import com.prayer.util.vertx.Feature;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.impl.AuthHandlerImpl;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class BasicKeaper extends AuthHandlerImpl implements SecureKeaper {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public BasicKeaper(@NotNull final AuthProvider provider){
        super(provider);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext event) {
        /** 1.提取参数 **/
        final PEUri uri = event.get(WebKeys.Request.Data.Meta.PEURI);
        /** 2.提取Token **/
        final Token token = Token.create(event.request());
        /** 3.判断Token是否取到 **/
        if(token.obtained()){

            Feature.next(event);
        }else{
            /** 3.从Header中没有拿到Token **/
            final Envelop stumer = Envelop.failure(token.getError());
            /** ERROR-ROUTE：错误路由 **/
            Feature.route(event, stumer);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
