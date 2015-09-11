package com.prayer.vx.engine;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.DeployService;
import com.prayer.bus.impl.DeploySevImpl;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.db.conn.MetadataConn;
import com.prayer.handler.web.ConversionHandler;
import com.prayer.model.bus.ServiceResult;

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
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient final DeployService service;
	/** **/
	private transient Server dbServer;
	/** **/
	private transient Server webServer;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public H2DatabaseServer(){
		service = singleton(DeploySevImpl.class);
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** 启动H2数据库 **/
	public boolean start(){
		boolean ret = true;
		try {
			// 1.Database Start
			info(LOGGER,"[H2] Starting H2 Database on 8081...");
			this.dbServer = Server.createTcpServer(new String[]{
				"-tcpPort","8081","-tcpAllowOthers"
			});
			this.dbServer.start();
			info(LOGGER,"[H2] H2 Database started: RUNNING on 8081");
			// 2.
			info(LOGGER,"[H2] Starting H2 Database Web Console on 8082...");
			this.webServer = Server.createWebServer(new String[]{
				"-webPort","8082","-webAllowOthers"	
			});
			this.webServer.start();
			info(LOGGER,"[H2] H2 Database Web Console started: RUNNING on 8082");
		} catch (SQLException ex) {
			info(LOGGER,"[H2] Start H2 Database Error.",ex);
			ret = false;
		}
		return ret;
	}
	/** 初始化元数据 **/
	public boolean initMetadata(){
		boolean flag = false;
		ServiceResult<Boolean> ret = this.service.initH2Database(Resources.DB_SQL_DIR + MetadataConn.H2_SQL);
		if(ResponseCode.SUCCESS == ret.getResponseCode()){
			ret = this.service.deployPrayerData();
			if(ResponseCode.SUCCESS == ret.getResponseCode()){
				flag = ret.getResult();
			}
		}
		return flag;
	}
	/** 停止Database **/
	public void stop(){
		if(null != this.dbServer){
			info(LOGGER,"[H2] Stopping H2 Database on 8081...");
			this.dbServer.stop();
			info(LOGGER,"[H2] H2 Database stopped.");
		}
		if(null != this.webServer){
			info(LOGGER,"[H2] Stopping H2 Database Web Console on 8082...");
			this.webServer.stop();
			info(LOGGER,"[H2] H2 Database Web Console stopped.");
		}
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
