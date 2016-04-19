package com.prayer.metaserver.h2;

import com.prayer.facade.resource.Inceptor;
import com.prayer.resource.inceptor.DynamicInceptor;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class H2Node {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 引用注入，只能注入Dynamic的Inceptor **/
    @NotNull
    private transient Inceptor inceptor;
    /** 当前节点名称 **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient String name;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 开启模式 **/
    @PostValidateThis
    public H2Node(@NotNull @NotBlank @NotEmpty final String name,
            @NotNull @InstanceOfAny(DynamicInceptor.class) final Inceptor inceptor) {

    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
