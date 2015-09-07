package com.prayer.schema.dao.impl;

import static com.prayer.util.Error.info;
import static com.prayer.util.Generator.uuid;
import static com.prayer.util.Instance.field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.exception.h2.MapperClassNullException;
import com.prayer.schema.dao.TemplateDao;
import com.prayer.schema.db.H2TMapper;
import com.prayer.schema.db.SessionManager;

import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 一个Dao的标准实现模型，包含了模板方法
 * 
 * @author Lang
 *
 */
@SuppressWarnings("unchecked")
@Guarded
public class TemplateDaoImpl<T, ID extends Serializable> extends AbstractDaoImpl implements TemplateDao<T, ID> { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateDaoImpl.class);
	/** **/
	private static final String UK_ID = "uniqueId";
	/** Exception Class **/
	protected static final String EXP_CLASS = "com.prayer.exception.vertx.DataAccessException";

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
	protected Class<?> getMapper() {
		return null;
	}

	/** 可支持批量创建的创建方法 **/
	@Override
	public List<T> insert(@NotNull @MinLength(1) final T... entities) throws AbstractTransactionException {
		// 1.设置返回List
		final List<T> retList = new ArrayList<>();
		// 2.开启Mybatis事务处理
		final SqlSession session = SessionManager.getSession();
		final Transaction transaction = transaction(session);
		// 3.执行插入操作
		final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
		{
			if (Constants.ONE == entities.length) {
				final T entity = entities[0];
				// 3.1.1.单挑记录ID设置
				if (null == field(entity, UK_ID)) {
					field(entity, UK_ID, uuid());
				}
				// 3.1.2.数据库插入
				mapper.insert(entity);
				retList.add(entity);
			} else {
				// 3.2.1.批量插入
				for (final T item : entities) {
					if (null == field(item, UK_ID)) {
						field(item, UK_ID, uuid());
					}
				}
				// 3.2.2.批量处理插入
				final List<T> params = Arrays.asList(entities);
				mapper.batchInsert(params);
				retList.addAll(params);
			}
		}
		// 4.事务提交
		submit(transaction, EXP_CLASS);
		return retList;
	}

	/** 更新的模板方法 **/
	@Override
	public T update(@NotNull final T entity) throws AbstractTransactionException {
		// 1.开启Mybatis事务
		final SqlSession session = SessionManager.getSession();
		final Transaction transaction = transaction(session);
		// 2.获取Mapper
		final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
		{
			// 3.更新数据信息
			mapper.update(entity);
		}
		// 4.事务提交
		submit(transaction, EXP_CLASS);
		return entity;
	}

	/** 删除的模板方法 **/
	@Override
	public boolean deleteById(@NotNull final ID uniqueId) throws AbstractTransactionException {
		// 1.开启Mybatis事务
		final SqlSession session = SessionManager.getSession();
		final Transaction transaction = transaction(session);
		// 2.删除当前记录
		final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
		mapper.deleteById(uniqueId);
		// 3.事务提交完成
		submit(transaction, EXP_CLASS);
		return true;
	}

	/** 获取实体的模板方法 **/
	@Override
	public T getById(final ID uniqueId) {
		// 1.初始化SqlSession
		final SqlSession session = SessionManager.getSession();
		T ret = null;
		try {
			// 2.获取Mapper
			final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
			// 3.读取返回信息
			ret = mapper.selectById(uniqueId);
			// 4.关闭Session返回结构
			session.close();
		} catch (AbstractTransactionException ex) {
			info(getLogger(), "[H2] (T getById(ID)) Exception occurs !", ex);
		}
		return ret;
	}

	/** 获取所有数据的模板方法 **/
	@Override
	public List<T> getAll() {
		// 1.初始化SqlSession
		final SqlSession session = SessionManager.getSession();
		List<T> retList = null;
		try {
			// 2.获取Mapper
			final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
			// 3.读取返回列表
			retList = mapper.selectAll();
			// 4.关闭Session返回最终结果
			session.close();
		} catch (AbstractTransactionException ex) {
			info(getLogger(), "[H2] (List<T> getAll()) Exception occurs !", ex);
		}
		return retList;
	}

	/** 清空数据库中的数据 **/
	@Override
	public boolean clear() throws AbstractTransactionException {
		// 1.开启Mybatis事务
		final SqlSession session = SessionManager.getSession();
		final Transaction transaction = transaction(session);
		// 2.删除当前记录
		final H2TMapper<T, ID> mapper = (H2TMapper<T, ID>) session.getMapper(mapper());
		mapper.purgeData();
		// 3.事务提交完成
		submit(transaction, EXP_CLASS);
		return true;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Class<?> mapper() throws AbstractTransactionException {
		final Class<?> mapperClass = getMapper();
		if (null == mapperClass) {
			throw new MapperClassNullException(getClass(), getClass().getName());
		}
		return mapperClass;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}