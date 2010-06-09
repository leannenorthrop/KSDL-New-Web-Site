package org.samye.dzong.london.venue

class Transport {
    String description;
    
    static auditable = true
    
    static constraints = {
        description(blank:false)
    }  
}
