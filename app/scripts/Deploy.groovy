/*
 * Script to generate distribution zip for remote deployment on to production
 * box.
 */
includeTargets << grailsScript("Init")

target(main: "Generate dist zip file and deploy to remote server") {
    property(file:"${basedir}/application.properties",prefix:"grails")
    property name: "zip.name", value: "${basedir}/target/Version-${ant.project.properties.'grails.app.version'}.zip"
    property name: "host.remote.dir", value: "/home/londonsamyedzong/application/lib/versions"
    assemble()
    input(message: "Host user name: ", addproperty: "grails.host.un")
    input(message: "Host password: ", addproperty: "grails.host.pw")

    copy()
    //install()
}

target(assemble: "Generate distributable zip file") {
    zip(destfile:ant.project.properties.'zip.name') {
        zipfileset(dir:"${basedir}/dist", includes:"**/*", prefix:"/")
        zipfileset(dir:"${basedir}/target", includes:"**/*.war", prefix:"/")
        zipfileset(dir:"${basedir}", includes:"README", prefix:"/")
    }
}

target(copy: "Copies zip to remote host") {
    def props = ant.project.properties
    def host = "londonsamyedzong.org"
    scp(file: props.'zip.name',
        todir: props.'grails.host.un' + '@' + host + ':' + props.'host.remote.dir',
        password: props.'grails.host.pw')
}

/* Not in use yet but would be nice to have automated remote install. */
target(install: "Run install script") {
    def props = ant.project.properties
    def host = "londonsamyedzong.org"
    def file = new File(basedir + '/target/install.sh') << """/etc/init.d/tomcat stop
rm -rf /opt/tomcat/logs/*
cd /home/londonsamyedzong/
/home/londonsamyedzong/application/bin/upgrade 1.0.16-SNAPSHOT
chown -R tomcat:tomcat /opt/tomcat/webapps/ROOT
cp -R /home/londonsamyedzong/application/lib/versions/Version-1.0.15-SNAPSHOT/ROOT/css/themes /opt/tomcat/webapps/ROOT/css/
chown -R tomcat:tomcat /opt/tomcat/webapps/ROOT/css/themes
rm -f /home/londonsamyedzong/public_html/js
rm -f /home/londonsamyedzong/public_html/css
ln -s /opt/tomcat/webapps/ROOT/css /home/londonsamyedzong/public_html/css
ln -s /opt/tomcat/webapps/ROOT/js /home/londonsamyedzong/public_html/js
chown londonsamyedzong:londonsamyedzong /home/londonsamyedzong/public_html/css
chown londonsamyedzong:londonsamyedzong /home/londonsamyedzong/public_html/js"""

    sshexec(host: host,
	username: props.'grails.host.un',
	commandResource: file.path,
    password: props.'grails.host.pw')
}

setDefaultTarget(main)
