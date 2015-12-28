package com.prayer.util.cv.log;
/**
 * 
 * @author Lang
 *
 */
public interface InfoKey {// NOPMD
    /** 读取文件路径 **/
    String INF_PU_READ_FILE = "p.read.file";
    /** 从系统中读取元数据文件 H2 **/
    String INF_PM_LKP_META = "p.lookup.metadata";
    /** 从系统中读取元数据文件 Property File **/
    String INF_PH_LKP_VMETA = "p.lookup.virtual.meta";
    /** 返回值信息 **/
    String INF_SEV_RET = "p.service.ret";
    /** 初始化脚本位置 **/
    String INF_META_INIT = "p.init.metadata.file";
    /** Json -> H2 Database **/
    String INF_DP_STEP1 = "p.deploy.step1";
    /** H2 Database -> Json **/
    String INF_DP_STEP2 = "p.deploy.step2";
}
