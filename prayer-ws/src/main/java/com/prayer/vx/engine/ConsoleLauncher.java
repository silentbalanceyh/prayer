package com.prayer.vx.engine;

import static com.prayer.util.Instance.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.exception.system.StartUpArgsInvalidException;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.Symbol;
import com.prayer.vx.console.EngineConsole;
import com.prayer.vx.console.H2DatabaseShell;
import com.prayer.vx.console.HazelcastConsole;

/**
 * 
 * @author Lang
 *
 */
public final class ConsoleLauncher {
    // ~ Static Fields =======================================
    /** **/
    private static final Map<String, String> ARGS_MAP = new TreeMap<>();

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** **/
    static {
        ARGS_MAP.putIfAbsent("-pshell", "Start H2 Metadata Database Shell");
        ARGS_MAP.putIfAbsent("-phazelcast", "Start Hazelcast Console");
        ARGS_MAP.putIfAbsent("-pconsole", "Start Prayer Engine Console");
        ARGS_MAP.putIfAbsent("-help", "Display help information");
    }

    // ~ Static Methods ======================================
    /**
     * 
     * @param args
     * @throws AbstractException
     */
    public static void main(final String... args) throws AbstractException {
        final ConsoleLauncher console = new ConsoleLauncher();
        console.runTool(args);
    }

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
        if (args.length <= Constants.ZERO) {
            System.out.println("Arguments required: -(pshell|phazelcast|pconsole|help) ");
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
        final Set<String> argsSet = ARGS_MAP.keySet();
        final String startArg = args[Constants.ZERO];
        if (0 <= startArg.indexOf('-') && !argsSet.contains(startArg)) {
            throw new StartUpArgsInvalidException(EngineLauncher.class, startArg);
        }
    }

    private void populateHelp() {
        System.out.println("Commands are case insensitive, please refer following comments: ");
        for (final String arg : ARGS_MAP.keySet()) {
            System.out.println(generateArgLine(arg, ARGS_MAP.get(arg)));
        }
    }

    private String generateArgLine(final String arg, final String comments) {
        final int wide = 24 - arg.length();
        final StringBuilder argsLine = new StringBuilder();
        argsLine.append(arg);
        for (int idx = 0; idx < wide; idx++) {
            argsLine.append(Symbol.SPACE);
        }
        argsLine.append(comments);
        return argsLine.toString();
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
