package com.prayer.vx.console.commands;

import org.apache.commons.cli.CommandLine;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class MServerCommand extends AbstractCommand{
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
        return "mserver";
    }
    // ~ Methods =============================================
    /**
     * 
     * @param args
     * @return
     */
    public JsonObject execute(final String... args){
        final CommandLine cl = this.parse(args);
        // TODO: 命令mserver的开发
        return null;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
