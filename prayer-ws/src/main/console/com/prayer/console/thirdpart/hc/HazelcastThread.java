package com.prayer.console.thirdpart.hc;

import static com.prayer.util.reflection.Instance.singleton;

import com.hazelcast.config.Config;
import com.hazelcast.config.ExecutorConfig;
import com.hazelcast.console.ConsoleApp;
import com.hazelcast.core.Hazelcast;
import com.prayer.console.util.OutGoing;
import com.prayer.facade.console.message.SharedTidings;
import com.prayer.facade.engine.opts.Intaker;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.vertx.config.tp.HazelcastIntaker;

/**
 * 
 * @author Lang
 *
 */
public class HazelcastThread implements Runnable {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient String[] params;
    /** **/
    private transient Intaker<Config> intaker = singleton(HazelcastIntaker.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public HazelcastThread(final String[] params) {
        this.params = params;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void run() {
        try {
            /** 1.扩展参数 **/
            final Config config = intaker.ingest();
            for (int idx = 1; idx <= 16; idx++) {
                config.addExecutorConfig(new ExecutorConfig("Hazelcast Executor " + idx).setPoolSize(idx));
            }
            final ConsoleApp consoleApp = new ConsoleApp(Hazelcast.newHazelcastInstance(config));
            consoleApp.start(this.params);
        } catch(AbstractException ex){
            OutGoing.outLn(SharedTidings.Error.THREAD, ex.getMessage());
        }catch (Exception ex) {
            OutGoing.outLn(SharedTidings.Error.THREAD, ex.getMessage());
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
