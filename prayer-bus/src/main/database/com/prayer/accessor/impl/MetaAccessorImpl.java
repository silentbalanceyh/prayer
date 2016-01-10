package com.prayer.accessor.impl;

import static com.prayer.util.reflection.Instance.singleton;

import java.io.Serializable;
import java.util.List;

import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.constant.Resources;
import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.facade.entity.Entity;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 作为Bridge存在，用于切换不同的底层访问机制，默认的所有元数据的主键规范：192长度GUID格式
 * 
 * @author Lang
 *
 */
@Guarded
public class MetaAccessorImpl implements MetaAccessor<Entity, String> {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 用于访问底层元数据的Accessor
     */
    private transient final MetaAccessor<Entity, Serializable> accessor;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 默认无参构造函数
     */
    public MetaAccessorImpl() {
        this.accessor = singleton(Resources.META_ACCESSOR);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @param entity
     * @return
     * @throws AbstractTransactionException
     */
    @Override
    @InstanceOf(List.class)
    public List<Entity> insert(@MinSize(1) final Entity... entity) throws AbstractTransactionException {
        return this.getAccessor().insert(entity);
    }

    /**
     * 
     * @param entity
     * @return
     * @throws AbstractTransactionException
     */
    @Override
    @InstanceOf(Entity.class)
    public Entity update(@NotNull @InstanceOf(Entity.class) final Entity entity) throws AbstractTransactionException {
        return this.getAccessor().update(entity);
    }

    /**
     * 提供ID更新
     * 
     * @param uniqueId
     * @return
     * @throws AbstractTransactionException
     */
    @Override
    public boolean deleteById(@NotNull @NotBlank @NotEmpty final String uniqueId) throws AbstractTransactionException {
        return this.getAccessor().deleteById(uniqueId);
    }

    /**
     * 
     * @param uniqueId
     * @return
     */
    @Override
    @InstanceOf(Entity.class)
    public Entity getById(@NotNull @NotBlank @NotEmpty final String uniqueId) {
        return this.getAccessor().getById(uniqueId);
    }

    /**
     * 
     * @return
     */
    @Override
    @InstanceOf(List.class)
    public List<Entity> getAll() {
        return this.getAccessor().getAll();
    }

    /**
     * 
     * @param index
     * @param size
     * @param orderBy
     * @return
     */
    @Override
    @InstanceOf(List.class)
    public List<Entity> getByPage(@Min(1) final int index, @Min(5) final int size,
            @NotNull @NotBlank @NotEmpty final String orderBy) {
        return this.getAccessor().getByPage(index, size, orderBy);
    }

    /**
     * 
     */
    @Override
    @InstanceOf(List.class)
    public List<Entity> queryList(@NotNull @NotBlank @NotEmpty final String whereClause) {
        return this.getAccessor().queryList(whereClause);
    }

    /**
     * 
     */
    @Override
    public boolean purge() throws AbstractTransactionException {
        return this.getAccessor().purge();
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private MetaAccessor<Entity, Serializable> getAccessor() {
        return this.accessor;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
