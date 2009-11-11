package org.samye.dzong.london.community

import grails.test.*

class TeacherTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testToString() {
        def teacher = new Teacher(titlePrefix: "Jnr.", firstName: "Donald", lastName: "Duck", titleSuffix: "Ba (Hons.) Comedy", bio: "bio dont display", summary: "dont display me")
        assertEquals "Jnr. Donald Duck Ba (Hons.) Comedy", teacher.toString()
    }

    void testBioCanNotBeBiggerThan512() {
        def bigName = ""
        (1..516).each{bigName += "a"}
        mockDomain(Person)
        def teacher = new Teacher(bio: bigName, firstName: "hi", lastName: "hi")
        teacher.validate()
        assertEquals "maxSize", teacher.errors.bio
    }
}
