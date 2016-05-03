package com.prayer.secure.basic;

import com.prayer.exception.web._401AuthorizationFailureException;
import com.prayer.facade.engine.cv.SecKeys;
import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class BasicJunctor implements AuthProvider {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void authenticate(@NotNull final JsonObject data, @NotNull final Handler<AsyncResult<User>> handler) {
        System.out.println(data.encode());
        final BasicUser user = new BasicUser();
        final AbstractException error = new _401AuthorizationFailureException(getClass(),data.getString(SecKeys.URI));
        handler.handle(Future.<User>failedFuture(error));
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
