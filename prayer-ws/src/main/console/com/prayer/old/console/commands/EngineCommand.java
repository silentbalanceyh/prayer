package com.prayer.old.console.commands;

import org.apache.commons.cli.CommandLine;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class EngineCommand extends AbstractCommand {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String command(){
        return "engine";
    }
    // ~ Methods =============================================
    /** **/
    public JsonObject execute(final String... args){
        final CommandLine cmdLine = this.parse(args);
        // TODO: 命令engine的开发
        if (null != cmdLine) {
            
        }
        return null;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
