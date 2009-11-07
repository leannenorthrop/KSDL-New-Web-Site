namespace :database do
  desc 'Install Postgres'
  task :install, :roles => :app do
    on_rollback { uninstall }
    sudo "yum install postgresql postgresql-server -q -y"
    sudo "mv /var/lib/pgsql/data/pg_hba.conf /var/lib/pgsql/data/pg_hba.conf.orig", :as => "postgres" 
    utils.install_template("config/deploy/templates/pg_hba.conf.erb", "/var/lib/pgsql/data/pg_hba.conf")
    sudo "/sbin/chkconfig postgresql on"
  end

  desc 'Uninstall Postgres'
  task :uninstall, :roles => :app, :on_error => :continue do
    sudo "rm -rf /var/lib/pgsql/data/pg_hba.conf.orig"
    sudo "yum erase postgresql postgresql-server -q -y"
  end
  
  desc 'Start Database'
  task :start, :roles => :app, :on_error => :continue do
    sudo "/etc/init.d/postgresql start"
  end

  
  desc 'Restart Database'
  task :restart, :roles => :app, :on_error => :continue do
    sudo "/etc/init.d/postgresql restart"  
  end
  
  desc 'Stop Database'
  task :stop, :roles => :app, :on_error => :continue do
    sudo "/etc/init.d/postgresql stop"
  end
 
  desc 'Status Database'
  task :status, :roles => :app, :on_error => :continue do
    sudo "/etc/init.d/postgresql status"
  end
  
  desc 'Create database and users'
  task :create_db, :roles => :app, :on_error => :continue do    
    run "echo password | xargs createuser -S -R -D -U postgres lsd", :as => "postgres" 
    run "echo password | xargs createdb lsd", :as => "postgres"
    run "psql -c \"ALTER ROLE lsd WITH PASSWORD 'password';\"", :as => "postgres"    
  end
   
  task :create_db_user, :roles => :db do
    sudo "createuser -D -A -q #{app}" 
  end
  
  task :create_db, :roles => :db do 
    sudo "createdb --owner #{app} #{app}_#{env}"
  end
  
  task :setup_db, :roles => :db do create_db_user create_db
  end   
end