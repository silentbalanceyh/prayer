package com.prayer.vx.console.commands;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.prayer.util.cv.Constants;

/**
 * 
 * @author Lang
 *
 */
public final class HelpCommand extends AbstractCommand {
    // ~ Static Fields =======================================
    /** 命令行名 **/
    private static final String COMMAND = "help";
    // ~ Instance Fields =====================================
    /** **/
    private transient CommandLineParser parser = new DefaultParser();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    public void execute(final String... args) {
        final Options options = this.getOpts(COMMAND);
        CommandLine cl = null;
        try {
            cl = this.parser.parse(options, args);
        } catch (ParseException ex) {
            this.help(COMMAND);
        }
        if (null == cl) {
            this.syntaxError();
        } else {
            if (Constants.ZERO == cl.getOptions().length) {
                this.noOptions();
                this.help(COMMAND);
            } else {
                // 如果包含有-h
                if (cl.hasOption('h')) {
                    this.help(COMMAND);
                }
                // 如果包含有-c
                if (cl.hasOption('c')){
                    final String command = cl.getOptionValue('c');
                    this.help(command);
                }
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
