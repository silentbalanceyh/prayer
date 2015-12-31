package com.prayer.console;

import static com.prayer.util.debug.Log.jvmError;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.Config;
import com.hazelcast.config.ExecutorConfig;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.console.ConsoleApp;
import com.hazelcast.core.Hazelcast;

/**
 * 
 * @author Lang
 *
 */
public class HazelcastConsole {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastConsole.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param args
     * @throws Exception
     */
    public void runTool(final String... args) {
        Config config;

        try {
            config = new FileSystemXmlConfig("hazelcast.xml");
        } catch (FileNotFoundException e) {
            config = new Config();
        }

        for (int k = 1; k <= 16; k++) {
            config.addExecutorConfig(new ExecutorConfig("Prayer Executor " + k).setPoolSize(k));    // NOPMD
        }
        final ConsoleApp consoleApp = new ConsoleApp(Hazelcast.newHazelcastInstance(config));
        try {
            consoleApp.start(args);
        } catch (Exception ex) {        // NOPMD
            jvmError(LOGGER, ex);
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
