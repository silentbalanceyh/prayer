package com.prayer.vx.console.commands;

import static com.prayer.util.Instance.singleton;

import java.net.URL;

import org.apache.commons.cli.CommandLine;

import com.prayer.bus.impl.std.SchemaSevImpl;
import com.prayer.facade.bus.SchemaService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.util.IOKit;
import com.prayer.util.cv.Resources;
import com.prayer.util.cv.SystemEnum.ResponseCode;

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
    /** **/
    private static final String ERROR = "error";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final SchemaService service;
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
        this.service = singleton(SchemaSevImpl.class);
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
        final String schemaFile = Resources.META_OD_FOLDER + "/schema/" + file;
        final URL url = IOKit.getURL(schemaFile);
        JsonObject ret = new JsonObject();
        if (null == url) {
            System.out.println("[ERROR] The schema file does not exist in classpath : " + schemaFile);  // NOPMD
        } else {
            final JsonObject checkConn = this.helper.getMetadata("H2");
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
        final ServiceResult<GenericSchema> ret = this.service.syncSchema(file);
        final JsonObject retJson = new JsonObject();
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            retJson.put(SYNCED, Boolean.TRUE);
        } else {
            retJson.put(SYNCED, Boolean.FALSE);
            retJson.put(ERROR, ret.getErrorMessage());
        }
        return retJson;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
