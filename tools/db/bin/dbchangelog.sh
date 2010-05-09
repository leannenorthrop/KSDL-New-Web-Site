#! /bin/bash
mvn process-resources
PROPFILE="$PWD/target/classes/database/liquibase.properties"
MVN_REPO_DIR="$HOME/.m2/repository"
HSQL_VER=1.8.0.7
$PWD/bin/liquibase --classpath=$MVN_REPO_DIR/hsqldb/hsqldb/$HSQL_VER/hsqldb-$HSQL_VER.jar:$PWD/bin/liquibase.jar:/Users/northrl/Desktop/pkg/mysql-connector-java-5.0.8.jar --defaultsFile=$PROPFILE generateChangelog
