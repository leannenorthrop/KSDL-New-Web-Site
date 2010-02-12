package org.samye.dzong.london.community

class CommunityController {
    def articleService

    def index = {
        redirect(action:home)
    }

    def home = {
        def topArticles = articleService.publishedByTags(['community','front'])
        def community = articleService.publishedByTags(['community'])
        def volunteerOpportunities = articleService.publishedByTags(['volunteer'])
        def totalCommunity = community.size()
        def totalVolunteer = volunteerOpportunities.size()
        return render(view: 'index',model: [topArticles: topArticles, community: community,volunteerOpportunities:volunteerOpportunities,totalCommunity:totalCommunity,totalVolunteer:totalVolunteer]);
    }
}
