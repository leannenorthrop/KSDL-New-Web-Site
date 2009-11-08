#This is overriding the existing deploy.rb in the giovanni gem
namespace :deploy do

  after 'deploy:setup' do
    sudo "yum update -q -y"
    sudo "yum install unzip -q -y"
    java.install
    database.install
    database.start
    sudo "yum clean all -q -y"
  end

  after 'deploy:teardown' do
    database.stop
    java.uninstall
    database.uninstall
  end

  task :finalize_update do
    run "ln -nfs #{log_path} #{latest_release}/logs", :as => :app
    run "ln -nfs #{tmp_path} #{latest_release}/temp", :as => :app
    run "mkdir #{['bin', 'conf', 'lib', 'work'].map { |d| File.join(latest_release, d) }.join(' ')}", :as => :app
    run "cp /opt/tomcat/conf/* #{latest_release}/conf", :as => :app
    #run "ln -nfs /opt/tomcat/lib/* #{latest_release}/lib", :as => :app
    #run "ln -nfs /opt/tomcat/bin/* #{latest_release}/bin", :as => :app

    run "unzip -q #{File.join(latest_release, webapps_dir, nexus.filename)} -d #{File.join(latest_release, webapps_dir, context_root)}", :as => :app
    run "rm #{File.join(latest_release, webapps_dir, nexus.filename)}", :as => :app
    # render Tomcat templates into current Tomcat dir
    Dir[File.dirname(__FILE__) + '/templates/tomcat/**/*.*'].each do |file|
      template = file[/tomcat.*/]
      destination = File.join(latest_release, template.gsub(/\.erb$/, '')[7..-1])

      # render all templates except context.xml, which is
      # only rendered if tomcat_ds has been set
      if File.basename(file) != 'context.xml.erb'
        utils.install_template(template, destination, :user => :app)
      end
    end
    # Get the oracle jar if there is a tomcat_ds or if the install_oracle_jar variable is set to true
    if exists?(:tomcat_ds)
      utils.install_template("config/deploy/templates/context.xml.erb", File.join(latest_release, 'conf/context.xml'))
    end
    permissions.normalise latest_release, :owner => application, :group => 'tomcat'
    cleanup
  end

end

