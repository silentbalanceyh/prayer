package com.prayer.old.console.commands;

import static com.prayer.util.reflection.Instance.singleton;

import java.net.URL;

import org.apache.commons.cli.CommandLine;

import com.prayer.business.deployment.impl.SchemaBllor;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.business.instantor.schema.SchemaInstantor;
import com.prayer.facade.schema.Schema;
import com.prayer.model.business.behavior.ServiceResult;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
public class SchemaCommand extends AbstractCommand {
    // ~ Static Fields =======================================
    /** **/
    private static final String SYNCED = "SYNCED";
    /** Json -> H2 Database **/
    private static final String STEP1 = "[STEP1]";
    /** H2 Database -> INIT Server **/
    private static final String STEP2 = "[STEP2]";
    /** **/
    private static final String ERROR = "error";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final SchemaInstantor service;
    /** **/
    @NotNull
    private transient final JdbcHelper helper;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    @PostValidateThis
    public SchemaCommand() {
        super();
        this.service = singleton(SchemaBllor.class);
        this.helper = singleton(JdbcHelper.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String command() {
        return "schema";
    }

    // ~ Methods =============================================
    /**
     * 
     * @param args
     * @return
     */
    public JsonObject execute(final String... args) {
        final CommandLine cmdLine = this.parse(args);
        // TODO：命令schema的开发
        JsonObject ret = null;
        if (null != cmdLine) {
            if (cmdLine.hasOption('u')) {
                ret = this.syncSchema(cmdLine);
            }
        }
        return ret;
    }
    // ~ Private Methods =====================================

    private JsonObject syncSchema(final CommandLine cmdLine) {
        final String file = cmdLine.getOptionValue('u');
        final String schemaFile = Resources.OOB_DATA_FOLDER + "/schema/" + file;
        final URL url = IOKit.getURL(schemaFile);
        JsonObject ret = new JsonObject();
        if (null == url) {
            System.out.println("[ERROR] The schema file does not exist in classpath : " + schemaFile);  // NOPMD
        } else {
            final JsonObject checkConn = this.helper.getMetadata(Resources.META_CATEGORY);
            if (checkConn.containsKey(ERROR)) {
                ret.put(ERROR, checkConn.getString(ERROR));
            } else {
                ret = this.syncSchema(schemaFile);
                if (ret.getBoolean(SYNCED)) {
                    ret.put("info", "[INFO] Schema in file : " + schemaFile
                            + " has been imported into H2 Database successfully.");
                }
                ret.remove(SYNCED);
            }
        }
        return ret;
    }

    private JsonObject syncSchema(final String file) {
        // Schema Json File --> H2 Database
        ServiceResult<Schema> ret = this.service.importSchema(file);
        final JsonObject retJson = new JsonObject();
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            // H2 Database --> INIT Server
            Schema schema = ret.getResult();
            if(null != schema){
                ret = this.service.syncMetadata(schema);
                if(ResponseCode.SUCCESS == ret.getResponseCode()){
                    retJson.put(SYNCED, Boolean.TRUE);
                }else{
                    retJson.put(SYNCED, Boolean.FALSE);
                    retJson.put(ERROR, STEP2 + " H2 ( Meta ) -> Database, Error : " + ret.getErrorMessage());
                }
            }else{
                retJson.put(SYNCED, Boolean.FALSE);
                retJson.put(ERROR, STEP1 + " Json -> H2 ( Meta ), Error : " + ret.getErrorMessage());
            }
        } else {
            retJson.put(SYNCED, Boolean.FALSE);
            retJson.put(ERROR, ret.getErrorMessage());
        }
        return retJson;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}