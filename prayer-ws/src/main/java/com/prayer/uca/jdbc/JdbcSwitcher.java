package com.prayer.uca.jdbc;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.singleton;

import com.prayer.base.exception.AbstractWebException;
import com.prayer.constant.SystemEnum.UACSource;
import com.prayer.dao.impl.jdbc.H2ConnImpl;
import com.prayer.dao.impl.jdbc.JdbcConnImpl;
import com.prayer.facade.dao.jdbc.JdbcContext;
import com.prayer.util.StringKit;
import com.prayer.util.web.Extractor;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class JdbcSwitcher {
    // ~ Static Fields =======================================
    /** 数据库的切换信息 **/
    private static final String SOURCE = "$$SOURCE";
    // ~ Instance Fields =====================================
    /** **/
    private transient final JdbcContext dataContext;
    /** **/
    private transient final JdbcContext h2Context;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public JdbcSwitcher(){
        this.dataContext = singleton(JdbcConnImpl.class);
        this.h2Context = singleton(H2ConnImpl.class);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @NotNull
    public JdbcContext getContext(final JsonObject config) throws AbstractWebException{
        JdbcContext context = null;
        if(config.containsKey(SOURCE)){
            // 检查$$SOURCE是不是一个字符串
            final String source = Extractor.getString(config, SOURCE);
            if(StringKit.isNonNil(source)){
                final UACSource uac = fromStr(UACSource.class,source);
                if(UACSource.META == uac){
                    // 只有这种情况访问元数据
                    context = this.h2Context;
                }else if(UACSource.DATA == uac){
                    context = this.dataContext;
                }
            }else{
                context = this.dataContext;
            }
        }else{
            context = this.dataContext;
        }
        return context;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
