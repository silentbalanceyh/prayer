package com.prayer.secure.basic;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.business.secure.basic.BasicAuthBllor;
import com.prayer.constant.SystemEnum.Credential;
import com.prayer.dao.ObjectTransferer;
import com.prayer.exception.web._401AuthorizationFailureException;
import com.prayer.facade.business.instantor.secure.SecureInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.cv.SecKeys;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.facade.util.Transferer;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.InceptBus;

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
    /** 安全配置读取器 **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Security.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void authenticate(@NotNull final JsonObject data, @NotNull final Handler<AsyncResult<User>> handler) {
        try {
            /** 1.读取用户 **/
            final Record record = this.readUser(data);
            /** 2.构造BasicUser **/
            final BasicUser user = this.buildUser(record, data);
            /** 3.正确响应 **/
            handler.handle(Future.<User> succeededFuture(user));
        } catch (AbstractException ex) {
            /** 3.错误响应 **/
            final AbstractException error = new _401AuthorizationFailureException(getClass(),
                    data.getString(SecKeys.URI), ex.getErrorMessage());
            handler.handle(Future.<User> failedFuture(error));
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private BasicUser buildUser(final Record record, final JsonObject params) throws AbstractException {
        /** 1.转换 **/
        final Transferer transferer = singleton(ObjectTransferer.class);
        /** 2.转换数据 **/
        final JsonObject data = transferer.fromRecord(record);
        /** 3.填充核心数据 **/
        data.put(SecKeys.User.Basic.ID, data.getString(Constants.PID));
        /** 4.Secure **/
        final JsonObject dataJson = params.getJsonObject(SecKeys.CREDENTIAL);
        data.put(SecKeys.User.Basic.TOKEN, dataJson.getString(SecKeys.User.Basic.PASSWORD));
        String username = INCEPTOR.getString(Point.Security.Shared.User.USER_ID);
        data.put(SecKeys.Shared.USERNAME, data.getString(username));
        return new BasicUser(this, data);
    }

    private Record readUser(final JsonObject data) throws AbstractException {
        /** 1.获取Secure访问器 **/
        final SecureInstantor instantor = singleton(BasicAuthBllor.class);
        /** 2.读取认证类型 **/
        final JsonObject dataJson = data.getJsonObject(SecKeys.CREDENTIAL);
        final Credential credential = fromStr(Credential.class, dataJson.getString(SecKeys.Shared.TYPE));
        /** 3.构造参数 **/
        final String realm = dataJson.getString(SecKeys.REALM);
        final String password = dataJson.getString(SecKeys.User.Basic.PASSWORD);
        /** 4.读取Record **/
        Record record = null;
        if (Credential.MOBILE == credential) {
            final String mobile = dataJson.getString(SecKeys.Shared.MOBILE);
            record = instantor.identByMobile(realm, mobile, password);
        } else if (Credential.EMAIL == credential) {
            final String email = dataJson.getString(SecKeys.Shared.EMAIL);
            record = instantor.identByEmail(realm, email, password);
        } else {
            final String username = dataJson.getString(SecKeys.Shared.USERNAME);
            record = instantor.identByName(realm, username, password);
        }
        return record;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
