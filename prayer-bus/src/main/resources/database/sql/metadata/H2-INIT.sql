--------------------------------------------------------------------------------------
-- SYS_META 核心元数据表
DROP TABLE IF EXISTS SYS_META;
CREATE TABLE SYS_META(
	-- 元数据对应的ID							
	K_ID VARCHAR(192),							-- Meta元素ID标识符，GUID格式
	-- 模型的配置属性
	C_OOBDATA_FILE VARCHAR(256),				-- OOB数据文件目录，CSV文件
	C_IN_USE BOOLEAN NOT NULL, 					-- 是否正在使用
	-- 模型的配置属性
	S_NAME VARCHAR(256) NOT NULL, 				-- 模型名称
	S_NAMESPACE VARCHAR(256) NOT NULL,			-- 模型名空间
	S_CATEGORY CHAR(8) NOT NULL 				-- ENTITY | RELATION
		CHECK(S_CATEGORY = 'ENTITY' OR S_CATEGORY='RELATION'),				
	S_GLOBAL_ID VARCHAR(256) NOT NULL UNIQUE,	-- Global ID
	S_MAPPING CHAR(10) NOT NULL					-- DIRECT | COMBINATED | PARTIAL
		CHECK(S_MAPPING = 'DIRECT' OR S_MAPPING='COMBINATED' OR S_MAPPING='PARTIAL'),
	S_POLICY CHAR(10) NOT NULL					-- GUID | INCREMENT | ASSIGNED | COLLECTION		
		CHECK(S_POLICY = 'GUID' OR S_POLICY='INCREMENT' OR S_POLICY='ASSIGNED' OR S_POLICY='COLLECTION'),
	-- 数据库属性
	D_TABLE VARCHAR(256) NOT NULL,				-- 数据库表名
	D_SUB_TABLE VARCHAR(256),					-- 数据库子表名
	D_SUB_KEY VARCHAR(256),						-- 数据库子表ID
	D_SEQ_NAME VARCHAR(256),					-- Oracle中使用的序列名
	D_SEQ_STEP INT								-- 如果使用自增长则表示自增长的梯度
		CHECK(D_SEQ_STEP > 0),
	D_SEQ_INIT INT,								-- 自增长的起始数据
		CHECK(D_SEQ_INIT > 0),
	-- 约束定义
	PRIMARY KEY(K_ID)
);
-- SYS_META的索引创建，主针对查询
CREATE INDEX IDX_META_NAME ON SYS_META(S_NAME);
CREATE INDEX IDX_META_NAMESPACE ON SYS_META(S_NAMESPACE);
CREATE INDEX IDX_META_CATEGORY ON SYS_META(S_CATEGORY);
CREATE INDEX IDX_META_GLOBAL_ID ON SYS_META(S_GLOBAL_ID);
CREATE INDEX IDX_META_MAPPING ON SYS_META(S_MAPPING);
CREATE INDEX IDX_META_POLICY ON SYS_META(S_POLICY);
CREATE INDEX IDX_META_IN_USE ON SYS_META(C_IN_USE);

--------------------------------------------------------------------------------------
-- SYS_KEY 核心键值表
DROP TABLE IF EXISTS SYS_KEYS;
CREATE TABLE SYS_KEYS(
	K_ID VARCHAR(192),							-- Keys对应的ID，GUID格式
	-- Key的系统属性
	S_NAME VARCHAR(256) NOT NULL,				-- 系统键名称
	S_CATEGORY CHAR(10) NOT NULL				-- 键的类型
		CHECK(S_CATEGORY = 'PrimaryKey' OR S_CATEGORY = 'ForeignKey' OR S_CATEGORY='UniqueKey'),
	IS_MULTI BOOLEAN NOT NULL,					-- 是否跨字段
	S_COLUMNS CLOB NOT NULL,					-- 列信息，Json格式，对应字段名（SYS_FIELDS）的集合
	-- 关联属性
	R_META_ID VARCHAR(192),						-- 关联SYS_META表
	PRIMARY KEY(K_ID),
	FOREIGN KEY(R_META_ID) REFERENCES SYS_META(K_ID)
);
-- SYS_KEYS的索引创建，主针对查询
CREATE INDEX IDX_KEYS_NAME ON SYS_KEYS(S_NAME);
CREATE INDEX IDX_KEYS_CATEGORY ON SYS_KEYS(S_CATEGORY);
CREATE INDEX IDX_KEYS_IS_MULTI ON SYS_KEYS(IS_MULTI);
CREATE INDEX RIDX_KEYS_META_ID ON SYS_KEYS(R_META_ID);

--------------------------------------------------------------------------------------
-- SYS_FIELD 核心字段表
DROP TABLE IF EXISTS SYS_FIELDS;
CREATE TABLE SYS_FIELDS(
	-- 字段的ID标识符
	K_ID VARCHAR(192),							-- Fields对应的ID标识符，GUID格式
	-- Field的系统属性
	S_NAME VARCHAR(256) NOT NULL,				-- 字段名称
	S_TYPE CHAR(16) NOT NULL					-- 字段类型
		CHECK(S_TYPE='BooleanType' OR S_TYPE='IntType' OR S_TYPE='LongType' 
		OR S_TYPE='DateType' OR S_TYPE='StringType' OR S_TYPE='BinaryType' 
		OR S_TYPE='DecimalType' OR S_TYPE='JsonType' OR S_TYPE='XmlType' OR S_TYPE='ScriptType'),
	-- Constraints对应的属性
	C_PATTERN VARCHAR(256),						-- StringType: 字段需要满足的格式正则表达式
	C_VALIDATOR VARCHAR(256),					-- 验证器对应的Validator
	C_LENGTH INT								-- 字段的长度
		CHECK(C_LENGTH >= 0),
	C_DATETIME CHAR(6)							-- STRING | TIMER
		CHECK(C_DATETIME='STRING' OR C_DATETIME='TIMER'),
	C_DATEFORMAT VARCHAR(32),					-- 时间格式的pattern
	C_PRECISION SMALLINT(16),					-- 浮点数精度描述
	C_UNIT VARCHAR(32),							-- 当前数据的单位描述
	C_MAX_LENGTH INT							-- 当前字符串最大长度
		CHECK(C_MAX_LENGTH >= -1),
	C_MIN_LENGTH INT							-- 当前字符串最小长度
		CHECK(C_MIN_LENGTH >= -1),
	C_MAX BIGINT,								-- 最大值
	C_MIN BIGINT,								-- 最小值
	-- 数据库对应的一部分约束
	IS_PRIMARY_KEY BOOLEAN NOT NULL,			-- bool: 当前字段是否主键
	IS_UNIQUE BOOLEAN NOT NULL,					-- bool: 当前字段是否Unique的
	IS_SUB_TABLE BOOLEAN NOT NULL,				-- bool: 当前字符是否属于子表字段
	IS_FOREIGN_KEY BOOLEAN NOT NULL,			-- bool: 当前字段是否外键
	IS_NULLABLE BOOLEAN NOT NULL,				-- bool: 当前字段是否可为null
	-- 数据库属性
	D_COLUMN_NAME VARCHAR(256) NOT NULL,		-- 数据列名称
	D_COLUMN_TYPE VARCHAR(256) NOT NULL,		-- 数据列类型
	D_REF_TABLE VARCHAR(256),					-- 当前字段所用于的引用表
	D_REF_ID VARCHAR(256),						-- 当前字段所用于的表的主键名
	-- 关联属性
	R_META_ID VARCHAR(192),						-- 关联SYS_META表 
	PRIMARY KEY(K_ID),
	FOREIGN KEY(R_META_ID) REFERENCES SYS_META(K_ID)
);
-- SYS_FIELDS的索引创建，主针对查询
CREATE INDEX IDX_FIELDS_NAME ON SYS_FIELDS(S_NAME);
CREATE INDEX IDX_FIELDS_TYPE ON SYS_FIELDS(S_TYPE);
CREATE INDEX IDX_FIELDS_LENGTH ON SYS_FIELDS(C_LENGTH);
CREATE INDEX IDX_FIELDS_DATETIME ON SYS_FIELDS(C_DATETIME);
CREATE INDEX IDX_FIELDS_MAX_LENGTH ON SYS_FIELDS(C_MAX_LENGTH);
CREATE INDEX IDX_FIELDS_MIN_LENGTH ON SYS_FIELDS(C_MIN_LENGTH);
CREATE INDEX IDX_FIELDS_MAX ON SYS_FIELDS(C_MAX);
CREATE INDEX IDX_FIELDS_MIN ON SYS_FIELDS(C_MIN);
CREATE INDEX IDX_FIELDS_IS_PRIMARY_KEY ON SYS_FIELDS(IS_PRIMARY_KEY);
CREATE INDEX IDX_FIELDS_IS_UNIQUE ON SYS_FIELDS(IS_UNIQUE);
CREATE INDEX IDX_FIELDS_IS_SUB_TABLE ON SYS_FIELDS(IS_SUB_TABLE);
CREATE INDEX IDX_FIELDS_IS_FOREIGN_KEY ON SYS_FIELDS(IS_FOREIGN_KEY);
CREATE INDEX IDX_FIELDS_IS_NULLABLE ON SYS_FIELDS(IS_NULLABLE);
CREATE INDEX RIDX_FIELDS_META_ID ON SYS_KEYS(R_META_ID);

--------------------------------------------------------------------------------------
-- EVX_VERTICLE表，保存了所有和VertX相关的元数据配置信息
DROP TABLE IF EXISTS EVX_VERTICLE;
CREATE TABLE EVX_VERTICLE(
	--主键ID标识符
	K_ID VARCHAR(192),							-- 当前Verticle对应的ID标识符
	S_CLASS VARCHAR(256) NOT NULL,				-- 当前Verticle的Java类名
	S_INSTANCES INT								-- 实例数量
		CHECK(S_INSTANCES > 0),
	S_IGROUP VARCHAR(256),						-- Isolation Group信息
	S_JSON_CONFIG VARCHAR(2048),				-- 额外的Json配置
	S_ISOLATED_CLASSES VARCHAR(2048),			-- Isolation Classes
	--配置项中的两个特殊的类路径
	CP_EXT VARCHAR(2000),						-- Extra Classpath
	--配置属性Boolean类型
	IS_HA BOOLEAN NOT NULL,						-- 是否开启了HA
	IS_WORKER BOOLEAN NOT NULL,					-- 是否是一个Worker
	IS_MULTI BOOLEAN NOT NULL,					-- 是否是一个Multi类型的Verticle
	--创建部署配置
	DP_ORDER INT 								-- Deployment Order发布顺序
		CHECK(DP_ORDER > 0),
	DP_ASYNC BOOLEAN,							-- 是否执行异步方式的Deployment过程			
	PRIMARY KEY(K_ID)
);
-- EVX_VERTICLE的索引创建
CREATE INDEX IDX_VERTICLE_CLASS ON EVX_VERTICLE(S_CLASS);
CREATE INDEX IDX_VERTICLE_IGROUP ON EVX_VERTICLE(S_IGROUP);
CREATE INDEX IDX_VERTICLE_IS_HA ON EVX_VERTICLE(IS_HA);
CREATE INDEX IDX_VERTICLE_IS_WORKER ON EVX_VERTICLE(IS_WORKER);
CREATE INDEX IDX_VERTICLE_IS_MULTI ON EVX_VERTICLE(IS_MULTI);
CREATE INDEX IDX_VERTICLE_DP_ORDER ON EVX_VERTICLE(DP_ORDER);

--------------------------------------------------------------------------------------
-- EVX_ROUTE，路由表结构，保存了路由地址以及相关的处理器
DROP TABLE IF EXISTS EVX_ROUTE;
CREATE TABLE EVX_ROUTE(
	--主键ID标识符
	K_ID VARCHAR(192),							-- 当前Route对应的ID标识符
	S_PARENT VARCHAR(256) NOT NULL,				-- 当前Route的父PATH
	S_PATH VARCHAR(256) NOT NULL,				-- 客户端访问的入口URI路径
	S_METHOD VARCHAR(10) NOT NULL				-- HTTP方法
		CHECK(S_METHOD = 'GET' OR S_METHOD = 'POST' OR S_METHOD = 'PUT' OR S_METHOD = 'DELETE' OR S_METHOD = 'OPTIONS' OR S_METHOD = 'HEAD' OR S_METHOD = 'TRACE' OR S_METHOD = 'CONNECT' OR S_METHOD = 'PATCH'),
	S_MIME_CONSUMER VARCHAR(2048),				-- Consumer Mimes
	S_MIME_PRODUCER VARCHAR(2048),				-- Producer Mimes
	S_ORDER INT,								-- 同一个路径下的ORDER，这个值只有路径相同时才会使用
	--处理器信息
	S_SHANDLER VARCHAR(256),					-- Java处理当前Route的SuccessHandler内容
	S_FHANDLER VARCHAR(256),					-- Java处理当前Route的FailureHandler内容
	--同步还是异步处理
	IS_SYNC BOOLEAN NOT NULL,					-- 判断是同步还是异步
	PRIMARY KEY(K_ID)
);
-- EVX_ROUTE的索引创建
CREATE INDEX IDX_ROUTE_PARENT ON EVX_ROUTE(S_PARENT);
CREATE INDEX IDX_ROUTE_PATH ON EVX_ROUTE(S_PATH);
CREATE INDEX IDX_ROUTE_METHOD ON EVX_ROUTE(S_METHOD);
CREATE INDEX IDX_ROUTE_IS_SYNC ON EVX_ROUTE(IS_SYNC);

--------------------------------------------------------------------------------------
-- EVX_URI, 接口参数验证表，保存了某个接口中的参数获取方式以及参数规范
DROP TABLE IF EXISTS EVX_URI;
CREATE TABLE EVX_URI(
	K_ID VARCHAR(192),							-- 当前URI对应的ID标识符
	S_URI VARCHAR(256) NOT NULL,				-- 系统中的URI地址，唯一值
	S_METHOD VARCHAR(10) NOT NULL				-- HTTP方法
		CHECK(S_METHOD = 'GET' OR S_METHOD = 'POST' OR S_METHOD = 'PUT' OR S_METHOD = 'DELETE' OR S_METHOD = 'OPTIONS' OR S_METHOD = 'HEAD' OR S_METHOD = 'TRACE' OR S_METHOD = 'CONNECT' OR S_METHOD = 'PATCH'),
	S_PARAM_TYPE VARCHAR(10) NOT NULL			-- 参数的获取方式
		CHECK(S_PARAM_TYPE = 'QUERY' OR S_PARAM_TYPE = 'FORM' OR S_PARAM_TYPE = 'BODY'),
	L_REQUIRED_PARAM VARCHAR(2048),				-- 必须参数列表
	PRIMARY KEY(K_ID)
);
-- EVX_URI的索引创建
CREATE INDEX IDX_URI_URI ON EVX_URI(S_URI);
CREATE INDEX IDX_URI_METHOD ON EVX_URI(S_METHOD);
CREATE INDEX IDX_URI_PARAM_TYPE ON EVX_URI(S_PARAM_TYPE);

--------------------------------------------------------------------------------------
--- EVX_RULE, 验证规则表，保存了某个接口参数的验证规则, Parameter Validation Rule
DROP TABLE IF EXISTS EVX_RULE;
CREATE TABLE EVX_RULE(
	K_ID VARCHAR(192),							-- 当前Rule对应的ID标识符
	S_NAME VARCHAR(256) NOT NULL,				-- 参数名称
	S_TYPE CHAR(16) NOT NULL					-- 字段类型
		CHECK(S_TYPE='BooleanType' OR S_TYPE='IntType' OR S_TYPE='LongType' 
		OR S_TYPE='DateType' OR S_TYPE='StringType' OR S_TYPE='BinaryType' 
		OR S_TYPE='DecimalType' OR S_TYPE='JsonType' OR S_TYPE='XmlType' OR S_TYPE='ScriptType'),
	S_ORDER INT,								-- 当前规则的顺序
	J_COMPONENT_TYPE VARCHAR(15) NOT NULL		-- 组件类型
		CHECK(J_COMPONENT_TYPE='VALIDATOR' OR J_COMPONENT_TYPE='CONVERTOR'),
	J_COMPONENT_CLASS VARCHAR(256),				-- Java组件Class
	J_CONFIG CLOB,								-- Java组件中需要使用的Json配置
	J_ERROR_MSG VARCHAR(2048),					-- Error Message验证失败的信息
	-- 关联属性
	R_URI_ID VARCHAR(192),						-- 关联EVX_URI表
	PRIMARY KEY(K_ID),
	FOREIGN KEY(R_URI_ID) REFERENCES EVX_URI(K_ID)
);
-- EVX_RULE的索引创建
CREATE INDEX IDX_RULE_NAME ON EVX_RULE(S_NAME);
CREATE INDEX IDX_RULE_TYPE ON EVX_RULE(S_TYPE);
CREATE INDEX IDX_RULE_ORDER ON EVX_RULE(S_ORDER);
CREATE INDEX IDX_RULE_COM_TYPE ON EVX_RULE(J_COMPONENT_TYPE);
CREATE INDEX RIDX_RULE_URI_ID ON EVX_RULE(R_URI_ID);



