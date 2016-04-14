package com.prayer.dao.impl.data.special;

import java.text.MessageFormat;

import com.prayer.constant.Resources;
import com.prayer.facade.sql.special.MsSqlStatement;
import com.prayer.fantasm.dao.database.AbstractDatabaseDao;

import net.sf.oval.guard.Guarded;

/**
 * INIT Server元数据信息
 * 
 * @author Lang
 *
 */
@Guarded
public class MsSqlDatabaseDalor extends AbstractDatabaseDao implements MsSqlStatement {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String buildRelSql() {
        return MessageFormat.format(R_RELATIONS, Resources.DB_DATABASE);
    }

    /** From **/
    @Override
    public String fromColumn() {
        return "FROM_TABLE";
    }

    /** To **/
    @Override
    public String toColumn() {
        return "TO_TABLE";
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
