package com.prayer.facade.bus.deploy;

import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.VerticleChain;
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
    ServiceResult<ConcurrentMap<String, VerticleChain>> importVerticles(String jsonPath);
}
