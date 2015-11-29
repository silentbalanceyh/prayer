package com.prayer.dao.impl.std.meta;

import static com.prayer.util.Error.info;
import static com.prayer.util.Generator.uuid;
import static com.prayer.util.Instance.singleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.dao.schema.TemplateDao;
import com.prayer.facade.kernel.JsonEntity;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.kernel.Value;
import com.prayer.model.h2.AbstractMetadata;
import com.prayer.model.h2.schema.FieldModel;
import com.prayer.model.kernel.MetaRecord;
import com.prayer.util.bus.RecordSerializer;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.MetaPolicy;
import com.prayer.util.dao.Interrupter.Api;
import com.prayer.util.dao.Interrupter.Policy;
import com.prayer.util.dao.Interrupter.PrimaryKey;
import com.prayer.util.dao.Interrupter.Response;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
@SuppressWarnings("unchecked")
public abstract class AbstractMDaoImpl<T extends AbstractMetadata, ID extends Serializable> implements RecordDao {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final RecordSerializer serializer;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public AbstractMDaoImpl() {
        this.serializer = singleton(RecordSerializer.class);
    }

    // ~ Abstract Methods ====================================
    /** Overwrite by sub class **/
    public abstract TemplateDao<T, ID> getDao();

    /** Entity **/
    public abstract T newT();

    /** Logger Reference **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    /** **/
    @Override
    @InstanceOf(Record.class)
    public Record insert(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        // ERR：检查主键定义
        PrimaryKey.interrupt(getClass(), record.identifier(), record.idschema().size());
        // ERR: Policy Support
        Policy.interrupt(getClass(), MetaPolicy.GUID, record.policy());
        // 3.预处理UUID
        final FieldModel pkSchema = record.idschema().get(Constants.ZERO);
        record.set(pkSchema.getName(), uuid());
        // 4.将Record数据转换成H2 Model
        final T entity = this.toEntity(record);
        // 5.直接插入
        List<T> retList = new ArrayList<>();
        try {
            retList = this.getDao().insert(entity);
        } catch (AbstractTransactionException ex) {
            info(getLogger(),
                    "[E] H2 Database (Insert) Error, (AbstractTransactionException) ex = " + ex.getErrorMessage());
            Response.interrupt(getClass(), false);
        }
        // 6.处理返回值
        return this.fromEntity(record.identifier(), retList.get(Constants.ZERO));
    }

    /** **/
    @Override
    @InstanceOf(Record.class)
    public Record selectById(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull @InstanceOf(Value.class) final Value<?> uniqueId) throws AbstractDatabaseException {
        // ERR.主键值验证
        PrimaryKey.interrupt(getClass(), record);
        // ERR: Policy Support
        Policy.interrupt(getClass(), MetaPolicy.GUID, record.policy());
        // 1.获取ID值
        final ID recordId = (ID) uniqueId.getValue();
        // 2.从数据库中获取T Entity
        final T ret = this.getDao().getById(recordId);
        // 3.将获取到的数据执行转换
        return this.fromEntity(record.identifier(), ret);
    }

    /** **/
    @Override
    public boolean delete(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        // ERR：检查主键定义
        PrimaryKey.interrupt(getClass(), record.identifier(), record.idschema().size());
        // ERR: 主键值验证
        PrimaryKey.interrupt(getClass(), record);
        // ERR: Policy Support
        Policy.interrupt(getClass(), MetaPolicy.GUID, record.policy());
        // 1.获取ID值
        final Value<?> uniqueId = record.get(record.idschema().get(Constants.ZERO).getName());
        final ID recordId = (ID) uniqueId.getValue();
        // 2.执行删除操作
        return this.getDao().deleteById(recordId);
    }

    /** **/
    @Override
    public Record update(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        // ERR：检查主键定义
        PrimaryKey.interrupt(getClass(), record.identifier(), record.idschema().size());
        // ERR: 主键值验证
        PrimaryKey.interrupt(getClass(), record);
        // ERR: Policy Support
        Policy.interrupt(getClass(), MetaPolicy.GUID, record.policy());
        // 1.将Record数据转换成H2 Model
        final T entity = this.toEntity(record);
        // 2.直接插入
        T retT = null;
        try {
            retT = this.getDao().update(entity);
        } catch (AbstractTransactionException ex) {
            info(getLogger(),
                    "[E] H2 Database (Update) Error, (AbstractTransactionException) ex = " + ex.getErrorMessage());
            Response.interrupt(getClass(), false);
        }
        return this.fromEntity(record.identifier(), retT);
    }

    /** NOT SUPPORT **/
    @Override
    @InstanceOf(Record.class)
    public Record selectById(@NotNull @InstanceOfAny(MetaRecord.class) final Record record,
            @NotNull @MinSize(1) final ConcurrentMap<String, Value<?>> uniqueIds) throws AbstractDatabaseException {
        Api.interrupt(getClass(), "selectById(Record,ConcurrentMap<String,Value<?>>)");
        return null;
    }

    // ~ Methods =============================================
    /** **/
    @NotNull
    protected RecordSerializer serializer() {
        return this.serializer;
    }

    /** **/
    protected T toEntity(@NotNull final Record record) throws AbstractDatabaseException {
        T ret = this.newT();
        final JsonObject data = this.serializer.extractRecord(record);
        final JsonEntity entity = ret.fromJson(data);
        if (null == entity) {
            ret = null;
        } else {
            ret = (T) entity;
        }
        return ret;
    }

    /** **/
    protected Record fromEntity(@NotNull @NotBlank @NotEmpty final String identifier, final JsonEntity entity)
            throws AbstractDatabaseException {
        Record ret = null;
        if (null != entity) {
            final JsonObject data = entity.toJson();
            ret = this.serializer().encloseRecord(identifier, MetaRecord.class, data);
        }
        return ret;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
