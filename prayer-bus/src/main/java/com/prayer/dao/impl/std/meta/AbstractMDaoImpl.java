package com.prayer.dao.impl.std.meta;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.dao.schema.TemplateDao;
import com.prayer.facade.kernel.JsonEntity;
import com.prayer.facade.kernel.Record;
import com.prayer.model.h2.AbstractMetadata;
import com.prayer.model.kernel.MetaRecord;
import com.prayer.util.bus.RecordSerializer;
import com.prayer.util.cv.Constants;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
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
    @SuppressWarnings("unchecked")
    @InstanceOf(Record.class)
    public Record insert(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        // 1.将Record数据转换成H2 Model
        final T entity = this.toEntity(record);
        // 2.直接插入
        List<T> retList = new ArrayList<>();
        try {
            retList = this.getDao().insert(entity);
        } catch (AbstractTransactionException ex) {
            info(getLogger(), "[E] H2 Database Access Error, ex = " + ex.getErrorMessage());
            throw ex;
        }
        // 3.处理返回值
        Record retR = null;
        if (!retList.isEmpty()) {
            retR = this.fromEntity(record.identifier(), retList.get(Constants.ZERO));
        }
        return retR;
    }

    // ~ Methods =============================================
    /** **/
    @NotNull
    protected RecordSerializer serializer() {
        return this.serializer;
    }

    /** **/
    @SuppressWarnings("unchecked")
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
    protected Record fromEntity(@NotNull @NotBlank @NotEmpty final String identifier, @NotNull final JsonEntity entity)
            throws AbstractDatabaseException {
        final JsonObject data = entity.toJson();
        Record record = this.serializer().encloseRecord(identifier, MetaRecord.class, data);
        return record;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
