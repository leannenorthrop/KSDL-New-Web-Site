// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
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
grails.views.gsp.sitemesh.preprocess=false
grails.gsp.enable.reload = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder=false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable fo AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
grails.views.javascript.library="jquery"

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    }

    root {
        warn 'stdout', 'file'
        additivity = true
    }

    fatal 'com.gargoylesoftware.htmlunit.html.HTMLParserListener',
          'com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine',
          'com.gargoylesoftware.htmlunit.html.HtmlPage',
          'com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument',
          'com.gargoylesoftware.htmlunit.javascript.host.HTMLDocument',
          'com.gargoylesoftware.htmlunit.DefaultCssErrorHandler'

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate',
           'com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine',
           stdout:"StackTrace"


    warn   'org.mortbay.log'

    trace   'org.samye'
}

auditLog {
  actor = 'userPrincipal.name'
}

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        println "Environment is set to Production"
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
        grails.serverURL = "http://184.73.216.159"
        grails.full.stacktrace=true
        log4j = {
            appenders {
               'null' name:'stacktrace'
               file name:'file', file:"/var/log/tomcat5/lsd.log"
            }
        }
    }
    development {
        println "Environment is set to Development"
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
    }
    test {
        println "Environment is set to Test"
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
    }

}
