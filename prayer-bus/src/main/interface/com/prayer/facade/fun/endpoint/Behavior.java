package com.prayer.facade.fun.endpoint;

import javax.script.ScriptException;

import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.behavior.ActRequest;
import com.prayer.model.business.behavior.ActResponse;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface Behavior {
    /** **/
    ActResponse dispatch(ActRequest request) throws ScriptException, AbstractException;
}
