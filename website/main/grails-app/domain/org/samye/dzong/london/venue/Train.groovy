package org.samye.dzong.london.venue

class Train extends Transport {
    String station;
    String timetable;
    
    static constraints = {
    	station(blank:false)
    	timetable(blank:false, url:true)
    }  
}
