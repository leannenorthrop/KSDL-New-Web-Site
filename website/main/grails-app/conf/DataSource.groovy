dataSource {
    pooled = true
}

hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            //url = "jdbc:mysql://localhost:3306/lsd"
            //url = "jdbc:hsqldb:hsql://localhost/lsd"
            //url = "jdbc:hsqldb:file:devDB;shutdown=true"
            url = "jdbc:hsqldb:file:lsd-test;shutdown=true"
            driverClassName = "org.hsqldb.jdbcDriver"
            //driverClassName = "com.mysql.jdbc.Driver"
//            username = "northrl"
//            password = "change!t"
            username = "sa"
            password = ""
            System.setProperty('dbtype', 'hsql')
        }
    }

    test {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:file:lsd-test;shutdown=true"
            driverClassName = "org.hsqldb.jdbcDriver"
            username = "sa"
            password = ""
        }
    }

    production {
        dataSource {
            dbCreate = "update"
            jndiName = "java:comp/env/jdbc/LsdDS"
        }
    }
}
