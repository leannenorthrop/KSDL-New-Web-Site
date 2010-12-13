package org.samye.dzong.london.meditation

import grails.test.*
import grails.plugin.spock.*
import org.samye.dzong.london.cms.Publishable
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.events.Event
import org.samye.dzong.london.site.Setting
import org.samye.dzong.london.site.Link
import org.samye.dzong.london.media.*

class MeditationControllerSpec extends ControllerSpec {
    def flickrService
    
    def 'Index redirects to home'() {
        when:
        controller.index()

        then:
        redirectArgs == [action: controller.home]
    }

    def 'section page featches home and featured articles, teachers, events and slideshow album'() {
        setup:
        stubFinderMethods(["MeditationHomeArticles","MeditationAllArticles","MeditationFeaturedEvents"])
        Setting.metaClass.static.meditationSlideshow = { new Expando(list: {[]}) }
        FlickrService.metaClass.getPhotosetCover = { [] }
        FlickrService.metaClass.getPhotoset = { i -> [] }        
        controller.flickrService = new FlickrService()
        mockDomain(Link)

        when:
        controller.home()

        then:
        controller.modelAndView.viewName == 'index'
        controller.modelAndView.model.linkedHashMap.links == [] 
        controller.modelAndView.model.linkedHashMap.album == [] 
        controller.modelAndView.model.linkedHashMap.homeArticles == [] 
        controller.modelAndView.model.linkedHashMap.allArticles == [] 
        controller.modelAndView.model.linkedHashMap.featuredEvents == [] 
    }

    def 'list should fetch all meditation content'() {
        setup:
        stubFinderMethods(["MeditationAllArticles"])

        when:
        def model = controller.list()

        then:
        model.allArticles == [] 
    }

    def 'view should return id of requested article'() {
        setup:
        stubViewMethods(["Article"])
        mockParams << [id:1]

        when:
        def model = controller.view()

        then:
        model.id == 1
    }
    
    def 'event'() {
        setup:
        stubViewMethods(["Event"])
        mockParams << [id:1]
        
        when:
        def model = controller.event()

        then:
        model.id == 1
    }

    def 'events'() {
        setup:
        stubFinderMethods(["MeditationAllEvents"])

        when:
        def model = controller.events()

        then:
        model.allEvents == []
    }

    def 'slideshow'() {
        setup:
        def album = []
        Setting.metaClass.static.meditationSlideshow = { new Expando(list: {[]}) }
        FlickrService.metaClass.getPhotoset = { i -> album }
        controller.flickrService = new FlickrService()

        when:
        def model = controller.slideshow()

        then:
        model.album == album
    }
    
    def stubFinderMethods(list) {
        list.each {
            controller.metaClass."findPublished${it}" = {params-> 
                def name = it - "Meditation"
                name = name.substring(0,1).toLowerCase() + name.substring(1)
                [(name):[]]
            }            
        }        
    }
        
    def stubViewMethods(list) {
        list.each {
            controller.metaClass."view${it}" = {id-> 
                [(it.toLowerCase()): null, id: id, similar:[]]
            }            
        }        
    } 
}
