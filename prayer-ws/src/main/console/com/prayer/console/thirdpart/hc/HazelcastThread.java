package com.prayer.console.thirdpart.hc;

import com.hazelcast.config.Config;
import com.hazelcast.config.ExecutorConfig;
import com.hazelcast.console.ConsoleApp;
import com.hazelcast.core.Hazelcast;
import com.prayer.console.util.OutGoing;
import com.prayer.facade.console.message.SharedTidings;

/**
 * 
 * @author Lang
 *
 */
public class HazelcastThread implements Runnable{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient String[] params;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public HazelcastThread(final String[] params){
        this.params = params;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void run(){
        try{
            /** 1.扩展参数 **/
            final Config config = new Config();
            for(int idx = 1; idx <= 16; idx++ ){
                config.addExecutorConfig(new ExecutorConfig("Hazelcast Executor " + idx).setPoolSize(idx)); 
            }
            final ConsoleApp consoleApp = new ConsoleApp(Hazelcast.newHazelcastInstance(config));
            consoleApp.start(this.params);
        }catch(Exception ex){
            OutGoing.outLn(SharedTidings.Error.THREAD,ex.getMessage());
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
