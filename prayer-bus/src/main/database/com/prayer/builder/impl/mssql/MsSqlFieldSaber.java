package com.prayer.builder.impl.mssql;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.builder.line.AbstractFieldSaber;
import com.prayer.facade.builder.special.MsSqlWord;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class MsSqlFieldSaber extends AbstractFieldSaber implements MsSqlWord {

    // ~ Static Fields =======================================
    /** 带Pattern的列映射：PRECISION **/
    private static ConcurrentMap<String, String> PRECISION_MAP = new ConcurrentHashMap<>();
    /** 带Pattern的列映射：LENGTH **/
    private static ConcurrentMap<String, String> LENGTH_MAP = new ConcurrentHashMap<>();

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 设置行映射 **/
    static {
        /** 精度映射 **/
        PRECISION_MAP.put(Type.DECIMAL, Pattern.P_DECIMAL);
        PRECISION_MAP.put(Type.NUMERIC, Pattern.P_NUMERIC);
        /** 长度映射 **/
        LENGTH_MAP.put(Type.CHAR, Pattern.P_CHAR);
        LENGTH_MAP.put(Type.NCHAR, Pattern.P_NCHAR);
        LENGTH_MAP.put(Type.VARCHAR, Pattern.P_VARCHAR);
        LENGTH_MAP.put(Type.NVARCHAR, Pattern.P_NVARCHAR);
        LENGTH_MAP.put(Type.BINARY, Pattern.P_BINARY);
        LENGTH_MAP.put(Type.DATETIME2, Pattern.P_DATETIME2);
        LENGTH_MAP.put(Type.DATETIMEOFFSET, Pattern.P_DATETIMEOFFSET);
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public ConcurrentMap<String,String> getPrecisionMap(){
        return PRECISION_MAP;
    }
    /** **/
    @Override
    public ConcurrentMap<String,String> getLengthMap(){
        return LENGTH_MAP;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
