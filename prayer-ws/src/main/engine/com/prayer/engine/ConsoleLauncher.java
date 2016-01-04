package com.prayer.engine;

import static com.prayer.util.Instance.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.console.AbstractConsole;
import com.prayer.console.EngineConsole;
import com.prayer.console.H2DatabaseShell;
import com.prayer.console.HazelcastConsole;
import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.exception.system.StartUpArgsInvalidException;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public final class ConsoleLauncher extends AbstractConsole {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 创建启动器
     * @return
     */
    public static ConsoleLauncher create(){
        // 开启Console模式
        Resources.useConsole = true;
        return new ConsoleLauncher();
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * Help Map Information
     */
    @Override
    public JsonObject getHelp() {
        final String content = IOKit.getContent("/console/help/engine.json");
        return new JsonObject(content);
    }

    // ~ Methods =============================================
    /**
     * 
     * @param args
     * @throws AbstractException
     */
    public void runTool(final String... args) throws AbstractException {
        if (args.length <= Constants.ZERO) {
            System.out.println("Arguments required: -(pshell|phazelcast|pconsole|help) "); // NOPMD
            this.populateHelp();
        } else {
            // 1.Verify
            this.verifyArgs(args);
            // 2.Preparing Inner Arguments
            final List<String> argsArr = new ArrayList<>();
            for (int idx = Constants.ONE; idx < args.length; idx++) {
                argsArr.add(args[idx]);
            }
            // 启动H2 Console
            if ("-pshell".equals(args[Constants.ZERO])) {
                final H2DatabaseShell shell = singleton(H2DatabaseShell.class);
                shell.start();
            } else if ("-phazelcast".equals(args[Constants.ZERO])) {
                final HazelcastConsole console = singleton(HazelcastConsole.class);
                // 启动Hazelcast Console
                console.runTool(argsArr.toArray(Constants.T_STR_ARR));
            } else if ("-help".equals(args[Constants.ZERO])) {
                // 打印Help信息
                this.populateHelp();
            } else if ("-pconsole".equals(args[Constants.ZERO])) {
                // 启动Prayer Console
                final EngineConsole console = new EngineConsole();
                console.runTool(argsArr.toArray(Constants.T_STR_ARR));
            }
        }
    }

    // ~ Private Methods =====================================

    private void verifyArgs(final String... args) throws AbstractSystemException {
        final Set<String> argsSet = this.getHelp().fieldNames();
        final String startArg = args[Constants.ZERO];
        if (0 <= startArg.indexOf('-') && !argsSet.contains(startArg)) {
            throw new StartUpArgsInvalidException(EngineLauncher.class, startArg);
        }
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
