package com.prayer.uca.jdbc;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.constant.SystemEnum.UACSource;
import com.prayer.database.pool.impl.jdbc.H2ConnImpl;
import com.prayer.database.pool.impl.jdbc.JdbcConnImpl;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.util.string.StringKit;
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
    private transient final JdbcConnection dataContext;
    /** **/
    private transient final JdbcConnection h2Context;
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
    public JdbcConnection getContext(final JsonObject config) throws AbstractWebException{
        JdbcConnection context = null;
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
