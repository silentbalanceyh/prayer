package com.prayer.demo;

import java.io.FileNotFoundException;

import com.hazelcast.config.Config;
import com.hazelcast.config.ExecutorConfig;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.console.ConsoleApp;
import com.hazelcast.core.Hazelcast;

public class HazelcastConsole {
    // ~ Static Fields =======================================
    private static final String EXECUTOR_NAMESPACE = "Sample Executor";
    private static final int LOAD_EXECUTORS_COUNT = 16;
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    public static void main(String args[]) throws Exception{
        Config config;

        try {
            config = new FileSystemXmlConfig("hazelcast.xml");
        } catch (FileNotFoundException e) {
            config = new Config();
        }
        
        for (int k = 1; k <= LOAD_EXECUTORS_COUNT; k++) {
            config.addExecutorConfig(new ExecutorConfig(EXECUTOR_NAMESPACE + " " + k).setPoolSize(k));
        }
        ConsoleApp consoleApp = new ConsoleApp(Hazelcast.newHazelcastInstance(config));
        consoleApp.start(args);
    }
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
