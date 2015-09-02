package com.prayer.model.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.prayer.model.h2.vx.VerticleModel;

import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public final class VerticleChain implements Serializable {
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 4152388926986901681L;
	// ~ Instance Fields =====================================
	/** 当前这个组的名称 **/
	private transient String name;
	/** 这个组中的同步发布Verticle的列表 **/
	private transient final List<VerticleModel> syncList;
	/** 这个组中的异步发布Verticle的列表 **/
	private transient final List<VerticleModel> asyncList;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public VerticleChain(final String name) {
		this.name = name;
		this.syncList = new ArrayList<>();
		this.asyncList = new ArrayList<>();
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 根据输入的List
	 * 
	 * @param inputList
	 */
	public void initVerticles(final List<VerticleModel> inputList) {
		for (final VerticleModel item : inputList) {
			// 只可以存放当前Group中的VerticleModel
			if (StringUtil.equals(item.getGroup(), name)) {
				if (item.isDeployAsync()) {
					// 异步Deployment Queue
					this.asyncList.add(item);
				} else {
					// 同步Deployment Queue
					this.syncList.add(item);
				}
			}
		}
	}

	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the syncList
	 */
	public List<VerticleModel> getSyncList() {
		return syncList;
	}

	/**
	 * @return the asyncList
	 */
	public List<VerticleModel> getAsyncList() {
		return asyncList;
	}
	// ~ hashCode,equals,toString ============================

}
