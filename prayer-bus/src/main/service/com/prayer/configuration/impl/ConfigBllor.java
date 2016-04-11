package com.prayer.configuration.impl;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.ComponentType;
import com.prayer.facade.configuration.ConfigInstantor;
import com.prayer.facade.entity.Attributes;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.crucial.MetaColumns;
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
public class ConfigBllor implements ConfigInstantor, Attributes {

    // ~ Static Fields =======================================
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
    public ConcurrentMap<String, List<PEVerticle>> verticles() throws AbstractException {
        return Inverter.invertList(this.selector.fetchers(PEVerticle.class).inquiryList(), GROUP);
    }

    /** **/
    @Override
    public ConcurrentMap<String, List<PERoute>> routes() throws AbstractException {
        return Inverter.invertList(this.selector.fetchers(PERoute.class).inquiryList(), PARENT);
    }

    /** **/
    @Override
    public PEScript script(@NotNull @NotEmpty @NotBlank final String name) throws AbstractException {
        return this.selector.fetchers(PEScript.class)
                .inquiry(AndEqer.reference().build(MetaColumns.column(PEScript.class, NAME), name));
    }

    /** **/
    @Override
    public PEAddress address(@NotNull final Class<?> workClass) throws AbstractException {
        return this.selector.fetchers(PEAddress.class).inquiry(
                AndEqer.reference().build(MetaColumns.column(PEAddress.class, WORK_CLASS), workClass.getName()));
    }

    /** **/
    @Override
    public ConcurrentMap<String, List<PERule>> dependants(@NotNull @NotEmpty @NotBlank final String uriId)
            throws AbstractException {
        return this.components(uriId, ComponentType.DEPENDANT);
    }

    /** **/
    @Override
    public ConcurrentMap<String, List<PERule>> convertors(@NotNull @NotEmpty @NotBlank final String uriId)
            throws AbstractException {
        return this.components(uriId, ComponentType.CONVERTOR);
    }

    /** **/
    @Override
    public ConcurrentMap<String, List<PERule>> validators(@NotNull @NotEmpty @NotBlank final String uriId)
            throws AbstractException {
        return this.components(uriId, ComponentType.VALIDATOR);
    }

    /** **/
    @Override
    public ConcurrentMap<HttpMethod, PEUri> uris(@NotNull @NotEmpty @NotBlank final String path)
            throws AbstractException {
        return Inverter.invertOrb(this.selector.fetchers(PEUri.class)
                .inquiryList(AndEqer.reference().build(MetaColumns.column(PEUri.class, URI), path)), METHOD);
    }

    /** **/
    @Override
    public List<PERoute> routes(@NotNull @NotEmpty @NotBlank final String parent) throws AbstractException {
        return this.selector.fetchers(PERoute.class)
                .inquiryList(AndEqer.reference().build(MetaColumns.column(PERoute.class, PARENT), parent));
    }

    /** **/
    @Override
    public List<PEVerticle> verticles(@NotNull @NotEmpty @NotBlank final String group) throws AbstractException {
        return this.selector.fetchers(PEVerticle.class)
                .inquiryList(AndEqer.reference().build(MetaColumns.column(PEVerticle.class, GROUP), group));
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private ConcurrentMap<String, List<PERule>> components(final String uriId, final ComponentType type)
            throws AbstractException {
        final AndEqer ander = AndEqer.reference().build(MetaColumns.column(PERule.class, REF_UID), uriId)
                .build(MetaColumns.column(PERule.class, COMPONENT_TYPE), type.toString());
        return Inverter.invertList(this.selector.fetchers(PERule.class).inquiryList(ander), NAME);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
