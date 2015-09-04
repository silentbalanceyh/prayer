package com.prayer.bus.impl;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.bus.ConfigService;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.vx.VerticleModel;
import com.prayer.schema.dao.ConfigDao;
import com.prayer.schema.dao.impl.ConfigDaoImpl;
import com.prayer.util.JsonKit;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ConfigSevImpl implements ConfigService {
	// ~ Static Fields =======================================
	/** 日志记录器 **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigSevImpl.class);
	// ~ Instance Fields =====================================
	/** 访问H2的Database数据库接口 **/
	@NotNull
	private transient final ConfigDao dao;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public ConfigSevImpl() {
		this.dao = singleton(ConfigDaoImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** 读取的特殊方法，无异常信息抛出。 **/
	@Override
	public ServiceResult<VerticleChain> findVerticles(@NotNull @NotBlank @NotEmpty final String group) {
		final ServiceResult<VerticleChain> result = new ServiceResult<>();
		final VerticleChain chain = this.dao.getByGroup(group);
		result.setResponse(chain, null);
		return result;
	}

	/** 将数据从Json文件导入到H2数据库中 **/
	@Override
	public ServiceResult<ConcurrentMap<String, VerticleChain>> importToH2(
			@NotNull @NotBlank @NotEmpty final String jsonPath) {
		// 1.构造响应数据
		final ServiceResult<ConcurrentMap<String, VerticleChain>> result = new ServiceResult<>();
		// 2.从Json中读取List
		List<VerticleModel> dataList = new ArrayList<>();
		try {
			// 特殊的Type引用，用于序列化
			final TypeReference<List<VerticleModel>> typeRef = new TypeReference<List<VerticleModel>>() {
			};
			dataList = JsonKit.fromFile(typeRef, jsonPath);
		} catch (AbstractSystemException ex) {
			info(LOGGER, "[I-BUS] Serialization error when reading data from file : file = " + jsonPath, ex);
			result.setResponse(null, ex);
		}
		info(LOGGER, "[I-BUS] Input Size: " + dataList.size());
		// 3.批量创建
		try {
			this.dao.insert(dataList.toArray(new VerticleModel[] {}));
		} catch (AbstractTransactionException ex) {
			info(LOGGER, "[I-BUS] Data accessing error !", ex);
			result.setResponse(null, ex);
		}
		// 4.返回最终的Result信息
		if (ResponseCode.SUCCESS == result.getResponseCode() && Constants.RC_SUCCESS == result.getErrorCode()) {
			result.setResponse(this.extractResult(dataList), null);
		}
		return result;
	}

	/** 读取数据库中所有的VerticleModel配置信息 **/
	@Override
	public ServiceResult<ConcurrentMap<String, VerticleChain>> readFromH2() {
		// 1.构造响应数据
		final ServiceResult<ConcurrentMap<String, VerticleChain>> result = new ServiceResult<>();
		// 2.读取所有的VerticleModel相关信息
		final List<VerticleModel> verticles = this.dao.getAll();
		// 3.设置返回结果
		result.setResponse(this.extractResult(verticles), null);
		// 4.返回最终结果
		return result;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private ConcurrentMap<String, VerticleChain> extractResult(final List<VerticleModel> dataList) {
		// 1.构造结果
		final ConcurrentMap<String, VerticleChain> retMap = new ConcurrentHashMap<>();
		// 2.遍历结果集
		for (final VerticleModel item : dataList) {
			if (StringKit.isNonNil(item.getGroup())) {
				// 3.1.获取某个Group的VerticleChain
				VerticleChain chain = retMap.get(item.getGroup());
				// 3.2.判断是否获取到，没获取到就重新获取
				if (null == chain) {
					chain = new VerticleChain(item.getGroup()); // NOPMD
				}
				// 3.3.修改chain引用
				chain.addVerticle(item);
				// 3.4.完成过后添加到Map中
				retMap.put(item.getGroup(), chain);
			}
		}
		// 4.返回最终过滤结果
		return retMap;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
