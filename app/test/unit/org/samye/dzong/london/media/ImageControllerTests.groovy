package org.samye.dzong.london.media

import grails.test.*
import org.samye.dzong.london.media.Image

class ImageControllerTests extends ControllerUnitTestCase {

    protected void setUp() {
        super.setUp()
        mockLogging(ImageController, true)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndexWithTagParams() {
        def images = []
        (0..11).each() { i ->
            images << new Image(name: "Image"+i)
        }
        def imageControl = mockFor(Image)
        imageControl.demand.static.findAllByTagInList {ArrayList tags, LinkedHashMap params -> return images }

        controller.params.tags = "ab,cd"
        def model = controller.index()

        assertNotNull model
        assertEquals 12, model['images'].size()
        assertEquals 12, model['total']
        imageControl.verify()
    }

    void testIndexWithNoParams() {
        mockDomain(Image, [new Image(name:"Image1"), new Image(name:"Image2")])

        def model = controller.index()

        assertNotNull model
        assertEquals 2, model['images'].size()
        assertEquals 2, model['total']
    }


    void ignoretestIndexWithMaxNoTagsParams() {
        def images = []
        (0.100).each() { i ->
            images << new Image(name: "Image"+i)
        }
        mockDomain(Image, images)
        controller.params.max = 8
        controller.params.offset = 5

        def model = controller.index()

        assertNotNull model
        assertEquals 8, model['images'].size()
        assertEquals 8, model['total']
    }

}
