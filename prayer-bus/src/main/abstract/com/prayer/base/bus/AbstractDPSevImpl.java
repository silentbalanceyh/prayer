package com.prayer.base.bus;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.base.exception.AbstractSystemException;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.facade.bus.deploy.TemplateDPService;
import com.prayer.facade.entity.Entity;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.bus.ResultExtractor;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 * @param <T>
 * @param <ID>
 */
@Guarded
public abstract class AbstractDPSevImpl<T extends Entity, ID extends Serializable> implements TemplateDPService<T, ID> { // NOPMD
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** 通用Dao **/
	private transient final MetaAccessor<T, ID> dao;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public AbstractDPSevImpl() {
		this.dao = singleton(getDaoClass());
	}

	// ~ Abstract Methods ====================================
	/** **/
	public abstract Class<?> getDaoClass();

	/** **/
	public abstract Logger getLogger();

	/** **/
	public abstract List<T> readJson(final String jsonPath) throws AbstractSystemException;

	/** **/
	public abstract T[] getArrayType();

	// ~ Override Methods ====================================
	/** **/
	@PreValidateThis
	@Override
	@InstanceOfAny(ServiceResult.class)
	public ServiceResult<List<T>> importToList(@NotNull @NotBlank @NotEmpty final String jsonPath) {
		// 1.构造响应结果
		final ServiceResult<List<T>> result = new ServiceResult<>();
		// 2.从Json中读取List
		List<T> dataList = new ArrayList<>();
		try {
			dataList = this.readJson(jsonPath);
		} catch (AbstractSystemException ex) {
			peError(getLogger(),ex);
			result.error(ex);
		}
		// 3.批量创建
		try {
			if (!dataList.isEmpty()) {
				this.dao.insert(dataList.toArray(getArrayType()));
			}
		} catch (AbstractTransactionException ex) {
		    peError(getLogger(),ex);
			result.error(ex);
		}
		if (ResponseCode.SUCCESS == result.getResponseCode() && Constants.RC_SUCCESS == result.getErrorCode()) {
			result.success(dataList);
		}
		return result;
	}

	/** **/
	@PreValidateThis
	@Override
	@InstanceOfAny(ServiceResult.class)
	public ServiceResult<ConcurrentMap<String, T>> importToMap(@NotNull @NotBlank @NotEmpty final String jsonPath,
			@NotNull @NotBlank @NotEmpty final String field) {
		// 1.构造响应结果
		final ServiceResult<ConcurrentMap<String, T>> result = new ServiceResult<>();
		// 2.读取List
		final ServiceResult<List<T>> listRet = this.importToList(jsonPath);
		// 3.直接设置结果
		if (ResponseCode.SUCCESS == listRet.getResponseCode() && Constants.RC_SUCCESS == result.getErrorCode()) {
			result.success(ResultExtractor.extractEntity(listRet.getResult(), field));
		}
		return result;
	}

	/** **/
	@PreValidateThis
	@Override
	@InstanceOfAny(ServiceResult.class)
	public ServiceResult<ConcurrentMap<String, List<T>>> importToMapList(
			@NotNull @NotBlank @NotEmpty final String jsonPath, @NotNull @NotBlank @NotEmpty final String field) {
		// 1.构造响应结果
		final ServiceResult<ConcurrentMap<String, List<T>>> result = new ServiceResult<>();
		// 2.读取List
		final ServiceResult<List<T>> listRet = this.importToList(jsonPath);
		// 3.直接设置结果
		if (ResponseCode.SUCCESS == listRet.getResponseCode() && Constants.RC_SUCCESS == result.getErrorCode()) {
			result.success(ResultExtractor.extractList(listRet.getResult(), field));
		}
		return result;
	}

	/** **/
	@PreValidateThis
	@Override
	@InstanceOfAny(ServiceResult.class)
	public ServiceResult<Boolean> purgeData() {
		// 1.构造响应数据
		final ServiceResult<Boolean> result = new ServiceResult<>();
		// 2.调用删除方法
		try {
			this.dao.purge();
			result.success(Boolean.TRUE);
		} catch (AbstractTransactionException ex) {
		    peError(getLogger(),ex);
			result.error(ex);
		}
		return result;
	}

	// ~ Methods ==============================================

	/** **/
	protected MetaAccessor<T, ID> getDao() {
		return this.dao;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
