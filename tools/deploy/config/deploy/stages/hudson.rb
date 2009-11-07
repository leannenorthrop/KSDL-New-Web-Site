role :app, '205.143.144.231'
#Unfortunately repository has to be set here to overwrite the path in the giovanni library
# This file is only intended to deploy so we use a different url here.
set :repository, 'http://205.143.144.244:8080/job/MainBuild/lastSuccessfulBuild/artifact/war/target'
set :artifact_id, 'london-samye-dzong'