/*******************************************************************************
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
 ******************************************************************************/

package org.samye.dzong.london.community

import org.samye.dzong.london.Publishable
import org.samye.dzong.london.media.Image

/**
 * Domain class for storing teacher information.
 * Note named queries unfortunately do not handle
 * order by properly in grails 1.2.0.
 * TODO: Test
 * TODO: Change tostring to lookup title.
 */
class Teacher extends Publishable {
    String name;
    String title;
    String type;
    String summary;
    String content;
    Image image;
    def messageSource

    static constraints = {
        title(blank:false,inList:['V','L','R','M','MS','MZ','MSS','K','HH','HE','HS'])
        name(blank:false,unique:true)
        type(blank:false,inList:['L','V','C','O'])
        summary(size:5..Integer.MAX_VALUE,blank:false)
        content(maxSize:Integer.MAX_VALUE,blank:true)
        image(nullable:true)
    }

    static namedQueries = {
        orderedAuthorPublishState { username, publishState, orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            author {
                eq 'username', username
            }
            order("${orderCol}", "${orderDir}")
        }

        authorPublishState { username, publishState ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            author {
                eq 'username', username
            }
        }

        orderedPublishState { publishState, orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            order("${orderCol}", "${orderDir}")
        }

        publishState { publishState ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
        }

        deletedAuthor { username ->
            eq('deleted', Boolean.TRUE)
            author {
                eq('username', username)
            }
        }

        deleted {
            eq('deleted', Boolean.TRUE)
        }
    }

    static mapping = {
        columns {
            content type:'text'
            summary type:'text'
        }
    }

    String toString() {
        return name
    }
}
