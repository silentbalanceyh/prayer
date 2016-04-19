package com.prayer.metaserver.h2.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.configurator.ServerConfigurator;
import com.prayer.facade.engine.Options;
import com.prayer.facade.engine.metaserver.h2.H2Server;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.metaserver.h2.AbstractH2Server;
import com.prayer.metaserver.model.MetaOptions;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ClusterServer extends AbstractH2Server {
    // ~ Static Fields =======================================
    /** **/
    private static H2Server INSTANCE = null;

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterServer.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient Options options = null;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param options
     * @return
     */
    public static H2Server create(@NotNull @InstanceOfAny(MetaOptions.class) final Options options){
        if(null == INSTANCE){
            INSTANCE = new ClusterServer(options);
        }
        return INSTANCE;
    }
    // ~ Constructors ========================================
    /** **/
    private ClusterServer(final Options options){
        super(options);
        this.options = options;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger(){
        return LOGGER;
    }
    /** **/
    @Override
    public boolean start() throws AbstractException {
        // TODO Auto-generated method stub
        return false;
    }

    /** **/
    @Override
    public boolean stop() throws AbstractException {
        // TODO Auto-generated method stub
        return false;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
