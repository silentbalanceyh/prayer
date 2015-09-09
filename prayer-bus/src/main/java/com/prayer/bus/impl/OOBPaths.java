package com.prayer.bus.impl;
/**
 * 路径常量
 * @author Lang
 *
 */
interface OOBPaths {	// NOPMD
	/** Verticle配置文件路径 **/
	String VX_VERTICLE = "deploy/oob/vertx/verticle.json";
	/** Route配置文件路径 **/
	String VX_ROUTES = "deploy/oob/vertx/route.json";
	/** URI配置文件路径 **/
	String VX_URI = "deploy/oob/vertx/uri.json";
	/** Schema Folder，用于读取Schema的配置信息 **/
	String SCHEMA_FOLDER = "deploy/oob/schema/";
	/** URI参数配置文件 **/
	String VX_URI_PARAM = "deploy/oob/vertx/uri/";
}
