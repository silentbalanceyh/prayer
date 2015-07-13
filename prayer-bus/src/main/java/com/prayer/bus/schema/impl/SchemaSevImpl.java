package com.prayer.bus.schema.impl;

import static com.prayer.util.Error.debug;
import static com.prayer.util.Error.info;
import static com.prayer.util.Generator.uuid;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.schema.SchemaService;
import com.prayer.db.mybatis.FieldMapper;
import com.prayer.db.mybatis.KeyMapper;
import com.prayer.db.mybatis.MetaMapper;
import com.prayer.db.mybatis.SessionManager;
import com.prayer.exception.system.DataLoadingException;
import com.prayer.mod.meta.FieldModel;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.mod.meta.KeyModel;
import com.prayer.mod.meta.MetaModel;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class SchemaSevImpl implements SchemaService {

	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(SchemaSevImpl.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient SqlSession sqlSession;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 */
	@PostValidateThis
	public SchemaSevImpl() {
		sqlSession = SessionManager.getSession();
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@NotNull
	@PreValidateThis
	public GenericSchema buildModel(@NotNull final GenericSchema schema) throws DataLoadingException {
		// 1.数据准备
		this.prepareData(schema);
		// 2.开启Mybatis的事务
		final TransactionFactory tranFactory = new JdbcTransactionFactory();
		final Transaction transaction = tranFactory.newTransaction(session().getConnection());
		// 3.MetaModel的导入
		{
			final MetaMapper metaMapper = this.getMetaMapper();
			metaMapper.insert(schema.getMeta());
		}
		// 4.KeyModel的导入
		{
			final KeyMapper keyMapper = this.getKeyMapper();
			keyMapper.batchInsert(new ArrayList<>(schema.getKeys().values()));
		}
		// 5.FieldModel的导入
		{
			final FieldMapper fieldMapper = this.getFieldMapper();
			fieldMapper.batchInsert(new ArrayList<>(schema.getFields().values()));
		}
		// 6.事务完成提交
		final DataLoadingException exp = submit(transaction);
		if (null != exp) {
			throw exp;
		}
		return schema;
	}

	/** **/
	@Override
	@PreValidateThis
	public GenericSchema findModel(@NotNull final String namespace, @NotNull final String name) {
		// 1.读取Meta
		final MetaMapper metaMapper = this.getMetaMapper();
		final MetaModel meta = metaMapper.selectByModel(namespace, name);

		// 2.返回GenericSchema
		return extractSchema(meta);
	}

	/** **/
	@Override
	@PreValidateThis
	public GenericSchema findModel(@NotNull final String globalId) {
		// 1.读取Meta
		final MetaMapper metaMapper = this.getMetaMapper();
		final MetaModel meta = metaMapper.selectByGlobalId(globalId);

		// 2.返回GenericSchema
		return extractSchema(meta);
	}

	/**
	 * 
	 */
	@Override
	@PreValidateThis
	public boolean removeModel(@NotNull final GenericSchema schema) throws DataLoadingException {
		// 1.开启Mybatis的事务
		final TransactionFactory tranFactory = new JdbcTransactionFactory();
		final Transaction transaction = tranFactory.newTransaction(session().getConnection());
		final String metaId = schema.getMeta().getUniqueId();
		// 2.删除Keys
		final KeyMapper keyMapper = this.getKeyMapper();
		keyMapper.deleteByMeta(metaId);
		// 3.删除Fields
		final FieldMapper fieldMapper = this.getFieldMapper();
		fieldMapper.deleteByMeta(metaId);
		// 4.删除Meta
		final MetaMapper metaMapper = this.getMetaMapper();
		metaMapper.deleteById(metaId);
		// 6.事务完成提交
		final DataLoadingException exp = submit(transaction);
		final boolean ret = null == exp;
		if (!ret) {
			throw exp;
		}
		return ret;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	/**
	 * 
	 * @param meta
	 * @return
	 */
	private GenericSchema extractSchema(final MetaModel meta) {
		// 1.读取Keys -> List
		List<KeyModel> keys = null;
		if (null != meta && null != meta.getUniqueId()) {
			final KeyMapper keyMapper = this.getKeyMapper();
			keys = keyMapper.selectByMeta(meta.getUniqueId());
		}
		// 2.读取Fields -> List
		List<FieldModel> fields = null;
		if (null != meta && null != meta.getUniqueId()) {
			final FieldMapper fieldMapper = this.getFieldMapper();
			fields = fieldMapper.selectByMeta(meta.getUniqueId());
		}
		return extractSchema(meta, keys, fields);
	}

	private GenericSchema extractSchema(final MetaModel meta, final List<KeyModel> keys,
			final List<FieldModel> fields) {
		if (null == meta) {
			return null;
		}
		final GenericSchema schema = new GenericSchema();
		schema.setIdentifier(meta.getGlobalId());
		schema.setMeta(meta);
		schema.setKeys(GenericSchema.getKeysMap(keys));
		schema.setFields(GenericSchema.getFieldsMap(fields));
		return schema;
	}

	private DataLoadingException submit(final Transaction transaction) {
		DataLoadingException throwExp = null;
		try {
			transaction.commit();
		} catch (SQLException ex) {
			throwExp = new DataLoadingException(getClass(), "Commit");
			debug(LOGGER, getClass(), "E20005", throwExp, "Commit");
			info(LOGGER, "Commit SQL Exception.", ex);
			try {
				transaction.rollback();
			} catch (SQLException exp) {
				throwExp = new DataLoadingException(getClass(), "Rollback");
				debug(LOGGER, getClass(), "E20005", throwExp, "Rollback");
				info(LOGGER, "Rollback SQL Exception.", ex);
			}
		} finally {
			try {
				transaction.close();
			} catch (SQLException ex) {
				throwExp = new DataLoadingException(getClass(), "Close");
				debug(LOGGER, getClass(), "E20005", throwExp, "Close");
				info(LOGGER, "Close SQL Exception.", ex);
			}
		}
		return throwExp;
	}

	private void prepareData(final GenericSchema schema) {
		// 1.设置Meta的ID
		final String metaId = uuid();
		schema.getMeta().setUniqueId(metaId);
		// 2.设置Keys的ID
		for (final KeyModel key : schema.getKeys().values()) {
			key.setUniqueId(uuid());
			key.setRefMetaId(metaId);
		}
		// 3.设置Fields的ID
		for (final FieldModel model : schema.getFields().values()) {
			model.setUniqueId(uuid());
			model.setRefMetaId(metaId);
		}
	}

	private SqlSession session() {
		if (null == sqlSession) {
			sqlSession = SessionManager.getSession();
		}
		return sqlSession;
	}

	private KeyMapper getKeyMapper() {
		return session().getMapper(KeyMapper.class);
	}

	private MetaMapper getMetaMapper() {
		return session().getMapper(MetaMapper.class);
	}

	private FieldMapper getFieldMapper() {
		return session().getMapper(FieldMapper.class);
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
