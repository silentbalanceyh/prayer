## META -- Model Configurator

### __*Classes*__
Package: `com.prayer.meta.sys`

- __[PC]__ `com.prayer.meta.sys.OldModelConfigurator`
- 
### __*Basic Description:*__

#### __*1.ModelConfigurator:*__
The class __[PC]__ `com.prayer.meta.sys.OldModelConfigurator` is a special meta-data class to read system meta-data from SYS\_MODEL table of database. All the model definition meta-data is stored in schema files and SYS_MODEL table store the schema files' meta-data for each model such as schema file path, data file path, type mapping file path etc. 

Initialization SQL script path is as following ( This script is for SQL database only. ):

**Module Name**: meta-milieu<br/>
**Path**: `/src/main/resources/schema/system/data/*`

Please refer following column descriptions of SYS_MODEL

* **_NAME_**<br/>
  The model identifier, this attribute could identify each model in Lyra Framework. 
* **_ROOT\_FOLDER_**<br/>
  Model meta-data files root location. Lyra Framework has defined the model specification for each one, every model meta-data files must be placed under one folder with same format and architecture. 
* **_FILE\_DATA_**<br/>
  This column described model data file name, the full path should be: `ROOT_FOLDER + FILE_DATA`.
* **_FILE\_SCHEMA_**<br/>
  This column described model schema file name, the full path should be: `ROOT_FOLDER + FILE_SCHEMA`.
* **_FILE\_MAPPING_**<br/>
  This column described model mapping file name, the full path should be: `ROOT_FOLDER + FILE_MAPPING`.
* **_IN\_USE_**<br/>
  This column could set the model using status.<br/>
  Equal to 1: It means that this model is used in Lyra Framework and it could be deployed.<br/>
  Equal to 0: It means that this model is only for special using, it could not be deployed.
* **_OBJ\_LEVEL_**<br/>
  This column is designed for foreign key meta-data building, all the tables must be created in sequence when they contain foreign keys which refer another tables that must in database exist before current creation.
* **_INIT\_ORDER_**<br/>
  Once model configurator read model meta-data information from database, this column describes the first `ORDER BY` clause;
* **_INIT\_SUB\_ORDER_**<br/>
  Once model configurator read model meta-data information from database, this column describes the second `ORDER BY` clause;

Please refer following code segment to understand the last three columns:

	SELECT NAME FROM SYS_MODEL WHERE IN_USE=1 ORDER BY INIT_ORDER ASC,INIT_SUB_ORDER ASC

<font style="color:red">OBJ\_LEVEL is reserved in current version, it may be removed in future. </font>

### __*File Name Specification:*__

<font style="color:red">
*Please refer below default name specification of configuration file.*
</font>

* Schema file: `%ROOT_FOLDER%/<model>-schema.json`
* Data file: `%ROOT_FOLDER%/<model>-data.csv`
* Mapping file: `%ROOT_FOLDER%/<model>-mapping.properties`

### __*About Mapping:*__

_FILE\_MAPPING_ stores the data type mapping information for different database, please refer below code segment of this file content:

	# Microsoft SQL Server
	db.mssql.country.id=BIGINT
	db.mssql.country.name=VARCHAR
	db.mssql.country.lang.code=VARCHAR
	db.mssql.country.flag=VARCHAR

	# Oracle
	db.oracle.country.id=NUMBER
	db.oracle.country.name=VARCHAR2
	db.oracle.country.lang.code=VARCHAR2
	db.oracle.country.flag=VARCHAR2

	# Default value
	default.country.id=-1
	default.country.name=EMPTY
	default.country.lang.code=NEW_COUNTRY
	default.country.flag=EMPTY

Configuration attribute name should obay following rules:

* `db.<database category>.<model name>.<field key name>`
* `default.<model name>.<field key name>`

<font style="color:red">
*: *Prefix `default` is used in NEW OBJECT and it provide default value for each field of model. `<field key name>` here is different from __field name__, it's used between `<model>-schema.json` and `<model>-mapping.properties` only and could not be used in other place.*
</font>