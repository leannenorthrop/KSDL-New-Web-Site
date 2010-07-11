#!/bin/bash
mkdir $PWD/ROOT
cp $PWD/ROOT.war $PWD/ROOT
cd $PWD/ROOT
unzip ROOT.war
rm -f ROOT.war
rm -rf ./css/themes
rm -rf ./files
cd ..
ln -sfF $1 $PWD/ROOT/css/themes
ln -sfF $2 $PWD/ROOT/files

ln -sfF $PWD/ROOT/ /opt/tomcat-5.5/webapps/ROOT

