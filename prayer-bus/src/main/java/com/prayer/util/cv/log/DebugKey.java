package com.prayer.util.cv.log;
/**
 * 
 * @author Lang
 *
 */
public interface DebugKey {
    /** Exception：JVM异常信息 **/
    String EXP_JVM = "exception.jvm";
    /** Exception: OVal异常信息 **/
    String EXP_OVAL = "exception.oval";
    
    /** Info: JDBC Parameters **/
    String INFO_JDBC_PARAM = "info.jdbc.in.params";
    /** **/
    String INFO_JDBC_AROWS = "info.jdbc.affected.rows";
    /** **/
    String INFO_R_EXTRACT = "info.record.extract";
    /** **/
    String INFO_RV_POLICY = "info.record.v.policy";
    /** **/
    String INFO_SMA_SYNC = "info.schema.sync";
    /** **/
    String INFO_H2_ID = "info.h2.id.invalid";
    /** SQL语句信息 **/
    String INFO_SQL_STMT = "info.sql.stmt";
    /** 参数信息 **/
    String INFO_SEV_PARAM = "info.service.param";
    /** 输入输出错误 **/
    String INFO_IO_ERROR = "info.read.file.err";
    
    /** **/
    String WEB_HANDLER = "web.handler";
    /** **/
    String WEB_STG_HANDLER = "web.std.handler";
    /** **/
    String WEB_UCA = "web.uca";
}
