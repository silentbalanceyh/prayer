package com.prayer.business.fun;

import com.prayer.model.business.behavior.ActRequest;
import com.prayer.model.business.behavior.ActResponse;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface ActMethod {
    /** **/
    ActResponse execute(ActRequest request);
}
