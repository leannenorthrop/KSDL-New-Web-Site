#!/bin/bash
rm -f $PWD/target/database/export*
rm -drf $PWD/target/database/csv

#driver=org.hsqldb.jdbcDriver
#url=jdbc:hsqldb:file:$PWD/src/main/webapp/database;shutdown=true
#username=sa
#password=""
#schema=PUBLIC
#dataTypeFactoryName=org.dbunit.ext.hsqldb.HsqldbDataTypeFactory

driver=oracle.jdbc.driver.OracleDriver
url="jdbc:oracle:thin:@(DESCRIPTION_LIST = (LOAD_BALANCE = OFF) (FAILOVER = ON) (DESCRIPTION = (ADDRESS_LIST = (LOAD_BALANCE = ON) (FAILOVER = ON) (ADDRESS = (PROTOCOL = TCP)(HOST = byl150d001-oravip.nat.bt.com)(PORT = 61904)) (ADDRESS = (PROTOCOL = TCP)(HOST = byl150d002-oravip.nat.bt.com)(PORT = 61904)) ) (CONNECT_DATA = (SERVICE_NAME = ADMIN_ANY.nat.bt.com) (FAILOVER_MODE = (TYPE = SESSION) (METHOD = BASIC) (RETRIES = 120) (DELAY = 5) ) ) ) )"
username=BER
password=
schema=BER
dataTypeFactoryName=org.dbunit.ext.oracle.Oracle10DataTypeFactory

# Format can be either xml or csv. We use xml and then process using saxon so that we can convert the Base64 encoded binary data back into hex for loading.
format=xml
#format=csv

export MAVEN_OPTS="-Xmx812m $MAVEN_OPTS"
mvn "-Dschema=$schema" "-Ddriver=$driver" "-Durl=$url" "-Dusername=$username" "-Dpassword=$password" "-Dformat=$format" "-DdataTypeFactoryName=$dataTypeFactoryName" dbunit:export
java -Xmx512m -classpath $PWD/bin/saxon9.jar:$PWD/bin/commons-codec-1.4.jar:$PWD/bin/base64wrapper.jar net.sf.saxon.Transform -s:$PWD/target/database/export -xsl:$PWD/bin/export.xsl