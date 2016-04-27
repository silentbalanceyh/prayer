package com.prayer.uca.validator;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.uca.WebValidator;
import com.prayer.util.web.Extractor;
import com.prayer.util.web.Interruptor;
import com.prayer.vertx.util.Skewer;

import io.vertx.core.json.JsonObject;
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
public class PagerValidator implements WebValidator {
    // ~ Static Fields =======================================
    /** 页码 **/
    private final static String KEY_PG_IDX = "index";
    /** 每页的记录数量 **/
    private final static String KEY_PG_SIZE = "size";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean validate(@NotNull @NotBlank @NotEmpty final String name, @NotNull final Value<?> value,
            @NotNull final JsonObject config) throws AbstractWebException {
        boolean ret = false;
        final JsonObject page = new JsonObject(value.literal());
        // 1.检查Validator中传入值的Page参数信息
        Interruptor.interruptRequired(getClass(), name, page, KEY_PG_IDX);
        Interruptor.interruptRequired(getClass(), name, page, KEY_PG_SIZE);
        // 2.检查类型
        Interruptor.interruptNumberConfig(getClass(), name, page, KEY_PG_IDX);
        Interruptor.interruptNumberConfig(getClass(), name, page, KEY_PG_SIZE);
        // 3.范围检查
        ret = Skewer.verifyRange(Extractor.getNumber(page, KEY_PG_IDX), Constants.ONE, Constants.RANGE);
        ret = ret && Skewer.verifyRange(Extractor.getNumber(page, KEY_PG_SIZE), Constants.ONE, Constants.RANGE);
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
