#!/bin/bash
rm -f $PWD/target/export*
rm -drf $PWD/target/csv

driver=org.hsqldb.jdbcDriver
url="jdbc:hsqldb:file:/Users/northrl/Desktop/lsd-test;shutdown=true;"
username=sa
password=
schema=PUBLIC
dataTypeFactoryName=org.dbunit.ext.hsqldb.HsqldbDataTypeFactory

# Format can be either xml or csv. We use xml and then process using saxon so that we can convert the Base64 encoded binary data back into hex for loading.
format=xml
#format=csv

export MAVEN_OPTS="-Xmx812m $MAVEN_OPTS"
mvn "-Dschema=$schema" "-Ddriver=$driver" "-Durl=$url" "-Dusername=$username" "-Dpassword=$password" "-Dformat=$format" "-DdataTypeFactoryName=$dataTypeFactoryName" dbunit:export
java -Xmx512m -classpath $PWD/bin/saxon9.jar:$PWD/bin/commons-codec-1.4.jar:$PWD/bin/base64wrapper.jar net.sf.saxon.Transform -s:$PWD/target/export -xsl:$PWD/bin/export.xsl
