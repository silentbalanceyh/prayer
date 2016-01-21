package com.prayer.business.impl.deploy;

import static com.prayer.util.reflection.Instance.clazz;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.facade.business.deploy.RouteDPService;
import com.prayer.fantasm.business.AbstractDPSevImpl;
import com.prayer.fantasm.exception.AbstractSystemException;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.util.io.JsonKit;

import io.vertx.core.http.HttpMethod;
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
public class RouteDPSevImpl extends AbstractDPSevImpl<PERoute, String>implements RouteDPService { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteDPSevImpl.class);

    // ~ Instance Fields =====================================

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Class<?> getDaoClass() {
        return null; // RouteDaoImpl.class;
    }

    /** 获取Logger **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** T Array **/
    @Override
    public PERoute[] getArrayType() {
        return new PERoute[] {};
    }

    /** **/
    @Override
    public List<PERoute> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath)
            throws AbstractSystemException {
        final TypeReference<List<PERoute>> typeRef = new TypeReference<List<PERoute>>() {
        };
        final List<PERoute> retList = JsonKit.fromFile(typeRef, jsonPath);
        for (final PERoute item : retList) {
            // Default Http Method
            if (null == item.getMethod()) {
                item.setMethod(HttpMethod.GET);
            }
            // Default Order
            if (null == item.getRequestHandler()){
                item.setRequestHandler(clazz("com.prayer.handler.standard.RecordHandler"));
            }
            // Default Parent
            if (null == item.getParent()){
                item.setParent("/api");
            }
            // Default MIME
            final List<String> mimes = new ArrayList<>();    // NOPMD
            mimes.add("json");
            if (null == item.getConsumerMimes()){
                item.setConsumerMimes(mimes);
            }
            if (null == item.getProducerMimes()){
                item.setProducerMimes(mimes);
            }
        }
        return retList;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
