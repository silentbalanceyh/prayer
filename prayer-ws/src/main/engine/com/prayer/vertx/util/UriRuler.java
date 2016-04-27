package com.prayer.vertx.util;

import java.util.ArrayList;
import java.util.List;

import com.prayer.facade.engine.cv.WebKeys;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class UriRuler {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param event
     * @param envelop
     * @return
     */
    public static PEUri buildUri(final RoutingContext event, final Envelop envelop){
        return new PEUri(envelop.getUriData(event.request().method()));
    }
    /**
     * Convertor
     * @param event
     * @param envelop
     * @return
     */
    public static List<PERule> buildConvertor(final RoutingContext event, final Envelop envelop) {
        final JsonArray validators = envelop.getRaw().getJsonObject(event.request().method().name())
                .getJsonArray(WebKeys.UriMeta.UAC.CONVERTOR);
        return buildRules(validators);
    }

    /**
     * Dependent
     * @param event
     * @param envelop
     * @return
     */
    public static List<PERule> buildDependent(final RoutingContext event, final Envelop envelop) {
        final JsonArray validators = envelop.getRaw().getJsonObject(event.request().method().name())
                .getJsonArray(WebKeys.UriMeta.UAC.DEPENDENT);
        return buildRules(validators);
    }

    /**
     * Validator
     * @param event
     * @param envelop
     * @return
     */
    public static List<PERule> buildValidator(final RoutingContext event, final Envelop envelop) {
        final JsonArray validators = envelop.getRaw().getJsonObject(event.request().method().name())
                .getJsonArray(WebKeys.UriMeta.UAC.VALIDATOR);
        return buildRules(validators);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static List<PERule> buildRules(final JsonArray ucas) {
        final List<PERule> rules = new ArrayList<>();
        final int size = ucas.size();
        for (int idx = 0; idx < size; idx++) {
            final JsonObject data = ucas.getJsonObject(idx);
            rules.add(new PERule(data));
        }
        return rules;
    }

    private UriRuler() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
