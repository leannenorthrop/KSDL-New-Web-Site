namespace :hsqldb do
  desc 'Install HSQL DB'
  task :install, :roles => :app do
    on_rollback { uninstall }
    sudo "#{wget} -nv http://lsd-website.googlecode.com/svn/trunk/third-party/hsqldb/hsqldb-1.8.0.7.jar -P '/opt/tomcat/lib'"
    run "mkdir -p /mnt/opt/hsql", :as => :app
    run "ln -nfs /opt/tomcat/lib/hsqldb-1.8.0.7.jar /mnt/opt/hsql/hsqldb.jar", :as => :app
    utils.install_template("config/deploy/templates/sqltool.rc.erb", '/mnt/opt/hsql/sqltool.rc')
    utils.install_template("config/deploy/templates/hsqldb.sh.erb", '/etc/init.d/lsddb')
    script.run_all <<-CMDS
      /sbin/chkconfig --add lsddb
      /sbin/chkconfig lsddb on
    CMDS
  end

  desc 'Uninstall Postgres'
  task :uninstall, :roles => :app, :on_error => :continue do
    script.run_all <<-CMDS
      rm -rf /mnt/opt/hsql
      /sbin/chkconfig --del lsddb
      rm /etc/init.d/lsddb
    CMDS
  end

  desc 'Start Database'
  task :start, :roles => :app, :on_error => :continue do
    sudo "/etc/init.d/lsddb start"
  end


  desc 'Restart Database'
  task :restart, :roles => :app, :on_error => :continue do
    sudo "/etc/init.d/lsddb restart"
  end

  desc 'Stop Database'
  task :stop, :roles => :app, :on_error => :continue do
    sudo "/etc/init.d/lsddb stop"
  end

  desc 'Status Database'
  task :status, :roles => :app, :on_error => :continue do
    sudo "/etc/init.d/lsddb status"
  end

end