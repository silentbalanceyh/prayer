package com.prayer.util.business;

import static com.prayer.util.Calculator.intersect;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
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
public final class Collater { // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param left
     * @param right
     * @return
     */
    public static boolean equal(@NotNull final JsonObject left, @NotNull final JsonObject right) {
        boolean equal = true;
        final Collection<String> keys = intersect(right.fieldNames(), left.fieldNames());
        if (keys.size() != left.size() && keys.size() != right.size()) {
            equal = false;
        } else {
            /** 1.左遍历 **/
            for (final String field : left.fieldNames()) {
                if (right.containsKey(field)) {
                    final Object lValue = left.getValue(field);
                    final Object rValue = right.getValue(field);
                    equal = equalValue(lValue, rValue);
                    if (!equal) {
                        break;
                    }
                }
            }
        }
        return equal;
    }

    /**
     * 对比两个Record是否相等，主要对比identifier和data（数据部分）
     * 
     * @param left
     * @param right
     * @return
     */
    public static boolean equal(@NotNull final Record left, @NotNull final Record right) { // NOPMD
        boolean equal = true;
        if (StringUtil.equals(left.identifier(), right.identifier())) {
            for (final String field : left.fields().keySet()) {
                try {
                    /** 1.分别读取两个Record中对应字段的Value<?> **/
                    final Value<?> lValue = left.get(field);
                    final Value<?> rValue = right.get(field);
                    if (null == lValue && null == rValue) {
                        /** 如果两个字段读取都为null，则维持true **/
                        continue;
                    } else if (null != lValue && null != rValue) { // NOPMD
                        /** 两个Value<?>进行比较，直接第一次equals方法 **/
                        if (lValue.equals(rValue)) {
                            // 两个值相等，直接Pass
                            continue;
                        } else {
                            // 两个值不相等，不等，退出
                            equal = false;
                            break;
                        }

                    } else {
                        /** False：其中一个Value<?>为null，另外一个不为null，则直接False **/
                        equal = false;
                        break;
                    }
                } catch (AbstractDatabaseException ex) {
                    /** False：在操作过程中出现了AbstractException异常，则两个Record不相等 **/
                    equal = false;
                }
            }
        } else {
            /** False：identifier不相等，则两个Record不相等 **/
            equal = false;
        }
        return equal;
    }

    /**
     * 比较两个时间，不使用Time，直接比较年、月、日、时、分、秒
     * 
     * @param left
     * @param right
     * @param time
     * @return
     */
    public static boolean equal(@NotNull final Date left, @NotNull final Date right, final boolean time) {
        boolean ret = true;
        /** 1.如果两个Time相等，那么绝对相等，不相等时分开比较 **/
        if (left.getTime() != right.getTime()) {
            /** 2.读取年、月、日、时、分、秒到两个数组 **/
            int leftValue = 0;
            int rightValue = 0;
            /** 3.赋值 **/
            final Calendar leftCal = Calendar.getInstance(Locale.getDefault());
            final Calendar rightCal = Calendar.getInstance(Locale.getDefault());
            leftCal.setTime(left);
            rightCal.setTime(right);
            /** 4.赋值 **/
            leftValue = leftCal.get(Calendar.YEAR) + leftCal.get(Calendar.MONTH) + leftCal.get(Calendar.DAY_OF_MONTH);
            rightValue = rightCal.get(Calendar.YEAR) + rightCal.get(Calendar.MONTH)
                    + rightCal.get(Calendar.DAY_OF_MONTH);
            /** 5.是否比较时间 **/
            if (time) {
                leftValue += leftCal.get(Calendar.HOUR_OF_DAY) + leftCal.get(Calendar.MINUTE)
                        + leftCal.get(Calendar.SECOND);
                rightValue += rightCal.get(Calendar.HOUR_OF_DAY) + rightCal.get(Calendar.MINUTE)
                        + rightCal.get(Calendar.SECOND);
            }
            /** 6.不相等就返回false **/
            if (leftValue != rightValue) {
                ret = false;
            }
        }
        return ret;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static boolean equalValue(final Object left, final Object right) {
        boolean equal = true;
        /** Object **/
        if (null == left && null == right) {
            equal = true;
        } else if (null != left && null != right) {
            /** JsonArray的比较Bug，需要手工修复 **/
            if (left instanceof JsonArray && right instanceof JsonArray) {
                final JsonArray lArr = ((JsonArray) left);
                final JsonArray rArr = ((JsonArray) right);
                equal = equalValue(lArr.getList(),rArr.getList());
            } else if (left instanceof JsonObject && right instanceof JsonObject) {
                /** JsonObject **/
                final JsonObject lObj = ((JsonObject) left);
                final JsonObject rObj = ((JsonObject) right);
                for (final String field : lObj.fieldNames()) {
                    final Object lValue = lObj.getValue(field);
                    final Object rValue = rObj.getValue(field);
                    equal = equalValue(lValue, rValue);
                    if (!equal) {
                        break;
                    }
                }
            } else {
                equal = left.equals(right);
            }
        } else {
            equal = false;
        }
        return equal;
    }

    private Collater() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
