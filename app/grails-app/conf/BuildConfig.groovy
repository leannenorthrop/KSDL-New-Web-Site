grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.test.source.dir = "test"
grails.project.war.file = "target/ROOT.war"

grails.project.dependency.resolution = {
    pom false

    // inherit Grails' default dependencies
    inherits( "global" ) {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()     
        grailsCentral()
        
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()
        mavenRepo "http://snapshots.repository.codehaus.org"
        mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        build   'commons-collections:commons-collections:3.2.1'
        
        compile 'org.eclipse.mylyn.wikitext:core:1.3.0.I20091106-0100-e3x', 
                'org.eclipse.mylyn.wikitext:textile:1.3.0.I20091106-0100-e3x',
                'net.fortuna.ical4j:ical4j:1.0-rc2',
                'commons-collections:commons-collections:3.2.1'

        runtime 'org.hsqldb:hsqldb:2.0.0', 
                'org.eclipse.mylyn.wikitext:core:1.3.0.I20091106-0100-e3x', 
                'org.eclipse.mylyn.wikitext:textile:1.3.0.I20091106-0100-e3x',
                'net.fortuna.ical4j:ical4j:1.0-rc2',
                'commons-collections:commons-collections:3.2.1'
        
        test 'javax.persistence:persistence-api:1.0',
             'com.icegreen:greenmail:1.3.1b'
    }
}
coverage {
    exclusions = ['*/test/*',
                  '*/conf/*',
                  '*/plugins/*',
                  '*/*Spec.groovy',
                  '*/*Tests.groovy',
                  "*/*BootStrap*",
                  "Config*",
                  "*/*DataSource*",
                  "*/*resources*",
                  "*/*UrlMappings*",
                  "*/*Tests*",
                  "*/grails/test/*",
                  "*/org/codehaus/groovy/grails/*",
                  "*/PreInit*",
                  "*GrailsPlugin*"]

    enabledByDefault = false
}

