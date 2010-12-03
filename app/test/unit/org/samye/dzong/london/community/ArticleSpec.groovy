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

package org.samye.dzong.london.community

import grails.plugin.spock.*
import spock.lang.*

/* 
 * Spock unit test for Article domain class. One of the bed-rock classes of this
 * web application.
 *
 * @author Leanne Northrop
 * @since 25th October 2010, 17:24
 */
class ArticleSpec extends UnitSpec {

    def "String shows title and publication status"() {
        expect:
            "title (Unpublished by null on null (Not Deleted))" == validArticle().toString()
    }

    def "Title can not be blank"() {
        setup:
            def title = ""
            def article = new Article(title: title, summary: "summary", content: "content")
            mockForConstraintsTests(Article, [ article ])
            def result

        when:
            result = article.validate()

        then:
            "blank" == article.errors["title"] 
    }

    def "Title can not be null"() {
        setup:
            def title = null 
            def article = new Article(title: title, summary: "summary", content: "content")
            mockForConstraintsTests(Article, [ article ])
            def result

        when:
            result = article.validate()

        then:
            "nullable" == article.errors["title"] 
    }

    def "Title must be unique"() {
        setup:
            def title = "title" 
            def article1 = new Article(title: title, summary: "summary", content: "content")
            def article2 = new Article(title: title, summary: "summary", content: "content")
            mockForConstraintsTests(Article, [ article1, article2 ])
            def result

        when:
            result = article2.validate()

        then:
            "unique" == article2.errors["title"] 
    }

    def "Summary can not be blank"() {
        setup:
            def article = new Article(title: "Hello", summary: "", content: "content")
            mockForConstraintsTests(Article, [ article ])
            def result

        when:
            result = article.validate()

        then:
            "blank" == article.errors["summary"] 
    }

    def "Summary can not be null"() {
        setup:
            def article = new Article(title: "Hello", summary: null, content: "content")
            mockForConstraintsTests(Article, [ article ])
            def result

        when:
            result = article.validate()

        then:
            "nullable" == article.errors["summary"] 
    }

    def "Summary can not be less than 5 characters long"() {
        setup:
            def article = new Article(title: "Hello", summary: "hi", content: "content")
            mockForConstraintsTests(Article, [ article ])
            def result

        when:
            result = article.validate()

        then:
            "size" == article.errors["summary"] 
    }

    def "Content can be blank"() {
        setup:
            def article = new Article(title: "Hello", summary: "summary", content: "")
            mockForConstraintsTests(Article, [ article ])
            def result

        when:
            result = article.validate()

        then:
            "blank" != article.errors["summary"] 
    }

    def "Valid article"() {
        setup:
            def article = validArticle()
            mockForConstraintsTests(Article, [ article ])
            def result

        when:
            result = article.validate()

        then:
            assert result
    }

    def validArticle() {
        def article = new Article(title: "title", 
                                  summary: "summary", 
                                  content: "content",
                                  publishState: 'Unpublished',
                                  category: 'N',
                                  deleted:false,
                                  featured:false,
                                  home:false)
        article
    }
}
