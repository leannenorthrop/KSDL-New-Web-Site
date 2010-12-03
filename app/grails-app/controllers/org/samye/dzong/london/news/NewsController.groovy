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

package org.samye.dzong.london.news

import org.samye.dzong.london.community.Article


/*
 * News content url handler. Displays only public facing pages.
 * TODO: Add support for similar articles
 * TODO: Add support for article not found
 * TODO: Add support for params to lists 
 *
 * @author Leanne Northrop
 * @since  November 2009
 */
class NewsController {
    def articleService

    def index = {
        redirect(action:home)
    }

    def home = {
        def articles = Article.featuredNewsArticles('datePublished', 'desc').list()
        def archivedArticles = Article.archivedNewsArticles('datePublished', 'desc').list(max:8)
        def totalPublishedNewsArticles = articles.size()
        def totalArchived = Article.publishState('Archived').count()
        def model = [ total: totalPublishedNewsArticles, totalArchived: totalArchived, articles: articles, archivedArticles: archivedArticles]
        articleService.addHeadersAndKeywords(model,request,response)
        render(view: 'index', model:model)
    }

    def current = {
        render(view: 'list', model: list(false,request,response))
    }

    def archived = {
        render(view: 'list', model: list(true,request,response))
    }

    def view = {
        def model = articleService.view(params.id)
        articleService.addHeadersAndKeywords(model,request,response)
        render(view: 'view', model: model)
    }

    def list(archived,request,response) {
        def articles = []
        def title
        if (archived) {
            articles = Article.archivedNewsArticles('datePublished', 'desc').list()
            title = 'news.archived.title'
        } else {
            articles = Article.newsArticles('datePublished', 'desc').list()
            title = 'news.current.title'
        }

        def model = [ news: articles, title: title]
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }

}
