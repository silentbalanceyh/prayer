package com.prayer.bus.impl.deploy;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.base.bus.AbstractDPSevImpl;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.bus.deploy.VerticleDPService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.vertx.PEVerticle;
import com.prayer.util.bus.ResultExtractor;
import com.prayer.util.io.JsonKit;

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
public class VerticleDPSevImpl extends AbstractDPSevImpl<PEVerticle, String> implements VerticleDPService { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VerticleDPSevImpl.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Class<?> getDaoClass() {
        return null; // VerticleDaoImpl.class;
    }

    /** 获取Logger **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** T Array **/
    @Override
    public PEVerticle[] getArrayType() {
        return new PEVerticle[] {};
    }

    /** **/
    @Override
    public List<PEVerticle> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath)
            throws AbstractSystemException {
        final TypeReference<List<PEVerticle>> typeRef = new TypeReference<List<PEVerticle>>() {
        };
        return JsonKit.fromFile(typeRef, jsonPath);
    }

    /**
     * 
     */
    @Override
    public ServiceResult<ConcurrentMap<String, List<PEVerticle>>> importVerticles(
            @NotNull @NotBlank @NotEmpty final String jsonPath) {
        final ServiceResult<ConcurrentMap<String, List<PEVerticle>>> result = new ServiceResult<>();
        final ServiceResult<List<PEVerticle>> retList = this.importToList(jsonPath);
        if (ResponseCode.SUCCESS == retList.getResponseCode() && Constants.RC_SUCCESS == retList.getErrorCode()) {
            result.success(ResultExtractor.extractList(retList.getResult(),"group"));
        }
        return result;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
