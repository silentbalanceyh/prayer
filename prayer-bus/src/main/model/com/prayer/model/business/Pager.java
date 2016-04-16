package com.prayer.model.business;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.ensurer.IntegerEnsurer;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.entity.Ensurer;
import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

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

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Pager.class);
    // ~ Instance Fields =====================================
    /**
     * 第几页
     */
    @Min(1)
    private transient Integer pageIndex = 1;
    /**
     * 每一页数量
     */
    @Min(1)
    private transient Integer pageSize = 10;
    /**
     * 
     */
    private transient Ensurer<JsonObject, Integer> ensurer = singleton(IntegerEnsurer.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param pageJson
     * @return
     */
    public static Pager create(@NotNull final JsonObject pageJson) {
        return new Pager(pageJson);
    }

    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public Pager() {
        this(1, 10);
    }

    /** **/
    @PostValidateThis
    public Pager(@Min(1) final Integer pageIndex, @Min(1) final Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    private Pager(final JsonObject pageJson) {
        try {
            this.pageIndex = this.ensurer.ensureRequired(pageJson, Constants.PARAM.ADMINICLE.PAGE.INDEX);
            this.pageSize = this.ensurer.ensureRequired(pageJson, Constants.PARAM.ADMINICLE.PAGE.SIZE);
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
        }
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
    public void setPageIndex(@Min(1) final Integer pageIndex) {
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
    public void setPageSize(@Min(1) final Integer pageSize) {
        this.pageSize = pageSize;
    }

    // ~ hashCode,equals,toString ============================

}
