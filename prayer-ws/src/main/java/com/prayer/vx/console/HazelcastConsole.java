package com.prayer.vx.console;

import static com.prayer.assistant.WebLogger.info;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.Config;
import com.hazelcast.config.ExecutorConfig;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.console.ConsoleApp;
import com.hazelcast.core.Hazelcast;
import com.prayer.assistant.WebLogger;

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
            config.addExecutorConfig(new ExecutorConfig("Prayer Executor " + k).setPoolSize(k));
        }
        final ConsoleApp consoleApp = new ConsoleApp(Hazelcast.newHazelcastInstance(config));
        try {
            consoleApp.start(args);
        } catch (Exception ex) {
            info(LOGGER, WebLogger.I_COMMON_INFO, ex.getMessage());
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
