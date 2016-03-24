package com.prayer.schema.json;

import com.prayer.exception.schema.IdentifierReferenceException;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.Altimeter;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.schema.AbstractAltimeter;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 针对Schema本身的验证过程
 * 
 * @author Lang
 *
 */
@Guarded
public class SchemaAltimeter extends AbstractAltimeter implements Altimeter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** SchemaDao实例化 **/
    public SchemaAltimeter(@NotNull final SchemaDao dao) {
        super(dao);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    // 1.Global ID存在，Table Name存在：进入Schema的更新验证
    // 2.Global ID不存在，Table Name存在：抛出异常，不可以同一个Global ID引用同一个Table Name
    // 3.【Ignore】Global ID存在，Table Name不存在：直接将旧表切换成新表：目前步骤可跳过，实际上换成创建Table流程
    // 4.【Ignore】Global ID不存在，Table Name不存在：相当于全新的操作：木匾步骤同样跳过
    // 只有Table存在的时候会进入更新流程，而且如果Global ID不存在会抛异常
    @Override
    public void verify(@NotNull final Schema schema) throws AbstractException {
        /** 1.从系统中读取原始Schema **/
        final Schema stored = this.getDao().get(schema.identifier());
        /** 2.当前Table是否存在 **/
        final boolean exist = this.existTable(schema.getTable());
        if (exist) {
            if (null == stored) {
                /** 3.1.系统中没有这个Global ID记录，但有表记录 **/
                throw new IdentifierReferenceException(getClass(),schema.identifier(),schema.getTable());
            } else {
                /** 3.2.系统中存在这个Global ID，有表记录，执行更新流程验证 **/
                
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
