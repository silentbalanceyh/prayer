package com.prayer.demo.multi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.prayer.demo.Topic;
import com.prayer.demo.util.Console;
import com.prayer.demo.util.Input;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;
import net.sf.oval.exception.ConstraintsViolatedException;
import net.sf.oval.guard.Guarded;

/**
 * 众所周知，荷兰国旗由红色、白色和蓝色3种颜色组成， 现在假设有很多这3种颜色被乱序排放在一个数组里，要求每次操作仅能进行一次交换，
 * 对数组进行一遍扫描后，三种颜色自然分开，颜色顺序为红、白、蓝 要求在O(n)的复杂度下，使得移动次数最少
 * 
 * @author Lang
 *
 */
// 输入：size ( size > 30 且必须是3的倍数 )
// 1.生成一个随机数组，长度为size
// 2.这个随机数组只有三个值：0 (红）、1（白）、2（蓝）
// 3.输入的size必须是合法数值
// 4.将随机数组乱序排列，最终打印出排好序的0000001111111222222三种颜色的荷兰国旗串
@Guarded
public class HollandEnsign implements Topic {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 验证这个问题的参数
     */
    @Override
    public boolean verifyInput(@NotNull @Size(min = 1, max = 1) final String... args) {
        /**
         * 匹配数值，只能是数字，且不可为负数
         */
        boolean ret = true;
        final String inputNum = args[0];
        final Pattern pattern = Pattern.compile("\\[0-9]+");
        final Matcher matcher = pattern.matcher(inputNum);
        if (matcher.matches()) {
            final Integer size = Integer.parseInt(inputNum);
            /**
             * 参数不可以小于30，并且参数必须能被3整除
             */
            if (size < 30 || 0 != size % 3) {
                ret = false;
            } else {
                ret = true;
            }
        } else {
            ret = false;
        }
        return ret;
    }
    /**
     * 主体算法函数
     */
    @Override
    public String process(@NotNull @Size(min = 1, max = 1) final String... args) {
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
        /**
         * 2.解析输入的参数信息
         */
        String[] inputArgs = Input.commandArgs(Console.prompt());
        while (true) {
            try {
                /**
                 * 3.验证输入参数信息
                 */
                if (this.verifyInput(inputArgs)) {
                    /**
                     * 4.3.执行主体逻辑，打印结果
                     */
                    final String result = this.process(inputArgs);
                    /**
                     * 4.4.打印结果集，程序执行完成
                     */
                    System.out.println("[SUCCESS] Result is : " + result);
                    System.exit(0);
                } else {
                    /**
                     * 4.2.参数验证未通过
                     */
                    System.out.println(
                            "[ERROR] The 1st element of arguments must be greater than 3 and could be divided evenly by 3. Args = "
                                    + Console.toStr(args));
                    inputArgs = Input.commandArgs(Console.prompt());
                    continue;
                }
            } catch (ConstraintsViolatedException ex) {
                /**
                 * 4.1.参数长度验证没通过，长度必须是1
                 */
                System.out.println("[ERROR] The Length of arguments must be 1 !");
                inputArgs = Input.commandArgs(Console.prompt());
                continue;
            }
        }
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
