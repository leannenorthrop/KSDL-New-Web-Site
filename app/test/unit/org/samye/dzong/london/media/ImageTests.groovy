package org.samye.dzong.london.media

import grails.test.*

class ImageTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNameCanNotBeBlank() {
        def validImage = new Image(name: "Valid Image")
        mockForConstraintsTests(Image, [validImage])

        def blankImage = new Image(name: "")
        assertFalse blankImage.validate()
        assertEquals "blank", blankImage.errors["name"]
    }

    void testNameCanNotBeNull() {
        def validImage = new Image(name: "Valid Image")
        mockForConstraintsTests(Image, [validImage])

        def blankImage = new Image()
        assertFalse blankImage.validate()
        assertEquals "nullable", blankImage.errors["name"]
    }

    void testNameMustBeUnique() {
        def validImage = new Image(name: "Valid Image")
        mockForConstraintsTests(Image, [validImage])

        def blankImage = new Image(name: "Valid Image")
        assertFalse blankImage.validate()
        assertEquals "unique", blankImage.errors["name"]
    }
}
