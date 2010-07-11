coverage {
    enabledByDefault = false
    xml =true
    exclusions = [
                  '**/de/andreasschmitt/richui/taglib/renderer/**',
                  '**/plugins/richui-0.4/src/groovy/de/andreasschmitt/richui/**',
                  '**/de/andreasschmitt/richui/image/**',
                  '**/org/jsecurity/**',
                  '**/org/jsecurity/grails/**',
                  '**/JsecDbRealm*',
                  '**/*TagLib*/**',
                  "**/*Tests*",
                  "**/AuditLogEvent*",
                  "**/AuditLogLis*",
                  "**/org/apache/**/*",
                  "**/org/grails/**/*",
                  "**/feedsplugin/*",
                  "**/Shiro*",
                  "**/BuildConfig*",
                  "**/Security*",
                  '**/JsecAuthBase*',
                  '**/JsecurityFilters*']

}
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.war.file = "target/ROOT.war"

grails.project.dependency.resolution = {
    //pom true
    // inherit Grails' default dependencies
    inherits( "global" ) {
        // uncomment to disable ehcache
        // excludes 'ehcache'
        excludes "slf4j-api","log4j"
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        //grailsPlugins()
        //grailsHome()
        plugins {
        	runtime( "org.grails.plugins:hibernate:1.2.2" ) {
        		excludes "javassist","slf4j-api"
        	}
        }        

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        //build 'mysql:mysql-connector-java:5.0.8','log4j:log4j:1.2.14','slfj4j:slf4j-api:1.5.2'
    }

}
