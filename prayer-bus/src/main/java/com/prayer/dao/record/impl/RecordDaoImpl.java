package com.prayer.dao.record.impl;

import static com.prayer.util.Instance.instance;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.dao.record.RecordDao;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;

import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RecordDaoImpl implements RecordDao {
	// ~ Static Fields =======================================
	/** 前置验证条件 **/
	private static final String DAO_EXPR = "_this.dao != null";
	// ~ Instance Fields =====================================
	/** 抽象Dao **/
	@NotNull
	private transient final RecordDao dao;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public RecordDaoImpl() {
		this.dao = instance(Resources.DB_DAO);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 * @param record
	 * @return
	 * @throws AbstractDatabaseException
	 */
	@Override
	@Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
	public Record insert(@NotNull final Record record) throws AbstractDatabaseException {
		return this.dao.insert(record);
	}

	/**
	 * 
	 */
	@Override
	@Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
	public Record update(@NotNull final Record record) throws AbstractDatabaseException {
		return this.dao.update(record);
	}

	/**
	 * 
	 */
	@Override
	@Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
	public Record selectById(@NotNull final Record record, @NotNull final Value<?> uniqueId)
			throws AbstractDatabaseException {
		return this.dao.selectById(record, uniqueId);
	}

	/**
	 * 
	 */
	@Override
	@Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
	public Record selectById(@NotNull final Record record,
			@NotNull @MinSize(1) final ConcurrentMap<String, Value<?>> uniqueIds) throws AbstractDatabaseException {
		return this.dao.selectById(record, uniqueIds);
	}

	/**
	 * 
	 */
	@Override
	@Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
	public boolean delete(@NotNull final Record record) throws AbstractDatabaseException {
		return this.dao.delete(record);
	}

	/**
	 * 
	 */
	@Override
	@Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
	public List<Record> queryByFilter(@NotNull final Record record, @NotNull @MinLength(1) final String[] columns,
			@NotNull final Expression filter) throws AbstractDatabaseException {
		return this.dao.queryByFilter(record, columns, filter);
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
