package com.prayer.vx.console;

import static com.prayer.util.Instance.singleton;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.prayer.base.exception.AbstractException;
import com.prayer.vx.configurator.VertxConfigurator;

/**
 * 
 * @author Lang
 *
 */
public class EngineConsole {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final VertxConfigurator vertxCfg = singleton(VertxConfigurator.class);
    /** **/
    private transient final CommandLineParser parser = new DefaultParser();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param args
     * @throws AbstractException
     */
    public void runTool(final String... args) throws AbstractException {
        
    }

    // ~ Private Methods =====================================
    private Options getConsoleOpts() {
        final Options options = new Options();
        Option opt = new Option("h", "help", false, "Display Help Information");
        opt.setRequired(false);
        options.addOption(opt);
        return options;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
