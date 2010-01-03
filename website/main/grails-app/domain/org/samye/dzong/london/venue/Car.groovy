package org.samye.dzong.london.venue

class Car extends Transport {
    String nearestCarPark;
    Boolean hasParking;
    
    static constraints = {
    	nearestCarPark(nullable:true)
    	hasParking(blank:false)
    }  
}
