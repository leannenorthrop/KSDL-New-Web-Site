dataSource {
    pooled = true
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:hsql://localhost/lsd"
            driverClassName = "org.hsqldb.jdbcDriver"
            username = "sa"
            password = ""
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            jndiName = "java:comp/env/jdbc/LsdDS"
        }
    }
    production {
        dataSource {
            dbCreate = "create-drop"
            jndiName = "java:comp/env/jdbc/LsdDS"
        }
    }
}