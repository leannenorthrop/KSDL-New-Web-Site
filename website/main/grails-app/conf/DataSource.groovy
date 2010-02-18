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
            // one of 'create', 'create-drop','update'
            dbCreate = "update"
//            url = "jdbc:mysql://localhost:3306/lsd"
//            driverClassName = "com.mysql.jdbc.Driver"
//            username = "northrl"
//            password = "change!t"
            url = "jdbc:hsqldb:file:db/dev;shutdown=true"
            driverClassName = "org.hsqldb.jdbcDriver"
            username = "sa"
            password = ""
        }
    }

    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:file:db/test;shutdown=true"
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
