package com.prayer.business.secure.basic;

import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.reservoir;

import java.util.ArrayList;
import java.util.List;

import com.prayer.business.service.Epsilon;
import com.prayer.constant.SystemEnum.Credential;
import com.prayer.exception.web._401DuplicatedUserFoundException;
import com.prayer.facade.business.instantor.secure.SecureInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.database.dao.RecordDao;
import com.prayer.facade.model.crucial.Expression;
import com.prayer.facade.model.crucial.Transducer.V;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.crucial.DataRecord;
import com.prayer.model.query.Restrictions;
import com.prayer.model.type.DataType;
import com.prayer.resource.InceptBus;
import com.prayer.resource.Resources;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class BasicAuthBllor implements SecureInstantor {
    // ~ Static Fields =======================================
    /** 安全配置读取器 **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Security.class);
    /** Basic的Password字段 **/
    private static final String PWD = INCEPTOR.getString(Resources.Security.MODE.name() + ".password");
    /** 共享的Identifier **/
    private static final String IDENTIFIER = INCEPTOR.getString(Point.Security.Shared.IDENTIFIER);
    // ~ Instance Fields =====================================
    /** 真正调用数据层的位置 **/
    private transient final RecordDao performer;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public BasicAuthBllor() {
        final Class<?> daoCls = Epsilon.getDalor(DataRecord.class);
        this.performer = reservoir(daoCls.getName(), daoCls);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    @Override
    public Record identByName(@NotNull @NotBlank @NotEmpty final String realm,
            @NotNull @NotBlank @NotEmpty final String username, @NotNull @NotBlank @NotEmpty final String password)
            throws AbstractException {
        /** 1.构造请求参数 **/
        final String uid = this.buildUID(Credential.USERNAME);
        /** 2.初始化Record **/
        final Record record = this.readData(uid, username);
        
        return null;
    }

    @Override
    public Record identByEmail(@NotNull @NotBlank @NotEmpty final String realm,
            @NotNull @NotBlank @NotEmpty final String email, @NotNull @NotBlank @NotEmpty final String password)
            throws AbstractException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Record identByMobile(@NotNull @NotBlank @NotEmpty final String realm,
            @NotNull @NotBlank @NotEmpty final String mobile, @NotNull @NotBlank @NotEmpty final String password)
            throws AbstractException {
        // TODO Auto-generated method stub
        return null;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Record readData(final String userId, final String userValue) throws AbstractException {
        /** 1.构造Record **/
        Record record = instance(DataRecord.class, IDENTIFIER);
        /** 2.构造Filters **/
        final Expression filters = Restrictions.eq(record.toColumn(userId));
        /** 3.构造参数 **/
        final List<Value<?>> params = new ArrayList<>();
        /** 4.添加参数 **/
        final DataType type = record.fields().get(userId);
        final Value<?> value = V.get().getValue(type, userValue);
        params.add(value);
        /** 5.读取Record列表 **/
        final List<Record> records = this.performer.queryByFilter(record, new String[] { record.toColumn(PWD) }, params,
                filters);
        if (Constants.ONE < records.size()) {
            throw new _401DuplicatedUserFoundException(getClass(), userId, userValue);
        } else if (Constants.ONE == records.size()) {
            record = records.get(Constants.IDX);
        } else {
            record = null;
        }
        return record;
    }

    /** 初始化Record **/
    private String buildUID(final Credential credential) {
        String uidField = INCEPTOR.getString(Point.Security.Shared.User.USER_ID);
        if (Credential.EMAIL == credential) {
            uidField = INCEPTOR.getString(Point.Security.Shared.User.EMAIL);
        } else if (Credential.MOBILE == credential) {
            uidField = INCEPTOR.getString(Point.Security.Shared.User.MOBILE);
        }
        return uidField;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}