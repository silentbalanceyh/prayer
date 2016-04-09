package com.prayer.fantasm.schema;

import static com.prayer.util.reflection.Instance.reservoir;

import com.prayer.constant.Accessors;
import com.prayer.constant.Resources;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.verifier.DataValidator;

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
public abstract class AbstractAltimeter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Schema的数据访问，底层调用Accessor **/
    @NotNull
    private transient final SchemaDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** SchemaDao实例化 **/
    @PostValidateThis
    protected AbstractAltimeter(@NotNull final SchemaDao dao) {
        this.dao = dao;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 获取Schema Dao的引用
     * 
     * @return
     */
    protected SchemaDao getDao() {
        return this.dao;
    }

    /**
     * 获取系统的表验证信息
     * 
     * @return
     */
    @NotNull
    private DataValidator validator() {
        return reservoir(Resources.DB_CATEGORY, Accessors.validator());
    }

    /**
     * 返回null表示当前这个表存在
     * @param table
     * @return
     */
    protected boolean existTable(@NotNull @NotBlank @NotEmpty final String table) {
        return null == this.validator().verifyTable(table);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
