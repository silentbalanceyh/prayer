package com.prayer.engine;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.singleton;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

import org.h2.tools.CreateCluster;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.configurator.ServerConfigurator;
import com.prayer.constant.Resources;
import com.prayer.facade.business.deployment.DeployInstantor;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.Converter;

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
    private static final String I_H2_DB_BEFORE = "{0} H2 {1} on {2}...";
    /** **/
    private static final String I_H2_DB_AFTER_ST = "H2 {0} started: RUNNING on {1} !";
    /** **/
    private static final String I_H2_DB_AFTER_SP = "H2 {0} stopped.";
    /** **/
    private static final String I_H2_DB_CLS_INIT = "H2 Database Cluster initialized successfully! Name List : {0}";
    /** **/
    private static final String I_H2_DB_CLS_STD = "H2 Database Cluster has been started successfully with parameters: {0}";
    /** **/
    private static final String I_H2_DB_CLS_DIS = "H2 Database Cluster has been disabled!";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient DeployInstantor service;
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
        // this.service = singleton(DeploySevImpl.class);
        this.configurator = singleton(ServerConfigurator.class);
        if (this.configurator.enabledH2Cluster()) {
            try {
                this.clusters = this.configurator.getH2CDatabases();
                info(LOGGER, I_H2_DB_CLS_INIT, Converter.toStr(this.clusters.keySet()));
            } catch (SQLException ex) {
                jvmError(LOGGER, ex);
            }
        } else {
            info(LOGGER, I_H2_DB_CLS_DIS);
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
            this.clusters.keySet().parallelStream().forEach(key -> {
                final Server single = this.clusters.get(key);
                info(LOGGER, I_H2_DB_BEFORE, "Starting", key, String.valueOf(single.getPort()));
                try {
                    if (isRunning(single)) {
                        info(LOGGER, key + " is already started on " + single.getPort());
                    } else {
                        single.start();
                        info(LOGGER, I_H2_DB_AFTER_ST, key, String.valueOf(single.getPort()));
                    }
                } catch (SQLException ex) {
                    jvmError(LOGGER, ex);
                }
            });
            // 3.创建一个Cluster
            try {
                final CreateCluster cluster = new CreateCluster();
                final String[] params = this.configurator.getClusterParams();
                cluster.runTool(params);
                started = true;
                info(LOGGER, I_H2_DB_CLS_STD, Converter.toStr(params));
            } catch (SQLException ex) {
                jvmError(LOGGER, ex);
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
            // TODO: 停止H2 Cluster的代码
        } else {
            if (null != this.dbServer) {
                info(LOGGER, I_H2_DB_BEFORE, "Stopping", DATABASE, String.valueOf(dbServer.getPort()));
                this.dbServer.stop();
                info(LOGGER, I_H2_DB_AFTER_SP, DATABASE);
            }
        }
    }

    /** 停止Console **/
    public void stopConsole() {
        if (null != this.webServer) {
            info(LOGGER, I_H2_DB_BEFORE, "Stopping", WEB_CONSOLE, String.valueOf(webServer.getPort()));
            this.webServer.stop();
            info(LOGGER, I_H2_DB_AFTER_SP, WEB_CONSOLE);
        }
    }
    // ~ H2 Cluster ==========================================

    // ~ H2 Metadata Init ====================================
    /**
     * 
     * @return
     */
    public boolean isDeployed() {
        final File file = new File("DEPLOYED.lock");
        boolean exist = false;
        if (file.exists()) {
            exist = true;
        }
        return exist;
    }

    /** 初始化OOB元数据 **/
    public boolean initMetadata() {
        return this.initMetadata(Resources.OOB_DATA_FOLDER);
    }

    /** 按照目录初始化元数据 **/
    public boolean initMetadata(final String dataFolder) {
        boolean flag = false;
        try {
            flag = this.service.initialize("initialize");
            if (flag) {
                flag = this.service.manoeuvre(dataFolder);
                if (flag) {
                    // 创建所文件
                    this.createLock();
                }
            }
        } catch (AbstractException ex) {
            flag = false;
        }
        return flag;
    }

    // ~ Private Methods =====================================

    private boolean createLock() {
        final File file = new File("DEPLOYED.lock");
        boolean ret = true;
        if (!file.exists()) {
            try {
                ret = file.createNewFile();
            } catch (IOException ex) {
                ret = false;
                jvmError(LOGGER, ex);
            }
        }
        return ret;
    }

    /** 启动Web Console **/
    private boolean startConsole() {
        boolean ret = true;
        try {
            // 2.Web Console Start
            this.webServer = configurator.getH2WebConsole();
            info(LOGGER, I_H2_DB_BEFORE, "Starting", WEB_CONSOLE, String.valueOf(webServer.getPort()));
            if (isRunning(this.webServer)) {
                info(LOGGER, " H2 WebConsole (Standalone) is already started on " + this.webServer.getPort());
            } else {
                this.webServer.start();
                info(LOGGER, I_H2_DB_AFTER_ST, WEB_CONSOLE, String.valueOf(webServer.getPort()));
            }
        } catch (SQLException ex) {
            jvmError(LOGGER, ex);
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
            info(LOGGER, I_H2_DB_BEFORE, "Starting", DATABASE, String.valueOf(dbServer.getPort()));
            if (isRunning(this.dbServer)) {
                info(LOGGER, " H2 Database (Standalone) is already started on " + this.dbServer.getPort());
            } else {
                this.dbServer.start();
                info(LOGGER, I_H2_DB_AFTER_ST, DATABASE, String.valueOf(dbServer.getPort()));
            }
            // 2.构造JDBC URL
        } catch (SQLException ex) {
            jvmError(LOGGER, ex);
            ret = false;
        }
        return ret;
    }

    /** 检查端口是否占用 **/
    private boolean isRunning(final Server server) {
        boolean ret = false;
        try {
            final ServerSocket socket = new ServerSocket(server.getPort());
            ret = false;
            socket.close();
        } catch (IOException ex) {
            ret = true;
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
