package com.prayer.facade.dao.schema;

import java.util.List;

import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.model.meta.vertx.PEUri;

import io.vertx.core.http.HttpMethod;

/**
 * 
 * @author Lang
 *
 */
public interface UriDao extends MetaAccessor<PEUri, String>{
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
