package com.prayer.metaserver.h2.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.prayer.facade.engine.metaserver.h2.H2Quoter;

/**
 * 
 * @author Lang
 *
 */
public class H2OptionsQuoter extends UnicastRemoteObject implements H2Quoter {
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
    public H2OptionsQuoter(final String reference) throws RemoteException {
        this.reference = reference;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String service(final String reference) {
        /** 执行Service赋值 **/
        return this.reference;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
