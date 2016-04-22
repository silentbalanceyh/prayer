package com.prayer.facade.engine.cv;
/**
 * 数据发布过程的消息
 * @author Lang
 *
 */
public interface MsgDeployment extends MsgCommon {
    /** **/
    String INIT_FILE = "( {0} ) Start to initialize Meta Server... ( filename = {1} )";
    /** **/
    String INIT_FILED = "( {0} ) Meta Server has been initialized successfully. MetaServer = {1}.";
    /** **/
    String INIT_META = "( {0} ) Start to deploy meta data into Meta Server... ( folder = {1} )";
    /** **/
    String INIT_METAED = "( {0} ) Meta data has been deployed into Meta Server successfully. MetaServer = {1}.";
}
