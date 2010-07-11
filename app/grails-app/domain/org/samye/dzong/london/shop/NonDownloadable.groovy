package org.samye.dzong.london.shop

class NonDownloadable extends Product {
    int weight
    int height
    int width
    int depth


    static constraints = {
        weight(min:0)
        height(min:0)
        width(min:0)
        depth(min:0)
    }
    
    NonDownloadable() {
        isnew = false
        isdiscount = false
        isdownloadable = false
    }
    
}
