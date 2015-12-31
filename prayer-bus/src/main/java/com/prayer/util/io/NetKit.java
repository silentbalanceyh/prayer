package com.prayer.util.io;

import static com.prayer.util.debug.Log.jvmError;

import java.io.IOException;

import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class NetKit {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(IOKit.class);
    /** **/
    private static final String VT220 = "VT220";
    /** **/
    private static final TelnetClient TELNET = new TelnetClient(VT220);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /**
     * 
     * @param hostname
     * @param port
     * @return
     */
    public static boolean isUse(@NotNull @NotBlank @NotEmpty final String hostname, @Min(1) final int port) {
        boolean ret = false;
        try {
            TELNET.connect(hostname, port);
            ret = true;
        } catch (IOException ex) {
            jvmError(LOGGER, ex);
            ret = false;
        }
        return ret;
    }
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private NetKit(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
