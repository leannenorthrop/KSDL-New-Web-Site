package org.samye.dzong.london.community
import org.samye.dzong.london.Publishable
import org.samye.dzong.london.ShiroUser
import org.samye.dzong.london.media.Image

class Article extends Publishable {
    String title;
    String summary;
    String content;
    Image image;

    static constraints = {
        title(blank:false,unique:true)
        summary(size:5..Integer.MAX_VALUE,blank:false)
        content(size:5..Integer.MAX_VALUE,blank:false)
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

        publishedByTags { tags, orderCol, orderDir ->

        }
    }

    static mapping = {
        columns {
            content type:'text'
            summary type:'text'
        }
    }

    String toString() {
        return "${title} (${super.toString()})"
    }
}
