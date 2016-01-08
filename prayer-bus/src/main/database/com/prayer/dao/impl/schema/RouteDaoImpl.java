package com.prayer.dao.impl.schema;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.dao.schema.RouteDao;
import com.prayer.facade.mapper.RouteMapper;
import com.prayer.model.vertx.RouteModel;
import com.prayer.plugin.ibatis.SessionManager;

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
public class RouteDaoImpl extends TemplateDaoImpl<RouteModel, String>implements RouteDao { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteDaoImpl.class);

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
        return RouteMapper.class;
    }

    /** 根据路径查询 **/
    @Override
    public RouteModel getByPath(@NotNull @NotBlank @NotEmpty final String parent,
            @NotNull @NotBlank @NotEmpty final String path) {
        // 1.初始化SqlSession
        final SqlSession session = SessionManager.getSession();
        // 2.获取Mapper
        final RouteMapper mapper = session.getMapper(RouteMapper.class);
        // 3.读取Model
        final RouteModel ret = mapper.selectByPath(parent, path);
        // 4.关闭Session并返回最终结果
        session.close();
        return ret;
    }

    /** 根据根路径查询 **/
    @Override
    public List<RouteModel> getByParent(@NotNull @NotBlank @NotEmpty final String parent) {
        // 1.初始化SqlSession
        final SqlSession session = SessionManager.getSession();
        // 2.获取Mapper
        final RouteMapper mapper = session.getMapper(RouteMapper.class);
        // 3.读取Model
        final List<RouteModel> retList = mapper.selectByParent(parent);
        // 4.关闭Session并返回最终结果
        session.close();
        return retList;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
