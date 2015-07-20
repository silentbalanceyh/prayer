package com.prayer.dao.record.impl;

import static com.prayer.util.Instance.instance;

import java.util.List;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.dao.record.RecordDao;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MinLength;
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
	public RecordDaoImpl(@NotNull final Record record) {
		this.dao = instance(Resources.DB_DAO, record);
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
	public Record selectById(@NotNull @Length(min = 1, max = 4) final Value<?>... uniqueId) {
		return this.dao.selectById(uniqueId);
	}

	/**
	 * 
	 */
	@Override
	@Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
	public boolean deleteById(@NotNull @Length(min = 1, max = 4) final Value<?>... uniqueId)
			throws AbstractDatabaseException {
		return this.dao.deleteById(uniqueId);
	}

	/**
	 * 
	 */
	@Override
	@Pre(expr = DAO_EXPR, lang = Constants.LANG_GROOVY)
	public List<Record> queryByFilter(@NotNull @MinLength(1) final String[] columns, @NotNull final Expression filter)
			throws AbstractDatabaseException {
		return this.dao.queryByFilter(columns, filter);
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
