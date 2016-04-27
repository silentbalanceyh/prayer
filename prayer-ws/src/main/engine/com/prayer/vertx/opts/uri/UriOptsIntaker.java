package com.prayer.vertx.opts.uri;

import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.business.configuration.impl.ConfigBllor;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.facade.vtx.config.EngineOptsIntaker;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEUri;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 配置读取器，读取PEUri
 * 
 * @author Lang
 *
 */
public class UriOptsIntaker implements EngineOptsIntaker<String, JsonObject> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================

    /** **/
    private transient ConfigInstantor instantor = singleton(ConfigBllor.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public ConcurrentMap<String, JsonObject> ingest() throws AbstractException {
        /** 1.从系统中读取所有的PEUri和PERule **/
        final ConcurrentMap<String, PEUri> uris = instantor.uris();
        final ConcurrentMap<String, List<PERule>> rules = instantor.rules();
        /** 2.生成最终结果 **/
        final ConcurrentMap<String, JsonObject> uriData = new ConcurrentHashMap<>();

        for (final String id : uris.keySet()) {
            /** 3.生成Message Addrs **/
            final PEUri uri = uris.get(id);
            /** 4.为了区分404和405，不构造Method到地址中 **/
            final String address = MessageFormat.format(WebKeys.URI_ADDR, uri.getUri());
            JsonObject message = new JsonObject();
            if (uriData.containsKey(address)) {
                message = uriData.get(address);
            }
            /** 5.添加Message **/
            message.put(uri.getMethod().name(), this.buildData(uri, rules));
            /** 6.填充address中对应的JsonObject **/
            uriData.put(address, message);
        }
        return uriData;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private JsonObject buildData(final PEUri rawData, final ConcurrentMap<String, List<PERule>> rulesData) {
        /** 1.生成Rule信息 **/
        List<PERule> ruleList = rulesData.get(rawData.id().toString());
        if (null == ruleList) {
            ruleList = new ArrayList<>();
        }
        final JsonObject data = new JsonObject();

        data.put(WebKeys.UriMeta.URI, rawData.toJson());

        /** 2.构造最终三个节点 **/
        final JsonArray validators = new JsonArray();
        final JsonArray convertors = new JsonArray();
        final JsonArray dependents = new JsonArray();
        /** 3.构造组件配置 **/
        if (null != ruleList && !ruleList.isEmpty()) {
            for (final PERule rule : ruleList) {
                switch (rule.getComponentType()) {
                // 添加验证器
                case VALIDATOR: {
                    validators.add(rule.toJson());
                }
                    break;
                // 添加转换器
                case CONVERTOR: {
                    convertors.add(rule.toJson());
                }
                    break;
                // 添加关联器
                case DEPENDANT: {
                    dependents.add(rule.toJson());
                }
                    break;
                }
            }
        }
        /** 4.添加组件配置 **/
        data.put(WebKeys.UriMeta.UAC.VALIDATOR, validators);
        data.put(WebKeys.UriMeta.UAC.CONVERTOR, convertors);
        data.put(WebKeys.UriMeta.UAC.DEPENDENT, dependents);
        return data;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
