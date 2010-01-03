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
                        dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            //url = "jdbc:hsqldb:hsql://localhost/lsd"
            url = "jdbc:hsqldb:hsql:mem:lsd"
            driverClassName = "org.hsqldb.jdbcDriver"
            username = "sa"
            password = ""
                }
        }
        test {
                dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:hsql:mem:lsd-test"
            driverClassName = "org.hsqldb.jdbcDriver"
            username = "sa"
            password = ""
                }
        }
        production {
                dataSource {
            dbCreate = "create-drop"
            jndiName = "java:comp/env/jdbc/LsdDS"
                }
        }
}