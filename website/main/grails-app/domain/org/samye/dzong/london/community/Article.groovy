package org.samye.dzong.london.community
import org.samye.dzong.london.Publishable

class Article extends Publishable {
    String title;
    String summary;
    String content;

    static constraints = {
        title(blank:false)
        summary(maxSize:1024)
        content(maxSize:Integer.MAX_VALUE)
    }

    String toString() {
        return "#{title}"
    }
}
