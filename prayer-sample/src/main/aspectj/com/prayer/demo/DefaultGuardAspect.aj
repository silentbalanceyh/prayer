package com.prayer.demo;

import net.sf.oval.guard.GuardAspect;

/**
 * OVal + AspectJ共同使用的合成类，启用AspectJ的OVal框架必须的代码
 * @author Lang
 * @see
 */
public aspect DefaultGuardAspect extends GuardAspect {
    public DefaultGuardAspect() {
        super();
    }
}
