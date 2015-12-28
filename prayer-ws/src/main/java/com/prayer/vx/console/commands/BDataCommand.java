package com.prayer.vx.console.commands;

import org.apache.commons.cli.CommandLine;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class BDataCommand extends AbstractCommand{
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
        return "bdata";
    }
    // ~ Methods =============================================
    @Override
    public JsonObject execute(final String... args){
        final CommandLine cmdLine = this.parse(args);
        // TODO: bdata
        JsonObject ret = null;
        if(null != cmdLine){
            
        }
        return ret;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
