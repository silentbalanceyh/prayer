package com.prayer.database.accessor.impl;

import static com.prayer.util.Generator.uuid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.facade.entity.Entity;
import com.prayer.facade.fun.accessor.IBatisMixer;
import com.prayer.facade.metadata.mapper.IBatisMapper;
import com.prayer.fantasm.exception.AbstractTransactionException;

import net.sf.oval.constraint.NotNull;

/**
 * 一个Dao的标准实现模型，包含了模板方法
 * 
 * @author Lang
 *
 */
public class IBatisAccessorImpl implements MetaAccessor { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final ConcurrentMap<String, IBatisMixer> OFFSET_FUN = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    /** VO -> Value Object用于维护实体的类型信息 **/
    @NotNull
    private transient final Class<?> entityCls;
    /** Helper **/
    @NotNull
    private transient final IBatisHelper helper;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 单参数的构造函数，这个操作必须
     * 
     * @param entityCls
     */
    public IBatisAccessorImpl(final Class<?> entityCls) {
        this.entityCls = entityCls;
        this.helper = IBatisHelper.create();
        if (OFFSET_FUN.isEmpty()) {
            // TODO: 【完成】暂时是H2的，计算Offset，可使用函数引用进行分离
            OFFSET_FUN.put(Resources.META_CATEGORY, IBatisPagerMixer::offset);
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public Entity insert(final Entity entity) throws AbstractTransactionException {
        final List<Entity> retList = this.insert(new Entity[] { entity });
        Entity ret = null;
        if (!retList.isEmpty()) {
            ret = retList.get(Constants.IDX);
        }
        return ret;
    }

    /**
     * 支持插入、批量插入两种类型
     */
    @Override
    public List<Entity> insert(final Entity... entities) throws AbstractTransactionException {
        // 1.开启Transaction
        final IBatisMapper<Entity, Serializable> mapper = this.helper.beginTransaction(this.entityCls);
        // 2.设置返回的List
        final List<Entity> retList = new ArrayList<>();
        // 3.插入操作，考虑主键的UUID设置
        {
            /**
             * 直接过滤掉null的实体，不处理这种类型
             */
            if (Constants.ONE == entities.length) {
                final Entity entity = entities[Constants.IDX];
                if (null != entity) {
                    // 4.1.1 -> ID: By Reference
                    if (null == entity.id()) {
                        entity.id(uuid());
                    }
                    // 4.1.2 -> 插入
                    mapper.insert(entity);
                    retList.add(entity);
                }
            } else {
                // 4.2.1 -> ID：By Reference
                for (final Entity item : entities) {
                    if (null != item) {
                        if (null == item.id()) {
                            item.id(uuid());
                        }
                    }
                }
                // 4.2.2 -> 批量插入
                final List<Entity> params = Arrays.asList(entities);
                mapper.batchInsert(params);
                retList.addAll(params);
            }
        }
        // 5.事务提交，必须带此语句
        this.helper.endTransaction();
        return retList;
    }

    // Helper中的事务处理流程
    // 1.this.helper.beginTransaction(this.entityCls)
    // 2.mapper.<operation> on Entity
    // 3.this.helper.submit()
    /**
     * 更新实体的方法
     */
    @Override
    public Entity update(final Entity entity) throws AbstractTransactionException {
        final IBatisMapper<Entity, Serializable> mapper = this.helper.beginTransaction(this.entityCls);
        mapper.update(entity);
        this.helper.endTransaction();
        return entity;
    }

    /**
     * 根据ID删除某个实体的方法
     */
    @Override
    public boolean deleteById(final Serializable uniqueId) throws AbstractTransactionException {
        final IBatisMapper<Entity, Serializable> mapper = this.helper.beginTransaction(this.entityCls);
        mapper.deleteById(uniqueId);
        this.helper.endTransaction();
        return true;
    }

    /**
     * 批量删除
     */
    @Override
    public boolean deleteById(final Serializable... uniqueId) throws AbstractTransactionException {
        final IBatisMapper<Entity, Serializable> mapper = this.helper.beginTransaction(this.entityCls);
        mapper.batchDelete(Arrays.asList(uniqueId));
        this.helper.endTransaction();
        return true;
    }

    /**
     * 根据ID获取Entity
     */
    @Override
    public Entity getById(final Serializable uniqueId) {
        final IBatisMapper<Entity, Serializable> mapper = this.helper.beginQuery(this.entityCls);
        final Entity entity = mapper.selectById(uniqueId);
        this.helper.endQuery();
        return entity;
    }

    /**
     * 查询系统中所有的Entity
     */
    @Override
    public List<Entity> getAll() {
        final IBatisMapper<Entity, Serializable> mapper = this.helper.beginQuery(this.entityCls);
        final List<Entity> entities = mapper.selectAll();
        this.helper.endQuery();
        return entities;
    }

    /**
     * 分页查询系统中所有的Entity
     */
    @Override
    public List<Entity> getByPage(final int index, final int size, final String orderBy) {
        final IBatisMapper<Entity, Serializable> mapper = this.helper.beginQuery(this.entityCls);
        final int offset = OFFSET_FUN.get(Resources.META_CATEGORY).offset(index, size);
        final List<Entity> entities = mapper.selectByPage(Resources.META_CATEGORY, orderBy, size, offset);
        this.helper.endQuery();
        return entities;
    }

    /**
     * 按照WHERE子句查询系统中所有的Entities
     */
    @Override
    public List<Entity> queryList(final String whereClause) {
        final IBatisMapper<Entity, Serializable> mapper = this.helper.beginQuery(this.entityCls);
        final List<Entity> entities = mapper.queryList(whereClause);
        this.helper.endQuery();
        return entities;
    }

    /**
     * 按照WHERE子句删除对应的Entities
     */
    @Override
    public boolean deleteList(final String whereClause) throws AbstractTransactionException {
        final IBatisMapper<Entity, Serializable> mapper = this.helper.beginTransaction(this.entityCls);
        mapper.deleteList(whereClause);
        this.helper.endTransaction();
        return true;
    }

    /**
     * 
     */
    @Override
    public long count() {
        final IBatisMapper<Entity, Serializable> mapper = this.helper.beginQuery(this.entityCls);
        final long count = mapper.count();
        this.helper.endQuery();
        return count;
    }

    /**
     * 执行某个表的Purge操作
     */
    @Override
    public boolean purge() throws AbstractTransactionException {
        final IBatisMapper<Entity, Serializable> mapper = this.helper.beginTransaction(this.entityCls);
        mapper.purge();
        this.helper.endTransaction();
        return true;
    }

}
