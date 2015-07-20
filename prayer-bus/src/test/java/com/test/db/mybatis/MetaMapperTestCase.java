package com.test.db.mybatis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.model.h2.MetaModel;
import com.prayer.schema.db.MetaMapper;

/**
 * 
 *
 * @author Lang
 * @see
 */
public class MetaMapperTestCase extends AbstractMetaCase {		// NOPMD
	// ~ Static Metas =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MetaMapperTestCase.class);
	// ~ Instance Metas =====================================
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
			final MetaModel meta = this.insertMetas(false).get(0);
			final boolean flag = this.deleteById(meta.getUniqueId());
			assertTrue("[E] (Insert) Executed result should be true.",flag);
		}
	}

	/** **/
	@Test
	public void testUpdate() {
		if (null != session()) {
			final MetaMapper mapper = this.getMetaMapper();
			final MetaModel meta = this.insertMetas(false).get(0);
			// Updating Testing
			final String uniqueId = meta.getUniqueId();
			final MetaModel targetMeta = this.getMeta(uniqueId);
			mapper.update(targetMeta);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Updated record: " + targetMeta);
			}
			
			final boolean flag = this.deleteById(uniqueId);
			assertTrue("[E] (Update) Executed result should be true.",flag);
		}
	}

	/** **/
	@Test
	public void testBatchUpdateAndDelete() {
		if (null != session()) {
			final MetaMapper mapper = this.getMetaMapper();
			final List<MetaModel> metata = this.insertMetas(true);
			// 获取插入的ID值
			final List<String> ids = new ArrayList<>();
			for (final MetaModel meta : metata) {
				ids.add(meta.getUniqueId());
			}
			// 批量更新数据
			final List<MetaModel> targetMetas = this.getMetata(ids);
			int row = 0;
			for (final MetaModel meta : targetMetas) {
				row += mapper.update(meta);
			}
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Batch updated records successfully: "
						+ targetMetas + ", Affected rows: " + row);
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
			final MetaMapper mapper = this.getMetaMapper();
			final List<MetaModel> metas = this.insertMetas(true);
			final List<MetaModel> queriedMetas = mapper.selectAll();
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("[TD] Queried records successfully: " + queriedMetas);
			}
			final boolean ret = metas.size() <= queriedMetas.size() && !queriedMetas.isEmpty();
			assertTrue("[E] Insert & Select Error!",ret);
			// 清除系统中的所有数据
			for(final MetaModel model: metas){
				mapper.deleteById(model.getUniqueId());
			}
			this.session().commit();
		}
	}

	/** **/
	@Test
	public void testBatchInsertAndDelete() {
		if (null != session()) {
			final MetaMapper mapper = this.getMetaMapper();
			final List<MetaModel> metas = this.insertMetas(true);
			// 获取插入的ID值
			final List<String> ids = new ArrayList<>();
			for (final MetaModel Meta : metas) {
				ids.add(Meta.getUniqueId());
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
	/** **/
	@Test
	public void testSpecialQuery(){
		if(null != session()){
			final MetaMapper mapper = this.getMetaMapper();
			final MetaModel meta = this.insertMetas(false).get(0);
			
			final String namespace = meta.getNamespace();
			final String name = meta.getName();
			final MetaModel targetMeta = mapper.selectByModel(namespace, name);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Queried by (namespace + name) successfully: " + targetMeta);
			}
			assertEquals("[E] (Speical GenericQuery) failure.",meta,targetMeta);
			
			final boolean flag = mapper.deleteByModel(namespace, name);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Deleted tested target MetaModel: " + flag);
			}
		}
	}

	// ~ Private Methods =====================================

	private boolean deleteById(final String uniqueId) {
		boolean flag = false;
		if (null != this.session()) {
			final MetaMapper mapper = this.getMetaMapper();
			flag = mapper.deleteById(uniqueId);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Deleted record by K_ID : " + uniqueId);
			}
		}
		return flag;
	}

	private List<MetaModel> insertMetas(final boolean isBatch) {
		final MetaMapper mapper = this.getMetaMapper();
		final List<MetaModel> retMetas = new ArrayList<>();
		if (isBatch) {
			final List<MetaModel> metas = this.getMetata(null);
			mapper.batchInsert(metas);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Batch inserted records successfully: "
						+ metas);
			}
			retMetas.addAll(metas);
		} else {
			final MetaModel meta = this.getMeta(null);
			mapper.insert(meta);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Inserted record successfully : " + meta);
			}
			retMetas.add(meta);
		}
		return retMetas;
	}
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
