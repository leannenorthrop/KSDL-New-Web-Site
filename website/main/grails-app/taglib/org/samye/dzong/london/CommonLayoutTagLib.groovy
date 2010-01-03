package org.samye.dzong.london
import org.apache.shiro.SecurityUtils
import groovy.xml.StreamingMarkupBuilder

class CommonLayoutTagLib {
    static namespace = 'lsdc'
    def tagService
    def messageSource

    def cloud = { attrs ->
        def tags = tagService.tagCounts()
        def biggestTagCount = tags.inject(0){ num, item ->
            num = Math.max(num, item[1])
        }
        if (biggestTagCount == 0) {

            out << "<div class=\"cloud group \"><h2>#{messageSource.getMessage('tag.cloud.title',null,null)}</h2><li></li></div>";
            return;
        }

        def ranks = tags.collect { tag ->
            def percent = (tag[1]/biggestTagCount)*100;
            def group =  Math.round(Math.floor(percent*0.1));
            group
        }
        def cloudList = {
            div(class: "cloud box group") {
                h2(messageSource.getMessage("article.tag.cloud.title",null,null))
                ul {
                    tags.eachWithIndex { tag,index ->
                        li {
                            def label = (tag[0].contains(' ') ? "\"${tag[0]}\"" : tag[0]) + " (${tag[1]})";
                            def linkElement = link(controller:'article', params:[tags:[tag[0]]]) {"<span class=\"tag${ranks[index]}\">${label}</span>"}
                            mkp.yieldUnescaped(linkElement)
                        }
                    }
                }
            }
        }

        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"
        out << builder.bind(cloudList)
    }

    def nav = { attrs ->
        def navControllers = ['home', 'news', 'events', 'meditation']
        if (SecurityUtils.subject.hasRole("Administrator")) {
            navControllers = ['home', 'news', 'events', 'meditation','manageSite','admin']
        } else if (SecurityUtils.subject.principal != null) {
            navControllers = ['home', 'news', 'events', 'meditation','manageSite']
        }

        def current = attrs.current
        def navList = {
            div(class: "nav group") {
                ul {
                    navControllers.each { controllerName ->
                        def aElem = link(controller:controllerName) {"<strong>${messageSource.getMessage(controllerName,null,null)}</strong><em>${messageSource.getMessage(controllerName+".desc",null,null)}</em>"}
                        if (controllerName.equals(attrs.current) && controllerName.equals(navControllers[-1])) {
                            mkp.yieldUnescaped("<li class='last current'>${aElem}</li>")
                        } else if (controllerName.equals(navControllers[-1])){
                            mkp.yieldUnescaped("<li class='last'>${aElem}</li>")
                        } else if (controllerName.equals(attrs.current) && controllerName.equals(navControllers[0])) {
                            mkp.yieldUnescaped("<li class='first current'><a href='${resource(dir:'')}'><strong>${messageSource.getMessage(controllerName,null,null)}</strong><em>${messageSource.getMessage(controllerName+".desc",null,null)}</em></a></li>")
                        } else if (controllerName.equals(navControllers[0])){
                            mkp.yieldUnescaped("<li class='first'><a href='${resource(dir:'')}'><strong>${messageSource.getMessage(controllerName,null,null)}</strong><em>${messageSource.getMessage(controllerName+".desc",null,null)}</em></a></li>")
                        } else if (controllerName.equals(attrs.current)) {
                            mkp.yieldUnescaped("<li class='current'>${aElem}</li>")
                        } else {
                            mkp.yieldUnescaped("<li>${aElem}</li>")
                        }
                    }
                }
            }
        }

        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"
        out << builder.bind(navList)
    }

    def header = { attrs ->
        def header = {
            div(class: "logo group") {
                img(src: resource(dir:'images',file:'logo.png'), alt: messageSource.getMessage('title',null,null))
                h1 {
                    mkp.yieldUnescaped(messageSource.getMessage('title',null,null))
                }
                h2{
                    def aElem
                    if (SecurityUtils.subject.principal != null) {
                        aElem = messageSource.getMessage('signed.in.greeting', [SecurityUtils.subject.principal].toArray(),null) + link(controller:"auth", action:"signOut"){messageSource.getMessage('sign.out',null,null)}
                    } else {
                        aElem = messageSource.getMessage('greeting',null,null) + link(controller:"auth", action:"index"){messageSource.getMessage('sign.in',null,null)}
                    }
                    mkp.yieldUnescaped(aElem)
                }
            }
        }

        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"
        out << builder.bind(header)
    }

    def toolbar = { attrs ->
        def adminControllers = ['home']
        def adminClasses = [home: 'home', article: 'list',image: 'list', venue:'list', roles:'list']

        if (SecurityUtils.subject.hasRole("Editor") && !SecurityUtils.subject.hasRole("Author")) {
            ['article'].each(){ item-> adminControllers << item }
        }
	    if (SecurityUtils.subject.hasRole("Author")) {
	        ['article','image'].each(){ item-> adminControllers << item }
        }
	    if (SecurityUtils.subject.hasRole("Venue Manager")) {
	        ['venue','room'].each(){ item-> adminControllers << item }
        }
	    if (SecurityUtils.subject.hasRole("Administrator")) {
            ['roles'].each(){ item-> adminControllers << item }
        }

        def toolbar = {
            div(class: "menuBar group") {
                adminControllers.each() { controller ->
                    span(class: "menuButton") {
                        def elem
                        if (controller.equals('home')){
                            elem = link(class:adminClasses[controller], controller:"manageSite", action:"home") {messageSource.getMessage('toolbar.'+controller,null,null)}
                        } else if (controller.equals('roles')) {
                            elem = link(class:adminClasses[controller], controller:"admin", action:"roles") {messageSource.getMessage('toolbar.'+controller,null,null)}
                        } else if (controller.equals(attrs.controller)){
                            if ("manage".equals(attrs.action)) {
                                if (SecurityUtils.subject.hasRole("Author")) {
                                	elem = link(class:"create", controller:controller, action:"create") {messageSource.getMessage("toolbar.${controller}.create",null,null)}
                                } else if (SecurityUtils.subject.hasRole("Venue Manager")) {
					elem = link(class:"create", controller:controller, action:"create") {messageSource.getMessage("toolbar.${controller}.create",null,null)}                                
                                }
                            } else if ("create".equals(attrs.action)) {
                                elem = link(class:"list", controller:controller, action:"manage", params:[offset:0,max:10]) {messageSource.getMessage('toolbar.'+controller,null,null)}
                            } else if ("show".equals(attrs.action)) {
                                elem = link(class:"list", controller:controller, action:"manage", params:[offset:0,max:10]) {messageSource.getMessage('toolbar.'+controller,null,null)}
                                if (SecurityUtils.subject.hasRole("Author")) {
                                    elem += link(class:"edit", controller:controller, action:"edit", params:[id:attrs.id]) {messageSource.getMessage("toolbar.${controller}.edit",null,null)}
                                }
                            } else if ("edit".equals(attrs.action) || "pre_publish".equals(attrs.action)) {
                                elem = link(class:"list", controller:controller, action:"manage", params:[offset:0,max:10]) {messageSource.getMessage('toolbar.'+controller,null,null)}
                                if (SecurityUtils.subject.hasRole("Author")) {
                                    elem += link(class:"delete", controller:controller, action:"delete", params:[id:attrs.id], onclick:"return confirm('" + messageSource.getMessage('toolbar.delete.confirm', null, null)+ "');") {messageSource.getMessage("toolbar.${controller}.delete",null,null)}
                                    elem += link(class:"create", controller:controller, action:"create") {messageSource.getMessage("toolbar.${controller}.create",null,null)}
                                }
                            }
                        } else {
                            elem = link(class:adminClasses[controller], controller:controller, action:"manage", params:[offset:0,max:10]) {messageSource.getMessage('toolbar.'+controller,null,null)}
                        }
                        if (elem != null) {
                            mkp.yieldUnescaped(elem)
                        }
                    }
                }
            }
        }
        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"
        out << builder.bind(toolbar)
    }

    def grid = { attrs ->
        out << """<div id="grid">"""
        0.times {
            out << """<span class="gcol"><span class="gleft">&nbsp;</span><span class="ggap">&nbsp;</span><span class="gright">&nbsp;</span></span>"""
        }
        out << "</div>"
    }
}
