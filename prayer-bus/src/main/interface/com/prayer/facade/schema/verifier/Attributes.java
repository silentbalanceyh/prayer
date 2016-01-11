package com.prayer.facade.schema.verifier;

/**
 * 
 * @author Lang
 * @see
 */
public interface Attributes { // NOPMD
    // ------------------ Root Node ---------------------
    /** **/
    String R_META = "__meta__";
    /** **/
    String R_KEYS = "__keys__";
    /** **/
    String R_FIELDS = "__fields__";
    /** **/
    String R_ORDERS = "__orders__";
    /** **/
    String R_INDEXES = "__indexes__";
    // ------------------ __meta__ ---------------------
    /** **/
    String M_NAME = "name";
    /** **/
    String M_NAMESPACE = "namespace";
    /** **/
    String M_CATEGORY = "category";
    /** **/
    String M_IDENTIFIER = "identifier";
    /** **/
    String M_MAPPING = "mapping";
    /** **/
    String M_POLICY = "policy";
    /** **/
    String M_STATUS = "status";

    /** **/
    String M_TABLE = "table";
    /** **/
    String M_SUB_TABLE = "subtable";
    /** **/
    String M_SUB_KEY = "subkey";
    /** **/
    String M_SEQ_NAME = "seqname";
    /** **/
    String M_SEQ_STEP = "seqstep";
    /** **/
    String M_SEQ_INIT = "seqinit";

    // ------------------ __meta__ Required Regex ----------------
    /** **/
    String RGX_M_NAME = "[A-Z]{1}[A-Za-z0-9]+";
    /** **/
    String RGX_M_NAMESPACE = "[a-z]+(\\.[a-z]+)*";
    /** **/
    String RGX_M_CATEGORY = "(ENTITY|RELATION){1}";
    /** **/
    String RGX_M_IDENTIFITER = "[a-z]{2,4}(\\.[a-z0-9]{2,})+";
    /** **/
    String RGX_M_MAPPING = "(DIRECT|COMBINATED|PARTIAL){1}";
    /** **/
    String RGX_M_POLICY = "(GUID|INCREMENT|ASSIGNED|COLLECTION){1}";
    /** **/
    String RGX_M_STATUS = "(SYSTEM|USER|DISABLED){1}";
    /** **/
    String RGX_M_TABLE = "[A-Z]{2,4}\\_[A-Z\\_0-9]*";
    /** **/
    String RGX_M_SUB_TABLE = RGX_M_TABLE;
    /** **/
    String RGX_M_SEQ_NAME = "SEQ_[A-Z\\_0-9]*";
    /** **/
    String RGX_M_SEQ_INIT = "[0-9]+";
    /** **/
    String RGX_M_SEQ_STEP = RGX_M_SEQ_INIT;

    // -------------------- __fields__ Required ------------------
    /** **/
    String F_NAME = "name";
    /** **/
    String F_COL_NAME = "columnName";
    /** **/
    String F_COL_TYPE = "columnType";
    /** **/
    String F_TYPE = "type";

    // -------------------- __fields__ Required Regex------------------
    /** **/
    String RGX_F_NAME = "[A-Za-z]{1}[A-Za-z0-9]+";
    /** **/
    String RGX_F_TYPE = "(BooleanType|IntType|LongType|DecimalType|DateType|StringType|JsonType|XmlType|ScriptType|BinaryType)";
    /** **/
    String RGX_F_COL_NAME = "[A-Z]{1,3}\\_[A-Z]{1}[A-Z\\_0-9]*";
    /** **/
    String RGX_F_COL_TYPE = "(BOOLEAN|INT|LONG|DECIMAL|DATE|STRING|JSON|XML|SCRIPT|BINARY)[0-9]*";

    // -------------------- __fields__ Optional ------------------
    /** **/
    String F_PATTERN = "pattern";
    /** **/
    String F_VALIDATOR = "validator";
    /** **/
    String F_PK = "primarykey";
    /** **/
    String F_UNIQUE = "unique";
    /** **/
    String F_FK = "foreignkey";
    /** **/
    String F_SUB_TABLE = "subtable";
    /** **/
    String F_REF_TABLE = "refTable";
    /** **/
    String F_REF_ID = "refId";
    /** **/
    String F_NULLABLE = "nullable";
    /** **/
    String F_LENGTH = "length";
    /** **/
    String F_DATETIME = "datetime";
    /** **/
    String F_DATEFORMAT = "dateformat";
    /** **/
    String F_PRECISION = "precision";
    /** **/
    String F_UNIT = "unit";
    /** **/
    String F_MAX_LENGTH = "maxLength";
    /** **/
    String F_MIN_LENGTH = "minLength";
    /** **/
    String F_MAX = "max";
    /** **/
    String F_MIN = "min";
    // -------------------- __fields__ Optional Regex------------------
    /** **/
    String RGX_F_REF_TABLE = RGX_M_TABLE;
    /** **/
    String RGX_F_REF_ID = RGX_F_COL_NAME;
    /** **/
    String RGX_F_VALIDATOR = "[a-z]+(\\.[a-z]+)*(\\.[A-Z]{1}[a-zA-Z0-9]+)";
    /** **/
    String RGX_F_PK = "((true|false){1})|((TRUE|FALSE){1})";
    /** **/
    String RGX_F_UNIQUE = RGX_F_PK;
    /** **/
    String RGX_F_FK = RGX_F_PK;
    /** **/
    String RGX_F_SUB_TABLE = RGX_F_PK;
    /** **/
    String RGX_F_NULLABLE = RGX_F_PK;
    /** **/
    String RGX_F_LENGTH = RGX_M_SEQ_INIT;
    /** **/
    String RGX_F_DATETIME = "(STRING|TIMER){1}";
    /** **/
    String RGX_F_PRECISION = RGX_M_SEQ_INIT;
    /** **/
    String RGX_F_MAX_LENGTH = RGX_M_SEQ_INIT;
    /** **/
    String RGX_F_MIN_LENGTH = RGX_M_SEQ_INIT;

    // ---------------------- __keys__ Required ------------------------
    /** **/
    String K_NAME = "name";
    /** **/
    String K_CATEGORY = "category";
    /** **/
    String K_MULTI = "multi";
    /** **/
    String K_COLUMNS = "columns";
    // ---------------------- __keys__ Required Regex ------------------
    /** **/
    String RGX_K_NAME = "(PK|UK|FK)\\_[A-Z\\_0-9]*";
    /** **/
    String RGX_K_CATEGORY = "PrimaryKey|ForeignKey|UniqueKey";
    /** **/
    String RGX_K_MULTI = RGX_F_PK;
}
