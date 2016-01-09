package com.prayer.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.vertx.PEUri;

import io.vertx.core.http.HttpMethod;

/**
 * 
 * @author Lang
 *
 */
public interface UriMapper extends H2TMapper<PEUri, String>{
    /**
     * 
     * @param uri
     * @return
     */
    PEUri selectByUriAndMethod(@Param("uri") String uri,@Param("method") HttpMethod method);
    /**
     * 
     * @param uri
     * @return
     */
    List<PEUri> selectByUri(@Param("uri") String uri);
}
