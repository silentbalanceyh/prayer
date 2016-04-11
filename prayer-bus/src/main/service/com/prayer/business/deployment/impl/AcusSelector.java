package com.prayer.business.deployment.impl;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.business.deployment.acus.SchemaAcus;
import com.prayer.business.deployment.acus.ScriptAcus;
import com.prayer.business.deployment.acus.SqlAcus;
import com.prayer.business.deployment.acus.UriAcus;
import com.prayer.business.deployment.acus.VertxAcus;
import com.prayer.constant.SystemEnum.Acus;
import com.prayer.facade.business.deployment.acus.DeployAcus;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 用于选择Acus的选择器
 * 
 * @author Lang
 *
 */
@Guarded
final class AcusSelector {
    // ~ Static Fields =======================================
    /** 读取Selectors **/
    private static final ConcurrentMap<Acus, DeployAcus> SELECTORS = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    static {
        /** 数据库发布器 **/
        SELECTORS.put(Acus.SQL, singleton(SqlAcus.class));
        /** Schema执行器 **/
        SELECTORS.put(Acus.SCHEMA, singleton(SchemaAcus.class));
        /** Vertx执行器 **/
        SELECTORS.put(Acus.VERTX, singleton(VertxAcus.class));
        /** Script执行器 **/
        SELECTORS.put(Acus.SCRIPT, singleton(ScriptAcus.class));
        /** Uri执行器 **/
        SELECTORS.put(Acus.URI,singleton(UriAcus.class));
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 读取对应的Acus
     * 
     * @param acus
     * @return
     */
    public DeployAcus selectors(@NotNull final Acus acus) {
        return SELECTORS.get(acus);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
