package com.prayer.database.accessor.impl;

import static com.prayer.util.reflection.Instance.reservoir;

import java.io.Serializable;
import java.util.List;

import com.prayer.constant.Resources;
import com.prayer.facade.database.accessor.MetaAccessor;
import com.prayer.facade.model.entity.Entity;
import com.prayer.fantasm.exception.AbstractTransactionException;

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
public class MetaAccessorImpl implements MetaAccessor {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 用于访问底层元数据的Accessor
     */
    private transient final MetaAccessor accessor;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 带一个参数的构造函数
     * 
     * @param entityCls
     *            实体的真实类型，该类型会影响底层获取Mapper
     */
    public MetaAccessorImpl(@NotNull final Class<?> entityCls) {
        /**
         * 池化操作，每一个实现必须包含一个值对象，这个对象存储了操作实体的类型
         * 即传入的entityCls表示其类型，使用池化的目的是防止单件的非初始化操作，保证Accessor操作的实体是准确无误的，最终目的：
         * 每一个类型的元数据对象仅拥有一个Accessor
         */
        // PEAddress -> IBatisAccessorImpl ( PEAddress.class )
        // PERule -> IBatisAccessorImpl ( PERule.class )
        // PEScript -> IBatisAccessorImpl ( PEScript.class )
        // ......
        this.accessor = reservoir(entityCls.getName(), Resources.META_ACCESSOR, entityCls);
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
    @InstanceOf(Entity.class)
    public Entity insert(@NotNull final Entity entity) throws AbstractTransactionException {
        return this.getAccessor().insert(entity);
    }

    /**
     * 
     * @param entity
     * @return
     * @throws AbstractTransactionException
     */
    @Override
    @InstanceOf(List.class)
    public List<Entity> insert(@NotNull @MinSize(1) final Entity... entity) throws AbstractTransactionException {
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
     * 
     * @param uniqueId
     * @return
     * @throws AbstractTransactionException
     */
    @Override
    public boolean deleteById(@NotNull @NotBlank @NotEmpty final Serializable uniqueId)
            throws AbstractTransactionException {
        return this.getAccessor().deleteById(uniqueId);
    }

    /**
     * 
     * @param uniqueId
     * @return
     * @throws AbstractTransactionException
     */
    @Override
    public boolean deleteById(@NotNull @MinSize(1) final Serializable... uniqueId) throws AbstractTransactionException {
        return this.getAccessor().deleteById(uniqueId);
    }

    /**
     * 
     * @param uniqueId
     * @return
     */
    @Override
    @InstanceOf(Entity.class)
    public Entity getById(@NotNull @NotBlank @NotEmpty final Serializable uniqueId) {
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
     * @param whereClause
     * @return
     */
    @Override
    @InstanceOf(List.class)
    public List<Entity> queryList(@NotNull @NotBlank @NotEmpty final String whereClause) {
        return this.getAccessor().queryList(whereClause);
    }

    /**
     * 
     * @param whereClause
     * @return
     */
    @Override
    public boolean deleteList(@NotNull @NotBlank @NotEmpty final String whereClause)
            throws AbstractTransactionException {
        return this.getAccessor().deleteList(whereClause);
    }

    /**
     * 
     * @return
     */
    @Override
    public long count() {
        return this.getAccessor().count();
    }

    /**
     * 
     * @param content
     * @return
     * @throws AbstractTransactionException
     */
    @Override
    public boolean initialize(@NotNull @NotBlank @NotEmpty final String file) throws AbstractTransactionException {
        return this.getAccessor().initialize(file);
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
    private MetaAccessor getAccessor() {
        return this.accessor;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
