package com.prayer.schema.dao;

import java.util.List;

import com.prayer.model.h2.vx.UriModel;

import io.vertx.core.http.HttpMethod;

/**
 * 
 * @author Lang
 *
 */
public interface UriDao extends TemplateDao<UriModel, String>{
    /**
     * 
     * @param uri
     * @return
     */
    UriModel getByUri(String uri,HttpMethod method);
    /**
     * 
     * @param uri
     * @return
     */
    List<UriModel> getByUri(String uri);
}
