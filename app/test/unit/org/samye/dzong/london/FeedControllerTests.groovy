package org.samye.dzong.london

import grails.test.*

class FeedControllerTests extends ControllerUnitTestCase {
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