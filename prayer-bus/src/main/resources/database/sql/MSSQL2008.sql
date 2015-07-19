-- SQL Server 2008 R2初始化脚本
----------------------------------------------------------------------------------------------------
--【定义全局变量】
/*
DECLARE @sql_checkTable NVARCHAR(MAX);			-- 检查表是否存在的语句
DECLARE @chk_tableName NVARCHAR(256);			-- 需要检查的表名
DECLARE @chk_counter BIGINT;					-- 从系统中按表名读取的表的数量
SET @sql_checkTable = '(SELECT @counter = COUNT(name) FROM SYSOBJECTS WHERE ID = OBJECT_ID(@table) AND OBJECTPROPERTY(ID, ''IsTable'') = 1)';
--【SYS_MODEL模型表定义】
BEGIN
SET @chk_tableName = 'SYS_MODEL'
EXEC sp_executesql @sql_checkTable, N'@counter BIGINT OUTPUT,@table VARCHAR(256)',@chk_counter OUTPUT,@chk_tableName
IF @chk_counter > 0
DROP TABLE SYS_MODEL
CREATE TABLE SYS_MODEL(
	NAME VARCHAR(256) NOT NULL UNIQUE, 		-- 模型名称 
	ROOT_FOLDER VARCHAR(256) NOT NULL,		-- 模型的根目录
	FILE_DATA VARCHAR(256),					-- CSV数据文件名称
	FILE_SCHEMA VARCHAR(256) NOT NULL, 		-- JSON定义文件名称
	FILE_MAPPING VARCHAR(256) NOT NULL, 		-- Mapping文件名称
	IN_USE BIT NOT NULL, 						-- 是否正在使用
	INIT_ORDER INT NOT NULL,					-- 初始化顺序
	INIT_SUB_ORDER INT NOT NULL, 				-- 初始化子顺序
	PRIMARY KEY(NAME)
)
END;
*/