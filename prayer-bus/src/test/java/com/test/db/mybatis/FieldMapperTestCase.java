package com.test.db.mybatis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.db.mybatis.FieldMapper;
import com.prayer.mod.sys.FieldModel;

/**
 * 
 *
 * @author Lang
 * @see
 */
public class FieldMapperTestCase extends AbstractMetaCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FieldMapperTestCase.class);
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
			final FieldModel field = this.insertFields(false).get(0);
			final boolean flag = this.deleteById(field.getUniqueId());
			assertTrue("[E] (Insert) Executed result should be true.",flag);
		}
	}

	/** **/
	@Test
	public void testUpdate() {
		if (null != session()) {
			final FieldMapper mapper = this.getFieldMapper();
			final FieldModel field = this.insertFields(false).get(0);
			// Updating Testing
			final String uniqueId = field.getUniqueId();
			final FieldModel targetField = this.getField(uniqueId);
			mapper.update(targetField);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Updated record: " + targetField);
			}
			
			final boolean flag = this.deleteById(uniqueId);
			assertTrue("[E] (Update) Executed result should be true.",flag);
		}
	}

	/** **/
	@Test
	public void testBatchUpdateAndDelete() {
		if (null != session()) {
			final FieldMapper mapper = this.getFieldMapper();
			final List<FieldModel> fields = this.insertFields(true);
			// 获取插入的ID值
			final List<String> ids = new ArrayList<>();
			for (final FieldModel field : fields) {
				ids.add(field.getUniqueId());
			}
			// 批量更新数据
			final List<FieldModel> targetFields = this.getFields(ids);
			int row = 0;
			for (final FieldModel field : targetFields) {
				row += mapper.update(field);
			}
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Batch updated records successfully: "
						+ targetFields + ", Affected rows: " + row);
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
			final FieldMapper mapper = this.getFieldMapper();
			final List<FieldModel> fields = this.insertFields(true);
			final List<FieldModel> queriedFields = mapper.selectAll();
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("[TD] Queried records successfully: " + queriedFields);
			}
			assertEquals("[E] Insert & Select Error!",fields.size(),queriedFields.size());
			// 清除系统中的所有数据
			mapper.purgeData();
			this.session().commit();
		}
	}

	/** **/
	@Test
	public void testBatchInsertAndDelete() {
		if (null != session()) {
			final FieldMapper mapper = this.getFieldMapper();
			final List<FieldModel> fields = this.insertFields(true);
			// 获取插入的ID值
			final List<String> ids = new ArrayList<>();
			for (final FieldModel field : fields) {
				ids.add(field.getUniqueId());
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
			final FieldMapper mapper = this.getFieldMapper();
			flag = mapper.deleteById(uniqueId);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Deleted record by K_ID : " + uniqueId);
			}
		}
		return flag;
	}

	private List<FieldModel> insertFields(final boolean isBatch) {
		final FieldMapper mapper = this.getFieldMapper();
		final List<FieldModel> retFields = new ArrayList<>();
		if (isBatch) {
			final List<FieldModel> fields = this.getFields(null);
			mapper.batchInsert(fields);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Batch inserted records successfully: "
						+ fields);
			}
			retFields.addAll(fields);
		} else {
			final FieldModel field = this.getField(null);
			mapper.insert(field);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Inserted record successfully : " + field);
			}
			retFields.add(field);
		}
		return retFields;
	}
	// ~ hashCode,equals,toString ============================
}
