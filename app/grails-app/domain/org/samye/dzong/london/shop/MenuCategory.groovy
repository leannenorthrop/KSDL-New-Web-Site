package org.samye.dzong.london.shop

class MenuCategory {

    int level
    String name
    boolean _deleted

    static transients = ['_deleted']

    static belongsTo = [ product : Product ]

    static constraints = {
    }
    
    static mapping = {
        sort 'level'
    }
    
    MenuCategory() {
        level = 0
        name = "New Category"
    }

    MenuCategory(MenuCategory toBeCopied) {
        this.level = toBeCopied.level
        this.name = toBeCopied.name
    }

    String toString() {
        name
    }
}
