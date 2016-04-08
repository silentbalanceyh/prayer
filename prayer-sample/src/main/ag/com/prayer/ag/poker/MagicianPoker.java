package com.prayer.ag.poker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.prayer.ag.Topic;
import com.prayer.ag.util.AbstractTopic;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;
import net.sf.oval.guard.Guarded;

/**
 * 魔术师发牌问题：魔术师使用一副牌中的13张黑桃，预先将它排好后放在一起，牌面向下，对观众说：我不看牌只数数就可以猜到每张牌是什么；然后数1，
 * 第一张翻开的是A，第二次数1，2，将前面1张放到这些牌下边，第二张翻开的是黑桃2，第三次数1，2，3，将前边两张放到这堆牌下边，第三张正好是3
 * 
 * @author Lang
 *
 */
// 输入
// total——总的牌数量，没有默认值
// 输出
// 输出原来的扑克牌的摆放顺序
@Guarded
public class MagicianPoker extends AbstractTopic implements Topic {
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
    // ~ Methods =============================================
    /**
     * 
     */
    @Override
    public boolean verifyInput(@NotNull @Size(min = 1, max = 1) final String... args) {
        /**
         * 只能是数字
         */
        boolean ret = true;
        final Pattern pattern = Pattern.compile("[0-9]+");
        final Matcher matcher = pattern.matcher(args[0]);
        if (!matcher.matches()) {
            ret = false;
        }
        return ret;
    }

    /** **/
    @Override
    public String title() {
        return "Magician Poker";
    }

    /**
     * 主体算法函数
     */
    public String process(@NotNull @Size(min = 1, max = 2) final String... args) {
        /**
         * 读取扑克牌总数
         */
        final int total = Integer.parseInt(args[0]);
        /**
         * 计算最终结果
         */
        return this.algorithm.pokerProcess(total);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
