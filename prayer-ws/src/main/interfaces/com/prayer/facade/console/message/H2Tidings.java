package com.prayer.facade.console.message;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */

@VertexPoint(Interface.CONSTANT)
public interface H2Tidings {
    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Data{
        /** **/
        String URL = "url";
    }
    /** **/
    String TOPIC = "Meta Server ( H2 Database Shell )";
    /** **/
    String OPTS = "[Connector] -> ( RMI ) Configuration data has been read : {0}";
    /** **/
    String JDBC = "[Connector] -> ( JDBC ) Access Point : {0}.";
    /** **/
    String SUCCESS = "[Consoler] -> Connected Successfully !";
    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Error {
        /** Category **/
        String CATEGORY = "[Connector] -> Meta Category is not H2, Current category is : {0}";
        /** Rmi **/
        String RMI = "[Connector] -> Connect RMI met errors, type = {0}, details = {1}";
        /** Shell **/
        String SHELL = "[Connector] -> SQL Connection met errors, details = {0}.";
        /** **/
        String THREAD = "[Connector] -> Thread met errors, details = {0}";
    }
}
