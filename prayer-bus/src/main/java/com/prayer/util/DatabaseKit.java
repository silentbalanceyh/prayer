package com.prayer.util;

import jodd.util.StringUtil;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 数据库分析
 *
 * @author Lang
 * @see
 */
@Guarded
public final class DatabaseKit {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param productName
     * @param version
     * @return
     */
    @NotNull
    @NotBlank
    @NotEmpty
    public static String getDatabaseVersion(
            @NotNull @NotBlank @NotEmpty final String productName,
            @NotNull @NotBlank @NotEmpty final String version){
        String retVar = null;
        switch(productName){
        case "Microsoft SQL Server":{
            retVar = getSqlServer(version);
        }break;
        case "PostgreSQL":{
            retVar = getPostgreSql(version);
        }break;
        default:{
            retVar = "Other";    // NOPMD
        }break;
        }
        return retVar;
    }

    /**
     * 
     * @param version
     * @return
     */
    @NotNull
    @NotBlank
    @NotEmpty
    private static String getSqlServer(
            @NotNull @NotBlank @NotEmpty final String version) {
        String ret = null;
        if (-1 != StringUtil.startsWithOne(version, new String[] { "9", "10",
                "11", "12" })) {
            if (version.startsWith("9.")) {
                ret = "2005";
            } else if (version.startsWith("10")) {
                if (version.startsWith("10.5")) {
                    ret = "2008R2";
                } else {
                    ret = "2008";
                }
            } else if (version.startsWith("11")) {
                ret = "2012";
            } else if (version.startsWith("12")) {
                ret = "2014";
            }
        }
        return ret;
    }

    /**
     * 
     * @param version
     * @return
     */
    @NotNull
    @NotBlank
    @NotEmpty
    private static String getPostgreSql(
            @NotNull @NotBlank @NotEmpty final String version) {
        String ret = null;
        if (version.startsWith("9.")) {
            ret = "9";
        }
        return ret;
    }

    // ~ Constructors ========================================
    private DatabaseKit() {
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================
}
