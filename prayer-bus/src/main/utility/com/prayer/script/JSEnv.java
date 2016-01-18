package com.prayer.script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.prayer.constant.Constants;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.kernel.Value;
import com.prayer.model.bus.OrderBy;
import com.prayer.model.bus.Pager;
import com.prayer.model.crucial.GenericRecord;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class JSEnv implements Serializable {
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
    /** Order 表达式引用 **/
    private transient OrderBy order = new OrderBy();
    /** Pager 相关信息 **/
    private transient Pager pager = new Pager();
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
    public Record getRecord(final String... ids) {
        Record refR = null;
        if (Constants.ZERO == ids.length) {
            refR = this.record;
        } else if (Constants.ZERO < ids.length) {
            refR = new GenericRecord(ids[Constants.ZERO]);
        }
        return refR;
    }

    /**
     * 
     * @return
     */
    public Pager getPager() {
        return pager;
    }

    /**
     * 
     * @param pager
     */
    public void setPager(@InstanceOfAny(Pager.class) final Pager pager) {
        this.pager = pager;
    }

    /**
     * 
     * @param order
     */
    public void setOrder(@InstanceOfAny(OrderBy.class) final OrderBy order) {
        this.order = order;
    }

    /**
     * 
     * @return
     */
    public OrderBy getOrder() {
        return this.order;
    }

    /**
     * @param record
     *            the record to set
     */
    public void setRecord(@InstanceOf(Record.class) final Record record) {
        this.record = record;
    }

    /**
     * @return the expr
     */
    public Expression getExpr() {
        return expr;
    }

    /**
     * @param expr
     *            the expr to set
     */
    public void setExpr(@InstanceOf(Expression.class) final Expression expr) {
        this.expr = expr;
    }

    /**
     * @return the values
     */
    public List<Value<?>> getValues() {
        return values;
    }

    /**
     * @param values
     *            the values to set
     */
    public void setValues(final List<Value<?>> values) {
        this.values = values;
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "JSEnv [expr=" + expr + ", record=" + record + ", order=" + order + ", pager=" + pager + ", values="
                + values + "]";
    }
}
