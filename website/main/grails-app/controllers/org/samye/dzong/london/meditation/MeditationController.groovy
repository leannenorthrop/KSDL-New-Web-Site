package org.samye.dzong.london.meditation

class MeditationController {
    def articleService

    def index = {
        def adviceArticles = [] 
        def benefitsArticles = []
        def topArticles = []
        
        try {
            articleService.publishedByTags(['meditation advice'])
            articleService.publishedByTags(['meditation benefits'])
            articleService.publishedByTags(['meditation benefits', 'meditation', 'meditation advice', 'front', 'top'])
        } catch (error) {
            log.error("Meditation controller encountered an error.")
        }
        
        model:[adviceArticles: adviceArticles, benefitsArticles: benefitsArticles, topArticles: topArticles]
    }
}
