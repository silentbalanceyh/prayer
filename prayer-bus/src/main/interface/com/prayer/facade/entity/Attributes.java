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
    /** PEScript -> content **/
    String CONTENT = "content";

    // ~ Address Attr =======================================
    /** PEAddress -> workClass **/
    String WORK_CLASS = "workClass";
    /** PEAddress -> consumerAddr **/
    String CONSUMER_ADDR = "consumerAddr";
    /** PEAddress -> consumerHandler **/
    String CONSUMER_HANDLER = "consumerHandler";

    // ~ Verticle Attr ======================================
    /** PEVerticle -> instances **/
    String INSTANCES = "instances";
    /** PEVerticle -> group **/
    String GROUP = "group";
    /** PEVerticle -> jsonConfig **/
    String JSON_CONFIG = "jsonConfig";
    /** PEVerticle -> isolatedClasses **/
    String ISOLATED_CLASSES = "isolatedClasses";
    /** PEVerticle -> extraCp **/
    String EXTRA_CP = "extraCp";
    /** PEVerticle -> ha **/
    String HA = "ha";
    /** PEVerticle -> worker **/
    String WORKER = "worker";
    /** PEVerticle -> multi **/
    String MULTI = "multi";

    // ~ Route Attr =========================================
    /** PERoute -> parent **/
    String PARENT = "parent";
    /** PERoute -> path **/
    String PATH = "path";
    /** PERoute -> consumerMimes **/
    String CONSUMER_MIMES = "consumerMimes";
    /** PERoute -> producerMimes **/
    String PRODUCER_MIMES = "producerMimes";
    
    String METHOD = "method";
    
    String ORDER = "order";
    
    String REQUEST_HANDLER = "requestHandler";
    
    String FAILURE_HANDLER = "failureHandler";
}
