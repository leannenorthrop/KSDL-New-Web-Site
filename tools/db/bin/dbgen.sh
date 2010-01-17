#!/bin/bash
rm -f $PWD/src/main/webapp/database.*
mvn clean resources:resources liquibase:update