package com.prayer.ag.josephus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.prayer.ag.Topic;
import com.prayer.ag.util.AbstractTopic;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;
import net.sf.oval.guard.Guarded;

/**
 * 约瑟夫是著名历史学家，有15个人排成一圈，编号为1 - 15，从1号开始报数，报到数字4，7，10的人退出，中间间隔为3，余下的人从退出者
 * 下一个位置开始继续刚才的报数，依次循环，最后整个队列剩下一个人，这个人是几号？
 * 
 * @author Lang
 *
 */
// 输入
// 1：total——总的人数，默认15
// 2：width——间隔，默认3
// 输出
// 输出最后一个人的号数
@Guarded
public class FlaviusJosephu extends AbstractTopic implements Topic {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 创建算法对象
     */
    private transient Algorithm algorithm = new Algorithm();
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 验证这个问题的输入
     */
    @Override
    public boolean verifyInput(@NotNull @Size(min = 1, max = 2) final String... args) {
        /**
         * 只能是数字
         */
        boolean ret = true;
        final Pattern pattern = Pattern.compile("[0-9]+");
        for (final String arg : args) {
            final Matcher matcher = pattern.matcher(arg);
            if (!matcher.matches()) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    /**
     * 当前Topic的标题信息
     */
    @Override
    public String title() {
        return "Flavius Josephu";
    }

    /**
     * 主体算法函数
     */
    @Override
    public String process(@NotNull @Size(min = 2, max = 2) final String... args) {
        /**
         * 1.读取总数以及间隔
         */
        final int total = Integer.parseInt(args[0]);
        final int width = Integer.parseInt(args[1]);
        /**
         * 2.计算最终的号码
         */
        return this.algorithm.josephuProcess(total, width);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
