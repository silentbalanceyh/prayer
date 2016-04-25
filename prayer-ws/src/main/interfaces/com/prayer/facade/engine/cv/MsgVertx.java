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
    /** **/
    String VX_START = "( {0} ) Start Vertx Engine ( name = {1} ) in {2} mode, it''s booting...";
    /** **/
    String VX_STOP = "( {0} ) Stop Vertx Engine, please wait for server to be stopped...";
    /** **/
    String VX_STOPPED = "( {0} ) Vertx Engine has been stopped successfully!";
    /** **/
    String ES_URI = "( {0} ) Vertx Engine has published ( Uri/Rule ) configuration data to {1} successfully! ( RMI Not Needed )";
    
    /** 2.Handler的基本信息 **/
    String INF_HANDLER = "( {0} ) Path: {1}, Handler: {2}.";
}
