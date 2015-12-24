package com.prayer.vx.console;

import static com.prayer.util.Instance.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractException;
import com.prayer.util.IOKit;
import com.prayer.util.StringKit;
import com.prayer.util.cv.Constants;
import com.prayer.vx.configurator.VertxConfigurator;
import com.prayer.vx.console.commands.Command;

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
            String arguments[] = this.prompt(vertxCfg.getActiveName() + " > ");
            /** 什么内容都没有的时候直接进入下一次循环 **/
            if (arguments.length == Constants.ZERO) {
                continue;
            } else {
                final String commandArgs[] = this.extractArgs(arguments);
                final String command = arguments[Constants.ZERO];
                // 存在命令的情况
                if (StringKit.isNonNil(command)) {
                    final JsonObject report = OPS.get(command).execute(commandArgs);
                    if (null != report) {
                        // 4.打印最终执行结果
                        System.out.println(report.encodePrettily());
                    }
                }
            }
        }
    }

    // ~ Private Methods =====================================
    /**
     * 除开第一个参数，直接提取剩余部分的Options信息，这些信息用于Common CLI的Parsing
     * 
     * @param args
     * @return
     */
    private String[] extractArgs(final String... args) {
        final String[] retArgs = new String[args.length - 1];
        for (int idx = 1; idx < args.length; idx++) {
            retArgs[idx - 1] = args[idx];
        }
        return retArgs;
    }

    private void promptHeader() {
        System.out.println("Welcome to Prayer Engine Console ( Version : 0.1.0-SNAPSHOT ) ");
        System.out.println("Exit with Ctrl + C");
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
