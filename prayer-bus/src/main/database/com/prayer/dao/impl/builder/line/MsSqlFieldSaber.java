package com.prayer.dao.impl.builder.line;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.builder.line.AbstractFieldSaber;
import com.prayer.constant.Constants;
import com.prayer.dao.impl.builder.SqlTypes;
import com.prayer.facade.dao.builder.special.MsSqlWord;
import com.prayer.model.meta.database.PEField;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
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
    public String type(@NotNull @NotBlank @NotEmpty final PEField field) {
        final StringBuilder type = new StringBuilder();
        // 1.获取原始类型
        final String rawType = SqlTypes.get(field.getColumnType());
        // 2.判断当前类型是否包含了括号
        String actualType = rawType;
        if (Constants.RANGE < rawType.indexOf('(')) {
            actualType = rawType.split("(")[Constants.IDX];
        }
        // 3.是否添加Pattern
        type.append(actualType);
        if (PRECISION_MAP.containsKey(actualType)) {
            type.append(MessageFormat.format(PRECISION_MAP.get(actualType), field.getLength(), field.getPrecision()));
        } else if (LENGTH_MAP.containsKey(actualType)) {
            type.append(MessageFormat.format(LENGTH_MAP.get(actualType), field.getLength()));
        }
        return type.toString();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
