package com.prayer.facade.engine.cv.msg;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 通用信息
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
public interface MsgCommon {
    /** **/
    String META_RUN = "( {0} ) : Meta Server has already be started. Status is : RUNNING.";
    /** **/
    String META_PURGE_RET = "( {0} ) : Meta data has been purged successfully, result = {1}.";
}
