package com.prayer.facade.entity;

import com.prayer.constant.Constants;

/**
 * Entity的属性表
 * 
 * @author Lang
 *
 */
public interface Attributes {
    // ~ Shared Attr ========================================
    /** 共享属性id **/// PEScript,
    String ID = Constants.PID;
    /** 共享属性name **/// PEScript
    String NAME = "name";
    /** 共享属性namespace **/ // PEScript
    String NAMESPACE = "namespace";

    // ~ Script Attr ========================================
    /** PEScript专用属性content **/
    String CONTENT = "content";

    // ~ Address Attr =======================================
    /** PEAddress专用属性workClass **/
    String WORK_CLASS = "workClass";
    /** PEAddress专用属性consumerAddr **/
    String CONSUMER_ADDR = "consumerAddr";
    /** PEAddress专用属性consumerHandler **/
    String CONSUMER_HANDLER = "consumerHandler";
}
