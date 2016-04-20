package com.prayer.facade.engine.metaserver.h2;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
public interface H2Messages {
    /** Single **/
    String URI_SINGLE = "jdbc:h2:tcp://{0}:{1}/META/{2}";
    /** Cluster **/
    String URI_CLUSTER = "jdbc:h2:tcp://{0}/META/{1}";

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Cluster {
        /** 创建Cluster失败 **/
        String ERROR_CLUSTER = "( H2 Cluster ) H2 cluster creation met some error. Details = {0}.";
        /** Target **/
        String INFO_DEST = "( H2 Cluster ) H2 cluster target list : {0}";
        /** Source **/
        String INFO_SRCS = "( H2 Cluster ) H2 cluster source list : {0}";
        /** 创建Cluster成功 **/
        String INFO_CLUSTER = "( H2 Cluster ) H2 cluster has been created successfully.";
        /** **/
        String INFO_PARAM = "( H2 Cluster ) H2 cluster parameters : {0}.";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface WebConsole {
        /** Error **/
        String ERROR_PORT = "( H2 Web Console ) The port : {0} has been taken, please setup another port for H2 Web Console.";
        /** Error **/
        String ERROR_SQL = "( H2 Web Console ) Web console starting met errors, Details = {0}.";
        /** I:Starting **/
        String INFO_STARTING = "( H2 Web Console ) Web console is starting on : {0}.";
        /** I:Is Running **/
        String INFO_RUNNING = "( H2 Web Console ) Web console is already started on : {0}.";
        /** I:Started **/
        String INFO_STARTED = "( H2 Web Console ) Web console has been started on : {0}. (WEB) Access Point: {1}.";
        /** I:Status **/
        String INFO_STATUS = "( H2 Web Console ) Web console status -> {0}";
        /** Stopped **/
        String T_STOPPED = "( H2 Web Console ) Web console has been stopped successfully !";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Database {
        /** **/
        String JDBC_URI = "( H2 Database ) JDBC URI : {0}";
        /** **/
        String INFO_QUEUE = "( H2 Database ) H2 database on Port {0} has been stopped, now the queue size is: {1}";
        /** **/
        String INFO_RUN_QUE = "( H2 Database ) H2 database queue size is : {0}";

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Single {
            /** Error **/
            String ERROR_PORT = "( H2 Database ) The port : {0} has been taken, please setup another port for H2 Database";
            /** Error **/
            String ERROR_SQL = "( H2 Database ) H2 database starting met errors, Details = {0}.";
            /** I:Starting **/
            String INFO_STARTING = "( H2 Database ) H2 database is starting on : {0}.";
            /** I:Is Running **/
            String INFO_RUNNING = "( H2 Database ) H2 database is already started on : {0}.";
            /** I:Started **/
            String INFO_STARTED = "( H2 Database ) H2 database has been started on : {0}. (TCP) Access Point: {1}.";
            /** I:Status **/
            String INFO_STATUS = "( H2 Database ) H2 database status -> {0}";

            /** I:Not Running **/
            String INFO_NOT_RUNNING = "( H2 Database ) H2 database is not started on : {0}.";
            /** I:Stopping **/
            String INFO_STOPPING = "( H2 Database ) H2 database is stopping on : {0}.";
            /** I:Stopped **/
            String INFO_STOPPED = "( H2 Database ) H2 database has been stopped via TCP URI : {0}.";

            /** T:Stopped **/
            String T_STOPPED = "( H2 Database ) H2 database has been stopped successfully !";
        }
    }
}
