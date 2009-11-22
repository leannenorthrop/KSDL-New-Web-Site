role :app, '75.101.145.155'
#Unfortunately repository has to be set here to overwrite the path in the giovanni library
# This file is only intended to deploy so we use a different url here.
#set :repository, 'http://:8080/job/MainBuild/lastSuccessfulBuild/artifact/war/target'
set :repository, 'http://90.199.221.159/~northrl'
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