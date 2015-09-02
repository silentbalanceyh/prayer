package com.prayer.schema.dao.impl;

import static com.prayer.util.Generator.uuid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.vx.VerticleModel;
import com.prayer.schema.dao.ConfigDao;
import com.prayer.schema.db.SessionManager;
import com.prayer.schema.db.VerticleMapper;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.MinLength;
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
public class ConfigDaoImpl extends AbstractDaoImpl implements ConfigDao {	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigDaoImpl.class);
	/** Exception Class **/
	private static final String EXP_CLASS = "com.prayer.exception.vertx.DataAccessException";

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

	/** 可支持多数量创建的VerticleModel添加方法 **/
	@Override
	public List<VerticleModel> insert(@NotNull @MinLength(1) final VerticleModel... verticle)
			throws AbstractTransactionException {
		// 1.设置返回List
		final List<VerticleModel> retList = new ArrayList<>();
		// 2.开启Mybatis事务
		final SqlSession session = SessionManager.getSession();
		final Transaction transaction = transaction(session);
		// 3.执行插入操作
		final VerticleMapper mapper = session.getMapper(VerticleMapper.class);
		{
			if (Constants.ONE == verticle.length) {
				final VerticleModel single = verticle[0];
				// 3.1.1.单条记录准备
				if (StringKit.isNil(single.getUniqueId())) {
					single.setUniqueId(uuid());
				}
				// 3.1.2.单挑记录插入
				mapper.insert(single);
				retList.add(single);
			} else {
				// 3.2.1.批量插入
				for (final VerticleModel item : verticle) {
					if (StringKit.isNil(item.getUniqueId())) {
						item.setUniqueId(uuid());
					}
				}
				// 3.2.2.批量处理插入
				final List<VerticleModel> params = Arrays.asList(verticle);
				mapper.batchInsert(params);
				retList.addAll(params);
			}
		}
		// 4.事务提交
		submit(transaction, EXP_CLASS);
		return retList;
	}

	/** 更新单挑VerticleModel的记录信息 **/
	@Override
	public VerticleModel update(@NotNull final VerticleModel verticle) throws AbstractTransactionException {
		// 1.开启Mybatis事务
		final SqlSession session = SessionManager.getSession();
		final Transaction transaction = transaction(session);
		// 2.执行插入操作
		final VerticleMapper mapper = session.getMapper(VerticleMapper.class);
		{
			// 3.更新数据信息
			mapper.update(verticle);
		}
		// 4.事务提交
		submit(transaction, EXP_CLASS);
		return verticle;
	}

	/** 根据ID删除系统中存在的记录 **/
	@Override
	public boolean deleteById(@NotNull @NotBlank @NotEmpty final String uniqueId) throws AbstractTransactionException {
		// 1.开启Mybatis事务
		final SqlSession session = SessionManager.getSession();
		final Transaction transaction = transaction(session);
		// 2.删除当前记录
		final VerticleMapper mapper = session.getMapper(VerticleMapper.class);
		mapper.deleteById(uniqueId);
		// 3.事务提交完成
		submit(transaction, EXP_CLASS);
		return true;
	}

	/** 根据name删除系统中存在的记录 **/
	@Override
	public boolean deleteByName(@NotNull @NotBlank @NotEmpty final String name) throws AbstractTransactionException {
		// 1.开启Mybatis事务
		final SqlSession session = SessionManager.getSession();
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
	public VerticleChain getByGroup(@NotNull @NotBlank @NotEmpty final String group) {
		// 1.初始化SqlSession
		final SqlSession session = SessionManager.getSession();
		// 2.获取Mapper
		final VerticleMapper mapper = session.getMapper(VerticleMapper.class);
		// 3.返回直接结果
		final List<VerticleModel> retList = mapper.selectByGroup(group);
		// 4.构造返回值
		final VerticleChain retObj = new VerticleChain(group);
		// 5.针对返回值进行分类填充
		retObj.initVerticles(retList);
		// 6.关闭Session返回最终结果
		session.close();
		return retObj;
	}

	/** 根据Class类名获取单条VerticleModel **/
	@Override
	public VerticleModel getByClass(@NotNull @NotBlank @NotEmpty final String clazz) {
		// 1.初始化SqlSession
		final SqlSession session = SessionManager.getSession();
		// 2.获取Mapper
		final VerticleMapper mapper = session.getMapper(VerticleMapper.class);
		// 3.读取返回信息
		final VerticleModel ret = mapper.selectByName(clazz);
		// 4.关闭Session并且返回最终结果
		session.close();
		return ret;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
