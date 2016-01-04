package com.prayer.dao.impl.schema;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.dao.schema.ScriptDao;
import com.prayer.facade.mapper.ScriptMapper;
import com.prayer.facade.mapper.SessionManager;
import com.prayer.model.h2.vertx.ScriptModel;

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
public class ScriptDaoImpl extends TemplateDaoImpl<ScriptModel, String> implements ScriptDao {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptDaoImpl.class);

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
        return ScriptMapper.class;
    }

    /** **/
    @Override
    public ScriptModel getByName(@NotNull @NotBlank @NotEmpty final String name) {
        // 1.初始化SqlSession
        final SqlSession session = SessionManager.getSession();
        // 2.获取Mapper
        final ScriptMapper mapper = session.getMapper(ScriptMapper.class);
        // 3.读取返回信息
        final ScriptModel ret = mapper.selectByName(name);
        // 4.关闭Session并且返回最终结果
        session.close();
        return ret;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
