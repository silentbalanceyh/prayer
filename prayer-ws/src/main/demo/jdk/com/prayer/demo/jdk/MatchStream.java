package com.prayer.demo.jdk;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class MatchStream {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    public static void main(String args[]){
        List<String> list = new ArrayList<>();
        for(int i = 1; i< 10; i++){
            final String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
            list.add(uuid);
        }
        Stream<String> stream = list.parallelStream();
        boolean matched = stream.anyMatch(s -> s.contains("a"));
        System.out.println("AnyMatch -> " + matched);
        stream = list.parallelStream();
        matched = stream.allMatch(s -> s.contains("a"));
        stream = list.parallelStream();
        System.out.println("AllMatch -> " + matched);
        matched = stream.noneMatch(s -> s.contains("a"));
        stream = list.parallelStream();
        System.out.println("NoneMatch -> " + matched);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
