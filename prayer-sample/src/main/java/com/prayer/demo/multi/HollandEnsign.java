package com.prayer.demo.multi;

import com.prayer.demo.Topic;
import com.prayer.demo.util.Console;

import net.sf.oval.guard.Guarded;

/**
 * 众所周知，荷兰国旗由红色、白色和蓝色3种颜色组成， 现在假设有很多这3种颜色被乱序排放在一个数组里，要求每次操作仅能进行一次交换，
 * 对数组进行一遍扫描后，三种颜色自然分开，颜色顺序为红、白、蓝 要求在O(n)的复杂度下，使得移动次数最少
 * 
 * @author Lang
 *
 */
// 输入：size ( size > 40 )
// 1.生成一个随机数组，长度为size
// 2.这个随机数组只有三个值：0 (红）、1（白）、2（蓝）
// 3.输入的size必须是合法数值
// 4.将随机数组乱序排列，最终打印出排好序的0000001111111222222三种颜色的荷兰国旗串
@Guarded
public class HollandEnsign implements Topic {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    @Override
    public boolean verifyInput(String... args) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String process(String... args) {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * 
     */
    @Override
    public String title() {
        return "Holladn Ensign";
    }

    @Override
    public void runTool(String... args) {
        /***
         * 1.打印Console的头部信息
         */
        Console.start(this.title());

    }
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
