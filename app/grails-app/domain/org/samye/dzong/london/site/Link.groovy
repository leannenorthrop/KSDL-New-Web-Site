package org.samye.dzong.london.site

class Link {
    def messageSource
    
    String type                                
    String name
    String href                            
    String controller
    String action
    String refid                 
    Integer position
    String section

    boolean _deleted

    static transients = ['_deleted']

    static constraints = {
        action(nullable:true)        
        controller(nullable:true)
        href(nullable:true)
        refid(nullable:true)        
        name(blank:false)
        type(blank:false,inList:['I','E'])        
        position(nullable:false)
        section(nullable:false,inList:['H','B','M'])        
    }

    static mapping = {
        sort position:"asc"
    }
    
    Link() {
        type = "I"
        name = "New"
        href = "/"
        position = 0
    }

    String toString() {
        def locale = Locale.UK
        def linkTypeName = (type == 'E' ? 'external' : 'internal');
        return messageSource ? messageSource.getMessage('link.' + linkTypeName + '.str',[href, linkTypeName,name].toArray(),locale) : "${name} ${type} ${position} ${section}"
    }
}
