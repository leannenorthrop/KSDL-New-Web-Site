#! /bin/bash
#mvn process-resources
PROPFILE="$PWD/target/classes/database/diff.properties"
URL=jdbc:hsqldb:file:/Users/northrl/Documents/LSD/dev/lsdwebsite/website/main/dev;shutdown=true
UN=sa
PW=
HSQL_VER=1.8.0.7
MVN_REPO_DIR="$HOME/.m2/repository"
$PWD/bin/liquibase --classpath=$MVN_REPO_DIR/hsqldb/hsqldb/$HSQL_VER/hsqldb-$HSQL_VER.jar:$PWD/bin/liquibase.jar --defaultsFile=$PROPFILE diffChangeLog --baseUername=$UN "--aPassword=na" --baseUrl=$URL --referenceUrl=$URL
