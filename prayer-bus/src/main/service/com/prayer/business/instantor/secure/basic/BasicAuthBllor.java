package com.prayer.business.instantor.secure.basic;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;

import com.prayer.business.instantor.util.CommonQuerier;
import com.prayer.constant.SystemEnum.Credential;
import com.prayer.exception.web._401DuplicatedUserFoundException;
import com.prayer.exception.web._401PasswordWrongException;
import com.prayer.exception.web._401RealmIncorrectException;
import com.prayer.exception.web._401UserNotFoundException;
import com.prayer.facade.business.instantor.secure.SecureInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.InceptBus;
import com.prayer.resource.Resources;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class BasicAuthBllor implements SecureInstantor {
    // ~ Static Fields =======================================
    /** 安全配置读取器 **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Security.class);
    /** Basic的Password字段 **/
    private static final String PWD = INCEPTOR.getString(Resources.Security.MODE.name() + ".password");
    /** 共享的Identifier **/
    private static final String IDENTIFIER = INCEPTOR.getString(Point.Security.Shared.IDENTIFIER);
    // ~ Instance Fields =====================================
    /** **/
    private transient final CommonQuerier querier = singleton(CommonQuerier.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 验证用户名
     */
    @Override
    public Record identByName(@NotNull @NotBlank @NotEmpty final String realm,
            @NotNull @NotBlank @NotEmpty final String username, @NotNull @NotBlank @NotEmpty final String password)
            throws AbstractException {
        /** 0.检查Realm **/
        this.checkRealm(realm);
        /** 1.构造请求参数 **/
        final String uid = this.buildUID(Credential.USERNAME);
        /** 2.初始化Record **/
        final Record record = this.readData(uid, username);
        /** 3.比较Password **/
        this.validate(record, password);
        /** 4.无任何异常，最终返回 **/
        return record;
    }

    /**
     * 验证邮箱
     */
    @Override
    public Record identByEmail(@NotNull @NotBlank @NotEmpty final String realm,
            @NotNull @NotBlank @NotEmpty final String email, @NotNull @NotBlank @NotEmpty final String password)
            throws AbstractException {
        /** 0.检查Realm **/
        this.checkRealm(realm);
        /** 1.构造请求参数 **/
        final String uid = this.buildUID(Credential.EMAIL);
        /** 2.初始化Record **/
        final Record record = this.readData(uid, email);
        /** 3.比较Password **/
        this.validate(record, password);
        /** 4.无任何异常，最终返回 **/
        return record;
    }

    /**
     * 验证手机号
     */
    @Override
    public Record identByMobile(@NotNull @NotBlank @NotEmpty final String realm,
            @NotNull @NotBlank @NotEmpty final String mobile, @NotNull @NotBlank @NotEmpty final String password)
            throws AbstractException {
        /** 0.检查Realm **/
        this.checkRealm(realm);
        /** 1.构造请求参数 **/
        final String uid = this.buildUID(Credential.MOBILE);
        /** 2.初始化Record **/
        final Record record = this.readData(uid, mobile);
        /** 3.比较Password **/
        this.validate(record, password);
        /** 4.无任何异常，最终返回 **/
        return record;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void checkRealm(final String realm) throws AbstractException {
        if (!realm.equals(Resources.Security.REALM)) {
            throw new _401RealmIncorrectException(getClass(), realm, Resources.Security.REALM);
        }
    }

    private void validate(final Record record, final String password) throws AbstractException {
        final Value<?> stored = record.get(PWD);
        if (null == stored || !stored.isCorrect() || !stored.getValue().toString().equals(password)) {
            throw new _401PasswordWrongException(getClass(), password);
        }
    }

    private Record readData(final String field, final String value) throws AbstractException {
        final List<Record> records = this.querier.queryMuster(IDENTIFIER, field, value);
        Record record = null;
        if (Constants.ONE < records.size()) {
            throw new _401DuplicatedUserFoundException(getClass(), field, value);
        } else if (records.size() < Constants.ONE) {
            throw new _401UserNotFoundException(getClass(), field, value);
        } else {
            record = records.get(Constants.IDX);
        }
        return record;
    }

    /** 初始化Record **/
    private String buildUID(final Credential credential) {
        String uidField = INCEPTOR.getString(Point.Security.Shared.User.USER_ID);
        if (Credential.EMAIL == credential) {
            uidField = INCEPTOR.getString(Point.Security.Shared.User.EMAIL);
        } else if (Credential.MOBILE == credential) {
            uidField = INCEPTOR.getString(Point.Security.Shared.User.MOBILE);
        }
        return uidField;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}