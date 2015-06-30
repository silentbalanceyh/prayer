package com.prayer.meta.schema.json;
/**
 * 
 * @author Lang
 * @see
 */
interface Attributes {  // NOPMD
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
	// ------------------ __meta__  ---------------------
	/** **/
	String M_NAME = "name";
	/** **/
	String M_NAMESPACE = "namespace";
	/** **/
	String M_CATEGORY = "category";
	/** **/
	String M_IDENTIFITER = "identifiter";
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
}
