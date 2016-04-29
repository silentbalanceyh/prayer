package com.prayer.business.deployment.impl;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.ObjectTransferer;
import com.prayer.dao.data.DataRecordDalor;
import com.prayer.facade.business.instantor.deployment.DataInstantor;
import com.prayer.facade.constant.Constants.EXTENSION;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.database.dao.RecordDao;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.util.Transferer;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.crucial.DataRecord;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
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
public class DataBllor implements DataInstantor {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBllor.class);
    /** **/
    private static final String DATA_PROC = "( {0} ) Data Loading from data file = {1} with identifier = {2}.";
    // ~ Instance Fields =====================================
    /** 底层调用 **/
    @NotNull
    private transient final RecordDao performer;
    /** **/
    @NotNull
    private transient final Transferer transferer;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public DataBllor() {
        /** 实例化当前的Dao **/
        this.performer = reservoir(DataRecordDalor.class.getName(), DataRecord.class);
        this.transferer = singleton(ObjectTransferer.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean push(@NotNull @NotBlank @NotEmpty final String folder) throws AbstractException {
        /** 1.枚举所有Folders **/
        final List<String> entities = IOKit.listDirectories(folder);
        /** 2.列举所有文件 **/
        for (final String file : entities) {
            /** 3.注：文件名就是identifier **/
            final String identifier = file;
            final String dataFile = folder + "/" + file + Symbol.DOT + EXTENSION.JSON;
            /** 4.数据导入 **/
            final JsonArray dataArr = this.buildRaw(dataFile);
            final int size = dataArr.size();
            info(LOGGER, MessageFormat.format(DATA_PROC, getClass().getSimpleName(), dataFile, identifier));
            for (int idx = 0; idx < size; idx++) {
                /** 5.读取对象数据 **/
                final JsonObject data = dataArr.getJsonObject(idx);
                /** 6.初始化Record **/
                final Record record = this.transferer.toRecord(identifier, DataRecord.class, data);
                /** 7.插入Record **/
                this.performer.insert(record);
            }
        }
        return true;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private JsonArray buildRaw(final String file) {
        JsonArray data = new JsonArray();
        try {
            data = new JsonArray(IOKit.getContent(file));
        } catch (DecodeException ex) {
            jvmError(LOGGER, ex);
        }
        return data;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
