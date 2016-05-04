package com.prayer.launcher.common;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.booter.util.Ensurer;
import com.prayer.business.deployment.impl.DataBllor;
import com.prayer.exception.system.ApiNotSupportException;
import com.prayer.facade.business.instantor.deployment.DataInstantor;
import com.prayer.facade.engine.Launcher;
import com.prayer.facade.engine.cv.msg.MsgDeployment;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.InceptBus;
import com.prayer.resource.Resources;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class DataLauncher implements Launcher {
    // ~ Static Fields =======================================
    /** **/
    private static final DataInstantor INSTANTOR = singleton(DataBllor.class);
    /** **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Deploy.class);
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLauncher.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void start() throws AbstractException {
        if (Ensurer.running(getClass(), LOGGER)) {
            /** 1.先得到对应的Deploy目录 **/
            final String folder = INCEPTOR.getString(Point.Deploy.META_FOLDER) + "/data";
            info(LOGGER, MessageFormat.format(MsgDeployment.DATA_FILE, getClass().getSimpleName(), folder));
            /** 2.导入数据 **/
            final boolean loaded = INSTANTOR.loading(folder);
            if (loaded) {
                info(LOGGER, MessageFormat.format(MsgDeployment.DATA_FIELD, getClass().getSimpleName(),
                        Resources.Data.CATEGORY));
            }
        }
    }

    /** **/
    @Override
    public void stop() throws AbstractException {
        try {
            info(LOGGER, MessageFormat.format(MsgDeployment.DATA_PURGE, getClass().getSimpleName()));
            INSTANTOR.purge();
            info(LOGGER, MessageFormat.format(MsgDeployment.DATA_PURGED, getClass().getSimpleName(),
                    Resources.Data.CATEGORY));
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
        }
    }

    /** **/
    @Override
    public boolean running() {
        try {
            throw new ApiNotSupportException(getClass(), "running");
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
        }
        return false;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
