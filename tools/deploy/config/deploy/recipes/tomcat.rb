namespace :tomcat do
  after 'tomcat:install' do
    sudo "#{wget} -nv http://mirrors.ibiblio.org/pub/mirrors/maven2/postgresql/postgresql/8.4-701.jdbc4/postgresql-8.4-701.jdbc4.jar -P '/opt/tomcat/lib'"
  end

after 'tomcat:uninstall' do
  sudo "rm -rf /opt/apache-tomcat-6.0.20"
  sudo "yum clean all -q -y"
end

end
