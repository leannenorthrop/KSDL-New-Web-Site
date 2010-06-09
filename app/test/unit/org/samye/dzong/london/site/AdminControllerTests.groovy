package org.samye.dzong.london.site

import grails.test.*

class AdminControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
        def model = controller.index()
        assertNull model
    }
}
