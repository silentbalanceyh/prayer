package com.prayer.console.thirdpart.h2;

import java.sql.SQLException;

import org.h2.tools.Shell;

import com.prayer.console.util.OutGoing;
import com.prayer.facade.console.message.H2Tidings;

/**
 * 
 * @author Lang
 *
 */
public class H2ShellThread implements Runnable {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient String[] params;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param params
     */
    public H2ShellThread(final String[] params){
        this.params = params;
    }
    /** **/
    public void run(){
        try{
            /** 2.实例化H2 **/
            final Shell shell = new Shell();
            shell.runTool(params);
        }catch(SQLException ex){
            OutGoing.outLn(H2Tidings.Error.SHELL, ex.getMessage());
        }
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
