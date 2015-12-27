package com.prayer.assistant.uca;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.instance;

import java.text.MessageFormat;

import com.prayer.assistant.Interruptor;
import com.prayer.base.exception.AbstractWebException;
import com.prayer.exception.web.DependParameterInvalidException;
import com.prayer.exception.web.DependParamsMissingException;
import com.prayer.exception.web.DependQueryInvalidException;
import com.prayer.exception.web.DependRuleInvalidException;
import com.prayer.exception.web.ValidationFailureException;
import com.prayer.facade.kernel.Value;
import com.prayer.model.h2.vertx.RuleModel;
import com.prayer.uca.WebDependant;
import com.prayer.util.StringKit;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.DependRule;

import io.vertx.core.json.JsonArray;
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
public final class UCADependant {   // NOPMD
    // ~ Static Fields =======================================

    /** **/
    private static final String[] REQ_PARAM = new String[] { "rule", "parameter", "query" };
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================

    /**
     * 这个方法的二义性太多，所以必须在此说明 <code>1.如果是出现CONVERT情况那么updatedParams会被更新成新的值；</code>
     * <code>2.CONVERT失败的时候抛出Exception</code>
     * <code>3.检查失败的时候同样抛出Exception</code> <code>4.验证失败的时候也抛出Exception</code>
     * <code>5.通过条件就是Validate成功并且根据关联数据Convert也成功，那么就继续请求</code>
     * 
     * @param paramName
     * @param paramValue
     * @param ruleModel
     * @throws AbstractWebException
     */
    public static void dependField(@NotNull @NotBlank @NotEmpty final String paramName, final String paramValue,
            @NotNull final RuleModel ruleModel, @NotNull final JsonObject inputParam, @NotNull final JsonObject outputParam)
                    throws AbstractWebException {
        // 1.验证Dependant是否合法
        final String componentCls = ruleModel.getComponentClass();
        Interruptor.interruptClass(UCADependant.class, componentCls, "Dependant");
        Interruptor.interruptImplements(UCADependant.class, componentCls, WebDependant.class);
        // 2.提取Dependant中处理的类型信息
        final String typeCls = ruleModel.getType().getClassName();
        final Value<?> value = instance(typeCls, paramValue);
        // 3.验证config的三个特殊参数以及数据类型
        verifyConfig(ruleModel);
        // 4.构造sqlQuery，Depend组件的必备参数
        final JsonObject config = ruleModel.getConfig();
        if (null != config) {
            final String sqlQuery = buildSql(config.getString("query"), inputParam, config.getJsonArray("parameter"));
            // 5.获取Dependant
            final WebDependant dependant = instance(componentCls);
            final JsonObject retJson = dependant.process(paramName, value, config, sqlQuery);
            // 6.获取当前Config的Rule
            final DependRule rule = fromStr(DependRule.class, config.getString("rule"));
            if (DependRule.VALIDATE == rule) {
                final Boolean ret = retJson.getBoolean(WebDependant.VAL_RET);
                if (!ret) {
                    throw new ValidationFailureException(ruleModel.getErrorMessage());
                }
            } else if (DependRule.CONVERT == rule) {
                // 返回Convert的值信息
                final String retVal = retJson.getString(WebDependant.CVT_RET);
                outputParam.put(paramName, retVal);
            }
        }
    }

    private static String buildSql(final String query, final JsonObject inputParam, final JsonArray params) {
        final int size = params.size();
        final Object[] arguments = new Object[size];
        for (int idx = 0; idx < size; idx++) {
            final Object key = params.getValue(idx);
            if (null != key) {
                arguments[idx] = inputParam.getValue(key.toString());
            }
        }
        return MessageFormat.format(query, arguments);
    }

    /**
     * 
     * @param ruleModel
     */
    private static void verifyConfig(final RuleModel ruleModel) throws AbstractWebException { // NOPMD
        // 3.提取配置信息，验证config的必须属性
        final JsonObject config = ruleModel.getConfig();
        if (null != config) {
            for (final String required : REQ_PARAM) {
                if (!config.containsKey(required)) {
                    throw new DependParamsMissingException(UCADependant.class, required, ruleModel.getName());
                }
            }
            // 4.rule的设置
            final String ruleStr = config.getString(REQ_PARAM[Constants.ZERO]);
            DependRule rule = null;
            if (null != ruleStr) {
                rule = fromStr(DependRule.class, ruleStr);
            }
            if (null == rule) {
                throw new DependRuleInvalidException(UCADependant.class, ruleStr);
            }
            // 5.parameter格式验证
            final Object paramObj = config.getValue(REQ_PARAM[Constants.ONE]);
            if (null != paramObj && JsonArray.class != paramObj.getClass()) {
                throw new DependParameterInvalidException(UCADependant.class, paramObj.getClass());
            }
            // 6.query格式验证
            final Object queryStr = config.getValue(REQ_PARAM[Constants.TWO]);
            if (queryStr == null) {
                // query为null
                throw new DependQueryInvalidException(UCADependant.class, null);
            } else {
                if (String.class == queryStr.getClass()) {
                    // query的字符串的值为空字符串
                    if (StringKit.isNil(queryStr.toString())) {
                        throw new DependQueryInvalidException(UCADependant.class, queryStr.toString());
                    }
                } else {
                    // query为非String的情况直接抛错
                    throw new DependQueryInvalidException(UCADependant.class, queryStr.getClass().getName());
                }
            }
        }
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private UCADependant() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
