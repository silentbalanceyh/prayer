package com.prayer.bus.impl.oob; // NOPMD

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.peError;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractException;
import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.exception.system.DeploymentException;
import com.prayer.facade.bus.DeployService;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vertx.UriModel;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.util.io.IOKit;
import com.prayer.util.io.PropertyKit;

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
@SuppressWarnings("unchecked")
public class DeploySevImpl implements DeployService, OOBPaths { // NOPMD
    // ~ Static Fields =======================================

    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DeploySevImpl.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient final MetadataConn metaConn = singleton(MetadataConnImpl.class);
    /** **/
    private transient final DeploySevManager manager = singleton(DeploySevManager.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    public Logger getLogger() {
        return LOGGER;
    }

    /**
     * 
     * @param scriptPath
     * @return
     */
    @Override
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<Boolean> initMetadata(@NotNull @NotBlank @NotEmpty final String scriptPath) {
        // 1.执行Script脚本
        final boolean executedRet = this.metaConn.initMeta(IOKit.getFile(scriptPath));
        // 2.设置相应信息
        final ServiceResult<Boolean> ret = new ServiceResult<>();

        return ret.success(executedRet);
    }

    /**
     * 
     */
    @Override
    @InstanceOfAny(ServiceResult.class)
    public ServiceResult<Boolean> deployMetadata(@NotNull @NotBlank @NotEmpty final String rootFolder) { // NOPMD
        final ServiceResult<Boolean> result = new ServiceResult<>();
        // 1.EVX_VERTICLE
        ServiceResult<?> ret = this.manager.getVerticleService().purgeData();
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            final String verticleFile = rootFolder + VX_VERTICLE;
            ret = this.manager.getVerticleService().importVerticles(verticleFile);
            info(LOGGER, " 1.EVX_VERTICLE ( Verticle deployed successfully ).");
        }

        // 2.EVX_ROUTE
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.manager.getRouteService().purgeData();
        }
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            final String routeFolder = rootFolder + VX_ROUTES;
            final List<String> files = IOKit.listFiles(routeFolder);
            for (final String file : files) {
                ret = this.manager.getRouteService().importToList(routeFolder + file);
                info(LOGGER, " 2.EVX_ROUTE ( Route deployed successfully ). PATH = " + routeFolder + file);
            }
        }

        // 3.EVX_URI
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.manager.getRuleService().purgeData();
            // 必须先PurgeValidators
            ret = this.manager.getUriService().purgeData();
        }
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            final String uriFolder = rootFolder + VX_URI;
            final List<String> files = IOKit.listFiles(uriFolder);
            for (final String file : files) {
                ret = this.manager.getUriService().importToList(uriFolder + file);
                info(LOGGER, " 3.EVX_URI ( URI deployed successfully ). PATH = " + uriFolder + file);
                if (ResponseCode.SUCCESS == ret.getResponseCode()) {
                    // URI中的Param参数List
                    final List<UriModel> uriModels = (List<UriModel>) ret.getResult();
                    for (final UriModel model : uriModels) {
                        // 4.EVX_RULE
                        final String ruleFolder = rootFolder + VX_URI_PARAM;
                        final String paramFolder = ruleFolder
                                + model.getMethod().toString().toLowerCase(Locale.getDefault()) + "/"
                                + model.getUri().substring(1, model.getUri().length()).replaceAll("/", ".")
                                        .replaceAll(":", "\\$");
                        final List<String> paramFiles = IOKit.listFiles(paramFolder);
                        for (final String paramFile : paramFiles) {
                            // Rule Sub Files for field
                            final String ruleFile = paramFolder + "/" + paramFile;
                            ret = this.manager.getRuleService().importRules(ruleFile, model);
                            if (ResponseCode.SUCCESS == ret.getResponseCode()) {
                                info(LOGGER,
                                        " ---> 4.EVX_RULE ( Rule deployed successfully ) " + "Method = "
                                                + model.getMethod().toString() + ", URL = " + model.getUri()
                                                + ", Rule = " + ruleFile);
                            }
                        }
                    }
                }
            }
        }
        // 5.EVX_ADDRESS
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.manager.getAddressService().purgeData();
        }
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            final String addressFile = rootFolder + VX_ADDRESS;
            ret = this.manager.getAddressService().importToList(addressFile);
            info(LOGGER, " 5.EVX_ADDRESS ( Message Address deployed successfully ).");
        }
        // 6.ENG_SCRIPT
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.manager.getScriptService().purgeData();
        }
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            final String scriptFolder = rootFolder + VX_SCRIPT;
            final List<String> files = IOKit.listFiles(scriptFolder);
            for (final String file : files) {
                ret = this.manager.getScriptService().importToList(scriptFolder + "/" + file);
                if (ResponseCode.SUCCESS == ret.getResponseCode()) {
                    info(LOGGER, " ---> 6.EVX_SCRIPT ( Script deployed successfully : " + file + " )");
                }
            }
            info(LOGGER, " 6.EVX_SCRIPT ( All Scripts deployed successfully ).");
        }
        // 2.导入OOB中的Schema定义
        {
            final String schemaFolder = rootFolder + SCHEMA_FOLDER;
            final List<String> jsonFiles = this.getSchemaFiles(schemaFolder);
            // 2.1.读取Order信息
            final PropertyKit orderKit = new PropertyKit(getClass(), schemaFolder + "/order.properties");
            // 2.2.设置Order信息
            final Map<Integer, String> orderMap = new TreeMap<>();
            for (final Object key : orderKit.getProp().keySet()) {
                if (null != key) {
                    final String keyStr = key.toString();
                    orderMap.putIfAbsent(orderKit.getInt(keyStr), keyStr);
                }
            }
            // 2.3.直接遍历TreeMap
            for (final Integer key : orderMap.keySet()) {
                final String fileName = orderMap.get(key) + Symbol.DOT + Constants.EXTENSION.JSON;
                if (jsonFiles.contains(fileName)) {
                    // 导入数据到H2的元数据数据库中
                    ServiceResult<GenericSchema> syncRet = this.manager.getSchemaService()
                            .syncSchema(schemaFolder + "/" + fileName);
                    if (ResponseCode.SUCCESS == syncRet.getResponseCode()) {
                        info(LOGGER, "7.Metadata Server (order = " + key + ",filename = " + fileName
                                + ") importing successfully !");
                        // 从元数据数据Server导入到Business Database中
                        syncRet = this.manager.getSchemaService().syncMetadata(syncRet.getResult());
                        info(LOGGER, "7.Sync from Metadata Server to Business Database successfully ! table = "
                                + syncRet.getResult().getMeta().getTable());
                    } else {
                        debug(LOGGER, "7.Error = " + syncRet.getErrorMessage());
                    }
                }
            }

        }
        // 最终结果
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            result.success(Boolean.TRUE);
        } else {
            final AbstractException exp = new DeploymentException(getClass());
            peError(LOGGER, exp);
            if (ResponseCode.ERROR == ret.getResponseCode()) {
                result.error(exp);
            } else {
                result.failure(exp);
            }
        }
        return result;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private List<String> getSchemaFiles(final String schemaFolder) {
        final URL uri = IOKit.getURL(schemaFolder);
        final List<String> retList = new ArrayList<>();
        if (null != uri) {
            final File folder = new File(uri.getPath());
            if (folder.isDirectory()) {
                Arrays.asList(folder.listFiles()).forEach(file -> {
                    retList.add(file.getName());
                });
            }
        }
        return retList;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
