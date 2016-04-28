package com.prayer.ag.latin;

import com.prayer.ag.Topic;
import com.prayer.ag.util.AbstractTopic;
import com.prayer.ag.util.Input;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;

/**
 * 拉丁方阵是一种特殊的N阶方阵，如果从1开始到N的一个N x N的方阵，且每一行每一列都没有重复数据，就称为一个N阶方阵
 * @author Lang
 *
 */
// 输入
// n - 方阵的阶数，数据从2到9即可
public class LatinMatrix extends AbstractTopic implements Topic{
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
     * 
     */
    @Override
    public boolean verifyInput(@NotNull @Size(min = 1, max = 1) final String... args){
        return Input.verifyOneNumber(args);
    }
    
    /** **/
    @Override
    public String title(){
        return "Latin Matrix";
    }
    
    /** **/
    public String process(@NotNull @Size(min = 1, max = 1) final String... args) {
        /**
         * 输入方阵阶数
         */
        final int n = Integer.parseInt(args[0]);
        /**
         * 
         */
        return this.algorithm.latinMatrix(n);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
