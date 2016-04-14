package com.prayer.business.configuration.fetch;

import static com.prayer.util.debug.Log.peError;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.configuration.impl.AndEqer;
import com.prayer.database.accessor.impl.MetaAccessorImpl;
import com.prayer.facade.business.instantor.configuration.fetch.ConfigFetcher;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.database.accessor.MetaAccessor;
import com.prayer.facade.model.entity.Entity;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
@SuppressWarnings("unchecked")
public class ConfigMounder implements ConfigFetcher {

    // ~ Static Fields =======================================
    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigMounder.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final Class<?> entityCls;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param entityCls
     */
    public ConfigMounder(@NotNull final Class<?> entityCls) {
        this.entityCls = entityCls;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public <T extends Entity> T inquiry(final AndEqer whereClause) throws AbstractException {
        List<Entity> ret = new ArrayList<>();
        try {
            ret = this.accessor(this.entityCls).queryList(whereClause.build());
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
            throw ex;
        }
        return ret.isEmpty() ? null : (T) ret.get(Constants.IDX);
    }

    /** **/
    @Override
    public <T extends Entity> List<T> inquiryList(final AndEqer whereClause) throws AbstractException {
        List<Entity> ret = new ArrayList<>();
        try {
            if (null == whereClause) {
                ret = this.accessor(this.entityCls).getAll();
            } else {
                ret = this.accessor(this.entityCls).queryList(whereClause.build());
            }
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
            throw ex;
        }
        return (List<T>) ret;
    }

    /** **/
    @Override
    public <T extends Entity> List<T> inquiryList() throws AbstractException {
        return this.inquiryList(null);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /** 获取对应实体的Accessor **/
    private MetaAccessor accessor(final Class<?> genericT) {
        return new MetaAccessorImpl(genericT);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
