package com.prayer.constant;

/**
 * SQL片断常量文件
 * 
 * @author Lang
 *
 */
public interface SqlSegment { // NOPMD
    // ~ SQL关键字 ==========================================
    /** NOT关键字 **/
    String NOT = "NOT";
    /** NULL关键字 **/
    String NULL = "NULL";
    /** CONSTRAINT关键字 **/
    String CONSTRAINT = "CONSTRAINT";
    /** UNIQUE关键字 **/
    String UNIQUE = "UNIQUE";
    /** PRIMARY关键字 **/
    String PRIMARY = "PRIMARY";
    /** KEY关键字 **/
    String KEY = "KEY";
    /** FOREIGN关键字 **/
    String FOREIGN = "FOREIGN";
    /** REFERENCES关键字 **/
    String REFERENCES = "REFERENCES";
    /** DROP关键字 **/
    String DROP = "DROP";
    /** COLUMN关键字 **/
    String COLUMN = "COLUMN";
    /** ADD关键字 **/
    String ADD = "ADD";
    /** WHERE关键字 **/
    String WHERE = "WHERE";
    /** ALTER关键字 **/
    String ALTER = "ALTER";
    /** Order By **/
    String ORDER_BY = "ORDER BY";

    // ~ SQL语句模板 ========================================
    /** 创建表的语句模板 **/
    String TB_CREATE = "CREATE TABLE {0} ( {1} )";
    /** 删除表的语句模板 **/
    String TB_DROP = "DROP TABLE {0}";
    /** 修改表的语句模板 **/
    String TB_ALTER = "ALTER TABLE {0} {1}";
    /** 统计表中有多少行数据 **/
    String TB_COUNT = "SELECT COUNT(*) FROM {0}";
    
    /** 插入语句的SQL模板 **/
    String TB_INSERT = "INSERT INTO {0} ({1}) VALUES ({2})";
    /** 查询语句的SQL模板 **/
    String TB_SELECT = "SELECT {0} FROM {1}";
    /** 删除数据的SQL模板 **/
    String TB_DELETE = "DELETE FROM {0}";
    /** 更新数据的SQL模板 **/
    String TB_UPDATE = "UPDATE {0} SET {1}";
    /** WHERE子句模板 **/
    String TB_WHERE = "WHERE {0}";

    // ~ SQL连接关键字 =====================================
    /** AND连接符 **/
    String AND = "AND";
    /** OR连接符 **/
    String OR = "OR"; // NOPMD
    /** AS关键字 **/
    String AS = "AS"; // NOPMD
    /** IS关键字 **/
    String IS = "IS"; // NOPMD
    /** IN关键字 **/
    String IN = "IN"; // NOPMD
    /** =符号 **/
    String EQUAL = "="; // NOPMD
    /** <符号 **/
    String LESS_THAN = "<"; // NOPMD
    /** >符号 **/
    String GREATER_THAN = ">"; // NOPMD
    /** <>符号 **/
    String NOT_EQUAL = LESS_THAN + GREATER_THAN; // NOPMD
    /** <=符号 **/
    String LESS_EQ_THAN = LESS_THAN + EQUAL; // NOPMD
    /** >=符号 **/
    String GREATER_EQ_THAN = GREATER_THAN + EQUAL; // NOPMD
    /** LIKE关键字 **/
    String LIKE = "LIKE";

    // ~ SQL基本操作关键字 ====================================
}
