package com.prayer.shane.course002.demo;

import java.util.function.Function;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class FunRef {
    
    public void doSave(String str, Callback callback){
        System.out.println(str);
        callback.execute(new JsonObject());
    }
    
    public static void main(String args[]){
        final Function<String,String> item = String::new;
        final String x = item.apply("Hello");
        
        System.out.println(x);
    }
    
    public static void console(JsonObject input){
        System.out.println(input);
    }
}

@FunctionalInterface
interface Fun{
    void print(String name);
}

@FunctionalInterface
interface Callback{
    void execute(JsonObject data);
}
