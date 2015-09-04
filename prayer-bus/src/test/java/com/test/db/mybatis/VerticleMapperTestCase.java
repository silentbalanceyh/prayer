package com.test.db.mybatis;

import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.ConfigService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.model.h2.vx.VerticleModel;
import com.prayer.schema.db.VerticleMapper;

public class VerticleMapperTestCase extends AbstractMetaCase { 	// NOPMD
	// ~ Static Verticles =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VerticleMapperTestCase.class);
	// ~ Instance Verticles =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/** **/
	@AfterClass
	public static void importH2Data(){
		final ConfigService service = singleton(ConfigSevImpl.class);
		service.importToH2("deploy/oob/prayer.json");
	}
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
			final VerticleModel field = this.insertVerticles(false).get(0);
			final boolean flag = this.deleteById(field.getUniqueId());
			assertTrue("[E] (Insert) Executed result should be true.",flag);
		}
	}

	/** **/
	@Test
	public void testUpdate() {
		if (null != session()) {
			final VerticleMapper mapper = this.getVerticleMapper();
			final VerticleModel field = this.insertVerticles(false).get(0);
			// Updating Testing
			final String uniqueId = field.getUniqueId();
			final VerticleModel targetVerticle = this.getVerticle(uniqueId);
			mapper.update(targetVerticle);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Updated record: " + targetVerticle);
			}
			
			final boolean flag = this.deleteById(uniqueId);
			assertTrue("[E] (Update) Executed result should be true.",flag);
		}
	}

	/** **/
	@Test
	public void testBatchUpdateAndDelete() {
		if (null != session()) {
			final VerticleMapper mapper = this.getVerticleMapper();
			final List<VerticleModel> fields = this.insertVerticles(true);
			// 获取插入的ID值
			final List<String> ids = new ArrayList<>();
			for (final VerticleModel field : fields) {
				ids.add(field.getUniqueId());
			}
			// 批量更新数据
			final List<VerticleModel> targetVerticles = this.getVerticles(ids);
			int row = 0;
			for (final VerticleModel field : targetVerticles) {
				row += mapper.update(field);
			}
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Batch updated records successfully: "
						+ targetVerticles + ", Affected rows: " + row);
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
			final VerticleMapper mapper = this.getVerticleMapper();
			final List<VerticleModel> fields = this.insertVerticles(true);
			final List<VerticleModel> queriedVerticles = mapper.selectAll();
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("[TD] Queried records successfully: " + queriedVerticles);
			}
			final boolean ret = fields.size() <= queriedVerticles.size() && !queriedVerticles.isEmpty();
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
			final VerticleMapper mapper = this.getVerticleMapper();
			final List<VerticleModel> fields = this.insertVerticles(true);
			// 获取插入的ID值
			final List<String> ids = new ArrayList<>();
			for (final VerticleModel field : fields) {
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
			final VerticleMapper mapper = this.getVerticleMapper();
			flag = mapper.deleteById(uniqueId);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Deleted record by K_ID : " + uniqueId);
			}
		}
		return flag;
	}

	private List<VerticleModel> insertVerticles(final boolean isBatch) {
		final VerticleMapper mapper = this.getVerticleMapper();
		final List<VerticleModel> retVerticles = new ArrayList<>();
		if (isBatch) {
			final List<VerticleModel> fields = this.getVerticles(null);
			mapper.batchInsert(fields);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Batch inserted records successfully: "
						+ fields);
			}
			retVerticles.addAll(fields);
		} else {
			final VerticleModel field = this.getVerticle(null);
			mapper.insert(field);
			this.session().commit();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[TD] Inserted record successfully : " + field);
			}
			retVerticles.add(field);
		}
		return retVerticles;
	}
	// ~ hashCode,equals,toString ============================
}
