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
            url = "jdbc:mysql://localhost:3306/londonsamyedzong"
            driverClassName = "com.mysql.jdbc.Driver"
            username = "londonsamyedzong"
            password = "change!t"
//            url = "jdbc:hsqldb:file:db/dev;shutdown=true"
            url = "jdbc:hsqldb:hsql://localhost/xdb"
            driverClassName = "org.hsqldb.jdbcDriver"
            username = "SA"
            password = ""
            dialect = org.hibernate.dialect.HSQLDialect            
*/
            pooled = true
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            properties {
                maxActive = 50
                maxIdle = 25
                minIdle = 5
                initialSize = 5
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 60000
                maxWait = 10000
                validationQuery = "select 1"
            }

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
