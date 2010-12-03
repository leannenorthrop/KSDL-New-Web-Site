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
 * Abstract base class for Author Article Tests. Can not test for error values
 * as jQuery validation catches errors.
 *
 * User: Leanne Northrop
 * Date: Jan 23, 2010, 3:30:46 PM
 */
abstract class AuthorArticleTest extends AbstractWebTest {
    abstract String getUsername();
    abstract String password();

    void testCanManageArticles() {
        login()
        get('/article/manage')
        assertStatus 200
        assertContentContains "Manage Articles"
    }

    void testCancelNewArticle() {
        login()
        get('/article/manage')
        assertStatus 200
        click "New Article"
        assertStatus 200
        form('createarticle') {
            title = "${getUsername()} Article With No Image"
            summary = "Summary text"
            content = "Content"
        }
        click "Articles"
        assertContentContains "Manage Articles"
        assertStatus 200
    }

    void testCreateArticleWithNoImage() {
        login()

        createArticle "${getUsername()} Article Without Image", "Summary text", "This is article content"
    }

    void testCreateArticleWithImage() {
        login()

        createArticleWithImage "${getUsername()} Article With Image", "Summary text", "This is article content", "3"
    }

    void testCreateArticlesForEditorTests() {
        login()

        createArticleWithImage "${getUsername()} Published Article", "Summary text", "This is article content", "1"
        createArticleWithImage "${getUsername()} Archived Article", "Summary text", "This is article content", "2"
        createArticleWithImage "${getUsername()} Deleted Article", "Summary text", "This is article content", "4"
    }

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
}
