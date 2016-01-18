package com.prayer.facade.bus.deploy;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.meta.vertx.PEVerticle;
/**
 * 
 * @author Lang
 *
 */
public interface VerticleDPService extends TemplateDPService<PEVerticle,String> {
    /** 
     * 
     * @param jsonPath
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<PEVerticle>>> importVerticles(String jsonPath);
}
