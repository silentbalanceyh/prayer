package com.prayer.bus.impl.deploy;

import static com.prayer.util.reflection.Instance.clazz;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.base.bus.AbstractDPSevImpl;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ParamType;
import com.prayer.dao.impl.schema.UriDaoImpl;
import com.prayer.facade.bus.deploy.UriDPService;
import com.prayer.model.vertx.PEUri;
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
public class UriDPSevImpl extends AbstractDPSevImpl<PEUri, String> implements UriDPService { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UriDPSevImpl.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Class<?> getDaoClass() {
        return UriDaoImpl.class;
    }

    /** 获取Logger **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** T Array **/
    @Override
    public PEUri[] getArrayType() {
        return new PEUri[] {};
    }

    /** **/
    @Override
    public List<PEUri> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath) throws AbstractSystemException { // NOPMD
        final TypeReference<List<PEUri>> typeRef = new TypeReference<List<PEUri>>() {
        };
        final List<PEUri> retList = JsonKit.fromFile(typeRef, jsonPath);
        for (final PEUri item : retList) {
            if (null == item.getMethod()) {
                item.setMethod(HttpMethod.GET);
            }
            if (null == item.getParamType()) {
                if (HttpMethod.GET == item.getMethod()) {
                    item.setParamType(ParamType.QUERY);
                } else {
                    item.setParamType(ParamType.BODY);
                }
            }
            if (null == item.getAddress()) {
                item.setAddress("MSG://RECORD/QUEUE");
            }
            final List<String> emptyArr = new ArrayList<>(); // NOPMD
            if (null == item.getReturnFilters()) {
                item.setReturnFilters(emptyArr);
            }
            if (null == item.getRequiredParam()) {
                item.setRequiredParam(emptyArr);
            }
            if (null == item.getSender()) {
                item.setSender(clazz("com.prayer.uca.sender.JsonRecordSender"));
            }
            if (null == item.getScript()) {
                final StringBuilder script = new StringBuilder(Constants.BUFFER_SIZE); // NOPMD
                script.append("js.api.").append(item.getMethod().toString().toLowerCase(Locale.getDefault()))
                        .append('.');
                final String exeJS = item.getUri().replaceAll("/api/", "").replaceAll("/", "\\.");
                script.append(exeJS);
                item.setScript(script.toString());
            }
        }
        return retList;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
