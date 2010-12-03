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

package org.samye.dzong.london.articles

import org.samye.dzong.london.AbstractWebTest

/*
 * Abstract base class for Published/Archived Articles actions.
 *
 * User: Leanne Northrop
 * Date: Jan 23, 2010, 8:32:59 PM
 */
class PostPublishArticleTest extends AbstractWebTest {

    void login() {
        login(getUsername(),password())
    }

    void createArticleWithImage(final titleContent, final summaryContent, final contentContent, final imageId) {
        get('/article/manage')
        assertStatus 200
        click "New Article"
        form('createarticle') {
            title = "${titleContent}"
            summary = "${summaryContent}"
            content = "${contentContent}"
            selects['image.id'].select "${imageId}"
            byId('submitbtn').click()
        }
        doFollowRedirect()
        assertStatus 200
        assertContentContains "Manage Articles"
        assertContentContains "${titleContent}"
    }

    void createArticle(final titleContent, final summaryContent, final contentContent) {
        get('/article/manage')
        assertStatus 200
        click "New Article"
        form('createarticle') {
            title = "${titleContent}"
            summary = "${summaryContent}"
            content = "${contentContent}"
            byId('submitbtn').click()
        }
        doFollowRedirect()
        assertStatus 200
        assertContentContains "Manage Articles"
        assertContentContains "${titleContent}"
    }

    String getUsername() {
        return "AuthorEditor";
    }

    String password() {
        return "change!t";
    }

    void publishArticle(final labels, final id, final title) {
        get('/article/manage')
        assertStatus 200

        get("/article/pre_publish/${id}")
        assertStatus 200

        form('publish') {
            tags = "${labels}"
            byId('submitbtn').click()
        }
        doFollowRedirect()
        assertStatus 200
        assertContentContains "Manage Articles"
        click "Published"
        assertContentContains "${title}"
    }
}
