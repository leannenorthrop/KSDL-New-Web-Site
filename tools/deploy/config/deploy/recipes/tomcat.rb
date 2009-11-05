namespace :tomcat do
after 'tomcat:uninstall' do
  sudo "rm -rf /opt/apache-tomcat-6.0.20"
  sudo "yum clean all -q -y"
end

end
