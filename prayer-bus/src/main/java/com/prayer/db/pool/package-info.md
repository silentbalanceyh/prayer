## META -- Database Meta Context

### __*Classes*__
Package: `com.prayer.db.pool`

- __[DA]__ `com.prayer.db.pool.AbstractDbContext`
- __[PC]__ `com.prayer.db.pool.BoneCPContext`


### __*Basic Description:*__

The package `com.prayer.db.pool` is sub-package of `com.prayer.db.conn`, all the implementation classes of database interface are included in this package.

#### *1 - AbstractDbContext*

This class defines abstract interface based on Template Design Pattern, it provides common database accessing operations such as *Create Connection*, *Close Connection*, *Execute SQL Statements* etc. It also provides following two abstract methods which should be implemented by its sub-classes. 

	// Must be implemented by sub class, JDBC configuration.
	public abstract void initJdbc();
	// Must be implemented by sub class, Addtional configuration.
	public abstract void initAddtional(); 

- __initJdbc()__ <br/>
  This method should be implemented by sub-class, the sub-class must provide basic JDBC connection configuration information such as *JDBC URL*, *JDBC Driver*, *Database UserName*, *Database Password*.
- __initAddtional()__ <br/>
  This method should be implemented by sub-class, the sub-class should provide some additional configuration information here, different connection pools have distinct configuration parameters, this method is designed for this, if there is no addtional information you could keep this method body EMPTY.

The Access Control of this class is default scope ( Used in package only ); It means that this abstract class could be used in `com.prayer.db.pool` package only. 

#### *2 - BoneCPContext*  

This class is default implementation, sub-class of `AbstractDbContext` in Lyra Framework, it implements __BoneCP__ database connection pool. Compared with other connection pool in java language such as __c3p0__, __proxool__ etc, BoneCP is more faster to support high concurrency system. `BoneCPContext` has another constructor with string parameter, so you could connect different databases in Lyra Framework, the default value of this parameter is *mssql*, it could support following value list in current version:

- mssql
- postgresql
- oracle
- mysql
- mongodb ( no-sql )

Please refer following content to check mssql properties ( __meta-milieu__, `src/main/resources/schema/system/database.properties` )

	# SQL Server Jdbc
	mssql.jdbc.url=jdbc:sqlserver://localhost:1433;DatabaseName=DBLYRA
	mssql.jdbc.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
	mssql.jdbc.username=sa
	mssql.jdbc.password=********
	mssql.jdbc.database.name=DBLYRA
	# Oracle Jdbc
	oracle.jdbc.url=jdbc:oracle:thin:@192.168.1.102:1521:DBLYRA
	oracle.jdbc.driver=oracle.jdbc.driver.OracleDriver
	oracle.jdbc.username=DBLYRA
	oracle.jdbc.password=********
	oracle.jdbc.database.name=DBLYRA

<font style="color:red">BoneCP database connection pool is singleton in Lyra Framework to improve performance.</font>

### __*Code Segment:*__ 

	@Override
	protected void initJdbc() {
		if(null == this.databaseCategory){
			throw new MemberInitException(getClass(),"databaseCategory[java.lang.String]");
		}else{
			this.dataSource.setDriverClass(this.loader.get(this.databaseCategory + ".jdbc.driver"));
			this.dataSource.setJdbcUrl(this.loader.get(this.databaseCategory + ".jdbc.url"));
			this.dataSource.setUsername(this.loader.get(this.databaseCategory + ".jdbc.username"));
			this.dataSource.setPassword(this.loader.get(this.databaseCategory + ".jdbc.password"));
			this.databaseName = this.loader.get(this.databaseCategory + ".jdbc.database.name");
		}
	}

