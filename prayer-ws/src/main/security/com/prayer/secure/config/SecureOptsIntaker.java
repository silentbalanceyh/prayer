package com.prayer.secure.config;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.SecureMode;
import com.prayer.facade.engine.Warranter;
import com.prayer.facade.engine.cv.SecKeys;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.resource.InceptBus;
import com.prayer.resource.Resources;
import com.prayer.secure.opts.SecureOptions;
import com.prayer.secure.opts.TpOptions;
import com.prayer.util.warranter.ValueWarranter;

import io.vertx.core.json.JsonObject;

/**
 * Security配置项
 * 
 * @author Lang
 *
 */
public class SecureOptsIntaker implements Intaker<ConcurrentMap<SecureMode, SecureOptions>> {
    // ~ Static Fields =======================================
    /** 读取器 **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Security.class);
    /** 单独配置读取器 **/
    private static final ConcurrentMap<SecureMode, Intaker<JsonObject>> INTAKERS = new ConcurrentHashMap<>();
    /** 配置信息 **/
    private static final ConcurrentMap<SecureMode, SecureOptions> CONFIGS = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================

    // ~ Static Block ========================================
    static {
        /** Basic **/
        INTAKERS.put(SecureMode.BASIC, singleton(BasicOptsIntaker.class));
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public ConcurrentMap<SecureMode, SecureOptions> ingest() throws AbstractException {
        /** 1.验证必须参数 **/
        warrantRequired();
        /** 2.读取配置 **/
        return this.buildOpts();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private ConcurrentMap<SecureMode, SecureOptions> buildOpts() throws AbstractException {
        if (CONFIGS.isEmpty()) {
            for (final SecureMode mode : SecureMode.values()) {
                /** 存在Mode对应的Intaker **/
                if (null != INTAKERS.get(mode)) {
                    final SecureOptions option = new SecureOptions();
                    /** Shared信息读取 **/
                    option.setIdentifier(INCEPTOR.getString("secure.identifier"));
                    option.setRealm(Resources.Security.REALM);
                    option.setMode(mode);

                    /** User信息读取 **/
                    final JsonObject user = new JsonObject();
                    user.put(SecKeys.Shared.USERNAME, INCEPTOR.getString("secure.user.id"));
                    user.put(SecKeys.Shared.MOBILE, INCEPTOR.getString("secure.mobile"));
                    user.put(SecKeys.Shared.EMAIL, INCEPTOR.getString("secure.email"));
                    option.setUser(user);

                    /** Tp信息读取 **/
                    final TpOptions options = new TpOptions();
                    options.put(SecKeys.Tp.QQ, INCEPTOR.getString("secure.qq"));
                    options.put(SecKeys.Tp.TAOBAO, INCEPTOR.getString("secure.taobao"));
                    options.put(SecKeys.Tp.WEIBO, INCEPTOR.getString("secure.weibo"));
                    options.put(SecKeys.Tp.WEBCHAT, INCEPTOR.getString("secure.webchat"));
                    options.put(SecKeys.Tp.ALIPAY, INCEPTOR.getString("secure.alipay"));
                    option.setOptions(options);
                    /** 配置信息读取 **/

                    option.setConfig(INTAKERS.get(mode).ingest());
                    CONFIGS.put(mode, option);
                }
            }
        }
        return CONFIGS;
    }

    private void warrantRequired() throws AbstractLauncherException {
        final Warranter vWter = singleton(ValueWarranter.class);
        final String[] params = new String[] { "secure.mode", "secure.realm", "secure.identifier", "secure.user.id",
                "secure.email", "secure.mobile" };
        vWter.warrant(INCEPTOR, params);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
