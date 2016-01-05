package com.prayer.ag.multi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.prayer.ag.Topic;
import com.prayer.ag.util.Console;
import com.prayer.ag.util.Input;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;
import net.sf.oval.exception.ConstraintsViolatedException;
import net.sf.oval.guard.Guarded;

/**
 * 超过Java中数值范围的数据的乘法计算方法，又称为大数据乘法
 * 
 * @author Lang
 *
 */
// 输入：num1 num2
// 1.只能输入两个数值
// 2.两个数值之间使用一个空白或者多个空白隔开
// 3.输入的数值必须是合法的数字
// 输出：num1 x num2 = result
// 示例输出：
// Welcome to Big/Little Fly Samples:
// Topic --> Large Integer Multiplication
// > 123456789 98521478536541257896
// [SUCCESS] Result is : 123,456,789 x 98,521,478,536,541,257,896 = 12,163,145,387,653,802,865,861,055,944
@Guarded
public class MultiBigInteger implements Topic {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @param args
     */
    public void runTool(final String... args) {
        /**
         * 1. 打印Console的头部信息
         */
        Console.start(this.title());
        /**
         * 2. 解析输入的参数信息
         */
        String[] inputArgs = Input.commandArgs(Console.prompt());
        while (true) {
            try {
                /**
                 * 3. 验证输入参数信息
                 */
                if (this.verifyInput(inputArgs)) {
                    /**
                     * 4.3.执行主逻辑，得到结果
                     */
                    final String result = this.process(inputArgs);
                    /**
                     * 4.4.打印结果集，程序执行完成
                     */
                    System.out.println("[SUCCESS] Result is : " + result);
                    System.exit(0);
                } else {
                    /**
                     * 4.2.参数验证没通过，参数必须是数值格式
                     */
                    System.out.println(
                            "[ERROR] Arguments must be format of Large Integer. Args = " + Console.toStr(inputArgs));
                    inputArgs = Input.commandArgs(Console.prompt());
                    continue;
                }
            } catch (ConstraintsViolatedException ex) {
                /**
                 * 4.1.参数长度验证没通过，长度必须是2
                 */
                System.out.println("[ERROR] The Length of arguments must be 2 !");
                inputArgs = Input.commandArgs(Console.prompt());
                continue;
            }
        }
    }

    /**
     * 验证这个问题的输入信息
     */
    @Override
    public boolean verifyInput(@NotNull @Size(min = 2, max = 2) final String... args) {
        /**
         * 匹配正数或者负数的正则表达式
         */
        final Pattern pattern = Pattern.compile("(\\+|-)*[0-9]+");
        boolean ret = true;
        for (final String arg : args) {
            final Matcher matcher = pattern.matcher(arg);
            /**
             * 只要有一个没有匹配则直接跳出返回false
             */
            if (!matcher.matches()) {
                ret = false;
            }
        }
        return ret;
    }
    /**
     * 主体算法函数
     */
    @Override
    public String process(@NotNull @Size(min = 2, max = 2) final String... args) {
        /**
         * 1.获取两个输入信息，后边会赋值，不执行final定义
         */
        String first = args[0];
        String second = args[1];
        /**
         * 2.构造结果输出对象
         */
        final StringBuilder ret = new StringBuilder();
        /**
         * 3.正数不输出加号，进行replace操作，称号使用x代替*
         */
        ret.append(formatNumber(first.replace("\\+", ""))).append(" x ").append(formatNumber(second.replace("\\+", "")))
                .append(" = ");
        /**
         * 4.异或运算，只要有一个为负那么结果为负
         */
        if (first.startsWith("-") ^ second.startsWith("-")) {
            ret.append('-');
        }
        /**
         * 5.将first和second的符号去掉，包含符号的情况
         */
        if (first.startsWith("-") || first.startsWith("+")) {
            first = first.substring(1, first.length() - 1);
        }
        if (second.startsWith("-") || second.startsWith("+")) {
            second = second.substring(1, second.length() - 1);
        }

        /**
         * 6.转换成输入数组，比如453463254 -> {4,5,3,4,6,3,2,5,4}
         */
        final int[] firstArr = this.prepareInput(first);
        final int[] secondArr = this.prepareInput(second);
        /**
         * 7.计算最终结果，并且添加到结果集中
         */
        final String result = this.multi(firstArr, secondArr);
        /**
         * 8.将最终结果输出
         */
        return ret.append(formatNumber(result)).toString();
    }

    /**
     * 当前Topic的标题信息
     */
    @Override
    public String title() {
        return "Large Integer Multiplication";
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 将String转换成int[]的函数
     * 
     * @param input
     * @return
     */
    private int[] prepareInput(final String input) {
        final int digit = input.length();
        final int[] retArr = new int[digit];
        for (int idx = 0; idx < digit; idx++) {
            retArr[idx] = Integer.parseInt(String.valueOf(input.charAt(idx)));
        }
        return retArr;
    }

    /**
     * 三位一组的计数法
     * 
     * @param number
     * @return
     */
    private String formatNumber(final String number) {
        final int length = number.length();
        final StringBuilder retNum = new StringBuilder();
        for (int i = length - 1; i >= 0; i--) {
            retNum.append(number.charAt(i));
            final int idx = length - i;
            if (idx % 3 == 0 && i > 0) {
                retNum.append(',');
            }
        }
        return retNum.reverse().toString();
    }

    /**
     * 大数据乘法
     * 
     * @param firstArr
     * @param secondArr
     * @return
     */
    private String multi(final int[] firstArr, final int[] secondArr) {
        /**
         * 1.将两个数组的长度计算，合计乘法最终结果的总长度，构造新的结果数组
         */
        final int size = firstArr.length + secondArr.length;
        final int[] retArr = new int[size];
        /**
         * 2.第一级运算，计算每一个单元格的乘积 <code>1,2,3</code> <code>4,5,6</code>
         */
        for (int i = secondArr.length - 1; i >= 0; i--) {
            /**
             * 
             */
            int k = i + firstArr.length;
            for (int j = firstArr.length - 1; j >= 0; j--) {
                retArr[k--] += secondArr[i] * firstArr[j];
            }
        }
        /**
         * 3.每个单元格中都有数据，但是上边计算的数据有可能超过10，这种情况需要进行10除转换，进位运算
         */
        for (int i = size - 1; i >= 0; i--) {
            if (retArr[i] >= 10) {
                /**
                 * 3.1.处理进位
                 */
                retArr[i - 1] += retArr[i] / 10;
                /**
                 * 3.2.保留个位
                 */
                retArr[i] %= 10;
            }
        }
        /**
         * 4.将最终的数组转换成String打印出来
         */
        final StringBuilder retStr = new StringBuilder();
        boolean start = false;
        for (int i = 0; i < size; i++) {
            /**
             * 5.寻找第一个开始点进行append
             */
            if (retArr[i] == 0 && !start) {
                continue;
            } else {
                start = true;
            }
            retStr.append(retArr[i]);
        }
        return retStr.toString();
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
