package com.prayer.launcher.common;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.booter.util.Ensurer;
import com.prayer.business.deployment.impl.DeployBllor;
import com.prayer.exception.system.ApiNotSupportException;
import com.prayer.facade.business.instantor.deployment.DeployInstantor;
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
public class DeploymentLauncher implements Launcher {
    // ~ Static Fields =======================================
    /** **/
    private static final DeployInstantor INSTANTOR = singleton(DeployBllor.class);
    /** **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Deploy.class);
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DeploymentLauncher.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient boolean schema;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    public DeploymentLauncher(final boolean schema) {
        this.schema = schema;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void start() throws AbstractException {
        /** Meta Server的判断 **/
        if (Ensurer.running(getClass(), LOGGER)) {
            /** 1.先执行Purge **/
            this.purgeMeta();
            /** 2.再执行Deploy **/
            this.deployMeta();
        }
    }

    /** **/
    @Override
    public void stop() throws AbstractException {
        this.purgeMeta();
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

    private void purgeMeta() throws AbstractException {
        /** 检查Meta Server是否在运行 **/
        if (Ensurer.running(getClass(), LOGGER)) {
            /** 执行Purge **/
            Ensurer.purge(getClass(), LOGGER, this.schema);
        }
    }

    private void deployMeta() throws AbstractException {
        /** 读取Init File **/
        final String appName = getClass().getSimpleName();
        boolean inited = true;
        if (this.schema) {
            final String file = INCEPTOR.getString(Point.Deploy.INIT_FILE);
            info(LOGGER, MessageFormat.format(MsgDeployment.INIT_FILE, appName, file));
            /** 执行Init **/
            inited = INSTANTOR.initialize(file);
        }
        if (inited) {
            /** 读取Folder **/
            info(LOGGER, MessageFormat.format(MsgDeployment.INIT_FILED, appName, Resources.Meta.CATEGORY));
            final String folder = INCEPTOR.getString(Point.Deploy.META_FOLDER);
            info(LOGGER, MessageFormat.format(MsgDeployment.INIT_META, appName, folder));
            /** 执行Deploy **/
            inited = INSTANTOR.manoeuvre(folder, this.schema);
            if (inited) {
                info(LOGGER, MessageFormat.format(MsgDeployment.INIT_METAED, appName, Resources.Meta.CATEGORY));
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
