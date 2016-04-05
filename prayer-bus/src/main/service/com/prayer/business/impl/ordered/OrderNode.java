package com.prayer.business.impl.ordered;

import java.util.Map;
import java.util.Set;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 每一个节点的信息
 * 
 * @author Lang
 *
 */
@Guarded
public class OrderNode {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 当前Model的全局ID **/
    private transient final String identifier;
    /** 当前Model的表名 **/
    private transient final String table;
    /** 当前Model的Order **/
    private transient Integer order;
    /** 当前节点中保存的引用节点：即当前节点中使用了FK定义，记得去掉引用本表 **/
    // Table -> Reference
    private transient final Map<String, OrderNode> references;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 构造时直接使用文件 **/
    public OrderNode(@NotNull final JsonObject data) {
        this.table = SimpleExtractor.getTable(data);
        this.identifier = SimpleExtractor.getIdentifier(data);
        this.references = SimpleExtractor.getRefs(data);
        // 移除自身引用
        if(this.references.containsKey(this.table)){
            this.references.remove(this.table);
        }
        this.order = this.references.size();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @return
     */
    public boolean isEmpty(){
    	return this.references.isEmpty();
    }
    /**
     * 
     * @return
     */
    public boolean isValid() {
        return null != this.table && null != this.identifier;
    }
    /**
     * 
     * @param node
     */
    public void addReference(OrderNode node){
    	this.references.put(node.getTable(), node);
    	this.order = this.references.size();
    }
    /**
     * 
     * @return
     */
    public Set<String> refTables(){
    	return this.references.keySet();
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @return the table
     */
    public String getTable() {
        return table;
    }

    /**
     * 
     * @return
     */
    public Integer getOrder() {
        return this.order;
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Order = ").append(this.order).append(", ID = ").append(this.identifier).append(", Table = ")
                .append(this.table);
        builder.append(": References [");
        for (final String key : this.references.keySet()) {
            builder.append(key).append(',');
        }
        if (!this.references.isEmpty()) {
            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append(']');
        return builder.toString();
    }

}
