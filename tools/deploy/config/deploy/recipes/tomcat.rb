namespace :tomcat do
  after 'tomcat:install' do
    permissions.normalise "/opt/apache-tomcat-6.0.20", :owner => application, :group => 'tomcat'
    permissions.normalise "/opt/tomcat", :owner => application, :group => 'tomcat'
    sudo "chmod +x /opt/tomcat/bin/*.sh"
    puts "You may need to add lsd to the tomcat group and the sudoers file, also check if ports are open"
  end

after 'tomcat:uninstall' do
  sudo "rm -rf /opt/apache-tomcat-6.0.20"
  sudo "yum clean all -q -y"
end

end
