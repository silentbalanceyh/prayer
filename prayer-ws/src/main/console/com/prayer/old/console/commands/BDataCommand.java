package com.prayer.old.console.commands;

import org.apache.commons.cli.CommandLine;

import com.prayer.constant.Resources;
import com.prayer.facade.business.DataService;
import com.prayer.model.business.behavior.ServiceResult;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class BDataCommand extends AbstractCommand {
    // ~ Static Fields =======================================
    /** **/
    private static final String DEPLOYED = "DEPLOYED";
    // ~ Instance Fields =====================================
    /** **/
    private transient final DataService service;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public BDataCommand() {
        super();
        this.service = null;// singleton(DataSevImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public String command() {
        return "bdata";
    }

    // ~ Methods =============================================
    @Override
    public JsonObject execute(final String... args) {
        final CommandLine cmdLine = this.parse(args);
        JsonObject ret = null;
        if (null != cmdLine) {
            // -d
            if (cmdLine.hasOption('d')) {
                ret = this.deployData(cmdLine);
            } else if (cmdLine.hasOption('p')) {
                ret = this.purgeData(cmdLine);
            }
        }
        return ret;
    }

    // ~ Private Methods =====================================
    /**
     * 发布业务数据
     * 
     * @param cmdLine
     * @return
     */
    private JsonObject deployData(final CommandLine cmdLine) {
        JsonObject retJson = new JsonObject();
        final String identifier = cmdLine.getOptionValue('d');
        if (StringKit.isNonNil(identifier)) {
            final ServiceResult<Boolean> ret = this.service.deployData(Resources.OOB_DATA_FOLDER, identifier);
            if (ret.getResult()) {
                retJson.put(DEPLOYED, Boolean.TRUE);
            } else {
                retJson.put(DEPLOYED, Boolean.FALSE);
                retJson.put(ERROR, ret.getErrorMessage());
            }
        }
        return retJson;
    }

    /**
     * 删除业务数据
     * 
     * @param cmdLine
     * @return
     */
    private JsonObject purgeData(final CommandLine cmdLine) {
        JsonObject retJson = new JsonObject();
        final String identifier = cmdLine.getOptionValue('d');
        if (StringKit.isNonNil(identifier)) {
            final ServiceResult<Boolean> ret = this.service.purgeData(identifier);
            if (ret.getResult()) {
                retJson.put(DEPLOYED, Boolean.TRUE);
            } else {
                retJson.put(DEPLOYED, Boolean.FALSE);
                retJson.put(ERROR, ret.getErrorMessage());
            }
        }
        return retJson;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}