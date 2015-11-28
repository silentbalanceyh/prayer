package com.prayer.plugin.mapper;

import java.util.List;

import com.prayer.model.vertx.UriModel;

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
