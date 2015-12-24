package com.prayer.vx.console.commands;

import org.apache.commons.cli.CommandLine;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class StatusCommand extends AbstractCommand {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String command() {
        return "status";
    }
    // ~ Methods =============================================
    /** **/
    public JsonObject execute(final String... args){
        final CommandLine cl = this.parse(args);
        // TODO: 命令status的-d,-m,-v,-p,-u参数逻辑
        return null;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
