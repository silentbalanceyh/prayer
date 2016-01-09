package com.prayer.facade.dao.schema;

import java.util.List;

import com.prayer.model.vertx.PEUri;

import io.vertx.core.http.HttpMethod;

/**
 * 
 * @author Lang
 *
 */
public interface UriDao extends TemplateDao<PEUri, String>{
    /**
     * 
     * @param uri
     * @return
     */
    PEUri getByUri(String uri,HttpMethod method);
    /**
     * 
     * @param uri
     * @return
     */
    List<PEUri> getByUri(String uri);
}
