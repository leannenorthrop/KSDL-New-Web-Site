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
 * Abstract base class for Publishing Articles.
 *
 * TODO Add test for delete archived article
 * TODO Add test for viewing published article
 * TODO Add test for editing published article
 * TODO Add test for viewing archived article
 * TODO Add test for editing archived article
 * TODO Add test for unpublishing articles
 *
 *
 * User: Leanne Northrop
 * Date: Jan 23, 2010, 5:15:26 PM
 */
abstract class EditorArticleTest extends AbstractWebTest {
    abstract String getUsername();
    abstract String getPassword();
    abstract Integer getArchivedId();
    abstract Integer getPublishedId();
    abstract Integer getDeletedId();
    abstract String getAuthor();

    void testCanManageArticles() {
        login()
        get('/article/manage')
        assertStatus 200
        assertContentContains "Manage Articles"
    }

    void testPublishNewsArticle() {
        login()
        get('/article/manage')
        assertStatus 200

        get("/article/pre_publish/${getPublishedId()}")
        assertStatus 200

        form('publish') {
            tags = "news, ${getAuthor()}"
            byId('submitbtn').click()
        }
        doFollowRedirect()
        assertStatus 200
        assertContentContains "Manage Articles"
        click "Published"
        assertContentContains "${getAuthor()} Published Article"
    }

    void testArchiveNewsArticle() {
        login()
        get('/article/manage')
        assertStatus 200

        get("/article/pre_publish/${getArchivedId()}")
        assertStatus 200

        form('publish') {
            tags = "news, ${getAuthor()}"
            byId('submitbtn').click()
        }
        doFollowRedirect()
        assertStatus 200
        assertContentContains "Manage Articles"
        click "Published"
        assertContentContains "${getAuthor()} Archived Article"
        get("/article/changeState/${getArchivedId()}?state=Archived")
        assertStatus 200
        assertContentContains "Manage Articles"
        click "Published"
        assertContentContains "${getAuthor()} Archived Article"
    }

    void testDeleteUnpublishedArticle() {
        login()
        get('/article/manage')
        assertStatus 200

        javaScriptEnabled = false
        get("/article/delete/${getDeletedId()}")
        assertStatus 200
        javaScriptEnabled = true
        get('/article/manage')
        assertStatus 200

        click "Recycle Bin"
        assertContentContains "Deleted Article"
    }

    void login() {
        login(getUsername(),getPassword())
    }
}
