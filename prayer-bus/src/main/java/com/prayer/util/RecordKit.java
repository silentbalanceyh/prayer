package com.prayer.util;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.kernel.Value;
import com.prayer.model.type.DataType;

import jodd.util.StringUtil;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Record相关的工具
 * 
 * @author Lang
 *
 */
@Guarded
public class RecordKit {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 对比两个Record是否相等，主要对比identifier和data（数据部分）
     * 
     * @param left
     * @param right
     * @return
     */
    public static boolean equal(@NotNull final Record left, @NotNull final Record right) {
        boolean equal = true;
        if (!StringUtil.equals(left.identifier(), right.identifier())) {
            equal = false;
        } else {
            for (final String field : left.fields().keySet()) {
                try {
                    final Value<?> lValue = left.get(field);
                    final Value<?> rValue = right.get(field);
                    if (null == lValue && null == rValue) {
                        // 两个字段都为空，直接Pass
                        continue;
                    } else if (null != lValue && null != rValue) {
                        if (lValue.getDataType() == DataType.DATE) {
                            // TODO: 时间有1纳秒的差距，不直接使用equal
                            if (lValue.literal().equals(rValue.literal())) {
                                // 两个值相等，直接Pass
                                continue;
                            } else {
                                // 两个值不相等，不等，退出
                                equal = false;
                                break;
                            }
                        } else {
                            if (lValue.equals(rValue)) {
                                // 两个值相等，直接Pass
                                continue;
                            } else {
                                // 两个值不相等，不等，退出
                                equal = false;
                                break;
                            }
                        }

                    } else {
                        // 其中一个为空，另外一个不为空
                        equal = false;
                        break;
                    }
                } catch (AbstractDatabaseException ex) {
                    equal = false;
                }
            }
        }
        return equal;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private RecordKit() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
