package com.prayer.uca.assistant;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.web.SpecialDataTypeException;
import com.prayer.exception.web._400ValidatorFailureException;
import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.type.DataType;
import com.prayer.model.type.JsonType;
import com.prayer.model.type.ScriptType;
import com.prayer.model.type.XmlType;
import com.prayer.uca.WebValidator;
import com.prayer.util.web.Interruptor;
import com.prayer.vertx.util.Skewer;

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
public final class UCAValidator {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UCAValidator.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param name
     * @param value
     * @param validators
     * @return
     */
    public static AbstractWebException verifyField(@NotNull @NotBlank @NotEmpty final String name, final String value,
            @MinSize(1) final List<PERule> validators) {
        AbstractWebException error = null;
        // Fix: Null Pointer，因为validators是从Map中取得的，所以必须判断是否为null
        if (null != validators && !validators.isEmpty()) {
            for (final PERule validator : validators) {
                error = verifyField(name, value, validator);
                if (null != error) {
                    break;
                }
            }
        }
        return error;
    }

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static AbstractWebException verifyField(final String paramName, final String paramValue,
            final PERule ruleModel) { // NOPMD
        AbstractWebException error = null;
        try {
            // 1.验证Validator是否存在
            final Class<?> comCls = ruleModel.getComponentClass();
            Interruptor.interruptClass(UCAValidator.class, comCls.getName(), "Ruler");
            Interruptor.interruptImplements(UCAValidator.class, comCls.getName(), WebValidator.class);
            // 2.从value中提取值信息
            final Value<?> value = buildValue(paramValue, ruleModel);

            // 3.提取配置信息
            final JsonObject config = ruleModel.getConfig();
            // 4.验证结果
            final WebValidator validator = instance(comCls);
            final boolean ret = validator.validate(paramName, value, config);
            // 5.验证失败，特殊的Exception
            if (!ret) {
                error = new _400ValidatorFailureException(ruleModel.getErrorMessage());
            }
        } catch (AbstractWebException ex) {
            peError(LOGGER, ex);
            error = ex;
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
            // 三种复杂基础类型的数据格式问题
            error = new SpecialDataTypeException(Skewer.class, ruleModel.getType(), paramValue);
        }
        return error;
    }

    private static Value<?> buildValue(final String paramValue, final PERule ruleModel)
            throws AbstractDatabaseException {
        Value<?> value = null;
        if (DataType.JSON == ruleModel.getType() || DataType.XML == ruleModel.getType()
                || DataType.SCRIPT == ruleModel.getType()) {
            // 抛出AbstractDatabaseException
            switch (ruleModel.getType()) {
            case JSON:
                value = new JsonType(paramValue);
                break;
            case XML:
                value = new XmlType(paramValue);
                break;
            case SCRIPT:
                value = new ScriptType(paramValue);
                break;
            default:
                value = null; // NOPMD
                break;
            }
        } else {
            final String typeCls = ruleModel.getType().getClassName();
            value = instance(typeCls, paramValue);
        }
        return value;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private UCAValidator() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
