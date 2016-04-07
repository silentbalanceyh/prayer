package com.prayer.business.impl.deployment;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import com.prayer.business.digraph.SchemaGraphicer;
import com.prayer.exception.system.RecurrenceReferenceException;
import com.prayer.fantasm.business.AbstractDeployer;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.digraph.Graphic;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 负责Deploy的类
 * 
 * @author Lang
 *
 */
@Guarded
final class MetadataDeployer extends AbstractDeployer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Order的图结构 **/
    private transient final SchemaGraphicer executor = singleton(SchemaGraphicer.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 构造OrdMap
     * 
     * @param folder
     * @return
     * @throws RecurrenceReferenceException
     */
    public ConcurrentMap<Integer, String> buildOrdMap(@NotNull @NotEmpty @NotBlank final String folder)
            throws AbstractException {
        /** 1.使用Schema文件构建图 **/
        final Graphic graphic = executor.build(folder);
        /** 2.检查连通性 **/
        this.checkSCC(graphic);

        return this.buildIdOrd(graphic);
    }
    // ~ Private Methods =====================================

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
