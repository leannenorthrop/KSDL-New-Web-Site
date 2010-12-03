/*
 * Copyright © 2010 Leanne Northrop
 *
 * This file is part of Samye Content Management System.
 *
 * Samye Content Management System is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Samye Content Management System is distributed in the hope that it will be
 * useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Samye Content Management System.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * BT plc, hereby disclaims all copyright interest in the program
 * “Samye Content Management System” written by Leanne Northrop.
 */

package org.samye.dzong.london.buddhism

import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.site.Setting
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.site.Link

/*
 * Buddhist content url handler. Displays only public facing pages.
 *
 * @author Leanne Northrop
 * @since  November 2009
 */
class BuddhismController {
    def articleService
    def eventService
    def flickrService
    def teacherService

    def index = {
        redirect(action:home)
    }

    def home = {
        def topArticles = Article.homeBuddhismArticles('datePublished','desc').list()
        def articles = Article.featuredBuddhismArticles('datePublished','desc').list()
        def total = Article.allBuddhismArticlesNotOrdered().count()
        def events = Event.buddhism('featured','desc').list()
        def lineageTeachers = Teacher.findAllByPublishStateAndType('Published', 'L',[sort: "name", order: "asc"])

        def album = []
        try {
            def ss = Setting.buddhistSlideshow().list()
            album = flickrService.getPhotosetCover(ss && ss.size() > 0 ? ss[0].value :'72157623174318636')
        } catch(error) {
            log.error("Unable to fetch flickr album for slideshow link", error)		
        }
		
        def model = [links:Link.findAllBySection("B"),album:album,topArticles: topArticles, articles: articles,total:total,events:events,teachers:lineageTeachers]
        articleService.addHeadersAndKeywords(model,request,response)
        return render(view: 'index',model: model);
    }

    def list = {
        def articles = Article.allBuddhismArticles('datePublished', 'desc').list()
        def model = [ articles: articles, title: 'buddhism.all.articles.title']
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }

    def view = {
        def model = articleService.view(params.id)
        articleService.addHeadersAndKeywords(model,request,response)
        if (!model) {
            redirect(action:home)
        } else {
            return model
        }
    }

    def event = {
        def event = Event.get(params.id)
        if (!event) {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: home)
        }
        else {
            def id = params.id;
            def similar = eventService.findSimilar(event)
            def model = [event: event, id: id, similar:similar]
            articleService.addHeadersAndKeywords(model,request,response)
            model
        }
    }

    def events = {
        def model = eventService.list('B',params)
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }

    def slideshow = {
        def ss = Setting.buddhistSlideshow().list()
        def album = flickrService.getPhotoset(ss && ss.size() > 0 ? ss[0].value :'72157623174318636')
        [album:album]
    }
    
    def teacher = {
        def teacher = Teacher.get(params.id)
        if (!teacher) {
            // TODO: render 404
            redirect(uri: '/')
        }
        else {
            /* TODO link in articles that mention the teacher
            def aboutUsArticles = articleService.publishedByTags(['about us']);
            aboutUsArticles = aboutUsArticles.findAll { article -> article.id != params.id }
            if (model['articles']) {
            def articles = model['articles']
            articles << aboutUsArticles
            } else {
            model['articles'] = aboutUsArticles
            }*/
            def events = teacherService.events(params.id);
            def articles = []
            if (teacher.name == 'Community' || teacher.name == 'All Welcome'){
                articles = articleService.publishedByTags(['about us']);
            }
            def model = [teacher: teacher, id: params.id, events:events, articles:articles]
            articleService.addHeadersAndKeywords(model,request,response)
            model
        }		
    }
}
