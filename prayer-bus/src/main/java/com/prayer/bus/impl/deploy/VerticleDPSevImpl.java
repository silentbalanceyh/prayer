package com.prayer.bus.impl.deploy;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.dao.impl.schema.VerticleDaoImpl;
import com.prayer.exception.AbstractSystemException;
import com.prayer.facade.bus.deploy.VerticleDPService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.vertx.VerticleModel;
import com.prayer.util.JsonKit;
import com.prayer.util.bus.ResultExtractor;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.ResponseCode;

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
public class VerticleDPSevImpl extends AbstractDPSevImpl<VerticleModel, String>implements VerticleDPService {    // NOPMD
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
        return VerticleDaoImpl.class;
    }

    /** 获取Logger **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** T Array **/
    @Override
    public VerticleModel[] getArrayType(){
        return new VerticleModel[]{};
    }
    
    /** **/
    @Override
    public List<VerticleModel> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath)
            throws AbstractSystemException {
        final TypeReference<List<VerticleModel>> typeRef = new TypeReference<List<VerticleModel>>() {
        };
        return JsonKit.fromFile(typeRef, jsonPath);
    }

    /**
     * 
     */
    @Override
    public ServiceResult<ConcurrentMap<String, VerticleChain>> importVerticles(
            @NotNull @NotBlank @NotEmpty final String jsonPath) {
        final ServiceResult<ConcurrentMap<String, VerticleChain>> result = new ServiceResult<>();
        final ServiceResult<List<VerticleModel>> retList = this.importToList(jsonPath);
        if (ResponseCode.SUCCESS == retList.getResponseCode() && Constants.RC_SUCCESS == retList.getErrorCode()) {
            result.success(ResultExtractor.extractVerticles(retList.getResult()));
        }
        return result;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
