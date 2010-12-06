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
 * Database configuration.
 *
 * @author Leanne Northrop
 * @since October 2009
 */
dataSource {
    pooled = true
    properties {
        maxActive = 200
        maxIdle = 25
        minIdle = 5
        initialSize = 25
        minEvictableIdleTimeMillis = 60000
        timeBetweenEvictionRunsMillis = 60000
        maxWait = 10000
//        validationQuery = "select count(*) from shiro_role"
    }    
}

hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings
def homeDir = grails.home[0]
environments {
    development {
        dataSource {
//            jndiName = "java:comp/jdbc/KsdlDB"
            // one of 'create', 'create-drop','update'
            //dbCreate = "update"
            /*url = "jdbc:mysql://localhost/londonsamyedzong"
            driverClassName = "com.mysql.jdbc.Driver"
            username = "londonsamyedzong"
            password = "change!t"
            dialect = org.hibernate.dialect.MySQLDialect */
            //url = "jdbc:hsqldb:hsql://localhost/lsd"
            url = "jdbc:hsqldb:file:db/lsd;shutdown=true"            
            driverClassName = "org.hsqldb.jdbcDriver"
            username = "SA"
            password = ""
            dialect = org.hibernate.dialect.HSQLDialect 
            //loggingSql = true       
        }
    }

    test {
        dataSource {
            dbCreate = "create"
            url = "jdbc:hsqldb:file:db/test;shutdown=true"
            driverClassName = "org.hsqldb.jdbcDriver"
            username = "SA"
            password = ""
            dialect = org.hibernate.dialect.HSQLDialect            
            loggingSql = false 
        }
    }

    production {
        dataSource {
            url = "jdbc:hsqldb:hsql://localhost/lsd"

            driverClassName = "org.hsqldb.jdbcDriver"
            username = "SA"
            password = ""
            dialect = org.hibernate.dialect.HSQLDialect
            //jndiName = "java:comp/env/jdbc/KsdlDS"
        }        
    }
}
