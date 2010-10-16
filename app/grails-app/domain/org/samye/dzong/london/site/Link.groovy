package org.samye.dzong.london.site

import org.codehaus.groovy.grails.commons.*

class Link {
    def config = ConfigurationHolder.config
    
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
        cache usage:'read-write', include:'non-lazy'
    }
    
    Link() {
        type = "I"
        name = "New"
        href = "/"
        position = 0
    }

    String toString() {
        def locale = Locale.UK
        def linkTypeName = ''
        def classes = ''
        def thehref = ''
        def link = ''
        if (type == 'E') {
            linkTypeName = 'external';
            classes = ['menuItem',linkTypeName].join(" ");
            thehref = this.href;
            link="<a href='${thehref}' target='_blank' class='${classes}'>${name}</a>"
        } else {
            linkTypeName = 'internal';
            thehref = controller + "/" + action
            def c = controller[0].toUpperCase()
            classes = ['menuItem',linkTypeName,action,c].join(" ")        
            link = "<a href='${thehref}' class='${classes}'>${name}</a>"
        }

        return link
    }
}
