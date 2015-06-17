package com.prayer.h2db;

import java.sql.SQLException;

import jodd.typeconverter.Convert;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * H2 Database嵌入式服务【Mixed模式】
 *
 * @author Lang
 * @see
 */
public class H2Server {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(H2Server.class);
	/**
	 * Tcp连接端口
	 */
	private static final int TCP_PORT = 9081;
	// ~ Instance Fields =====================================
	/**
	 * H2 Server
	 */
	private transient Server server;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * H2 Server实例
	 */
	public H2Server(){
		if( null == server ){
			try{
				server = Server.createTcpServer("-TCP_PORT",Convert.toString(TCP_PORT));
				LOGGER.debug("[E] H2 Server initialized!");
			}catch(SQLException ex){
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug("[E] H2 Server init failure...",ex);
				}
			}
		}
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	public void start(){
		if(null != server){
			try{
				server.start();
				LOGGER.debug("[E] H2 Server started!");
			}catch(SQLException ex){
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug("[E] H2 Server start failure...",ex);
				}
			}
		}
	}
	/** **/
	public void stop(){
		if(null != server){
			server.shutdown();
			LOGGER.debug("[E] H2 Server stopped!");
		}
	}
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
