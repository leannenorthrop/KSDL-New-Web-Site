package org.samye.dzong.london.events
import org.samye.dzong.london.media.Image
import org.samye.dzong.london.Publishable

class Event extends Publishable {
    String title;
    String summary;
    String content;
    Image image;
    Boolean onceOnly;
    // Figure out scheduling
    /*
    Duration duration;
    Time startTime;
    Date startDate;
    Date endDate;
    // Repeats
    daily, weekly (set days of week), monthly (set days of month), every x weeks, every x years
    */
    
    static constraints = {
        title(blank:false,unique:true)
        summary(size:5..Integer.MAX_VALUE)
        content(size:5..Integer.MAX_VALUE)
        onceOnly(nullable:false)
        image(nullable:true)    
    }
    
    String toString() {
        return "${title} (${super.toString()})"
    }    
}
