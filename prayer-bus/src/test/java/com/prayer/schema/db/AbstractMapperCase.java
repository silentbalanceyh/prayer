package com.prayer.schema.db;

import static com.prayer.util.Error.info;
import static com.prayer.util.Generator.uuid;
import static com.prayer.util.Instance.field;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.constant.Constants;

/**
 * 
 * @author Lang
 *
 */
@SuppressWarnings("unchecked")
public abstract class AbstractMapperCase<T, ID extends Serializable> { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	protected static final String UK_ID = "uniqueId";
	/** **/
	private static final int BATCH_SIZE = 24;
	// ~ Instance Fields =====================================
	/** **/
	private transient final H2TMapper<T, ID> mapper;
	/** **/
	private transient final SqlSession _session; // NOPMD
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================

	/** **/
	public AbstractMapperCase() {
		this._session = SessionManager.getSession();
		this.mapper = (H2TMapper<T, ID>) _session.getMapper(getMapperClass());
	}

	// ~ Abstract Methods ====================================
	/** 子类需要实现的抽象方法 **/
	public abstract Logger getLogger();

	/** 获取Mapper的类信息 **/
	public abstract Class<?> getMapperClass();

	/** 获取一个新的实例 **/
	public abstract T instance();

	// ~ Override Methods ====================================
	/** **/
	protected ConcurrentMap<String, Integer> fieldLenMap() { // NOPMD
		return null;
	}
	/** **/
	protected String[] filterFields(){
		return new String[]{};
	}

	/**
	 * 
	 * @return
	 */
	protected SqlSession session() {
		return this._session;
	}

	/**
	 * 
	 * @return
	 */
	protected H2TMapper<T, ID> getMapper() {
		return this.mapper;
	}

	/** 自动生成实例信息 **/
	protected T getT(final ID id) { // NOPMD
		T entity = null;
		if (null == id) {
			entity = instance();
			field(entity, UK_ID, uuid());
		} else {
			entity = this.mapper.selectById(id);
			this._session.commit();
		}
		Generator.setValues(entity, new HashSet<>(Arrays.asList(this.filterFields())));
		// 特殊字段长度测试
		if (null != fieldLenMap()) {
			for (final String field : fieldLenMap().keySet()) {
				String value = field(entity, field);
				if (null != value && fieldLenMap().get(field) < value.length()) {
					value = value.substring(0, fieldLenMap().get(field) - 1);
					field(entity, field, value);
				}
			}
		}
		info(getLogger(), "[TD] Entity data : " + entity);
		return entity;
	}

	/** 自动生成一个实例的集合 **/
	protected List<T> getTs(final List<ID> ids) {
		final List<T> list = new ArrayList<>();
		if (null == ids) {
			for (int i = 0; i < BATCH_SIZE; i++) {
				list.add(getT(null));
			}
		} else {
			for (final ID id : ids) {
				list.add(getT(id));
			}
		}
		return list;
	}

	/**
	 * 
	 * @param isBatch
	 * @return
	 */
	protected List<T> insertTs(final boolean isBatch) {
		final H2TMapper<T, ID> mapper = this.getMapper();
		final List<T> retList = new ArrayList<>();
		if (isBatch) {
			final List<T> entities = this.getTs(null);
			mapper.batchInsert(entities);
			this.session().commit();
			retList.addAll(entities);
			info(getLogger(), "[TD] Batch inserted records successfully : " + entities);
		} else {
			final T entity = this.getT(null);
			mapper.insert(entity);
			this.session().commit();
			retList.add(entity);
			info(getLogger(), "[TD] Inserted record successfully : " + entity);
		}
		return retList;
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	protected boolean deleteByIds(final ID... ids) {
		boolean flag = false;
		final H2TMapper<T, ID> mapper = this.getMapper();
		if (Constants.ONE == ids.length) {
			flag = mapper.deleteById(ids[0]);
			this.session().commit();
			info(getLogger(), "[TD] Deleted record by K_ID : " + ids[0]);
		} else {
			flag = mapper.batchDelete(Arrays.asList(ids));
			this.session().commit();
			info(getLogger(), "[TD] Batch deleted by List<K_ID> : " + ids);
		}
		return flag;
	}

	// ~ Template Test Case ==================================
	/**
	 * 模板测试方法：INSERT
	 **/
	@Test
	public void testInsert() {
		final T entity = this.insertTs(false).get(0);
		final ID uniqueId = field(entity,UK_ID);
		final boolean flag = this.deleteByIds(uniqueId);
		assertTrue("[E] (Insert) Executed result should be true.", flag);

	}

	/**
	 * 模板测试方法： UPDATE
	 */
	@Test
	public void testUpdate() {
		final H2TMapper<T, ID> mapper = this.getMapper();
		final T entity = this.insertTs(false).get(0);
		// Updating Testing
		final ID uniqueId = field(entity, UK_ID);
		final T targetT = this.getT(uniqueId);
		mapper.update(targetT);
		this.session().commit();
		info(getLogger(), "[TD] Updated record : " + targetT);
		// 后期处理
		final boolean flag = this.deleteByIds(uniqueId);
		assertTrue("[E] (Update) Executed result should be true.", flag);
	}

	/** **/
	@Test
	public void testBatchUpdateAndDelete() {
		final H2TMapper<T, ID> mapper = this.getMapper();
		final List<T> entities = this.insertTs(true);
		// 获取插入的ID值
		final List<ID> ids = new ArrayList<>();
		for (final T entity : entities) {
			ids.add(field(entity, UK_ID));
		}
		// 批量更新数据
		final List<T> targetTs = this.getTs(ids);
		int row = 0;
		for (final T entity : targetTs) {
			row += mapper.update(entity);
		}
		this.session().commit();
		info(getLogger(), "[TD] Batch updated records successfully : " + targetTs + ", Affected rows : " + row);
		// 批量删除更新过后的数据
		final boolean flag = mapper.batchDelete(ids);
		this.session().commit();
		info(getLogger(), "[TD] Batch deleted records successfully : " + ids);
		assertTrue("[E] (BatchUpdate) Executed result should be true.", flag);
	}

	/** **/
	@Test
	public void testSelectAllAndDelete() {
		final H2TMapper<T, ID> mapper = this.getMapper();
		final List<T> entities = this.insertTs(true);
		final List<T> queriedTs = mapper.selectAll();
		info(getLogger(), "[TD] Queried records successfully : " + queriedTs);
		final boolean ret = entities.size() <= queriedTs.size() && !queriedTs.isEmpty();
		assertTrue("[E] Insert & Select Error!", ret);
		// 清除系统中的所有数据
		mapper.purgeData();
		this.session().commit();
	}

	/** **/
	@Test
	public void testBatchInsertAndDelete() {
		final H2TMapper<T, ID> mapper = this.getMapper();
		final List<T> entities = this.insertTs(true);
		// 获取插入的ID值
		final List<ID> ids = new ArrayList<>();
		for (final T entity : entities) {
			ids.add(field(entity, UK_ID));
		}
		// 批量删除插入的数据
		final boolean flag = mapper.batchDelete(ids);
		this.session().commit();
		info(getLogger(), "[TD] Batch deleted records successfully : " + ids);
		assertTrue("[E] (BatchInsert) Executed result should be true. ", flag);
	}

	// ~ Private Methods =====================================

	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
