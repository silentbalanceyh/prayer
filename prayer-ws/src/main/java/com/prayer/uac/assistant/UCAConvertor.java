package com.prayer.uac.assistant;

import static com.prayer.util.Instance.instance;

import com.prayer.assistant.Interruptor;
import com.prayer.base.exception.AbstractWebException;
import com.prayer.facade.kernel.Value;
import com.prayer.model.h2.vertx.RuleModel;
import com.prayer.uca.WebConvertor;

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
public final class UCAConvertor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param paramName
     * @param paramValue
     * @param ruleModel
     * @return
     * @throws AbstractWebException
     */
    public static String convertField(@NotNull @NotEmpty @NotBlank final String paramName, final String paramValue,
            @NotNull final RuleModel ruleModel) throws AbstractWebException {
        // 1.验证Convertor是否合法
        final String convertorCls = ruleModel.getComponentClass();
        Interruptor.interruptClass(UCAConvertor.class, convertorCls, "UCAConvertor");
        Interruptor.interruptImplements(UCAConvertor.class, convertorCls, WebConvertor.class);
        // 2.提取Convertor中的
        final String typeCls = ruleModel.getType().getClassName();
        final Value<?> value = instance(typeCls, paramValue);
        // 3.提取配置信息
        final JsonObject config = ruleModel.getConfig();
        // 4.执行转换
        final WebConvertor convertor = instance(convertorCls);
        final Value<?> ret = convertor.convert(paramName, value, config);
        // 5.最终返回literal，转换失败的时候使用原值
        return null == ret ? paramValue : ret.literal();
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private UCAConvertor(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
