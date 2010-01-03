package org.samye.dzong.london.venue

class Bicycle extends Transport {
    String routes;
    
    static constraints = {
    	routes(blank:false)
    }  
}
