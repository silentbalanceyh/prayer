package com.prayer.schema.json;

import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.Altimeter;
import com.prayer.fantasm.exception.AbstractSchemaException;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 针对Schema本身的验证过程
 * 
 * @author Lang
 *
 */
@Guarded
public class SchemaAltimeter implements Altimeter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Schema的数据访问，底层访问了Accessor **/
    private transient final SchemaDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** SchemaDao实例化 **/
    public SchemaAltimeter(@NotNull final SchemaDao dao) {
        this.dao = dao;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    // 1.Global ID存在，Table Name存在：进入Schema的更新验证
    // 2.Global ID不存在，Table Name存在：抛出异常，不可以同一个Global ID引用同一个Table Name
    // 3.Global ID存在，Table Name不存在：直接将旧表切换成新表：目前步骤可跳过
    // 4.Global ID不存在，Table Name不存在：相当于全新的操作：木匾步骤同样跳过
    @Override
    public void verify(@NotNull final Schema schema) throws AbstractSchemaException {
        /** 1.从系统中读取原始 **/
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
