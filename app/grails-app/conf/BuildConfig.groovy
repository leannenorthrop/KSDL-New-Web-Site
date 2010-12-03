/*
 * Copyright © 2010 Leanne Northrop
 *
 * This file is part of Samye Content Management System.
 *
 * Samye Content Management System is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Samye Content Management System is distributed in the hope that it will be
 * useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Samye Content Management System.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * BT plc, hereby disclaims all copyright interest in the program
 * “Samye Content Management System” written by Leanne Northrop.
 */

/**
 * Build configuration. Would be nice to externalize most of the information
 * so that not building a war per environment, but since only have one deployment
 * may not be necessary.
 *
 * @author Leanne Northrop
 * @since  October, 2009
 */
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
                'commons-collections:commons-collections:3.2.1',
//                'javax.persistence:persistence-api:1.0',
//                 'com.icegreen:greenmail:1.3.1b',
                 'mysql:mysql-connector-java:5.0.8'
        
        test 'javax.persistence:persistence-api:1.0',
             'com.icegreen:greenmail:1.3.1b',
             'org.objenesis:objenesis:1.2'
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

