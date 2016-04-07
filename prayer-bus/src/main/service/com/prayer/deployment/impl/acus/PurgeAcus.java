package com.prayer.deployment.impl.acus;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.digraph.OrderedBuilder;
import com.prayer.facade.deployment.acus.DeployAcus;
import com.prayer.facade.fun.deploy.AcusPoster;
import com.prayer.fantasm.exception.AbstractException;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 3.Purge用的组件
 * 
 * @author Lang
 *
 */
@Guarded
public class PurgeAcus implements DeployAcus {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PurgeAcus.class);
    // ~ Instance Fields =====================================

    /** 主要用于Purge **/
    @NotNull
    private transient final OrderedBuilder orderer; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /** **/
    @PostValidateThis
    public PurgeAcus() {
        this.orderer = singleton(OrderedBuilder.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 发布接口
     * 
     * @param filename
     */
    @Override
    public boolean deploy(@NotNull @NotEmpty @NotBlank final String set, final AcusPoster fun)
            throws AbstractException {
        /** 1.构建顺序 **/
        final ConcurrentMap<Integer, String> ordMap = this.orderer.buildPurgeOrder(set);
        /** 2.顺序操作 **/
        final int size = ordMap.size();
        for (int idx = 1; idx <= size; idx++) {
            /** 3.删除的表名 **/
            final String table = ordMap.get(idx);
            /** 4.执行表删除 **/
            fun.execute(table);
        }
        info(LOGGER, "[I] Metadata have been purged from ( Metadata Server & Transaction Database ) Successfully !");
        return true;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
