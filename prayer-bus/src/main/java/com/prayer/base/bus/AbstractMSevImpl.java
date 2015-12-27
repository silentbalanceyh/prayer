package com.prayer.base.bus;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.peError;

import org.slf4j.Logger;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.base.exception.AbstractException;
import com.prayer.dao.impl.jdbc.DatabaseConnImpl;
import com.prayer.facade.dao.jdbc.DatabaseDirector;
import com.prayer.model.bus.Metadata;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.bus.Interruptor;
import com.prayer.util.bus.ConsoleExtractor;
import com.prayer.util.cv.Constants;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractMSevImpl {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final DatabaseDirector director;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public AbstractMSevImpl() {
        this.director = singleton(DatabaseConnImpl.class);
    }

    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param params
     * @return
     */
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<JsonObject> getMetadata(@NotNull final JsonObject params) {
        final ServiceResult<JsonObject> ret = new ServiceResult<>();
        final AbstractException error = Interruptor.interruptJdbcParams(getClass(), params);
        if (null == error) {
            try {
                final String url = params.getString(Constants.CMD.STATUS.JDBC_URL);
                final String username = params.getString(Constants.CMD.STATUS.USERNAME);
                final String password = params.getString(Constants.CMD.STATUS.PASSWORD);
                final Metadata meta = this.director.getMetadata(url, username, password);
                ret.success(ConsoleExtractor.extractMetadata(meta));
            } catch (AbstractDatabaseException ex) {
                peError(getLogger(), ex);
                ret.failure(ex);
            }
        } else {
            ret.failure(error);
        }
        return ret;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
