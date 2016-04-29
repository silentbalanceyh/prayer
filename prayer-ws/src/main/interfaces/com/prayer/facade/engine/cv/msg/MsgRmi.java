package com.prayer.facade.engine.cv.msg;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
public interface MsgRmi {
    /** **/
    String RMI_ADDR = "( RMI Server ) Registrying address : {0}";
    /** **/
    String RMI_REGISTRY = "( RMI Server ) Instance of {0} has been registered successfully. Registered Data : {1} ";
    /** **/
    String RMI_ERROR = "( RMI Server ) Instance registry met exception : {0}";
    /** **/
    String RMI_LOOKUP = "( RMI Client ) Lookup remote object reference via address: {0}";
    /** **/
    String RMI_CLERROR = "( RMI Client ) Lookup remote object met exception : {0}";
    /** **/
    String RMI_REFERENCE = "( RMI Client ) Remote Object of {0} ( hashCode = {1} ) has been looked up.";
}
