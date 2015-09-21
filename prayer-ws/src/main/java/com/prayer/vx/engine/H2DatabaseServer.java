package com.prayer.vx.engine;

import static com.prayer.assistant.WebLogger.error;
import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Instance.singleton;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.WebLogger;
import com.prayer.bus.deploy.oob.DeploySevImpl;
import com.prayer.bus.std.DeployService;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.db.conn.MetadataConn;
import com.prayer.handler.web.ConversionHandler;
import com.prayer.model.bus.ServiceResult;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ConversionHandler.class);
    /** **/
    private static final String DATABASE = "Database";
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
    private transient Server webServer;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public H2DatabaseServer() {
        this.service = singleton(DeploySevImpl.class);
        this.configurator = singleton(ServerConfigurator.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 启动H2数据库 **/
    public boolean start() {
        boolean ret = true;
        try {
            // 1.Database Start
            this.dbServer = configurator.getH2Database();
            info(LOGGER, WebLogger.I_H2_DB_BEFORE, "Starting", DATABASE, String.valueOf(dbServer.getPort()));
            this.dbServer.start();
            info(LOGGER, WebLogger.I_H2_DB_AFTER_ST, DATABASE, String.valueOf(dbServer.getPort()));

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

    /** 停止Database **/
    public void stop() {
        if (null != this.dbServer) {
            info(LOGGER, WebLogger.I_H2_DB_BEFORE, "Stopping", DATABASE, String.valueOf(dbServer.getPort()));
            this.dbServer.stop();
            info(LOGGER, WebLogger.I_H2_DB_AFTER_SP, DATABASE);
        }
        if (null != this.webServer) {
            info(LOGGER, WebLogger.I_H2_DB_BEFORE, "Stopping", WEB_CONSOLE, String.valueOf(webServer.getPort()));
            this.webServer.stop();
            info(LOGGER, WebLogger.I_H2_DB_AFTER_SP, WEB_CONSOLE);
        }
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
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
