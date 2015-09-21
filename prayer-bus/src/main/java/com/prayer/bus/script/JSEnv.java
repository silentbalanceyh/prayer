package com.prayer.bus.script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
/**
 * 
 * @author Lang
 *
 */
public class JSEnv implements Serializable{
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -1914878886434781322L;
    // ~ Instance Fields =====================================
    /** Expression 表达式引用 **/
    private transient Expression expr;
    /** Record 记录引用 **/
    private transient Record record;
    /** Values **/
    private transient List<Value<?>> values = new ArrayList<>();
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    /**
     * @return the record
     */
    public Record getRecord() {
        return record;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(final Record record) {
        this.record = record;
    }
    /**
     * @return the expr
     */
    public Expression getExpr() {
        return expr;
    }
    /**
     * @param expr the expr to set
     */
    public void setExpr(final Expression expr) {
        this.expr = expr;
    }
    
    /**
     * @return the values
     */
    public List<Value<?>> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(final List<Value<?>> values) {
        this.values = values;
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "JSEnv [expr=" + expr + ", record=" + record + ", values=" + values + "]";
    }
}
