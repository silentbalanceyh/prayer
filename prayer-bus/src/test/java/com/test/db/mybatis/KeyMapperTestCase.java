package com.test.db.mybatis;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.mod.meta.KeyModel;
import com.prayer.schema.db.KeyMapper;

/**
 * 
 *
 * @author Lang
 * @see
 */
public class KeyMapperTestCase extends AbstractMetaCase { 	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(KeyMapperTestCase.class);

	// ~ Instance Fields =====================================

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Get/Set =============================================
	// ~ Methods =============================================
	/** **/
	@Test
	public void testInsert() {
		/**
		 * 插入数据的代码
		 */
		if (null != session()) {
			final KeyModel key = this.insertKey(false).get(0);
			final boolean flag = this.deleteById(key.getUniqueId());
			assertTrue("[E] (Insert) Executed result should be true.",flag);
		}
	}

	/** **/
	@Test
	public void testUpdate() {
		if (null != session()) {
			final KeyMapper mapper = this.getKeyMapper();
			final KeyModel key = this.insertKey(false).get(0);
			// Updating Testing
			final String uniqueId = key.getUniqueId();
			final KeyModel keyTarget = this.getKey(uniqueId);
			mapper.update(keyTarget);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Updated record: " + keyTarget);
			}
			
			final boolean flag = this.deleteById(uniqueId);
			assertTrue("[E] (Update) Executed result should be true.",flag);
		}
	}

	/** **/
	@Test
	public void testBatchUpdateAndDelete() {
		if (null != session()) {
			final KeyMapper mapper = this.getKeyMapper();
			final List<KeyModel> keys = this.insertKey(true);
			// 获取插入的ID值
			final List<String> ids = new ArrayList<>();
			for (final KeyModel key : keys) {
				ids.add(key.getUniqueId());
			}
			// 批量更新数据
			final List<KeyModel> targetKeys = this.getKeys(ids);
			int row = 0;
			for (final KeyModel key : targetKeys) {
				row += mapper.update(key);
			}
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Batch updated records successfully: "
						+ targetKeys + ", Affected rows: " + row);
			}
			// 批量删除更新过后的数据
			final boolean flag = mapper.batchDelete(ids);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Batch deleted records successfully: " + ids);
			}
			assertTrue("[E] (BatchUpdate) Executed result should be true.",flag);
		}
	}
	/** **/
	@Test
	public void testSelectAllAndDelete() {
		if (null != session()) {
			final KeyMapper mapper = this.getKeyMapper();
			final List<KeyModel> keys = this.insertKey(true);
			final List<KeyModel> queriedKeys = mapper.selectAll();
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("[TD] Queried records successfully: " + queriedKeys);
			}
			final boolean ret = keys.size() <= queriedKeys.size() && !queriedKeys.isEmpty();
			assertTrue("[E] Insert & Select Error!",ret);
			// 清除系统中的所有数据
			mapper.purgeData();
			this.session().commit();
		}
	}

	/** **/
	@Test
	public void testBatchInsertAndDelete() {
		if (null != session()) {
			final KeyMapper mapper = this.getKeyMapper();
			final List<KeyModel> keys = this.insertKey(true);
			// 获取插入的ID值
			final List<String> ids = new ArrayList<>();
			for (final KeyModel key : keys) {
				ids.add(key.getUniqueId());
			}
			// 批量删除插入的值
			final boolean flag = mapper.batchDelete(ids);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Batch deleted records successfully: " + ids);
			}
			assertTrue("[E] (BatchInsert) Executed result should be true.",flag);
		}
	}

	// ~ Private Methods =====================================

	private boolean deleteById(final String uniqueId) {
		boolean flag = false;
		if (null != this.session()) {
			final KeyMapper mapper = this.getKeyMapper();
			flag = mapper.deleteById(uniqueId);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Deleted record by K_ID : " + uniqueId);
			}
		}
		return flag;
	}

	private List<KeyModel> insertKey(final boolean isBatch) {
		final KeyMapper mapper = this.getKeyMapper();
		final List<KeyModel> retKeys = new ArrayList<>();
		if (isBatch) {
			final List<KeyModel> keys = this.getKeys(null);
			mapper.batchInsert(keys);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Batch inserted records successfully: "
						+ keys);
			}
			retKeys.addAll(keys);
		} else {
			final KeyModel key = this.getKey(null);
			mapper.insert(key);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Inserted record successfully : " + key);
			}
			retKeys.add(key);
		}
		return retKeys;
	}
	// ~ hashCode,equals,toString ============================
}
