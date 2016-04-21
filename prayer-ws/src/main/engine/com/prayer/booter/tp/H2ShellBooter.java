package com.prayer.booter.tp;

import org.apache.log4j.PropertyConfigurator;

import com.prayer.console.thirdpart.H2ShellConsoler;
import com.prayer.facade.console.Consoler;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;
import com.prayer.util.resource.DatumLoader;

import net.sf.oval.internal.Log;
import net.sf.oval.logging.LoggerFactorySLF4JImpl;

/**
 * 
 * @author Lang
 *
 */
public class H2ShellBooter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static void main(final String args[]) {
        /** 1.在Consoler中替换日志输出 **/
        Log.setLoggerFactory(new LoggerFactorySLF4JImpl());
        /** 2.设置SLF4J的日志级别，不输出INFO日志 **/
        final Inceptor inceptor = InceptBus.build(Point.Console.class);
        /** 3.运行过程就直接输出 **/
        PropertyConfigurator.configure(DatumLoader.getLoader(inceptor.getString(Point.Console.SLF4J_CONFIG)));
        /** 4.配置完成过后启动Consoler **/
        final Consoler consoler = new H2ShellConsoler();
        consoler.start();
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
