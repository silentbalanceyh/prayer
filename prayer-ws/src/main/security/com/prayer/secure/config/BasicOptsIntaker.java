package com.prayer.secure.config;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.constant.SystemEnum.SecureMode;
import com.prayer.facade.engine.Warranter;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.resource.InceptBus;
import com.prayer.util.warranter.ValueWarranter;

import io.vertx.core.json.JsonObject;

/**
 * Basic配置项
 * 
 * @author Lang
 *
 */
public class BasicOptsIntaker implements Intaker<JsonObject> {
    // ~ Static Fields =======================================
    /** 读取器 **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Security.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public JsonObject ingest() throws AbstractException {
        /** 1.验证Basic模式必须参数 **/
        warrantRequired();
        /** 2.读取Basic配置 **/
        return this.buildOpts();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private JsonObject buildOpts() {
        final JsonObject options = new JsonObject();
        options.put("password", INCEPTOR.getString(SecureMode.BASIC.name() + ".password"));
        options.put("script", INCEPTOR.getString(SecureMode.BASIC.name() + ".script"));
        if (INCEPTOR.contains(SecureMode.BASIC.name() + ".login.url")) {
            options.put("login.url", INCEPTOR.getString(SecureMode.BASIC.name() + ".login.url"));
        }
        return options;
    }

    private void warrantRequired() throws AbstractLauncherException {
        final Warranter vWter = singleton(ValueWarranter.class);
        final String[] params = new String[] { SecureMode.BASIC.name() + ".password",
                SecureMode.BASIC.name() + ".script" };
        vWter.warrant(INCEPTOR, params);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
