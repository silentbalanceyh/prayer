package com.prayer.business.impl.deployment;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.prayer.business.digraph.DatabaseGraphicer;
import com.prayer.fantasm.business.AbstractPreProcessor;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.digraph.Graphic;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 负责Purge的类
 * 
 * @author Lang
 *
 */
@Guarded
public class MetadataPurger extends AbstractPreProcessor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Order的图结构 **/
    private transient final DatabaseGraphicer executor = singleton(DatabaseGraphicer.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @param folder
     * @return
     */
    public ConcurrentMap<Integer, String> buildOrdMap(@NotNull final Set<String> tables) throws AbstractException {
        /** 1.构建图信息 **/
        final Graphic graphic = this.executor.build(tables);
        
        return this.buildIdOrd(graphic);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
