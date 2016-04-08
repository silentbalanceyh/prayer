package com.prayer.configuration.impl;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.ComponentType;
import com.prayer.facade.configuration.ConfigService;
import com.prayer.facade.entity.Attributes;
import com.prayer.model.meta.vertx.PEAddress;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEScript;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.model.meta.vertx.PEVerticle;
import com.prayer.util.business.Inverter;

import io.vertx.core.http.HttpMethod;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ConfigBllor implements ConfigService, Attributes {

    // ~ Static Fields =======================================
    /** Script条件 **/
    private static final String S_NAME = "S_NAME";
    /** Work Class条件 **/
    private static final String S_WORK_CLASS = "S_WORK_CLASS";
    /** Uri条件 **/
    private static final String S_URI = "S_URI";
    /** Uri条件 **/
    private static final String S_PARENT = "S_PARENT";
    /** Verticle条件 **/
    private static final String S_IGROUP = "S_IGROUP";
    /** Rule条件 **/
    private static final String R_URI_ID = "R_URI_ID";
    /** Rule条件 **/
    private static final String J_COMPONENT_TYPE = "J_COMPONENT_TYPE";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient MoundSelector selector;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 配置需要使用的Selector初始化
     */
    @PostValidateThis
    public ConfigBllor() {
        this.selector = singleton(MoundSelector.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public ConcurrentMap<String, List<PEVerticle>> verticles() {
        return Inverter.invertList(this.selector.fetchers(PEVerticle.class).inquiryList(), GROUP);
    }

    /** **/
    @Override
    public ConcurrentMap<String, List<PERoute>> routes() {
        return Inverter.invertList(this.selector.fetchers(PERoute.class).inquiryList(), PARENT);
    }

    /** **/
    @Override
    public PEScript script(@NotNull @NotEmpty @NotBlank final String name) {
        return this.selector.fetchers(PEScript.class).inquiry(AndEqer.reference().build(S_NAME, name));
    }

    /** **/
    @Override
    public PEAddress address(@NotNull final Class<?> workClass) {
        return this.selector.fetchers(PEAddress.class)
                .inquiry(AndEqer.reference().build(S_WORK_CLASS, workClass.getName()));
    }

    /** **/
    @Override
    public ConcurrentMap<String, List<PERule>> dependants(@NotNull @NotEmpty @NotBlank final String uriId) {
        return this.components(uriId, ComponentType.DEPENDANT);
    }

    /** **/
    @Override
    public ConcurrentMap<String, List<PERule>> convertors(@NotNull @NotEmpty @NotBlank final String uriId) {
        return this.components(uriId, ComponentType.CONVERTOR);
    }

    /** **/
    @Override
    public ConcurrentMap<String, List<PERule>> validators(@NotNull @NotEmpty @NotBlank final String uriId) {
        return this.components(uriId, ComponentType.VALIDATOR);
    }

    /** **/
    @Override
    public ConcurrentMap<HttpMethod, PEUri> uris(@NotNull @NotEmpty @NotBlank final String path) {
        return Inverter.invertOrb(
                this.selector.fetchers(PEUri.class).inquiryList(AndEqer.reference().build(S_URI, path)), METHOD);
    }

    /** **/
    @Override
    public List<PERoute> routes(@NotNull @NotEmpty @NotBlank final String parent) {
        return this.selector.fetchers(PERoute.class).inquiryList(AndEqer.reference().build(S_PARENT, parent));
    }

    /** **/
    @Override
    public List<PEVerticle> verticles(@NotNull @NotEmpty @NotBlank final String group) {
        return this.selector.fetchers(PEVerticle.class).inquiryList(AndEqer.reference().build(S_IGROUP, group));
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private ConcurrentMap<String, List<PERule>> components(final String uriId, final ComponentType type) {
        final AndEqer ander = AndEqer.reference().build(R_URI_ID, uriId).build(J_COMPONENT_TYPE, type.toString());
        return Inverter.invertList(this.selector.fetchers(PERule.class).inquiryList(ander), "name");
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
