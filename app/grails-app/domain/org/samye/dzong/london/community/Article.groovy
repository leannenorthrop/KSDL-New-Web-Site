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

import org.samye.dzong.london.cms.Publishable
import org.samye.dzong.london.users.ShiroUser
import org.samye.dzong.london.media.Image
import org.grails.comments.*

/*
 * Domain class for storing most site content.
 * This is the bed-rock domain class for this 
 * application.
 * Note named queries unfortunately do not handle
 * order by properly in grails 1.2.0 and will not
 * be fixed until grails 1.3.6
 *
 * @author Leanne Northrop
 * @since  October 2009
 * @see ShiroUser
 */
class Article extends Publishable {
    String title;
    String summary;
    String content;
    Image image;

    static constraints = {
        title(blank:false,unique:true)
        summary(size:5..Integer.MAX_VALUE,blank:false)
        content(maxSize:Integer.MAX_VALUE,blank:true)
        image(nullable:true)
    }

    static namedQueries = {
        authorPublishState { username, final publishState ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', "${publishState}"
            author {
                eq 'username', username
            }
        }

        publishState { final publishState ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', "${publishState}"
        }

        authorDeleted { username ->
            eq('deleted', Boolean.TRUE)
            author {
                eq('username', username)
            }
        }

        deleted {
            eq('deleted', Boolean.TRUE)
        }
        
        publishStateByCategory { final publishState,
                                 final category ->
            eq 'deleted', Boolean.FALSE
            eq 'category', "${category}"
            eq 'publishState', "${publishState}"
        }  

        homeArticles { final orderCol, final orderDir ->
            eq 'home', Boolean.TRUE
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            order("${orderCol}", "${orderDir}")
        }
    }

    static mapping = {
        columns {
            content type:'text'
            summary type:'text'
        }
    }

    /*
     * {@inheritDoc}
     */
    String toString() {
        return title
    }
}
