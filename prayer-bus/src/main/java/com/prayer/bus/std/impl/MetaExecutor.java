package com.prayer.bus.std.impl;

import static com.prayer.util.Instance.instance;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.schema.dao.TemplateDao;

import io.vertx.core.json.JsonObject;
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
@SuppressWarnings("rawtypes")
class MetaExecutor {
    // ~ Static Fields =======================================
    /** **/
    private static final ConcurrentMap<String,TemplateDao> DAO_CLS = new ConcurrentHashMap<>();
    /** **/
    private static final ConcurrentMap<String,Class<?>> META_CLS = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient TemplateDao dao; 
    // ~ Static Block ========================================
    /** **/
    static{
        DAO_CLS.put("META.SCRIPT", instance("com.prayer.schema.dao.impl.ScriptDaoImpl"));
        
    }
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param identifier
     */
    @PostValidateThis
    public MetaExecutor(@NotNull @NotEmpty @NotBlank final String identifier){
        this.dao = DAO_CLS.get(identifier);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
