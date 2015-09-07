package com.prayer.bus.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.vx.RouteModel;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.model.h2.vx.VerticleModel;
import com.prayer.util.StringKit;

/**
 * 
 * @author Lang
 *
 */
final class Extractor {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param dataList
	 * @return
	 */
	public static ConcurrentMap<String, UriModel> extractUris(final List<UriModel> dataList) {
		// 1.构造结果
		final ConcurrentMap<String, UriModel> retMap = new ConcurrentHashMap<>();
		// 2.遍历结果集
		dataList.stream().filter(item -> StringKit.isNonNil(item.getUri())).forEach(item -> {
			// 3.将DataList转换成一个Map
			retMap.put(item.getUri(), item);
		});
		return retMap;
	}

	/**
	 * 
	 * @param dataList
	 * @return
	 */
	public static ConcurrentMap<String, List<RouteModel>> extractRoutes(final List<RouteModel> dataList) {
		// 1.构造结果
		final ConcurrentMap<String, List<RouteModel>> retMap = new ConcurrentHashMap<>();
		// 2.遍历结果集
		dataList.stream().filter(item -> StringKit.isNonNil(item.getParent())).forEach(item -> {
			// 3.1.获取某个Parent下的List
			List<RouteModel> list = retMap.get(item.getParent());
			// 3.2.判断是否获取到
			if (null == list) {
				list = new ArrayList<>(); // NOPMD
			}
			// 3.3.添加对象到List中
			list.add(item);
			// 3.4.完成过后将内容重新推回到Map中
			retMap.put(item.getParent(), list);
		});
		return retMap;
	}

	/**
	 * 
	 * @param dataList
	 * @return
	 */
	public static ConcurrentMap<String, VerticleChain> extractVerticles(final List<VerticleModel> dataList) {
		// 1.构造结果
		final ConcurrentMap<String, VerticleChain> retMap = new ConcurrentHashMap<>();
		// 2.遍历结果集
		dataList.stream().filter(item -> StringKit.isNonNil(item.getGroup())).forEach(item -> {
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
		});
		// 4.返回最终过滤结果
		return retMap;
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Extractor() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
