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
 * Runtime application configuration. Grails and plugins configuration plus
 * anything other configuration required across the application lives here.
 * Additional sets logging config, however sql logging lives in Datasource.
 *
 * @author Leanne Northrop
 * @since October 2009
 */

// // locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
tomcat.deploy.url="http://10.0.1.6:8080/manager"
tomcat.deploy.username="tomcat"
tomcat.deploy.password="tomcat"

grails.project.groupId = org.londonsamyedzong
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = true
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      ical: 'text/calendar',
                      ics: 'text/calendar',
                      svg: ['image/svg+xml','image/svg-xml'],
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess=true
grails.gsp.enable.reload = true
//grails.gsp.view.dir = "/var/www/grails/my-app/"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = ''

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder=false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable fo AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
grails.views.javascript.library="jquery"
grails.gorm.autoFlush=true

// set per-environment serverURL stem for creating absolute links
def logfileName = '/var/log/tomcat6/lsd.log'
def catalinaBase = System.properties.getProperty('catalina.base')
if (catalinaBase) {
    logfileName = "${catalinaBase}/logs/lsd.log"
}

environments {
    production {
        if (!catalinaBase){
            logfileName = '/home/londonsamyedzong/logs/lsd.log'
        }
        grails {
           mail {
             //grails.mail.jndiName = "myMailSession"
             grails.mail.default.from="site@londonsamyedzong.org"
             host = "smtp.gmail.com"
             username = "site@londonsamyedzong.org"
             password = "thx1138"
             props = ["mail.smtp.auth":"true",
                      "mail.smtp.socketFactory.port":"465",
                      "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                      "mail.smtp.socketFactory.fallback":"false"]
           }
        }
        grails.serverURL = "http://www.londonsamyedzong.org"
        grails.full.stacktrace=true
        fileuploader {
            themes {
                maxSize = 1000 * 1024 * 4 //4 mbytes
                allowedExtensions = ["zip"]
                path = "/tmp/docs/"
            }
			attachments {
                maxSize = 1000 * 1024 * 12 //12 mbytes
                allowedExtensions = ["mp4","m4v","doc","zip","txt","pdf","avi","mp3","rtf","mov"]
                path = "/tmp/docs/"			
			}
        }
    }
    development {
        grails {
           mail {
             host = "127.0.0.1"
             username = "site@londonsamyedzong.org"
             password = "change!t"
             props = ["mail.smtp.auth":"true",
                      "mail.smtp.socketFactory.port":com.icegreen.greenmail.util.ServerSetupTest.SMTP.port]
           }
        }
        grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
        grails.serverURL = "http://localhost:8080/${appName}"
        grails.full.stacktrace=false
        fileuploader {
            themes {
                maxSize = 1000 * 1024 * 4 //4 mbytes
                allowedExtensions = ["zip"]
                path = System.properties["user.dir"] + "/web-app/css/themes"
            }
			attachments {
                maxSize = 1000 * 1024 * 12 //12 mbytes
                allowedExtensions = ["mp4","m4v","doc","zip","txt","pdf","avi","mp3","rtf","mov"]
                path = "/"		
			}
        }
    }
    test {
        grails {
           mail {
             host = "0.0.0.0"
             username = "site@londonsamyedzong.org"
             password = "change!t"
             props = ["mail.smtp.auth":"true",
                      "mail.smtp.socketFactory.port":com.icegreen.greenmail.util.ServerSetupTest.SMTP.port]
           }
        }
        grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
        grails.serverURL = "http://localhost:8080/${appName}"
        fileuploader {
            themes {
                maxSize = 1000 * 1024 * 4 //4 mbytes
                allowedExtensions = ["zip"]
                path = "/tmp/css/"
            }
			attachments {
                maxSize = 1000 * 1024 * 12 //12 mbytes
                allowedExtensions = ["mp4","m4v","doc","zip","txt","pdf","avi","mp3","rtf","mov"]
                path = "/tmp/docs/"			
			}
        }
    }

}

// log4j configuration
log4j = {
    appenders {
       console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
       rollingFile name: "file", file: logfileName, maxFileSize:'512MB', maxBackupIndex:10,layout:pattern(conversionPattern: '%d{ISO8601} [%t] %p %c %x - %m%n')
       rollingFile name: "stacktrace", file: logfileName + '_stacktrace.log', maxFileSize:'512MB', maxBackupIndex:10,layout:pattern(conversionPattern: '%d{ISO8601} [%t] %p %c %x - %m%n')
    }
    
    root { 
        info 'stdout', 'file'
        additivity = true
    }
            
 /*   production{
        root { 
            info 'stdout', 'file'
            additivity = true
        }
    }
    development {
       root { 
            debug 'stdout','file'
            additivity = true            
        }
        //log4j.appender.'errors.File'="/var/log/tomcat6/stacktrace.log"
    }*/

    fatal 'com.gargoylesoftware.htmlunit.html.HTMLParserListener',
          'com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine',
          'com.gargoylesoftware.htmlunit.html.HtmlPage',
          'com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument',
          'com.gargoylesoftware.htmlunit.javascript.host.HTMLDocument',
          'com.gargoylesoftware.htmlunit.DefaultCssErrorHandler',
          'org.apache.http'

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine',
           'org.quartz',
           'net.sf.ehcache',
           'grails.spring',
           'org.apache.tomcat',
           'org.codehaus.groovy',
           'org.apache.catalina',
           'org.apache.coyote,',
           'org.apache.commons.digester',
           'net.fortuna.ical4j',
           stdout:"StackTrace"


    warn    'org.hibernate',
            'org.mortbay.log',
            'org.samye',
            'grails.app'  
            
    info    'org'
}

auditLog {
  actor = 'userPrincipal.name'
}

grails.commentable.poster.evaluator = { 
    org.samye.dzong.london.users.ShiroUser.findByUsername(org.apache.shiro.SecurityUtils.getSubject().getPrincipal())
}

coverage {
        enabledByDefault = false
        exclusions = ["**/BuildConfig*", "**/*Tests*", '**/*Spec', '**/SecurityFilters*','**/ShiroDbRealm*' ]
        sourceExclusions = ['BuildConfig']
        xml = true
}

quartz {
    autoStartup = true
    jdbcStore = false
}

grails.tomcat.jvmArgs = ["-Xmx1024m", "-XX:MaxPermSize=256m"]

/* For running development/test builds with a JNDI source
grails.naming.entries = [
    "jdbc/KsdlDB": [
        type: "javax.sql.DataSource", //required
        driverClassName: "org.hsql.jdbcDriver",
        url: "jdbc:HypersonicSQL:database",
        username: "SA",
        password: "",
        maxActive: "8",
        maxIdle: "4"
    ]
]*/