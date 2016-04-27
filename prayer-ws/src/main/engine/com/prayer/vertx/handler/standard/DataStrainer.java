package com.prayer.vertx.handler.standard;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prayer.exception.web._400ConvertorMultiException;
import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.model.crucial.Transducer.V;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.vtx.uca.WebConvertor;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractWebException;
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
public class DataStrainer implements Handler<RoutingContext> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================

    public static DataStrainer create() {
        return new DataStrainer();
    }

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(final RoutingContext event) {
        /** 1.跳跃转换器 **/
        final List<PERule> rules = event.get(WebKeys.Request.Data.Meta.PEC);
        if (rules.isEmpty()) {
            /** 2.跳过不执行转换 **/
            event.next();
        } else {
            /** 3.抽取Parameters **/
            final Envelop stumer = event.get(WebKeys.Request.Data.PARAMS);
            final JsonObject params = stumer.getRaw();
            /** 4.转换流程 **/
            final Envelop converted = this.convert(rules, params);
            /** 400 错误 **/
            if (Fault.route(event, converted)) {
                /** 重新填充Params参数 **/
                event.put(WebKeys.Request.Data.PARAMS, Envelop.success(params));
                event.next();
            } else {
                // Fix: Response Already Written
                return;
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private Envelop convert(final List<PERule> rules, final JsonObject params) {
        Envelop stumer = Envelop.success();
        try {
            this.ensureConvertors(rules);
            for (final PERule rule : rules) {
                final String name = rule.getName();
                if (params.containsKey(name)) {
                    final String value = params.getString(name);
                    final Envelop converted = this.convert(rule, name, value);
                    if (converted.succeeded()) {
                        /** 参数会被修改掉 **/
                        params.put(name, converted.getRaw().getString("value"));
                    }
                }
            }
        } catch (AbstractWebException ex) {
            stumer = Envelop.failure(ex, StatusCode.BAD_REQUEST);
        }
        return stumer;
    }

    private Envelop convert(final PERule rule, final String name, final String value) {
        Envelop stumer = Envelop.success();
        try {
            /** 1.读取ComponentClass **/
            final Class<?> ucaCls = rule.getComponentClass();
            if (null != ucaCls) {
                /** 2.从Value中提取值 **/
                final Value<?> valueObj = V.get().getValue(rule.getType(), value);
                /** 3.提取配置信息 **/
                final JsonObject config = rule.getConfig();
                /** 4.处理结果 **/
                final WebConvertor convertor = singleton(ucaCls);
                stumer = convertor.convert(name, valueObj, config);
            }
        } catch (AbstractDatabaseException ex) {
            stumer = Envelop.failure(ex, StatusCode.BAD_REQUEST);
        }
        return stumer;
    }

    private void ensureConvertors(final List<PERule> rules) throws AbstractWebException {
        final Set<String> names = new HashSet<>();
        for (final PERule rule : rules) {
            final String name = rule.getName();
            if (names.contains(name)) {
                throw new _400ConvertorMultiException(getClass(), name);
            } else {
                names.add(name);
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
