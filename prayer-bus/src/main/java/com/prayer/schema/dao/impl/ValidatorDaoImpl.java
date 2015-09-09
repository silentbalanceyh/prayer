package com.prayer.schema.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.model.h2.vx.ValidatorModel;
import com.prayer.schema.dao.ValidatorDao;
import com.prayer.schema.db.SessionManager;
import com.prayer.schema.db.ValidatorMapper;

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
public class ValidatorDaoImpl extends TemplateDaoImpl<ValidatorModel, String>implements ValidatorDao { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorDaoImpl.class);

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
		return ValidatorMapper.class;
	}

	/** **/
	@Override
	public List<ValidatorModel> getByUri(@NotNull @NotBlank @NotEmpty final String uriId) {
		// 1.初始化SqlSession
		final SqlSession session = SessionManager.getSession();
		// 2.获取Mapper
		final ValidatorMapper mapper = session.getMapper(ValidatorMapper.class);
		// 3.读取返回信息
		final List<ValidatorModel> ret = mapper.selectByUri(uriId);
		// 4.关闭Session
		session.close();
		return ret;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
