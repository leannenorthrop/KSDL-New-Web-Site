package org.samye.dzong.london.community
import org.grails.taggable.*

class Article implements Taggable {
    String title;
    String summary;
    String content;

    static constraints = {
        title(blank:false)
        summary(maxSize:1024)
        content(maxSize:Integer.MAX_VALUE)
    }
}
