package com.prayer.util.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.prayer.facade.engine.rmi.StandardQuoter;

/**
 * 
 * @author Lang
 *
 */
public class CommonOptionsQuoter extends UnicastRemoteObject implements StandardQuoter {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -709340150270708163L;
    // ~ Instance Fields =====================================
    /** **/
    private String reference;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public CommonOptionsQuoter(final String reference) throws RemoteException {
        this.reference = reference;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String getData() {
        /** 执行Service赋值 **/
        return this.reference;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
