package org.samye.dzong.london

class HomeController {
    def articleService

    def index = {
        def meditationArticles = articleService.publishedByTags(['meditation','home'], [max: 1])
        def communityArticles = articleService.publishedByTags(['community', 'home'], [max: 1])
        def buddhismArticles = articleService.publishedByTags(['buddhism', 'home'], [max: 1])
        def wellbeingArticles = articleService.publishedByTags(['wellbeing', 'home'], [max: 1])
        def newsArticles = articleService.publishedByTags(['news'], [max: 5])
        model:[meditationArticles: meditationArticles, communityArticles: communityArticles, buddhismArticles: buddhismArticles, wellbeingArticles: wellbeingArticles, newsArticles: newsArticles]
    }
}
