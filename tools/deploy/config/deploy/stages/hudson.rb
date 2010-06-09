role :app, 'ec2-75-101-145-155.compute-1.amazonaws.com'
#Unfortunately repository has to be set here to overwrite the path in the giovanni library
# This file is only intended to deploy so we use a different url here.
#set :repository, 'http://:8080/job/MainBuild/lastSuccessfulBuild/artifact/war/target'
set :repository, 'http://127.0.0.1'
set :artifact_id, 'london-samye-dzong'
=begin
set :tomcat_ds, {
  :username => 'lsd',
  :password => 'password',
  :host => 'localhost',
  :port => 5432,
  :driver => 'org.postgresql.Driver',
  :url_prefix => 'jdbc:postgresql://',
  :path => '/lsd'
}
=end
set :tomcat_ds, {
  :username => 'sa',
  :password => '',
  :host => 'localhost',
  :port => 9001,
  :driver => 'org.hsqldb.jdbcDriver',
  :url_prefix => 'jdbc:hsqldb:hsql://',
  :path => '/lsd'
}

# for fedora need to install jdk manually
# wget http://cds.sun.com/is-bin/INTERSHOP.enfinity/WFS/CDS-CDS_Developer-Site/en_US/-/USD/VerifyItem-Start/jdk-6u17-linux-i586-rpm.bin?BundledLineItemUUID=jPxIBe.m2hcAAAElbRVZ7x4f&OrderID=zQ9IBe.mfpwAAAElYBVZ7x4f&ProductID=lBFIBe.oSOMAAAEkGehn5G0y&FileName=/jdk-6u17-linux-i586-rpm.bin