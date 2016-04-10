package com.prayer.jdk8;

import java.util.Optional;

/**
 * 探索JDK 8.0中的新语法
 * 
 * @author Lang
 *
 */
public class OptionalTesting {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    public static void main(String args[]) {
        /** EXP：抛出NullPointer **/
        // Optional<String> name = Optional.of(null);
        /** 1.构造一个Optional **/
        Optional<String> name = Optional.of("Hello World");
        console(name);
        /** 2.构造一个Optional，但可以传入null **/
        Optional<String> empty = Optional.ofNullable(null);
        console(empty);
        /** 3.构造一个Optional **/
        Optional<String> name2 = Optional.ofNullable("Hello World 1");
        console(name2);
        /** 4.存在值的时候执行里面的内容 **/
        name.ifPresent((value) -> {
            System.out.println("If Present : " + value);
        });
        /** 5.orElse **/
        // 1.如果值不为null，返回Optional实例的值
        // 2.如果为null，返回传入的消息
        System.out.println("Value : " + name.orElse("Value Existing"));
        System.out.println("No Value : " + empty.orElse("Empty Value"));
        
        
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    private static <T> void console(Optional<T> test) {
        System.out.println("Is Present : " + test.isPresent());
        /** 无值的时候返回NoSuchElementException **/
        try {
            System.out.println("Get : " + test.get());
        } catch (Exception ex) {
            System.out.println("GET : " + ex.getMessage());
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
