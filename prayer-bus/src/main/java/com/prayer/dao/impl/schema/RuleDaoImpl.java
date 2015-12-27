package com.prayer.dao.impl.schema;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.dao.schema.RuleDao;
import com.prayer.facade.mapper.RuleMapper;
import com.prayer.facade.mapper.SessionManager;
import com.prayer.model.h2.vertx.RuleModel;
import com.prayer.util.cv.SystemEnum.ComponentType;

import net.sf.oval.constraint.InstanceOfAny;
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
public class RuleDaoImpl extends TemplateDaoImpl<RuleModel, String> implements RuleDao { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleDaoImpl.class);

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
        return RuleMapper.class;
    }

    /** **/
    @Override
    public List<RuleModel> getByUri(@NotNull @NotBlank @NotEmpty final String uriId) {
        // 1.初始化SqlSession
        final SqlSession session = SessionManager.getSession();
        // 2.获取Mapper
        final RuleMapper mapper = session.getMapper(RuleMapper.class);
        // 3.读取返回信息
        final List<RuleModel> ret = mapper.selectByUri(uriId);
        // 4.关闭Session
        session.close();
        return ret;
    }

    /** **/
    @Override
    public List<RuleModel> getByUriAndCom(@NotNull @NotBlank @NotEmpty final String uriId,
            @NotNull @InstanceOfAny(ComponentType.class) final ComponentType type) {
        // 1.初始化SqlSession
        final SqlSession session = SessionManager.getSession();
        // 2.获取Mapper
        final RuleMapper mapper = session.getMapper(RuleMapper.class);
        // 3.读取返回信息
        final List<RuleModel> ret = mapper.selectByUriAndCom(uriId, type);
        // 4.关闭Session
        session.close();
        return ret;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
