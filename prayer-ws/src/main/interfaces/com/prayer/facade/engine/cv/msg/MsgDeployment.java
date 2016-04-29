package com.prayer.facade.engine.cv.msg;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 数据发布过程的消息
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
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
