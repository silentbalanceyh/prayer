package com.prayer.facade.engine.cv;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
public interface RmiMessages {
    /** **/
    String RMI_ADDR = "( RMI ) Registrying address : {0}";
    /** **/
    String RMI_REGISTRY = "( RMI ) Instance of {0} ( hashCode = {1} ) has been registered successfully.";
    /** **/
    String RMI_ERROR = "( RMI ) Instance registry met exception : {0}";
    /** **/
    String RMI_LOOKUP = "( RMI Client ) Lookup remote object reference via address: {0}";
    /** **/
    String RMI_CLERROR = "( RMI Client ) Lookup remote object met exception : {0}";
    /** **/
    String RMI_REFERENCE = "( RMI Client ) Remote Object of {0} ( hashCode = {1} ) has been looked up.";
}
