package org.samye.dzong.london

import grails.test.*

class PersonTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testToString() {
        def person = new Person(titlePrefix: "Jnr.", firstName: "Donald", lastName: "Duck", titleSuffix: "Ba (Hons.) Comedy")
        assertEquals "Jnr. Donald Duck, Ba (Hons.) Comedy", person.toString()
    }

    void testFirstNameCanNotBeBlank() {
        mockDomain(Person)
        def person = new Person(firstName: "", lastName: "hi")
        person.validate()
        assertEquals "blank", person.errors.firstName
    }

    void testLastNameCanNotBeBlank() {
        mockDomain(Person)
        def person = new Person(firstName: "hi", lastName: "")
        person.validate()
        assertEquals "blank", person.errors.lastName
    }

    void testFirstNameCanNotBeBiggerThan512() {
        def bigName = ""
        (1..516).each{bigName += "a"}
        mockDomain(Person)
        def person = new Person(firstName: bigName, lastName: "hi")
        person.validate()
        assertEquals "maxSize", person.errors.firstName
    }

    void testLastNameCanNotBeBiggerThan512() {
        def bigName = ""
        (1..516).each{bigName += "a"}
        mockDomain(Person)
        def person = new Person(firstName: "hi", lastName: bigName)
        person.validate()
        assertEquals "maxSize", person.errors.lastName
    }
}
