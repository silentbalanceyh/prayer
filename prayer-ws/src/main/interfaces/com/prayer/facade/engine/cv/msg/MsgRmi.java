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
    String RMI_REGINFO = "( RMI Server ) Registrying address : {0}, Data : {1}, Instance: {2}.";
    /** **/
    String RMI_ERROR = "( RMI Server ) Instance registry met exception : {0}";
    /** **/
    String RMI_LOOKUP = "( RMI Client ) Lookup remote object reference via address: {0}";
    /** **/
    String RMI_CLERROR = "( RMI Client ) Lookup remote object met exception : {0}";
    /** **/
    String RMI_REFERENCE = "( RMI Client ) Remote Object of {0} ( hashCode = {1} ) has been looked up.";
}
