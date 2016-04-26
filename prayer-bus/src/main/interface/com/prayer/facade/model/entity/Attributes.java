package com.prayer.facade.model.entity;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.constant.Constants;

/**
 * Entity的属性表
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
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
    /** Shared: refMID **/
    String REF_MID = "refMID";
    /** Shared: refId **/
    String REF_ID = "refId";
    /** Shared: method **/
    String METHOD = "method";
    /** Shared: category **/
    String CATEGORY = "category";
    /** Shared -> multi **/
    String MULTI = "multi";
    /** Shared -> subtable **/
    String SUB_TABLE = "subtable";
    /** Shared -> columns **/
    String COLUMNS = "columns";

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

    // ~ Route Attr =========================================
    /** PERoute -> parent **/
    String PARENT = "parent";
    /** PERoute -> path **/
    String PATH = "path";
    /** PERoute -> consumerMimes **/
    String CONSUMER_MIMES = "consumerMimes";
    /** PERoute -> producerMimes **/
    String PRODUCER_MIMES = "producerMimes";
    /** PERoute -> requestHandler **/
    String REQUEST_HANDLER = "requestHandler";
    /** PERoute -> failureHandler **/
    String FAILURE_HANDLER = "failureHandler";

    // ~ Rule Attr ==========================================
    /** PERute -> componentType **/
    String COMPONENT_TYPE = "componentType";
    /** PERute -> componentClass **/
    String COMPONENT_CLASS = "componentClass";
    /** PERute -> config **/
    String CONFIG = "config";
    /** PERute -> errorMessage **/
    String ERROR_MESSAGE = "errorMessage";
    /** PERute -> refUID **/
    String REF_UID = "refUID";
    // ~ Uri Attr ===========================================

    String URI = "uri";

    String PARAM_TYPE = "paramType";

    String REQUIRED_PARAM = "requiredParam";
    
    String CONTENT_MIMES = "contentMimes";
    
    String ACCEPT_MIMES = "acceptMimes";

    String GLOBAL_ID = "identifier";

    String ADDRESS = "address";

    String SCRIPT = "script";

    String RETURN_FILTERS = "returnFilters";

    String SENDER = "sender";

    // ~ Meta Attr ==========================================

    String STATUS = "status";

    String IDENTIFIER = GLOBAL_ID;

    String MAPPING = "mapping";

    String POLICY = "policy";

    String TABLE = "table";

    String SUB_KEY = "subkey";

    String SEQ_NAME = "seqname";

    String SEQ_STEP = "seqstep";

    String SEQ_INIT = "seqinit";

    // ~ Key Attr ===========================================
    // ~ Index Attr =========================================
    
    // ~ Field Attr =========================================

    String PATTERN = "pattern";

    String VALIDATOR = "validator";

    String LENGTH = "length";

    String DATE_TIME = "datetime";

    String DATE_FORMAT = "dateFormat";

    String PRECISION = "precision";

    String UNIT = "unit";

    String MAX_LENGTH = "maxLength";

    String MIN_LENGTH = "minLength";

    String MAX = "max";

    String MIN = "min";

    String PRIMARY_KEY = "primarykey";

    String UNIQUE = "unique";

    String FOREIGN_KEY = "foreignkey";

    String NULLABLE = "nullable";

    String COLUMN_NAME = "columnName";

    String COLUMN_TYPE = "columnType";

    String REF_TABLE = "refTable";

    // ~ Trigger Attr ======================================
    
    String TARGET = "target";
    
    String OP_TYPE = "opType";
    
    String MODE = "mode";
    
    String STATEMENT = "statement";
    
    // ~ View Attr =========================================
    
    String VIEW_NAME = "view";
    
    String CHECK_OPTION = "check";
    
    // ~ Column Attr =======================================
    
    String REF_VID = "refVID";
}
