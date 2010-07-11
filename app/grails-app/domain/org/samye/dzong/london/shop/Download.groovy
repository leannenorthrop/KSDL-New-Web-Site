package org.samye.dzong.london.shop

import com.lucastex.grails.fileuploader.UFile

class Download extends Product {
    UFile file
    
    static constraints = {
        file(nullable:false)
    }
    
    static mapping = {
        file cascade: "all-delete-orphan"        
    }    
}
