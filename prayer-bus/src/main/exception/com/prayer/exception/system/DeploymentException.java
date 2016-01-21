package com.prayer.exception.system;

import com.prayer.fantasm.exception.AbstractSystemException;

/**
 * 
 * @author Lang
 *
 */
public class DeploymentException extends AbstractSystemException {

    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 3591133195003928601L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param process
     */
    public DeploymentException(final Class<?> clazz) {
        super(clazz, -20008);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -20008;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
