package com.prayer.model.business;

import java.io.Serializable;
import java.util.List;

import com.prayer.constant.Constants;
import com.prayer.exception.web.ServiceParamMissingException;
import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ServiceRequest implements Serializable {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 821436569725674257L;
    // ~ Instance Fields =====================================
    /** **/
    private transient String identifier;
    /** 执行请求的脚本链 **/
    private transient List<String> script;
    /** Json数据 **/
    private transient JsonObject data;
    /** Pager对象 **/
    private transient Pager pager;
    /** Order By排序对象 **/
    private transient OrderBy orders;
    /** 构造Request时的Error **/
    private transient AbstractException error;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param params
     */
    public ServiceRequest(@NotNull final JsonObject params) {
        
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /** 抽取Global ID **/
    private void extractIdentifier(@NotNull final JsonObject params) throws AbstractException {
        if(params.containsKey(Constants.PARAM.ID)){
            
        }else{
            throw new ServiceParamMissingException(getClass(),Constants.PARAM.ID);
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
