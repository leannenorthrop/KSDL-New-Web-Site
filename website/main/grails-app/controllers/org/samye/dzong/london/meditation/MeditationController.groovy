package org.samye.dzong.london.meditation

class MeditationController {
    def articleService

    def index = {
        def adviceArticles = [] 
        def benefitsArticles = []
        def topArticles = []
        
        try {
            adviceArticles = articleService.publishedByTags(['meditation advice'])
            benefitsArticles = articleService.publishedByTags(['meditation benefits'])
            topArticles = articleService.publishedByTags(['meditation benefits', 'meditation', 'meditation advice', 'front', 'top'])
        } catch (error) {
            println error
            log.error("Meditation controller encountered an error.")
        }
        
        model:[adviceArticles: adviceArticles, benefitsArticles: benefitsArticles, topArticles: topArticles]
    }
}
