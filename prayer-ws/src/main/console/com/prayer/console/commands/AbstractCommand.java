package com.prayer.console.commands;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractCommand implements Command{
    // ~ Static Fields =======================================
    /** **/
    private static final JsonObject COMMANDS;

    /** **/
    protected static final String ERROR = "error";
    // ~ Instance Fields =====================================
    /** **/
    private transient final CommandLineParser parser = new DefaultParser();

    // ~ Static Block ========================================
    /** **/
    static {
        final String content = IOKit.getContent("/console/help/usage.json");
        COMMANDS = new JsonObject(content);
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /**
     * 
     * @return
     */
    public abstract String command();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @return
     */
    protected CommandLineParser getParser() {
        return this.parser;
    }

    /**
     * 获取Command的Options
     * 
     * @return
     */
    protected Options getOpts(@NotNull @NotBlank @NotEmpty final String command) {
        final String content = IOKit.getContent("/console/" + command + ".json");
        final JsonArray optionJson = new JsonArray(content);
        final Options retOpts = new Options();
        for (int idx = 0; idx < optionJson.size(); idx++) {
            final JsonObject item = optionJson.getJsonObject(idx);
            final JsonArray args = item.getJsonArray("args");
            Option opt = null;
            if (Constants.ZERO < args.size()) {
                // 带参�?
                final Builder builder = Option.builder(item.getString("single"));
                builder.argName(args.getString(Constants.ZERO));
                builder.desc(item.getString("description"));
                builder.hasArg();
                builder.longOpt(item.getString("name"));
                opt = builder.build();
            } else {
                // 不带参数
                opt = new Option(item.getString("single"), item.getString("name"), false,      // NOPMD
                        item.getString("description"));
            }
            opt.setRequired(item.getBoolean("required"));
            retOpts.addOption(opt);
        }
        return retOpts;
    }

    /**
     * 
     * @param args
     * @return
     */
    protected CommandLine parse(final String... args) {
        final Options options = this.getOpts(command());
        CommandLine cmdLine = null;
        try {
            cmdLine = this.getParser().parse(options, args);
        } catch (ParseException ex) {
            this.help(command());
        }
        if (null == cmdLine) {
            // 解析异常打印语法�?
            this.syntaxError();
        } else {
            if (Constants.ZERO == cmdLine.getOptions().length) {
                // 如果没有任何Options则直接报错，不支持无Args的输�?
                this.noOptions();
                this.help(command());
            } else {
                // 解析共享参数-h，打印当前Command的Help信息
                if (cmdLine.hasOption('h')) {
                    this.help(command());
                    // 直接跳出
                    cmdLine = null;
                }
            }
        }
        return cmdLine;
    }

    /**
     * 显示Help信息
     */
    protected void help(@NotNull @NotBlank @NotEmpty final String command) {
        if (this.verifyCommand(command)) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(COMMANDS.getString(command), this.getOpts(command));
            System.out.println();      // NOPMD
        } else {
            System.out.println("[ERROR] Command name " + command + " is unavailable!");      // NOPMD
            final StringBuilder builder = new StringBuilder();
            for (final String cmd : COMMANDS.fieldNames()) {
                builder.append(cmd).append(Symbol.COMMA);
            }
            builder.delete(builder.length() - 1, builder.length());
            System.out.println("Available Commands:\n\t" + builder.toString());      // NOPMD
        }
    }

    private void noOptions() {
        System.out.println("[WARNING] No Option provided.");   // NOPMD
    }

    /**
     * 语法错误
     */
    private void syntaxError() {
        System.out.println("[ERROR] Syntax Error when execute command.");   // NOPMD
    }

    // ~ Private Methods =====================================
    private boolean verifyCommand(final String command) {
        boolean ret = false;
        if (COMMANDS.containsKey(command)) {
            ret = true;
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
