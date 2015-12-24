package com.prayer.vx.console.commands;

import org.apache.commons.cli.CommandLine;

/**
 * 
 * @author Lang
 *
 */
public final class HelpCommand extends AbstractCommand {
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
        return "help";
    }

    // ~ Methods =============================================
    /** **/
    public void execute(final String... args) {
        final CommandLine cl = this.parse(args);
        // 如果包含有-c
        if (cl.hasOption('c')) {
            final String command = cl.getOptionValue('c');
            this.help(command);
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
