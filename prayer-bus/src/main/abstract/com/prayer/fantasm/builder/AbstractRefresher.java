package com.prayer.fantasm.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.prayer.facade.builder.Refresher;
import com.prayer.facade.builder.reflector.Reflector;
import com.prayer.facade.kernel.Referencer;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.crucial.schema.FKReferencer;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 抽象层的Refresher
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractRefresher implements Refresher {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    private transient final JdbcConnection connection; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractRefresher(@NotNull final JdbcConnection connection){
        this.connection = connection;
        /**
         * 固定类型引用，调用构造过后初始化Referencer
         */
    }
    // ~ Abstract Methods ====================================
    /** 反向构造器 **/
    public abstract Reflector reflector();
    /** 构造系统需要使用的Reference的获取器 **/
    public abstract Referencer referencer();
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String buildAlterSQL(@NotNull final Schema schema) throws AbstractDatabaseException{
        // 不需要，这个语句只有表存在的时候会执行：Fix Issue：Invalid object name 'XXXXX'
        final List<String> lines = new ArrayList<>();
        /** 1.添加引用删除语句 **/
        lines.addAll(this.getRefsDropSql(schema));
        
        return null;
    }
    // ~ Methods =============================================
    /**
     * 读取JDBC数据库连接
     * @return
     */
    protected JdbcConnection connection(){
        return this.connection;
    }
    // ~ Private Methods =====================================
    
    private List<String> getRefsDropSql(final Schema schema){
        /** 1.获取约束 **/
        final List<FKReferencer> refs = new ArrayList<>();
        {
            final Set<String> columns = schema.getColumns();
            for(final String column: columns){
                /** 2.加载约束信息 **/
                refs.addAll(this.referencer().getReferences(schema.getTable(), column));
            }
        }
        /** 3.生成Reference的Drop语句 **/
        final List<String> sqlLines = new ArrayList<>();
        {
            if(!refs.isEmpty()){
                sqlLines.addAll(this.referencer().prepDropSql(refs));
            }
        }
        return sqlLines;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
