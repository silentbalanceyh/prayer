package com.prayer.meta.schema.json;

/**
 * 
 * @author Lang
 * @see
 */
interface Attributes { // NOPMD
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
	String RGX_M_IDENTIFITER = "[a-z]{2,4}(\\.[a-z]{2,})+";
	/** **/
	String RGX_M_MAPPING = "(DIRECT|COMBINATED|PARTIAL){1}";
	/** **/
	String RGX_M_POLICY = "(GUID|INCREMENT|ASSIGNED|COLLECTION){1}";
	/** **/
	String RGX_M_TABLE = "[A-Z]{2,4}\\_[A-Z]+";
	/** **/
	String RGX_M_SUB_TABLE = RGX_M_TABLE;
	/** **/
	String RGX_M_SEQ_NAME = "REQ_[A-Z]+";
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
	
	// -------------------- __fields__ Required Regex------------------
	/** **/
	String RGX_F_NAME = "[A-Za-z]{1}[A-Za-z0-9]+";
	/** **/
	String RGX_F_COL_NAME = "[A-Z]{1,3}\\_[A-Z]+";
	/** **/
	String RGX_F_COL_TYPE = "(BOOLEAN|INT|LONG|DECIMAL|DATE|STRING|JSON|XML|SCRIPT|BINARY)[0-9]*";
	
	// -------------------- __fields__ Optional ------------------
	/** **/
	String F_TYPE = "type";
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
	
}
