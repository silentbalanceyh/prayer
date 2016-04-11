package com.prayer.facade.fun.endpoint;

import javax.script.ScriptException;

import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.web.WebRequest;
import com.prayer.model.web.WebResponse;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface Behavior {
    /** **/
    WebResponse dispatch(WebRequest request) throws ScriptException, AbstractException;
}
