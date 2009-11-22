package org.samye.dzong.london.meditation

class MeditationController {
    def articleService

    def index = {
        def adviceArticles = articleService.publishedByTags(['meditation advice'])
        def benefitsArticles = articleService.publishedByTags(['meditation benefits'])
        model:[adviceArticles: adviceArticles, benefitsArticles: benefitsArticles]
    }
}
