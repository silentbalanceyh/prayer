package com.prayer.model.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.prayer.facade.constant.Constants;

import io.vertx.core.json.JsonArray;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class Projection implements Serializable{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 6459749239288242174L;
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient String[] filters;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static Projection create(@NotNull final JsonArray filters){
        return new Projection(filters);
    }
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public Projection(final JsonArray filters){
        /** 初始化列信息 **/
        final List<String> colList = new ArrayList<>();
        filters.forEach(item -> {
            if(item.getClass() == String.class){
                colList.add(item.toString());
            }
        });
        this.filters = colList.toArray(Constants.T_STR_ARR);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 返回Projection中的columns
     * @return
     */
    public String[] getFilters(){
        return this.filters;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
