package com.prayer.facade.engine.cv.msg;

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
    String DP_WORKERED = "( {0} ) Consumer: {1} has been connected to Address {2} successfully.";
    
    /** **/
    String DP_MODE = "( {0} ) Vertx is running in ( Cluster = {1}, HA = {2} ) mode. --> Full Config: {3}";
    /** **/
    String VX_START = "( {0} ) Start Vertx Engine ( name = {1} ) in {2} mode, it''s booting...";
    /** **/
    String VX_API = "( {0} ) \n****************   Api Endpoint Inforamation ****************\n*    Secure Endpoint: http://{1}:{2}{3}\n*   Publish Endpoint: http://{1}:{2}{4}\n*************************************************************\n";
    /** **/
    String VX_SERVER = "( {0} ) Http Server has been started successfully. Status: RUNNING";
    /** **/
    String VX_STOP = "( {0} ) Stop Vertx Engine, please wait for server to be stopped...";
    /** **/
    String VX_STOPPED = "( {0} ) Vertx Engine has been stopped successfully!";
    /** **/
    String VX_EXP_STOPPED = "( {0} ) Because Meta Server has been stopped, Vertx Engine also has been stopped at the same time.";
    /** **/
    String ES_URI = "( {0} ) Vertx Engine has pushed ( Uri/Rule ) configuration data to {1} successfully! ( RMI Not Needed )";

    /** 2.Handler的基本信息 **/
    String INF_HANDLER = "( {0} ) Path: {1}, Handler: {2}.";

    /** PUT **/
    String MAP_PUT = "( {0} ) SharedMap ( name = {1}, hash = {2} ) executed PUT with ( key = {3}, value = {4} ).";
    /** **/
    String MAP_GET = "( {0} ) SharedMap ( name = {1}, hash = {2} ) executed GET with key = {3}, the result value is {4}.";

    /** **/
    String REQ_BPATH = "( {0} ) Before : Request path ( Metadata ) is {1}.";
    /** **/
    String REQ_APATH = "( {0} ) After : Request path ( Metadata ) is {1}.";

    /** **/
    String REQ_ACCEPT_MATCH = "( {0} ) Actual: {1} ( q = {2} ), Expect: {3}, the match result is {4}";
    /** **/
    String SEV_ENDDATA = "( {0} ) Message Locating to address : {1}, message data {2} has been send by Sender: {3}.";
    /** **/
    String SEV_SENDER = "( {0} ) Message Sender {1} has got response data {2} from event Bus.";
    /** **/
    String SEV_CONSUMER = "( {0} ) Message Consumer {1} has got request data {2}, call service method ( Stubor ).";
}
