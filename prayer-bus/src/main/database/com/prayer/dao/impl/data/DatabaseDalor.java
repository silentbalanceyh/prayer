package com.prayer.dao.impl.data;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.constant.Accessors;
import com.prayer.facade.dao.DatabaseDao;
import com.prayer.util.digraph.Edges;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Metadata元数据信息
 * 
 * @author Lang
 *
 */
@Guarded
public class DatabaseDalor implements DatabaseDao {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    @NotNull
    private transient final DatabaseDao dao; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /** Database连接 **/
    public DatabaseDalor() {
        this.dao = singleton(Accessors.databaser());
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** Database Discovery **/
    @Override
    public String getFile() {
        return this.dao.getFile();
    }

    /** Database Relations **/
    @Override
    public Edges getRelations() {
        return this.dao.getRelations();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
