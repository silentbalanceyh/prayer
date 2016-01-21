package com.prayer.business.impl.oob;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.dao.impl.std.record.RecordDaoImpl;
import com.prayer.facade.business.DataService;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.ServiceResult;
import com.prayer.model.crucial.DataRecord;
import com.prayer.util.business.RecordSerializer;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOfAny;
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
public class DataSevImpl implements DataService, OOBPaths {
    // ~ Static Fields =======================================
    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DeploySevImpl.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient final RecordDao dao = singleton(RecordDaoImpl.class);
    /** **/
    private transient final RecordSerializer serializer = singleton(RecordSerializer.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<Boolean> purgeData(@NotNull @NotBlank @NotEmpty final String identifier) {
        final ServiceResult<Boolean> result = new ServiceResult<>();
        try {
            // 1.只针对GenericRecord
            final Record record = instance(DataRecord.class.getName(), identifier);
            // 2.有了这个record过后就可以了，里面会得到schema信息
            this.dao.purge(record);
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
            result.failure(ex);
        }
        return result;
    }

    /** **/
    @Override
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<Boolean> deployData(@NotNull @NotBlank @NotEmpty final String rootFolder,
            @NotNull @NotBlank @NotEmpty final String identifier) {
        final ServiceResult<Boolean> result = new ServiceResult<>();
        // 1.从rootFolder中读取符合identfier的目录
        final String dataFolder = rootFolder + VX_DATA + identifier;
        final List<String> dataFiles = IOKit.listFiles(dataFolder);
        try {
            for (final String file : dataFiles) {
                final String targetFile = dataFolder + "/" + file;
                debug(LOGGER, "Imported File : " + targetFile);
                // 2.读取File中的内容
                final String content = IOKit.getContent(targetFile);
                // 3.每个文件可以转换成JsonArray
                final JsonArray data = new JsonArray(content);
                // 4.遍历JsonArray中的每一个JsonObject，并且转换成Record
                final int size = data.size();
                for (int idx = 0; idx < size; idx++) {
                    // 5.Skip非Json对象
                    final Object item = data.getValue(idx);
                    if (JsonObject.class == item.getClass()) {
                        // 6.将JsonObject序列化成对应的对象
                        final Record record = this.serializer.encloseRecord(identifier, DataRecord.class,
                                data.getJsonObject(idx));
                        this.dao.insert(record);
                    }
                }
            }
            result.success(Boolean.TRUE);
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
            result.failure(ex);
        }
        return result;
    }

    /** **/
    @Override
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<Boolean> deployAllData(String rootFolder) {
        ServiceResult<Boolean> result = new ServiceResult<>();
        // 1.从rootFolder中读取所有的目录信息
        final String dataFolder = rootFolder + VX_DATA;
        final List<String> dataFolders = IOKit.listDirectories(dataFolder);
        try {
            for(final String folder: dataFolders){
                result = this.deployData(rootFolder, folder);
                // 某一个identifier没有导入成功
                if(ResponseCode.SUCCESS != result.getResponseCode()){
                    throw result.getServiceError();
                }
            }
            result.success(Boolean.TRUE);
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
            result.failure(ex);
        }
        return result;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
