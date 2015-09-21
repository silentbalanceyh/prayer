package com.prayer.schema.db;

import java.util.List;

import com.prayer.model.h2.vx.UriModel;

import io.vertx.core.http.HttpMethod;

/**
 * 
 * @author Lang
 *
 */
public interface UriMapper extends H2TMapper<UriModel, String>{
    /**
     * 
     * @param uri
     * @return
     */
    UriModel selectByUriAndMethod(String uri,HttpMethod method);
    /**
     * 
     * @param uri
     * @return
     */
    List<UriModel> selectByUri(String uri);
}
