package org.samye.dzong.london.meditation

import grails.test.*
import org.samye.dzong.london.Publishable
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.community.ArticleService

class MeditationControllerTests extends ControllerUnitTestCase {
    def articleServiceControl

    protected void setUp() {
        super.setUp()
        mockLogging(MeditationController, false)
        articleServiceControl = mockFor(ArticleService)
        def testInstances = [ new Article(title: "NH-4273997", summary: "Laptop", content: "", publishState: "published"),
                              new Article(title: "EC-4395734", summary: "Lamp", content: "", publishState: "published"),
                              new Article(title: "TF-4927324", summary: "Laptop", content: "", publishState: "published") ]
        mockDomain(Article, testInstances)
        articleServiceControl.demand.publishedByTags(1..2) { tags ->
            if (tags[0].equals('meditation benefits')) {
                [testInstances[1], testInstances[2]]
            } else {
                [testInstances[0]]
            }
        }

        controller.articleService = articleServiceControl.createMock()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
        def model = controller.index()

        assertNotNull model
        assertNotNull model.adviceArticles
        assertNotNull model.benefitsArticles
        assertEquals 1, model.adviceArticles.size()
        assertEquals 2, model.benefitsArticles.size()
    }

    void testIndexFetchesArticlesFromService() {
        controller.index()
        articleServiceControl.verify()
    }
}
