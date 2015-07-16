package com.prayer.dao.schema.impl;

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

import com.prayer.dao.schema.SchemaDao;
import com.prayer.db.mybatis.FieldMapper;
import com.prayer.db.mybatis.KeyMapper;
import com.prayer.db.mybatis.MetaMapper;
import com.prayer.db.mybatis.SessionManager;
import com.prayer.exception.system.DataLoadingException;
import com.prayer.mod.meta.FieldModel;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.mod.meta.KeyModel;
import com.prayer.mod.meta.MetaModel;
import com.prayer.util.StringKit;

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
public class SchemaDaoImpl implements SchemaDao {

	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(SchemaDaoImpl.class);
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
	public SchemaDaoImpl() {
		sqlSession = SessionManager.getSession();
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@NotNull
	@PreValidateThis
	public GenericSchema create(@NotNull final GenericSchema schema) throws DataLoadingException {
		// 1.数据准备
		if (StringKit.isNonNil(schema.getMeta().getUniqueId())) {
			schema.getMeta().setUniqueId(null);
		}
		this.prepareData(schema);
		// 2.开启Mybatis的事务
		final TransactionFactory tranFactory = new JdbcTransactionFactory();
		final Transaction transaction = tranFactory.newTransaction(session().getConnection());

		{
			// 3.MetaModel的导入
			this.getMetaMapper().insert(schema.getMeta());
			// 4.KeyModel的导入
			this.getKeyMapper().batchInsert(new ArrayList<>(schema.getKeys().values()));
			// 5.FieldModel的导入
			this.getFieldMapper().batchInsert(new ArrayList<>(schema.getFields().values()));
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
	@NotNull
	@PreValidateThis
	public GenericSchema synchronize(@NotNull final GenericSchema schema) throws DataLoadingException {
		// 1.刷新数据库中的Schema数据
		final GenericSchema latestSchema = this.refreshData(schema);
		// 2.数据准备
		this.prepareData(latestSchema);
		// 3.开启Mybatis的事务
		final TransactionFactory tranFactory = new JdbcTransactionFactory();
		final Transaction transaction = tranFactory.newTransaction(session().getConnection());
		{
			// 4.MetaModel的更新
			this.getMetaMapper().update(schema.getMeta());
			// 5.KeyModel的更新
			this.getKeyMapper().deleteByMeta(schema.getMeta().getUniqueId());
			this.getKeyMapper().batchInsert(new ArrayList<>(schema.getKeys().values()));
			// 6.FieldModel的更新
			this.getFieldMapper().deleteByMeta(schema.getMeta().getUniqueId());
			this.getFieldMapper().batchInsert(new ArrayList<>(schema.getFields().values()));
		}
		// 6.事务完成提交
		// 6.事务完成提交
		final DataLoadingException exp = submit(transaction);
		if (null != exp) {
			throw exp;
		}
		return latestSchema;
	}

	/** **/
	@Override
	@PreValidateThis
	public GenericSchema getById(@NotNull final String globalId) {
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
	public boolean deleteById(@NotNull final String identifier) throws DataLoadingException {
		// 1.开启Mybatis的事务
		final TransactionFactory tranFactory = new JdbcTransactionFactory();
		final Transaction transaction = tranFactory.newTransaction(session().getConnection());
		final GenericSchema schema = this.getById(identifier);
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
		}
		return throwExp;
	}

	/**
	 * 1.在刷新数据过程GlobalId是不可更改的 2.GlobalId -> UniqueId 3.字段Field和Key以name作为不可变更维度
	 * 
	 * @param schema
	 *            新的Json传入的Schema文件
	 */
	private GenericSchema refreshData(final GenericSchema schema) {
		// 1.从数据库中读取原始schema
		final GenericSchema original = this.getById(schema.getIdentifier());
		// 2.拷贝Meta中的数据到original中执行Overwrite
		// uniqueId不执行更新
		// initOrder不执行更新
		// initSubOrder不执行更新
		// oobFile不执行更新
		// using不执行更新
		info(LOGGER, "[I] Meta from database original = " + original);
		if (null == original || null == original.getMeta() || null == schema || null == schema.getMeta()) {
			info(LOGGER, "[I] The meta data object does not exist in H2 : Global Id = " + schema.getIdentifier());
		} else {
			original.getMeta().setCategory(schema.getMeta().getCategory());
			original.getMeta().setGlobalId(schema.getMeta().getGlobalId());
			original.getMeta().setMapping(schema.getMeta().getMapping());
			original.getMeta().setName(schema.getMeta().getName());
			original.getMeta().setNamespace(schema.getMeta().getNamespace());
			original.getMeta().setPolicy(schema.getMeta().getPolicy());
			original.getMeta().setSeqInit(schema.getMeta().getSeqInit());
			original.getMeta().setSeqName(schema.getMeta().getSeqName());
			original.getMeta().setSeqStep(schema.getMeta().getSeqStep());
			original.getMeta().setSubKey(schema.getMeta().getSubKey());
			original.getMeta().setSubTable(schema.getMeta().getSubTable());
			original.getMeta().setTable(schema.getMeta().getTable());
		}
		return original;
	}

	private void prepareData(final GenericSchema schema) {
		// 1.设置Meta的ID
		String metaId = null;
		if (StringKit.isNil(schema.getMeta().getUniqueId())) {
			// 创建新的Schema
			metaId = uuid();
			schema.getMeta().setUniqueId(metaId);
		} else {
			// 更新现有的Schema
			metaId = schema.getMeta().getUniqueId();
		}
		// 设置Identifier
		schema.setIdentifier(schema.getMeta().getGlobalId());
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
