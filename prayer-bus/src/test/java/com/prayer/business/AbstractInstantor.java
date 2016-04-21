package com.prayer.business;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.singleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.prayer.business.configuration.impl.ConfigBllor;
import com.prayer.business.deployment.impl.DeployBllor;
import com.prayer.database.accessor.MetaAccessorImpl;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.business.instantor.deployment.DeployInstantor;
import com.prayer.facade.database.accessor.MetaAccessor;
import com.prayer.facade.model.entity.Entity;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.io.IOKit;
import com.prayer.util.reflection.Instance;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractInstantor extends AbstractBusiness {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String getFolder() {
        return "business/instantor/";
    }

    // ~ Methods =============================================
    /**
     * Direct：Instantor - Bllor
     * 
     * @return
     */
    protected DeployInstantor getDeployItor() {
        return singleton(DeployBllor.class);
    }

    /**
     * Direct：Instantor - Bllor
     * 
     * @return
     */
    protected ConfigInstantor getConfigItor() {
        return singleton(ConfigBllor.class);
    }
    
    /** 读取Accessor **/
    protected MetaAccessor accessor(final Class<?> genericT) {
        return new MetaAccessorImpl(genericT);
    }
    /**
     * 读取JsonObject
     * 
     * @param file
     * @param entityT
     * @return
     */
    protected Entity readData(final String file, final Class<?> entityT) {
        final String content = IOKit.getContent(path(file));
        Entity entity = null;
        try {
            final JsonObject item = new JsonObject(content);
            entity = Instance.instance(entityT, item);
        } catch (DecodeException ex) {
            jvmError(getLogger(), ex);
        }
        return entity;
    }

    /**
     * 读取JsonObject集合的方法
     * 
     * @param file
     * @param entityT
     * @return
     */
    protected List<Entity> readListData(final String file, final Class<?> entityT) {
        final String content = IOKit.getContent(path(file));
        final List<Entity> retList = new ArrayList<>();
        try {
            final JsonArray arr = new JsonArray(content);
            for (final Object item : arr) {
                if (null != item && JsonObject.class == item.getClass()) {
                    retList.add(Instance.instance(entityT, (JsonObject) item));
                }
            }
        } catch (DecodeException ex) {
            jvmError(getLogger(), ex);
        }
        return retList;
    }

    /**
     * 准备List数据
     */
    protected List<Entity> preparedListData(final String file, final Class<?> entityT) {
        final List<Entity> entities = this.readListData(file, entityT);
        try {
            this.accessor(entityT).purge();
            for (final Entity entity : entities) {
                this.accessor(entityT).insert(entity);
            }
        } catch (AbstractException ex) {
            jvmError(getLogger(), ex);
        }
        return entities;
    }

    /**
     * 删除List数据
     * 
     * @param entities
     * @param entityT
     * @return
     */
    protected boolean purgeListData(final List<Entity> entities, final Class<?> entityT) {
        boolean ret = false;
        try {
            final List<Serializable> ids = new ArrayList<>();
            for (final Entity entity : entities) {
                ids.add(entity.id());
                // this.accessor(entityT).deleteById(entity.id());
            }
            this.accessor(entityT).deleteById(ids.toArray(new Serializable[] {}));
            ret = true;
        } catch (AbstractException ex) {
            jvmError(getLogger(), ex);
            ret = false;
        }
        return ret;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
