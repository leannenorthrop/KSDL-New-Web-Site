package org.samye.dzong.london.media

import grails.test.*

class ImageTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testImageSearchByTag() {
        new Image(name:"Image1").save(flush:true);
        new Image(name:"Image2").save(flush:true);
        new Image(name:"Image3").save(flush:true);
        new Image(name:"Image4").save(flush:true);
        new Image(name:"Image4").save(flush:true);

        def image = Image.findByName("Image1")
        assertNotNull image
        image.parseTags("beautiful,strawberry")
        Image.findByName("Image2").parseTags("beautiful")
        Image.findByName("Image3").parseTags("strawberry")
        Image.findByName("Image4").parseTags("hello")
        Image.findByName("Image5").parseTags("strawberry")

        assertEquals 3, Image.countByTag("strawberry")
        assertEquals 2, Image.countByTag("beautiful")
        assertEquals 1, Image.countByTag("hello")
        assertEquals 0, Image.countByTag("me")
    }

}
