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

/*
 * Functional tests for user with Author and Editor  permissions only for
 * publishing articles.
 *
 * User: Leanne Northrop
 * Date: Jan 23, 2010, 7:57:08 PM
 */
class bAuthorEditorPublishArticleFunctionalTests extends EditorArticleTest {
    String getUsername() {
        return "AuthorEditor";
    }

    String getPassword() {
        return "change!t";
    }

     Integer getArchivedId() {
        return 9
     }

     Integer getPublishedId() {
         return 8
     }

     Integer getDeletedId() {
         return 10
     }

    String getAuthor() {
        return "AuthorEditor"
    }


}
