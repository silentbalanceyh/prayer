package com.prayer.fantasm.vtx.uca;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.vtx.uca.WebKatana;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.vertx.uca.katana.NumberKatana;
import com.prayer.vertx.uca.katana.RequiredKatana;
import com.prayer.vertx.uca.katana.StringKatana;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.MinSize;
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
public abstract class AbstractUCA {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient WebKatana reqKatana = singleton(RequiredKatana.class);
    /** **/
    private transient WebKatana strKatana = singleton(StringKatana.class);
    /** **/
    private transient WebKatana numKatana = singleton(NumberKatana.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 检查必须参数
     * 
     * @param config
     * @param names
     * @throws AbstractWebException
     */
    protected void skewerRequired(@NotNull final JsonObject config, @MinSize(1) final String... names)
            throws AbstractWebException {
        for (final String name : names) {
            reqKatana.interrupt(config, name);
        }
    }

    /**
     * 
     * @param config
     * @param names
     * @throws AbstractWebException
     */
    protected void skewerString(@NotNull final JsonObject config, @MinSize(1) final String... names)
            throws AbstractWebException {
        for (final String name : names) {
            strKatana.interrupt(config, name);
        }
    }

    /**
     * 
     * @param config
     * @param names
     * @throws AbstractWebException
     */
    protected void skewerNumber(@NotNull final JsonObject config, @MinSize(1) final String... names)
            throws AbstractWebException {
        for (final String name : names) {
            numKatana.interrupt(config, name);
        }
    }
    /**
     * 读取Number
     * @param config
     * @param key
     * @return
     */
    protected Integer getNumber(@NotNull final JsonObject config, @NotNull @NotBlank @NotEmpty final String key){
        Integer integer = Constants.RANGE;
        final Object value = config.getValue(key);
        if (null != value) {
            final Class<?> type = value.getClass();
            if (Integer.class == type) {
                integer = config.getInteger(key);
            }
        }
        return integer;
    }
    /**
     * 读取String
     * @param config
     * @param key
     * @return
     */
    protected String getString(@NotNull final JsonObject config, @NotNull @NotBlank @NotEmpty final String key) {
        String str = null;
        final Object value = config.getValue(key);
        if (null != value) {
            final Class<?> type = value.getClass();
            if (String.class == type) {
                str = config.getString(key);
            }
        }
        return str;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
