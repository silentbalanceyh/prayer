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

        /** I:Not Running **/
        String INFO_NOT_RUNNING = "( H2 Web Console ) Web console is not started on : {0}.";
        /** I:Stopping **/
        String INFO_STOPPING = "( H2 Web Console ) Web console is stopping on : {0}.";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Database {
        /** **/
        String JDBC_URI = "( H2 Database ) JDBC URI : {0}";

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
        }
    }
}
