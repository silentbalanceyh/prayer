package com.prayer.base.builder.line;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.dao.impl.builder.SqlTypes;
import com.prayer.facade.dao.builder.SQLWord;
import com.prayer.facade.dao.builder.line.FieldSaber;
import com.prayer.model.meta.database.PEField;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 创建行操作语句
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractFieldSaber implements FieldSaber, SQLWord {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Type 包装器，子类必须实现，用于处理N和PS部分=========
    /**
     * 精度匹配类型映射
     * 
     * @return
     */
    public abstract ConcurrentMap<String, String> getPrecisionMap();

    /**
     * 长度匹配类型映射
     * 
     * @return
     */
    public abstract ConcurrentMap<String, String> getLengthMap();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 生成列定义的SQL语句：NAME VARCHAR(256) NOT NULL
     */
    // Example：NAME VARCHAR(256) NOT NULL
    // Format: <NAME> <TYPE>(P) <NULLABLE>
    public String buildLine(@NotNull @InstanceOfAny(PEField.class) final PEField field) {
        // 1.初始化缓存区
        final StringBuilder sql = new StringBuilder();
        // 2.读取原始类型，并且添加对应的类型的封装，封装看子类表现
        final String type = type(field);
        // 3.添加字段名
        sql.append(field.getColumnName()).append(Symbol.SPACE).append(type).append(Symbol.SPACE);
        // 4.添加是否为空部分
        if (!field.isNullable() || field.isPrimaryKey()) {
            sql.append(Comparator.NOT).append(Symbol.SPACE).append(Comparator.NULL);
        }
        return sql.toString();
    }
    // ~ Private Methods =====================================

    private String type(@NotNull @NotBlank @NotEmpty final PEField field) {
        final StringBuilder type = new StringBuilder();
        // 1.获取原始类型
        final String rawType = SqlTypes.get(field.getColumnType());
        // 2.判断当前类型是否包含了括号
        String actualType = rawType;
        if (Constants.RANGE < rawType.indexOf('(')) {
            actualType = rawType.split("(")[Constants.IDX];
        }
        // 3.是否添加Pattern
        if (getPrecisionMap().containsKey(actualType)) {
            type.append(
                    MessageFormat.format(getPrecisionMap().get(actualType), field.getLength(), field.getPrecision()));
        } else if (getLengthMap().containsKey(actualType)) {
            type.append(MessageFormat.format(getLengthMap().get(actualType), field.getLength()));
        } else {
            type.append(actualType);
        }
        return type.toString();
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
