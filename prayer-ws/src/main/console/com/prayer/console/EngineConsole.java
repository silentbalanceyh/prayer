package com.prayer.console;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractException;
import com.prayer.configurator.VertxConfigurator;
import com.prayer.console.commands.Command;
import com.prayer.constant.Constants;
import com.prayer.util.io.IOKit;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class EngineConsole extends AbstractConsole {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final VertxConfigurator vertxCfg = singleton(VertxConfigurator.class);
    /** 操作列表 **/
    private static final ConcurrentMap<String, Command> OPS = new ConcurrentHashMap<>();

    // ~ Static Block ========================================
    /** 主路径信息 **/
    static {
        OPS.putIfAbsent("exit", CommandSender::exit);
        OPS.putIfAbsent("help", CommandSender::help);
        OPS.putIfAbsent("status", CommandSender::status);
        OPS.putIfAbsent("bdata", CommandSender::bdata);
        OPS.putIfAbsent("mserver", CommandSender::mserver);
        OPS.putIfAbsent("engine", CommandSender::engine);
        OPS.putIfAbsent("schema", CommandSender::schema);
        OPS.putIfAbsent("input", CommandSender::input);
        OPS.putIfAbsent("api", CommandSender::api);
        OPS.putIfAbsent("tool", CommandSender::tool);
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public JsonObject getHelp() {
        final String content = IOKit.getContent("/console/help/commands.json");
        return new JsonObject(content);
    }

    // ~ Methods =============================================
    /**
     * 
     * @param args
     * @throws AbstractException
     */
    public void runTool(final String... args) throws AbstractException {
        // 1.打印Console Header
        this.promptHeader();
        // 2.打印Help信息
        this.populateHelp();
        // 3.进入交互式界面
        while (true) {
            final String arguments[] = this.prompt(vertxCfg.getActiveName() + " > ");
            /** 什么内容都没有的时候直接进入下一次循环 **/
            if (arguments.length == Constants.ZERO) {
                continue;
            } else {
                final String commandArgs[] = this.extractArgs(arguments);
                final String command = arguments[Constants.ZERO];
                // 存在命令的情况
                if (StringKit.isNonNil(command)) {
                    if (this.verifyCommand(command)) {
                        final JsonObject report = OPS.get(command).execute(commandArgs);
                        if (null != report && Constants.ZERO < report.fieldNames().size()) {
                            System.out.println("----------------------- Result -----------------------"); // NOPMD
                            // 4.打印最终执行结果
                            System.out.println(report.encodePrettily()); // NOPMD
                        }
                    } else {
                        // 打印帮助，并且显示错误的命令
                        System.out.println("[ERROR] Command does not exist : " + command); // NOPMD
                        this.populateHelp();
                    }
                }else{
                    this.populateHelp();
                }
            }
        }
    }

    // ~ Private Methods =====================================
    /**
     * 验证命令对不对
     * 
     * @param command
     * @return
     */
    private boolean verifyCommand(final String command) {
        boolean ret = false;
        if (OPS.keySet().contains(command)) {
            ret = true;
        }
        return ret;
    }

    /**
     * 除开第一个参数，直接提取剩余部分的Options信息，这些信息用于Common CLI的Parsing
     * 
     * @param args
     * @return
     */
    private String[] extractArgs(final String... args) {
        final String[] retArgs = new String[args.length - 1];
        System.arraycopy(args, Constants.ONE, retArgs, Constants.ZERO, args.length - 1);
        return retArgs;
    }

    private void promptHeader() {
        System.out.println("Welcome to Prayer Engine Console ( Version : 0.1.0-SNAPSHOT ) "); // NOPMD
        System.out.println("Exit with Ctrl + C"); // NOPMD
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
