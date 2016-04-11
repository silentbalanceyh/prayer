package com.prayer.accessor;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.genericT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.prayer.database.accessor.impl.MetaAccessorImpl;
import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.crucial.Expression;
import com.prayer.facade.model.entity.Entity;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.util.io.IOKit;
import com.prayer.util.reflection.Instance;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * IBATIS的Mapper接口测试
 * 
 * @author Lang
 *
 */
public abstract class AbstractAccessorTool<T> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient MetaAccessor accessor;
    /** **/
    private transient Class<?> genericT;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractAccessorTool() {
        this.genericT = genericT(getClass());
        if (null != this.genericT) {
            this.accessor = new MetaAccessorImpl(this.genericT);
        }
    }

    // ~ Abstract Methods ====================================
    /** 返回日志记录器 **/
    protected abstract Logger getLogger();

    // ~ Override Methods ====================================
    /**
     * 
     * @return
     */
    protected MetaAccessor getAccessor() {
        return this.accessor;
    }

    /**
     * 读取JsonObject的方法
     * 
     * @param file
     * @return
     */
    protected JsonObject readData(final String file) {
        final String content = IOKit.getContent(file);
        final JsonObject json = new JsonObject();
        if (null != content) {
            json.mergeIn(new JsonObject(content));
        }
        return json;
    }

    /**
     * 读取JsonObject的集合的方法
     * 
     * @param file
     * @return
     */
    protected List<JsonObject> readListData(final String file) {
        final String content = IOKit.getContent(file);
        final List<JsonObject> retList = new ArrayList<>();
        if (null != content) {
            final JsonArray json = new JsonArray(content);
            for (final Object item : json) {
                if (null != item && JsonObject.class == item.getClass()) {
                    retList.add((JsonObject) item);
                }
            }
        }
        return retList;
    }

    // ~ Methods =============================================
    // ~ Template Methods ====================================
    /**
     * 完整的流程设计用于测试
     * 
     * @param input
     * @return
     * @throws AbstractDatabaseException
     */
    public boolean insert(final String input) throws AbstractDatabaseException {
        try {
            // 1.从文件中读取数据构造一个新的Entity
            final Entity entity = Instance.instance(this.genericT, this.readData(input));
            // 2.将这个Entity插入到数据库中
            final Entity inserted = this.getAccessor().insert(entity);
            debug(getLogger(), "[PET] Inserted ==> " + inserted.toJson());
            // 3.判断是否读取了id
            if (null == entity.id()) {
                entity.id(inserted.id());
            }
            // 4.设置插入过后的判断结果：第一次判断
            boolean ret = entity.toJson().equals(inserted.toJson());
            // 5.从数据库中读取另外一条数据
            final Entity selected = this.getAccessor().getById(inserted.id());
            // 6.第二次判断
            ret = ret & inserted.toJson().equals(selected.toJson());
            // 7.清除掉系统中新添加的这条数据，删除成功
            ret = ret & this.getAccessor().deleteById(selected.id());
            // 8.返回结果
            return ret;
        } catch (Exception ex) {
            jvmError(getLogger(), ex);
            return false;
        }
    }

    /**
     * 完整的Select流程设计
     * 
     * @param input
     * @param filter
     * @return
     * @throws AbstractDatabaseException
     */
    public boolean selectWhere(final String input, final Expression filter) throws AbstractDatabaseException {
        try {
            // 1.从文件中读取数据构造一个新的Entity
            final Entity entity = Instance.instance(this.genericT, this.readData(input));
            // 2.将这个Entity插入到数据库中
            final Entity inserted = this.getAccessor().insert(entity);
            // 3.从系统中读取新插入的
            final List<Entity> selectedList = this.getAccessor().queryList(filter.toSql());
            // 4.第一次判断
            boolean ret = selectedList.size() == Constants.ONE;
            // 5.对比选择的Entity
            final Entity selected = selectedList.get(Constants.IDX);
            debug(getLogger(),
                    "[PET] Selected By Where ==> " + selected.toJson() + " | Inserted ==> " + inserted.toJson());
            ret = ret & selected.toJson().equals(inserted.toJson());
            // 6.清除系统中的数据
            ret = ret & this.getAccessor().deleteById(inserted.id());
            // 7.返回结果
            return ret;
        } catch (Exception ex) {
            jvmError(getLogger(), ex);
            return false;
        }
    }

    /**
     * 
     * @param input
     * @return
     * @throws AbstractDatabaseException
     */
    public boolean page(final String input, final String page) throws AbstractDatabaseException {
        try {
            // 0.Purge当前数据库中的所有信息
            this.getAccessor().purge();
            // 1.从文件中读取数据构造一个Entity的List
            final List<Entity> entityList = new ArrayList<>();
            final List<JsonObject> paramList = this.readListData(input);
            // 2.Build整个EntityList
            for (final JsonObject param : paramList) {
                entityList.add(Instance.instance(this.genericT, param));
            }
            // 3.批量插入到系统中
            this.getAccessor().insert(entityList.toArray(new Entity[] {}));
            // 4.从系统中读取所有
            final List<Entity> selected = this.getAccessor().getAll();
            // 5.第一次比较
            boolean ret = selected.size() == entityList.size();
            // 6.直接读取
            final JsonObject pageInfo = this.readData(page);
            // 7.根据pageInfo读取
            final List<Entity> paged = this.getAccessor().getByPage(pageInfo.getInteger("index"),
                    pageInfo.getInteger("size"), pageInfo.getString("orderby"));
            // 8.再次对比
            ret = ret & pageInfo.getInteger("expected") == paged.size();
            // 9.删除系统中目前存在的数据
            this.getAccessor().purge();
            // 10.返回结果
            return ret;
        } catch (Exception ex) {
            jvmError(getLogger(), ex);
            return false;
        }
    }

    /**
     * 
     * @param input
     * @return
     * @throws AbstractDatabaseException
     */
    public boolean batchOp(final String input) throws AbstractDatabaseException {
        try {
            // 0.Purge当前数据库中的所有信息
            this.getAccessor().purge();
            // 1.从文件中读取数据构造一个Entity的List
            final List<Entity> entityList = new ArrayList<>();
            final List<JsonObject> paramList = this.readListData(input);
            // 2.Build整个EntityList
            for (final JsonObject param : paramList) {
                entityList.add(Instance.instance(this.genericT, param));
            }
            // 3.将数据批量插入到系统中
            this.getAccessor().insert(entityList.toArray(new Entity[] {}));
            // 4.从系统中读取数量
            long count = this.getAccessor().count();
            // 5.判断数量是否匹配
            boolean ret = count == entityList.size();
            // 6.构造ID集合
            final List<Serializable> ids = new ArrayList<>();
            for (final Entity item : entityList) {
                ids.add(item.id());
            }
            // 7.批量删除
            ret = ret & this.getAccessor().deleteById(ids.toArray(new Serializable[] {}));
            // 8.再从数据库中读取数据
            count = this.getAccessor().count();
            ret = ret & count == Constants.ZERO;
            // 9.第二次Purge
            this.getAccessor().purge();
            // 10.返回结果
            return ret;
        } catch (Exception ex) {
            jvmError(getLogger(), ex);
            return false;
        }
    }

    /**
     * 完整的流程用于update方法
     * 
     * @param input
     * @param updated
     * @return
     * @throws AbstractDatabaseException
     */
    public boolean update(final String input, final String newIn) throws AbstractDatabaseException {
        try {
            // 1.从文件中读取数据构造一个新的Entity
            final Entity entity = Instance.instance(this.genericT, this.readData(input));
            // 2.将这个Entity插入到数据库中
            final Entity inserted = this.getAccessor().insert(entity);
            debug(getLogger(), "[PET] Before ==> " + inserted.toJson());
            // 3.获取新的数据信息
            Entity updated = Instance.instance(this.genericT, this.readData(newIn));
            // 4.用插入的id覆盖更新的updated的id
            if (null != inserted.id()) {
                updated.id(inserted.id());
            }
            // 5.更新数据：第一次判断
            updated = this.getAccessor().update(updated);
            debug(getLogger(), "[PET] After ==> " + updated.toJson());
            boolean ret = !updated.toJson().equals(inserted.toJson());
            // 6.从数据库中读取最新的信息
            final Entity selected = this.getAccessor().getById(updated.id());
            // 7.判断结果
            ret = ret & selected.toJson().equals(updated.toJson());
            // 8.删除当前数据
            ret = ret & this.getAccessor().deleteById(selected.id());
            // 9.返回结果
            return ret;
        } catch (Exception ex) {
            jvmError(getLogger(), ex);
            return false;
        }
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
