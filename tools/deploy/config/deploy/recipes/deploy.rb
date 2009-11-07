#This is overriding the existing deploy.rb in the giovanni gem
namespace :deploy do

  after 'deploy:setup' do
    sudo "yum update -q -y"
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
end
