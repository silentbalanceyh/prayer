package com.prayer.vx.engine;

import static com.prayer.assistant.WebLogger.error;
import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Instance.singleton;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

import org.h2.tools.CreateCluster;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.WebLogger;
import com.prayer.bus.impl.oob.DeploySevImpl;
import com.prayer.facade.bus.DeployService;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.Converter;
import com.prayer.util.cv.Resources;
import com.prayer.util.cv.SystemEnum.ResponseCode;
import com.prayer.vx.configurator.ServerConfigurator;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class H2DatabaseServer {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(H2DatabaseServer.class);
    /** **/
    private static final String DATABASE = "Source Database";
    /** **/
    private static final String WEB_CONSOLE = "Web Console";
    /** **/
    private static final String LOCK_FILE = "PRAYER.lock";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final DeployService service;
    /** **/
    @NotNull
    private transient final ServerConfigurator configurator;
    /** **/
    private transient Server dbServer;
    /** **/
    private transient ConcurrentMap<String, Server> clusters;
    /** **/
    private transient Server webServer;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public H2DatabaseServer() {
        this.service = singleton(DeploySevImpl.class);
        this.configurator = singleton(ServerConfigurator.class);
        if (this.configurator.enabledH2Cluster()) {
            try {
                this.clusters = this.configurator.getH2CDatabases();
                info(LOGGER, WebLogger.I_H2_DB_CLS_INIT_S, Converter.toStr(this.clusters.keySet()));
            } catch (SQLException ex) {
                info(LOGGER, WebLogger.E_H2_DB_ERROR, ex.toString());
            }
        } else {
            info(LOGGER, WebLogger.I_H2_DB_CLS_DIS);
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ H2 Standalone =======================================

    /** 启动H2数据库 **/
    public boolean start() {
        // 1.先启动Console
        boolean started = this.startConsole();
        if (this.configurator.enabledH2Cluster()) {
            // 2.启动Clusters的每一个独立数据库
            for (final String key : this.clusters.keySet()) {
                final Server single = this.clusters.get(key);
                info(LOGGER, WebLogger.I_H2_DB_BEFORE, "Starting", key, String.valueOf(single.getPort()));
                try {
                    single.start();
                    info(LOGGER, WebLogger.I_H2_DB_AFTER_ST, key, String.valueOf(single.getPort()));
                } catch (SQLException ex) {
                    info(LOGGER, WebLogger.E_H2_DB_ERROR, key + ":" + ex.toString());
                }
            }
            // 3.创建一个Cluster
            try {
                final CreateCluster cluster = new CreateCluster();
                final String[] params = this.configurator.getClusterParams();
                cluster.runTool(params);
                started = true;
                info(LOGGER, WebLogger.I_H2_DB_CLS_STD,Converter.toStr(params));
            } catch (SQLException ex) {
                info(LOGGER, WebLogger.E_H2_DB_ERROR, ex.toString());
                ex.printStackTrace();
                started = false;
            }
        } else {
            // 独立启动数据库
            started = this.startSource();
        }
        return started;
    }

    /** 停止Database **/
    public void stop() {
        if (this.configurator.enabledH2Cluster()) {
            // TODO: Debug
        } else {
            if (null != this.dbServer) {
                info(LOGGER, WebLogger.I_H2_DB_BEFORE, "Stopping", DATABASE, String.valueOf(dbServer.getPort()));
                this.dbServer.stop();
                info(LOGGER, WebLogger.I_H2_DB_AFTER_SP, DATABASE);
            }
        }
    }

    /** 停止Console **/
    public void stopConsole() {
        if (null != this.webServer) {
            info(LOGGER, WebLogger.I_H2_DB_BEFORE, "Stopping", WEB_CONSOLE, String.valueOf(webServer.getPort()));
            this.webServer.stop();
            info(LOGGER, WebLogger.I_H2_DB_AFTER_SP, WEB_CONSOLE);
        }
    }
    // ~ H2 Cluster ==========================================

    // ~ H2 Metadata Init ====================================
    /** 初始化元数据 **/
    public boolean initMetadata() {
        boolean flag = false;
        ServiceResult<Boolean> ret = this.service.initH2Database(Resources.DB_SQL_DIR + MetadataConn.H2_SQL);
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            ret = this.service.deployPrayerData();
            if (ResponseCode.SUCCESS == ret.getResponseCode()) { // NOPMD
                flag = ret.getResult();
            }
        }
        return flag;
    }

    /** 创建锁文件 **/
    public boolean createLocks() {
        boolean flag = false;
        final File file = new File(LOCK_FILE);
        if (!file.exists()) {
            try {
                flag = file.createNewFile();
            } catch (IOException ex) {
                error(LOGGER, WebLogger.E_COMMON_EXP, ex.toString());
            }
        }
        return flag;
    }

    /** 检查锁文件 **/
    public boolean checkLocks() {
        boolean flag = false;
        final File file = new File(LOCK_FILE);
        if (file.exists() && file.isFile()) {
            flag = true;
        }
        return flag;
    }

    // ~ Private Methods =====================================
    /** 启动Web Console **/
    private boolean startConsole() {
        boolean ret = true;
        try {
            // 2.Web Console Start
            this.webServer = configurator.getH2WebConsole();
            info(LOGGER, WebLogger.I_H2_DB_BEFORE, "Starting", WEB_CONSOLE, String.valueOf(webServer.getPort()));
            this.webServer.start();
            info(LOGGER, WebLogger.I_H2_DB_AFTER_ST, WEB_CONSOLE, String.valueOf(webServer.getPort()));
        } catch (SQLException ex) {
            info(LOGGER, WebLogger.E_H2_DB_ERROR, ex.toString());
            ret = false;
        }
        return ret;
    }

    /** 启动原始数据库 **/
    private boolean startSource() {
        boolean ret = true;
        try {
            // 1.Database Start
            this.dbServer = configurator.getH2Database();
            info(LOGGER, WebLogger.I_H2_DB_BEFORE, "Starting", DATABASE, String.valueOf(dbServer.getPort()));
            this.dbServer.start();
            info(LOGGER, WebLogger.I_H2_DB_AFTER_ST, DATABASE, String.valueOf(dbServer.getPort()));
        } catch (SQLException ex) {
            info(LOGGER, WebLogger.E_H2_DB_ERROR, ex.toString());
            ret = false;
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
