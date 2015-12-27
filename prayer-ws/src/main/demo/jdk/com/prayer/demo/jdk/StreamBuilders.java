package com.prayer.demo.jdk;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamBuilders {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    public static void main(String args[]){
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 1; i< 10000000; i++){
            list.add(i);
        }
        Stream<Integer> stream = list.parallelStream();
        stream.forEach(p -> System.out.println(p));
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
