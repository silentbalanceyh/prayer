package com.prayer.schema.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.model.h2.vx.UriModel;
import com.prayer.schema.dao.UriDao;
import com.prayer.schema.db.SessionManager;
import com.prayer.schema.db.UriMapper;

import io.vertx.core.http.HttpMethod;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class UriDaoImpl extends TemplateDaoImpl<UriModel, String>implements UriDao { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UriDaoImpl.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 日志记录器 **/
    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    /** 获取Mapper类型 **/
    @Override
    protected Class<?> getMapper() {
        return UriMapper.class;
    }

    /** 根据URI查询系统中的唯一记录 **/
    @Override
    public UriModel getByUri(@NotNull @NotBlank @NotEmpty final String uri, @NotNull final HttpMethod method) {
        // 1.初始化SqlSession
        final SqlSession session = SessionManager.getSession();
        // 2.获取Mapper
        final UriMapper mapper = session.getMapper(UriMapper.class);
        // 3.读取返回信息
        final UriModel ret = mapper.selectByUriAndMethod(uri, method);
        // 4.关闭Session并且返回最终结果
        session.close();
        return ret;
    }

    /** 根据URI查询系统中存在的Method，405 问题专用 **/
    @Override
    public List<UriModel> getByUri(@NotNull @NotBlank @NotEmpty final String uri) {
        // 1.初始化SqlSession
        final SqlSession session = SessionManager.getSession();
        // 2.获取Mapper
        final UriMapper mapper = session.getMapper(UriMapper.class);
        // 3.List结果
        final List<UriModel> uris = mapper.selectByUri(uri);
        // 4.关Session返回最终结果
        session.close();
        return uris;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
