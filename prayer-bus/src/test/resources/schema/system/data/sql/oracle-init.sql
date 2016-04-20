-- Model definitions
--IF OBJECT_ID('SYS_MODEL','U') IS NOT NULL DROP TABLE SYS_MODEL;
--IF OBJECT_ID('SYS_MODEL','U') IS NULL CREATE TABLE SYS_MODEL( NAME VARCHAR(256) NOT NULL UNIQUE, ROOT_FOLDER VARCHAR(256) NOT NULL UNIQUE,FILE_DATA VARCHAR(256) NOT NULL,FILE_SCHEMA VARCHAR(256) NOT NULL, FILE_MAPPING VARCHAR(256) NOT NULL, PRIMARY KEY(NAME));
--INSERT INTO SYS_MODEL(NAME,ROOT_FOLDER,FILE_DATA,FILE_SCHEMA,FILE_MAPPING) VALUES ('role','/schema/json/active/model/role/','role-data.dat','role-schema.json','role.properties');

--DROP TABLE SYS_MODEL;
--CREATE TABLE SYS_MODEL(NAME VARCHAR(256) NOT NULL, ROOT_FOLDER VARCHAR(256) NOT NULL,FILE_DATA VARCHAR(256) NOT NULL,FILE_SCHEMA VARCHAR(256) NOT NULL, FILE_MAPPING VARCHAR(256) NOT NULL, PRIMARY KEY(NAME),UNIQUE(NAME,ROOT_FOLDER));
--INSERT INTO SYS_MODEL(NAME,ROOT_FOLDER,FILE_DATA,FILE_SCHEMA,FILE_MAPPING) VALUES ('role','/schema/json/active/model/role/','role-data.dat','role-schema.json','role.properties');



-- run following before setup the service
--create sequence S_COUNTRY_PID1 minvalue 1 maxvalue 999999999999999999999999999 start with 1 increment by 1 nocache;
select * from SYS_MODEL