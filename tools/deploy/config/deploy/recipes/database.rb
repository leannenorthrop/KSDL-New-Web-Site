namespace :database do
  desc 'Install Postgres'
  task :install, :roles => :app do
    on_rollback { uninstall }
    sudo "yum install postgresql postgresql-server -q -y"
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

  task :create_db_user, :roles => :app do
    sudo "createuser -D -A -R -q #{application}", :as => "postgres"
    sudo "psql -c \"ALTER ROLE #{application} WITH PASSWORD 'password';\"", :as => "postgres"
  end

  task :create_db, :roles => :app do
    sudo "createdb --owner #{application} #{application}", :as => "postgres"
  end

  desc 'Create database and users'
  task :setup, :roles => :app do
    create_db_user
    xcreate_db
    sudo "mv /var/lib/pgsql/data/pg_hba.conf /var/lib/pgsql/data/pg_hba.conf.orig", :as => "postgres"
    utils.install_template("config/deploy/templates/pg_hba.conf.erb", "/var/lib/pgsql/data/pg_hba.conf")
    restart
  end
end