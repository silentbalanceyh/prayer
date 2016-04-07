package com.prayer.business.digraph;

import com.prayer.facade.util.digraph.NodeData;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 每个节点中的信息，对应图中的Data
 * 
 * @author Lang
 *
 */
@Guarded
@SuppressWarnings("unchecked")
public class OrderedData implements NodeData {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient String key = null;

    private transient String identifier = null;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public OrderedData(@NotNull @NotEmpty @NotBlank final String key) {
        this.key = key;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Override
    public String getKey() {
        return this.key;
    }

    /** **/
    @Override
    public <T> T getData() {
        T ret = null;
        if (null != this.identifier) {
            ret = (T) this.identifier;
        }
        return ret;
    }

    /** **/
    @Override
    public <T> void setData(@NotNull final T data) {
        this.identifier = data.toString();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
