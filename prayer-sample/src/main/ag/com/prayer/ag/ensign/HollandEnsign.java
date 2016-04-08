package com.prayer.ag.ensign;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.prayer.ag.Topic;
import com.prayer.ag.util.Console;
import com.prayer.ag.util.Input;
import com.prayer.facade.constant.Symbol;

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
// 输入：size ( size > 60 )
// 1.生成一个随机数组，长度为size
// 2.这个随机数组只有三个值：0 (红）、1（白）、2（蓝）
// 3.输入的size必须是合法数值
// 4.将随机数组乱序排列，最终打印出排好序的0000001111111222222三种颜色的荷兰国旗串
@Guarded
public class HollandEnsign implements Topic {

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
     * 验证这个问题的参数
     */
    @Override
    public boolean verifyInput(@NotNull @Size(min = 1, max = 1) final String... args) {
        /**
         * 匹配数值，只能是数字，且不可为负数
         */
        boolean ret = true;
        final String inputNum = args[0];
        final Pattern pattern = Pattern.compile("[0-9]+");
        final Matcher matcher = pattern.matcher(inputNum);
        if (matcher.matches()) {
            final Integer size = Integer.parseInt(inputNum);
            /**
             * 参数不可以小于60，并且参数必须能被3整除
             */
            if (size >= 60) {
                ret = true;
            } else {
                ret = false;
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
        /**
         * 1.生成一个数组，这个数组只包含0,1,2三个数，分别代表三种颜色
         */
        final int size = Integer.parseInt(args[0]);
        final int[] flags = initFlagArray(size);
        /**
         * 2.打印排序之前的国旗信息，国旗高度为16
         */
        System.out.println(this.getArrayOut(flags, "[Before]", 8));
        /**
         * 3.开始执行主函数，计算最终的国旗值，并且通过交换函数修改moves
         */
        final int moves = this.algorithm.quickProcess(flags);
        /**
         * 4.打印移动步数
         */
        System.out.println("Moves : " + moves);
        return this.getArrayOut(flags, "\n[After]", 8);
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
        Console.start(title());
        /**
         * 2.解析输入的参数信息
         */
        String[] inputArgs = Input.commandArgs(Console.prompt());
        while (true) {
            try {
                /**
                 * 3.验证输入参数信息
                 */
                if (verifyInput(inputArgs)) {
                    /**
                     * 4.3.执行主体逻辑，打印结果
                     */
                    final String result = process(inputArgs);
                    /**
                     * 4.4.打印结果集，程序执行完成
                     */
                    System.out.println("[SUCCESS] Result is : " + result);
                    System.exit(0);
                } else {
                    /**
                     * 4.2.参数验证未通过
                     */
                    System.out.println("[ERROR] The 1st element of arguments must be greater than 60. Args = "
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
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    /**
     * 构造输出格式
     * 
     * @param prefix
     * @param height
     * @return
     */
    private String getArrayOut(final int[] flags, final String prefix, final int height) {
        final StringBuilder retStr = new StringBuilder();
        retStr.append(prefix).append(Symbol.COLON).append(Symbol.NEW_LINE);
        for (int i = 0; i < height; i++) {
            for (final int item : flags) {
                retStr.append(item);
            }
            retStr.append(Symbol.NEW_LINE);
        }
        return retStr.toString();
    }

    private int[] initFlagArray(final int size) {
        final int[] flags = new int[size];
        final Random random = new Random();
        for (int i = 0; i < flags.length; i++) {
            // 生成随机数0,1,2赋值给retArr数组
            flags[i] = random.nextInt(3);
        }
        return flags;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
