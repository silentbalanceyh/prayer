package com.prayer.dao.impl.schema;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.dao.schema.AddressDao;
import com.prayer.facade.mapper.AddressMapper;
import com.prayer.model.vertx.PEAddress;
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
public class AddressDaoImpl extends TemplateDaoImpl<PEAddress, String> implements AddressDao {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressDaoImpl.class);

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
        return AddressMapper.class;
    }

    /** **/
    @Override
    public PEAddress getByClass(@NotNull @NotBlank @NotEmpty final Class<?> workClass) {
        // 1.初始化SqlSession
        final SqlSession session = SessionManager.getSession();
        // 2.获取Mapper
        final AddressMapper mapper = session.getMapper(AddressMapper.class);
        // 3.读取返回信息
        final PEAddress ret = mapper.selectByClass(workClass);
        // 4.关闭Session并返回
        session.close();
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
