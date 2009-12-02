package org.samye.dzong.london.events

import grails.test.*

class EventsControllerTests extends ControllerUnitTestCase {
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
