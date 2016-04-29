package com.prayer.plugin.oval;

import net.sf.oval.guard.GuardAspect;
import net.sf.oval.internal.Log;
import net.sf.oval.logging.LoggerFactorySLF4JImpl;

/**
 * OVal + AspectJ共同使用的合成类，启用AspectJ的OVal框架必须的代码
 * @author Lang
 * @see
 */
public aspect DefaultGuardAspect extends GuardAspect {
    public DefaultGuardAspect() {
        super();
        /** 使用SLF4J **/
        Log.setLoggerFactory(new LoggerFactorySLF4JImpl());
    }
}
