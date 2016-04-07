package com.prayer.business.digraph;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.AbstractCommonTool;
import com.prayer.business.digraph.DatabaseGraphicer;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.digraph.Graphic;

/**
 * 
 * @author Lang
 *
 */
public class GraphicDatabaseTestCase extends AbstractCommonTool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    protected Logger getLogger() {
        // TODO Auto-generated method stub
        return null;
    }

    /** **/
    @Override
    protected Class<?> getTarget() {
        // TODO Auto-generated method stub
        return null;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testDatabaseGraphic() throws AbstractException {
        final DatabaseGraphicer executor = new DatabaseGraphicer();
        final Graphic graphic = executor.build(this.getTables());
        System.out.println(graphic);
    }

    // ~ Private Methods =====================================
    /** **/
    private Set<String> getTables() {
        final Set<String> tables = new HashSet<>();
        tables.add("REL_ROLE_PERM");
        tables.add("REL_PERM_ACTION");
        tables.add("REL_GROUP_ROLE");
        tables.add("REL_ACCOUNT_ROLE");
        tables.add("REL_ACCOUNT_GROUP");
        tables.add("REL_RESOURCE_PERM");
        tables.add("SEC_ACCOUNT_MATRIX");
        tables.add("SEC_ROLE_MATRIX");
        tables.add("SEC_ACCOUNT");
        tables.add("SEC_GROUP");
        tables.add("SEC_ROLE");
        tables.add("SEC_PERM");
        tables.add("SEC_AUDIT");
        tables.add("SEC_ACTION");
        tables.add("ADDT_TABLE");
        return tables;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
