package com.prayer.business.impl.ordered;

import com.prayer.facade.util.graphic.NodeData;

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
public class OrderedData implements NodeData {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient String key = null;
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
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
