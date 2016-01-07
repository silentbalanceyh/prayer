package com.prayer.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    UriModel selectByUriAndMethod(@Param("uri") String uri,@Param("method") HttpMethod method);
    /**
     * 
     * @param uri
     * @return
     */
    List<UriModel> selectByUri(@Param("uri") String uri);
}
