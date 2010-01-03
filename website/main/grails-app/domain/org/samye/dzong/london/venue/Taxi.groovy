package org.samye.dzong.london.venue

class Taxi extends Transport {
    String name;
    Telephone number;
    
    static constraints = {
    	name(blank:false)
    	number(nullable:true)
    }  
}
