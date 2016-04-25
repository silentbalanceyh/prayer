package com.prayer.vertx.opts.uri;

import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
            final String address = this.buildAddr(uri);
            /** 4.生成Rule信息 **/
            List<PERule> ruleList = rules.get(id);
            if (null == ruleList) {
                ruleList = new ArrayList<>();
            }
            uriData.put(address, this.buildData(uri, ruleList));
        }
        return uriData;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private JsonObject buildData(final PEUri rawData, final List<PERule> rulesData) {
        final JsonObject data = new JsonObject();
        data.put("uri", rawData.toJson());
        /** 1.构造Rules **/
        final JsonArray ruleData = new JsonArray();
        if (null != ruleData) {
            for (final PERule rule : rulesData) {
                ruleData.add(rule.toJson());
            }
        }
        data.put("rules", ruleData);
        return data;
    }

    private String buildAddr(final PEUri rawData) {
        final StringBuilder uriPoint = new StringBuilder();
        uriPoint.append(rawData.getUri()).append('/')
                .append(rawData.getMethod().toString().toUpperCase(Locale.getDefault()));
        return MessageFormat.format(WebKeys.URI_ADDR, uriPoint);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
