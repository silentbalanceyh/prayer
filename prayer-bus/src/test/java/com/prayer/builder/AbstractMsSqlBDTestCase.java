package com.prayer.builder;

import java.text.MessageFormat;
import java.util.List;

import com.prayer.constant.DBConstants;
import com.prayer.constant.Resources;
import com.prayer.facade.dao.builder.SQLStatement;
import com.prayer.facade.dao.builder.special.MsSqlStatement;
import com.prayer.facade.dao.builder.special.MsSqlWord;
import com.prayer.util.string.StringKit;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractMsSqlBDTestCase extends AbstractBDTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected String getCategory() {
        return DBConstants.CATEGORY_MSSQL;
    }

    /** **/
    protected boolean validDB() {
        return StringKit.equals(getCategory(), Resources.DB_CATEGORY);
    }

    /** Purge模板 **/
    @SuppressWarnings("unused")
    private void executePurge() {
        int counter = 0;
        do {
            final List<String> tables = this.connection().select(
                    MessageFormat.format(MsSqlStatement.T_TABLES, Resources.DB_DATABASE), MsSqlWord.Metadata.TABLE);
            counter = tables.size();
            for (final String table : tables) {
                final String sql = MessageFormat.format(SQLStatement.TB_DROP, table);
                this.connection().executeBatch(sql);
            }
        } while (counter > 0);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
