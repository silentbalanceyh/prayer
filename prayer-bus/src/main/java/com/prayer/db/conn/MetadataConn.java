package com.prayer.db.conn;

import java.io.InputStream;

import com.prayer.model.meta.Metadata;

/**
 * 元数据操作
 * 
 * @author Lang
 */
public interface MetadataConn {
	// ~ Constants =======================================
	/**
	 * H2 初始化SQL语句
	 */
	String H2_SQL = "metadata/H2-INIT.sql";

	// ~ Method ==========================================
	// region Metadata: Database Information
	/**
	 * 获取当前数据库连接的元数据
	 * 
	 * @return
	 */
	Metadata getMetadata();

	/**
	 * 导入SQL文件
	 * 
	 * @param sqlFile
	 * @return
	 */
	boolean loadSqlFile(InputStream sqlFile);

	/**
	 * H2 Database中创建对应的表结构
	 * 
	 * @return
	 */
	boolean initMeta(InputStream sqlFile);

	// endregion
}
