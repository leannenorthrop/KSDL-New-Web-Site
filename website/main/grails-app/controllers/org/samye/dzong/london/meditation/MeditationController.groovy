package org.samye.dzong.london.meditation

class MeditationController {
    def articleService

    def index = {
        def adviceArticles = articleService.publishedByTags(['meditation advice'])
        def benefitsArticles = articleService.publishedByTags(['meditation benefits'])
        def topArticles = articleService.publishedByTags(['meditation benefits', 'meditation', 'meditation advice', 'front', 'top'])
        model:[adviceArticles: adviceArticles, benefitsArticles: benefitsArticles, topArticles: topArticles]
    }
}
