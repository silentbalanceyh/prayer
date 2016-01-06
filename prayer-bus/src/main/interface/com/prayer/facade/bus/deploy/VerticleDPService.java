package com.prayer.facade.bus.deploy;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.vertx.VerticleModel;
/**
 * 
 * @author Lang
 *
 */
public interface VerticleDPService extends TemplateDPService<VerticleModel,String> {
    /** 
     * 
     * @param jsonPath
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<VerticleModel>>> importVerticles(String jsonPath);
}
