package com.prayer.bus.impl.deploy;

import static com.prayer.util.Instance.clazz;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.base.bus.AbstractDPSevImpl;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.dao.impl.schema.RouteDaoImpl;
import com.prayer.facade.bus.deploy.RouteDPService;
import com.prayer.model.vertx.RouteModel;
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
public class RouteDPSevImpl extends AbstractDPSevImpl<RouteModel, String>implements RouteDPService { // NOPMD
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
        return RouteDaoImpl.class;
    }

    /** 获取Logger **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** T Array **/
    @Override
    public RouteModel[] getArrayType() {
        return new RouteModel[] {};
    }

    /** **/
    @Override
    public List<RouteModel> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath)
            throws AbstractSystemException {
        final TypeReference<List<RouteModel>> typeRef = new TypeReference<List<RouteModel>>() {
        };
        final List<RouteModel> retList = JsonKit.fromFile(typeRef, jsonPath);
        for (final RouteModel item : retList) {
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
