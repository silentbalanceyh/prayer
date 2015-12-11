package com.prayer.bus.impl.oob; // NOPMD

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.bus.BusinessLogger.error;
import static com.prayer.util.bus.BusinessLogger.info;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractException;
import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.exception.system.DeploymentException;
import com.prayer.facade.bus.DeployService;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vertx.UriModel;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.util.IOKit;
import com.prayer.util.bus.BusinessLogger;
import com.prayer.util.cv.SystemEnum.ResponseCode;

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
    public ServiceResult<Boolean> initH2Database(@NotNull @NotBlank @NotEmpty final String scriptPath) {
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
    public ServiceResult<Boolean> deployPrayerData() { // NOPMD
        final ServiceResult<Boolean> result = new ServiceResult<>();
        // 1.EVX_VERTICLE
        ServiceResult<?> ret = this.manager.getVerticleService().purgeData();
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.manager.getVerticleService().importVerticles(VX_VERTICLE);
            info(LOGGER, " 1.EVX_VERTICLE ( Verticle deployed successfully ).");
        } else {
            error(LOGGER, BusinessLogger.E_PROCESS_ERR, "Verticle Deploy", ret.getErrorMessage());
        }

        // 2.EVX_ROUTE
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.manager.getRouteService().purgeData();
        }
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            final List<String> files = IOKit.listFiles(VX_ROUTES);
            for (final String file : files) {
                ret = this.manager.getRouteService().importToList(VX_ROUTES + file);
                info(LOGGER, " 2.EVX_ROUTE ( Route deployed successfully ). PATH = " + VX_ROUTES + file);
            }
        } else {
            error(LOGGER, BusinessLogger.E_PROCESS_ERR, "Route Deploy", ret.getErrorMessage());
        }

        // 3.EVX_URI
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.manager.getRuleService().purgeData();
            // 必须先PurgeValidators
            ret = this.manager.getUriService().purgeData();
        }
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            final List<String> files = IOKit.listFiles(VX_URI);
            for (final String file : files) {
                ret = this.manager.getUriService().importToList(VX_URI + file);
                info(LOGGER, " 3.EVX_URI ( URI deployed successfully ). PATH = " + VX_URI + file);
                if (ResponseCode.SUCCESS == ret.getResponseCode()) {
                    // URI中的Param参数List
                    final List<UriModel> uriModels = (List<UriModel>) ret.getResult();
                    for (final UriModel model : uriModels) {
                        // 4.EVX_RULE
                        final String paramFolder = VX_URI_PARAM
                                + model.getMethod().toString().toLowerCase(Locale.getDefault()) + "/"
                                + model.getUri().substring(1, model.getUri().length()).replaceAll("/", ".")
                                        .replaceAll(":", "\\$");
                        final List<String> paramFiles = IOKit.listFiles(paramFolder);
                        for (final String paramFile : paramFiles) {
                            // Rule Sub Files for field
                            final String ruleFile = paramFolder + "/" + paramFile;
                            ret = this.manager.getRuleService().importRules(ruleFile, model);
                            if (ResponseCode.SUCCESS != ret.getResponseCode()) {
                                error(LOGGER, BusinessLogger.E_PROCESS_ERR,
                                        model.getUri() + " Rule Deploy : file = " + ruleFile, ret.getErrorMessage());
                            } else {
                                info(LOGGER,
                                        " ---> 4.EVX_RULE ( Rule deployed successfully ) " + "Method = "
                                                + model.getMethod().toString() + ", URL = " + model.getUri()
                                                + ", Rule = " + ruleFile);
                            }
                        }
                    }
                } else {
                    error(LOGGER, BusinessLogger.E_PROCESS_ERR, "Uri Deploy", ret.getErrorMessage());
                }
            }
        }
        // 5.EVX_ADDRESS
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.manager.getAddressService().purgeData();
        }
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.manager.getAddressService().importToList(VX_ADDRESS);
            info(LOGGER, " 5.EVX_ADDRESS ( Message Address deployed successfully ).");
        } else {
            error(LOGGER, BusinessLogger.E_PROCESS_ERR, "Address Deploy", ret.getErrorMessage());
        }
        // 6.ENG_SCRIPT
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.manager.getScriptService().purgeData();
        }
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.manager.getScriptService().importToList(VX_SCRIPT);
            info(LOGGER, " 6.EVX_SCRIPT ( Script deployed successfully ).");
        } else {
            error(LOGGER, BusinessLogger.E_PROCESS_ERR, "Script Deploy", ret.getErrorMessage());
        }
        // 2.导入OOB中的Schema定义
        {
            final List<String> jsonPathList = this.getSchemaFiles();
            jsonPathList.forEach(jsonPath -> {
                ServiceResult<GenericSchema> syncRet = this.manager.getSchemaService()
                        .syncSchema(SCHEMA_FOLDER + jsonPath);
                syncRet = this.manager.getSchemaService().syncMetadata(syncRet.getResult());
            });
            info(LOGGER, " 7.Metadata H2 importing successfully!");
        }
        // 最终结果
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            result.success(Boolean.TRUE);
        } else {
            final AbstractException exp = new DeploymentException(getClass());
            if (ResponseCode.ERROR == ret.getResponseCode()) {
                result.error(exp);
            } else {
                result.failure(exp);
            }
            error(LOGGER, BusinessLogger.E_DPDATA_ERR);
        }
        return result;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private List<String> getSchemaFiles() {
        final URL uri = IOKit.getURL(SCHEMA_FOLDER);
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
