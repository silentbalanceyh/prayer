package com.prayer.fantasm.builder.line;

import com.prayer.constant.Resources;
import com.prayer.facade.database.pool.JdbcConnection;
import com.prayer.util.io.PropertyKit;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractReflector {
    // ~ Static Fields =======================================
    /** **/
    private static final PropertyKit LOADER = new PropertyKit(AbstractReflector.class, Resources.DB_CFG_FILE);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient JdbcConnection connection;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public AbstractReflector(@NotNull final JdbcConnection connection){
        this.connection = connection;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 子类调用 **/
    protected JdbcConnection connection(){
        return this.connection;
    }
    /** 获取数据库信息，子类使用 **/
    protected String database(){
        return LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
    }
    // ~ Template Method =====================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
