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
    /** Shared: id **/
    String ID = Constants.PID;
    /** Shared: name **/
    String NAME = "name";
    /** Shared: type **/
    String TYPE = "type";
    /** Shared: namespace **/
    String NAMESPACE = "namespace";
    /** Shared: order **/
    String ORDER = "order";
    /** Shared: refID **/
    String REF_ID = "refID";
    /** Shared: method **/
    String METHOD = "method";

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
    
    String REQUEST_HANDLER = "requestHandler";
    
    String FAILURE_HANDLER = "failureHandler";
    
    // ~ Rule Attr ==========================================
    
    String COMPONENT_TYPE = "componentType";
    
    String COMPONENT_CLASS = "componentClass";
    
    String CONFIG = "config";
    
    String ERROR_MESSAGE = "errorMessage";
    
    // ~ Uri Attr ===========================================
    
    String URI = "uri";
    
    String PARAM_TYPE = "paramType";
    
    String REQUIRED_PARAM = "requiredParam";
    
    String GLOBAL_ID = "globalId";
    
    String ADDRESS = "address";
    
    String SCRIPT = "script";
    
    String RETURN_FILTERS = "returnFilters";
    
    String SENDER = "sender";
    
    
}
