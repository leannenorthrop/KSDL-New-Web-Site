package org.samye.dzong.london

import grails.test.*
import org.apache.shiro.crypto.hash.Sha1Hash

class ShiroUserTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testChangePassword() {
        def user = ShiroUser.get(2)
        assertEquals 'web-admin', user.username
        assertEquals 'f86c09c1159dc2082ee27f7aef08f1ed6a5be03d', user.passwordHash
        def newPasswordHash = new Sha1Hash('abc').toHex()
        user.passwordHash = newPasswordHash
        user.save()
        user.errors.each {
            println it
        }
        assertFalse user.hasErrors()
        def user2 = ShiroUser.get(2)
        assertEquals newPasswordHash, user2.passwordHash
        println "New password is ${user2.passwordHash}"
    }
}

