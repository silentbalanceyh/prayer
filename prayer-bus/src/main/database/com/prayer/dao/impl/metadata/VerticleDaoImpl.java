package com.prayer.dao.impl.metadata;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.accessor.impl.IBatisAccessorImpl;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.facade.dao.metadata.VerticleDao;
import com.prayer.facade.mapper.VerticleMapper;
import com.prayer.model.vertx.PEVerticle;
import com.prayer.plugin.ibatis.PESessionManager;

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
public class VerticleDaoImpl extends IBatisAccessorImpl<PEVerticle, String> implements VerticleDao { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VerticleDaoImpl.class);

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
        return VerticleMapper.class;
    }

    /** 根据name删除系统中存在的记录 **/
    @Override
    public boolean deleteByName(@NotNull final Class<?> name) throws AbstractTransactionException {
        // 1.开启Mybatis事务
        final SqlSession session = PESessionManager.getSession();
        final Transaction transaction = transaction(session);
        // 2.删除当前记录
        final VerticleMapper mapper = session.getMapper(VerticleMapper.class);
        mapper.deleteByName(name);
        // 3.事务提交完成
        submit(transaction, EXP_CLASS);
        return true;
    }

    /** 根据Group名称获取系统存在记录 **/
    @Override
    public List<PEVerticle> getByGroup(@NotNull @NotBlank @NotEmpty final String group) {
        // 1.初始化SqlSession
        final SqlSession session = PESessionManager.getSession();
        // 2.获取Mapper
        final VerticleMapper mapper = session.getMapper(VerticleMapper.class);
        // 3.返回直接结果
        final List<PEVerticle> retList = mapper.selectByGroup(group);
        // 3.关闭Session返回最终结果
        session.close();
        return retList;
    }

    /** 根据Class类名获取单条VerticleModel **/
    @Override
    public PEVerticle getByClass(@NotNull final Class<?> clazz) {
        // 1.初始化SqlSession
        final SqlSession session = PESessionManager.getSession();
        // 2.获取Mapper
        final VerticleMapper mapper = session.getMapper(VerticleMapper.class);
        // 3.读取返回信息
        final PEVerticle ret = mapper.selectByName(clazz);
        // 4.关闭Session并且返回最终结果
        session.close();
        return ret;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
