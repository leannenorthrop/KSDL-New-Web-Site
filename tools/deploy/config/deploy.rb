set :application, 'lsd'
set :group_id, 'org.northrop.leanne'
set :artifact_id, 'lsd'
set :version, '1.0'
set :packaging, 'war'
set :tomcat_java_opts, '-Xms512m -Xmx812m'
set :http_get_method, 'wget -nv'
set :apache_path, 'apache'
ssh_options[:keys] = [File.join(ENV["HOME"], ".ssh", "lsd.ppk")]

