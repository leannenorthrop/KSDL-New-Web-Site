eventCreateWarStart = { final warName, final dir ->
    println "Removing unrequired libraries and plugins"
    // mail-1.4 may be required if not already present in tomcat as
    // does persistence-api
    Ant.delete() {
       fileset(dir:dir.path+'/WEB-INF/lib'){
           exclude(name:"antlr*.jar")
           include(name:"jdbc2_0-stdext.jar")
           include(name:"log4j*.jar")
           include(name:"slf4j*.jar")
           include(name:"mysql*.jar")
           include(name:"activation.jar")
           include(name:"ant*.jar")
           include(name:"*-jdk14-*.jar")
           include(name:"javassist-3.4.GA.jar")
           include(name:"mail-1.4.jar")
           include(name:"commons-beanutils-1.7.0.jar")
           include(name:"commons-collections-3.1.jar")
           include(name:"*junit*.jar")
           include(name:"ehcache-1.2.3.jar")
           include(name:"quartz-1.5.2.jar")
       }
    }
    Ant.delete(dir:dir.path+'/WEB-INF/plugins/spock-0.5-groovy-1.7-SNAPSHOT')
    Ant.delete(dir:dir.path+'/plugins/code-coverage-1.1.8')
}
