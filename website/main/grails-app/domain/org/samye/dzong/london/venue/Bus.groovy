package org.samye.dzong.london.venue

class Bus extends Transport {
    String route;
    String timetable;
    
    static constraints = {
    	route(blank:false)
    	timetable(blank:false, url:true)
    }  
}
