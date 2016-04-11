package com.prayer.business.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.record.Record;
import com.prayer.model.business.OrderBy;
import com.prayer.model.business.Pager;
import com.prayer.model.business.Projection;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class InquiryMarchal implements Serializable {
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
    /** Projection 相关信息 **/
    private transient Projection projection = null;
    /** Values **/
    private transient List<Value<?>> values = new ArrayList<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================\
    /**
     * 只能使用Record执行初始化
     * @param record
     */
    public InquiryMarchal(@NotNull final Record record){
        this.record = record;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    /**
     * @return the record
     */
    public Record getRecord() {
        return this.record;
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
     * 设置返回列
     * 
     * @param projection
     */
    public void setProjection(final Projection projection) {
        this.projection = projection;
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
     * 返回Projection中的列信息
     * 
     * @return
     */
    public String[] getFilters() {
        String[] ret;
        if (null == projection) {
            ret = new String[] {};
        } else {
            ret = projection.getFilters();
        }
        return ret;
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
