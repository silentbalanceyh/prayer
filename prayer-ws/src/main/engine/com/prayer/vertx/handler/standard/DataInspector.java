package com.prayer.vertx.handler.standard;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.model.crucial.Transducer.V;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.vtx.uca.WebValidator;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.web.StatusCode;
import com.prayer.vertx.util.Fault;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 请求接收器
 * 
 * @author Lang
 *
 */
public class DataInspector implements Handler<RoutingContext> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================

    public static DataInspector create() {
        return new DataInspector();
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(final RoutingContext event) {
        /** 1.跳跃验证器 **/
        final List<PERule> rules = event.get(WebKeys.Request.Data.Meta.PEV);
        if (rules.isEmpty()) {
            /** 2.跳过不执行验证 **/
            event.next();
        } else {
            /** 3.抽取Parameters **/
            final JsonObject params = event.get(WebKeys.Request.Data.PARAMS);
            /** 4.验证流程 **/
            final Envelop validated = this.validate(rules, params);
            /** 400 错误 **/
            if (Fault.route(event, validated)) {
                event.next();
            } else {
                // Fix: Response Already Written
                return;
            }
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private Envelop validate(final List<PERule> rules, final JsonObject params) {
        Envelop stumer = Envelop.success();
        for (final PERule rule : rules) {
            final String name = rule.getName();
            if (!params.containsKey(name)) {
                continue;
            } else {
                final String value = params.getString(name);
                stumer = this.validate(rule, name, value);
                if (!stumer.succeeded()) {
                    break;
                }
            }
        }
        return stumer;
    }

    private Envelop validate(final PERule rule, final String name, final String value) {
        Envelop stumer = Envelop.success();
        try {
            /** 1.读取ComponentClass **/
            final Class<?> ucaCls = rule.getComponentClass();
            if (null != ucaCls) {
                /** 2.从Value中提取值 **/
                final Value<?> valueObj = V.get().getValue(rule.getType(), value);
                /** 3.提取配置信息 **/
                final JsonObject config = rule.getConfig();
                /** 4.验证结果 **/
                final WebValidator validator = singleton(ucaCls);
                stumer = validator.validate(name, valueObj, config);
            }
        } catch (AbstractDatabaseException ex) {
            stumer = Envelop.failure(ex, StatusCode.BAD_REQUEST);
        }
        return stumer;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
