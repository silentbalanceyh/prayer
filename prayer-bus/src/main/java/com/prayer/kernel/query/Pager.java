package com.prayer.kernel.query;

import java.io.Serializable;

import com.prayer.constant.Constants;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class Pager implements Serializable {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -7656036586914824667L;
    // ~ Instance Fields =====================================
    /**
     * 第几页
     */
    private transient Integer pageIndex = 1;
    /**
     * 每一页数量
     */
    private transient Integer pageSize = 10;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================

    public static Pager create(@NotNull final JsonObject pageJson) {
        return new Pager(pageJson);
    }

    // ~ Constructors ========================================
    /** **/
    public Pager() {
        this(1, 10);
    }

    /** **/
    public Pager(@Min(1) final Integer pageIndex, @Min(1) final Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    private Pager(final JsonObject pageJson) {
        this(pageJson.getInteger(Constants.PARAM.PAGE.PAGE_INDEX), pageJson.getInteger(Constants.PARAM.PAGE.PAGE_SIZE));
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    /**
     * 
     * @return
     */
    public Integer getPageIndex() {
        return pageIndex;
    }

    /**
     * 
     * @param pageIndex
     */
    public void setPageIndex(final Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 
     * @return
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 
     * @param pageSize
     */
    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    // ~ hashCode,equals,toString ============================

}
