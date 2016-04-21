package com.prayer.facade.console.message;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */

@VertexPoint(Interface.CONSTANT)
public interface SharedTidings {
    /** **/
    String SUCCESS = "[Consoler] -> Connected Successfully !";

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Error {
        /** Rmi **/
        String RMI = "[Connector] -> Connect RMI met errors, type = {0}, details = {1}";
        /** Shell **/
        String SHELL = "[Connector] -> SQL Connection met errors, details = {0}.";
        /** **/
        String THREAD = "[Connector] -> Thread met errors, details = {0}";
        /** **/
        String FAILURE = "[Connector -> Connected refused.";
    }
}
