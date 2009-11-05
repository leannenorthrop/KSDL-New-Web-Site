#cap -S user=leanne development tomcat:install
cap -S user=leanne development deploy
#cap -S user=leanne development tomcat:uninstall
#cap -S user=bambooremoteagent -S password=bamboo4pp integration deploy:cleanup

#sudo "/usr/sbin/useradd postgres" & activate user account
