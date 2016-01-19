package com.prayer.model.crucial.schema;

/**
 * 外键引用处理，针对Constraint的删除操作
 * 
 * @author Lang
 *
 */
public class FKReferencer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 当前约束的名称CONSTRAINT_NAME
     */
    private transient String name;
    /**
     * 当前表被引用的表名
     */
    private transient String fromTable;
    /**
     * 当前表被引用的字段名
     */
    private transient String fromColumn;
    /**
     * 当前表
     */
    private transient String toTable;
    /**
     * 当前字段
     */
    private transient String toColumn;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the toTable
     */
    public String getToTable() {
        return toTable;
    }

    /**
     * @param toTable
     *            the toTable to set
     */
    public void setToTable(final String toTable) {
        this.toTable = toTable;
    }
    /**
     * @return the fromTable
     */
    public String getFromTable() {
        return fromTable;
    }

    /**
     * @param fromTable
     *            the fromTable to set
     */
    public void setFromTable(final String fromTable) {
        this.fromTable = fromTable;
    }

    /**
     * @return the fromColumn
     */
    public String getFromColumn() {
        return fromColumn;
    }

    /**
     * @param fromColumn the fromColumn to set
     */
    public void setFromColumn(final String fromColumn) {
        this.fromColumn = fromColumn;
    }

    /**
     * @return the toColumn
     */
    public String getToColumn() {
        return toColumn;
    }

    /**
     * @param toColumn the toColumn to set
     */
    public void setToColumn(final String toColumn) {
        this.toColumn = toColumn;
    }


    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
