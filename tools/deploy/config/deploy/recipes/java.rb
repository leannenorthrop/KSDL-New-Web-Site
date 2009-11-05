namespace :java do
  desc 'Install Java 6'
  task :install, :roles => :app do
    on_rollback { uninstall }
    sudo "sudo yum install java-1.6.0-openjdk.i386 -q -y"
  end

  desc 'Uninstall Java'
  task :uninstall, :roles => :app, :on_error => :continue do
    sudo "sudo yum erase java-1.6.0-openjdk.i386 -q -y"
  end
end