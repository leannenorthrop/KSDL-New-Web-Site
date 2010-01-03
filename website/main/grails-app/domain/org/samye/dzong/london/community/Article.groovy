package org.samye.dzong.london.community
import org.samye.dzong.london.Publishable
import org.samye.dzong.london.media.Image

class Article extends Publishable {
    String title;
    String summary;
    String content;
    Image image;

    static constraints = {
        title(blank:false,unique:true)
        summary(size:5..Integer.MAX_VALUE)
        content(size:5..Integer.MAX_VALUE)
        image(nullable:true)
    }  

    String toString() {
        return "#{title}"
    }
}
