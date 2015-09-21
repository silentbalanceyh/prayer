package com.prayer.model.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.prayer.model.h2.vx.VerticleModel;

import jodd.util.StringUtil;
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
 */
@Guarded
public final class VerticleChain implements Serializable {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 4152388926986901681L;
    // ~ Instance Fields =====================================
    /** 当前这个组的名称 **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient String name; // NOPMD
    /** 这个组中的同步发布Verticle的列表 **/
    private transient final List<VerticleModel> syncList;
    /** 这个组中的异步发布Verticle的列表 **/
    private transient final List<VerticleModel> asyncList;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public VerticleChain(@NotNull @NotEmpty @NotBlank final String name) {
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
    @PreValidateThis
    public void initVerticles(@NotNull final List<VerticleModel> inputList) {
        this.clearQueue();
        for (final VerticleModel item : inputList) {
            this.addVerticle(item);
        }
    }
    /**
     * 添加单个Verticle配置
     * @param item
     */
    public void addVerticle(@NotNull final VerticleModel item) {
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
    /**
     * 清空Queue
     */
    public void clearQueue(){
        this.asyncList.clear();
        this.syncList.clear();
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
