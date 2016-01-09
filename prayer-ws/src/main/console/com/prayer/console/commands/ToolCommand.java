package com.prayer.console.commands;

import org.apache.commons.cli.CommandLine;

import com.prayer.util.Encryptor;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class ToolCommand extends AbstractCommand {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public String command() {
        return "tool";
    }

    // ~ Methods =============================================
    /**
     * 
     */
    @Override
    public JsonObject execute(final String... args) {
        final CommandLine cmdLine = this.parse(args);
        // TODO: tool
        JsonObject ret = null;
        if (null != cmdLine) {
            // -m
            if (cmdLine.hasOption('m')) {
                ret = this.getMd5(cmdLine);
            }
        }
        return ret;
    }

    // ~ Private Methods =====================================
    private JsonObject getMd5(final CommandLine cmdLine) {
        JsonObject ret = new JsonObject();
        final String input = cmdLine.getOptionValue('m');
        if(StringKit.isNonNil(input)){
            ret.put("MD5 Result", Encryptor.encryptMD5(input));
        }
        return ret;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
