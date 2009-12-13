package org.samye.dzong.london

class HomeController {
    def articleService

    def index = {
        def meditationArticles = articleService.publishedByTags(['meditation','front'])
        def communityArticles = articleService.publishedByTags(['community', 'front'])
        def buddhismArticles = articleService.publishedByTags(['buddhism', 'front'])
        def wellbeingArticles = articleService.publishedByTags(['wellbeing', 'front'])
        def newsArticles = articleService.publishedByTags(['news', 'front'])
        model:[meditationArticles: meditationArticles, communityArticles: communityArticles, buddhismArticles: buddhismArticles, wellbeingArticles: wellbeingArticles, newsArticles: newsArticles]
    }
}
