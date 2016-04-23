package com.prayer.facade.engine.cv;

/**
 * 
 * @author Lang
 *
 */
public interface MsgVertx extends MsgCommon {
    /** **/
    String DP_HANDLER = "( {0} ) Deploying verticle: {1} has been deployed {2} instances successfully! --> Full Config: {3}";
    /** **/
    String DP_HANDLER_ERR = "( {0} ) Deploying verticle {1} with instances {2} met errors.";
    /** **/
    String DP_COUNTER = "( {0} ) Please set counter first, it's the kernel concurrent variable.";
    /** **/
    String DP_MODE = "( {0} ) Vertx is running in ( Cluster = {1}, HA = {2} ) mode. --> Full Config: {3}";
}
