package com.prayer.metaserver.h2.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.h2.tools.Server;

import com.prayer.facade.engine.metaserver.h2.H2RemoteQuoter;

/**
 * 
 * @author Lang
 *
 */
public class H2ServerQuoter extends UnicastRemoteObject implements H2RemoteQuoter {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -709340150270708163L;
    // ~ Instance Fields =====================================
    /** **/
    private Server reference;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public H2ServerQuoter(final Server reference) throws RemoteException {
        this.reference = reference;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public Server service(final Server reference) {
        return this.reference;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
