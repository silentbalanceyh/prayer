package com.prayer.old.console.commands;

import org.apache.commons.cli.CommandLine;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class InputCommand extends AbstractCommand{
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
    public String command(){
        return "input";
    }
    // ~ Methods =============================================
    /**
     * 
     */
    @Override
    public JsonObject execute(final String... args){
        final CommandLine cmdLine = this.parse(args);
        // TODO: input
        JsonObject ret = null;
        if(null != cmdLine){
            
        }
        return ret;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
