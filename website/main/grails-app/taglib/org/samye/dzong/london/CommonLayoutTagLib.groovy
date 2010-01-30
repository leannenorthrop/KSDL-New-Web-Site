package org.samye.dzong.london
import org.apache.shiro.SecurityUtils
import groovy.xml.StreamingMarkupBuilder

class CommonLayoutTagLib {
    static namespace = 'lsdc'
    def tagService
    def messageSource

    def cloud = { attrs ->
        def tags = tagService.tagCounts()
        def biggestTagCount = tags.inject(0) {num, item ->
            num = Math.max (num, item[1])
        }

        if (biggestTagCount == 0) {
            out << "<div class=\"cloud group \"><h2>#{messageSource.getMessage('tag.cloud.title',null,null)}</h2><li></li></div>";
            return;
        }

        def ranks = tags.collect { tag->
            def percent = (tag[1] / biggestTagCount) * 100;
            def group = Math.round (Math.floor (percent * 0.1));
            return group
        }

        def cloudList = {
            div (class:"cloud box group") {
                h2 (messageSource.getMessage ("article.tag.cloud.title", null, null))
                ul {
                    tags.eachWithIndex { tag, index ->
                    li {
                        def label = (tag[0].contains (' ') ? "\"${tag[0]}\"" : tag[0]) + " (${tag[1]})";
                        def linkElement = link (controller: 'article', params: [tags:[tag[0]]]) {
                            "<span class=\"tag${ranks[index]}\">${label}</span>"
                        }
                        mkp.yieldUnescaped (linkElement)}
                    }
                }
            }
        }

        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"
        out << builder.bind (cloudList)
    }


     def nav = { attrs ->
        def navControllers = ['home', 'news', 'events', 'meditation','community','wellbeing','buddhism']
        if (SecurityUtils.subject.hasRole ("Administrator")) {
            navControllers = ['home', 'news', 'events', 'meditation','community','wellbeing','buddhism', 'manageSite']
        } else if (SecurityUtils.subject.principal != null) {
            navControllers =['home', 'news', 'events', 'meditation','community','wellbeing','buddhism', 'manageSite']
        }

        def current = attrs.current
        def navList = {
            div (class:"nav group") {
                ul {
                    navControllers.each { controllerName ->
                        def aElem = link (controller:controllerName) {
                            "<strong>${messageSource.getMessage(controllerName,null,null)}</strong><em>${messageSource.getMessage(controllerName+".desc",null,null)}</em>"
                        }
                        if (controllerName.equals(attrs.current) && controllerName.equals(navControllers[-1])) {
                            mkp.yieldUnescaped ("<li class='last current'>${aElem}</li>")
                        } else if (controllerName.equals (navControllers[-1])) {
                            mkp.yieldUnescaped ("<li class='last'>${aElem}</li>")
                        } else if (controllerName.equals (attrs.current) && controllerName.equals (navControllers[0])) {
                            mkp.yieldUnescaped("<li class='first current'><a href='${resource(dir:'')}'><strong>${messageSource.getMessage(controllerName,null,null)}</strong><em>${messageSource.getMessage(controllerName+".desc",null,null)}</em></a></li>")
                        } else if (controllerName.equals (navControllers[0])){
                            mkp.yieldUnescaped("<li class='first'><a href='${resource(dir:'')}'><strong>${messageSource.getMessage(controllerName,null,null)}</strong><em>${messageSource.getMessage(controllerName+".desc",null,null)}</em></a></li>")
                        } else if (controllerName.equals (attrs.current)) {
                            mkp.yieldUnescaped ("<li class='current'>${aElem}</li>")
                        } else {
                            mkp.yieldUnescaped ("<li>${aElem}</li>")
                        }
                    }
                }
            }
        }

        def builder = new StreamingMarkupBuilder ()
        builder.encoding = "UTF-8"
        out << builder.bind (navList)
    }

    def header = { attrs ->
        def header = {
            div(id:"banner") {
                div(id:"text") {
                    img(src:resource (dir: 'css/themes/default/images', file:'heading.png'))
                }
                div(id:"back") {
                    div(id:"mid") {
                        img(id:"bannerImg", src: resource(dir: 'css/themes/default/images', file:'whitetara.png'))
                        div(id:"front"){}
                    }
                }
            }
        }

        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"
        out << builder.bind (header)
    }

     def toolbar = { attrs ->
        def adminControllers =['home']
        def adminClasses =[home: 'home', article: 'article', image: 'image', teacher: 'teacher', venue: 'venue', roles:'roles', event:'event']

        if (SecurityUtils.subject.hasRole ("Editor") && !SecurityUtils.subject.hasRole ("Author")) {
            ['article'].each () { item ->
                adminControllers << item
            }
        }

        if (SecurityUtils.subject.hasRole ("Editor") && !SecurityUtils.subject.hasRole ("EventOrganiser")) {
            ['event'].each () { item ->
                adminControllers << item
            }
        }

        if (SecurityUtils.subject.hasRole ("Author") || (SecurityUtils.subject.hasRole ("Editor") && SecurityUtils.subject.hasRole ("Author"))) {
            ['article', 'image', 'teacher'].each () { item ->
                adminControllers << item
            }
        }

        if (SecurityUtils.subject.hasRole ("VenueManager")) {
            ['venue'].each () { item ->
                adminControllers << item
            }
        }

        if (SecurityUtils.subject.hasRole ("EventOrganiser")) {
            ['event'].each() { item ->
                adminControllers << item
            }
        }

        if (SecurityUtils.subject.hasRole ("Administrator")) {
            ['roles'].each () { item ->
                adminControllers << item
            }
        }

        if (SecurityUtils.subject.hasRole ("Admin")) {
            ['article', 'image', 'venue', 'event', 'roles'].each () { item ->
                adminControllers << item
            }
        }

        adminControllers << 'auth'
        def toolbar = {
            div (class:"menuBar ui-tab-selected"){
                adminControllers.each () { controller ->
                    def max = controller == 'image' ? 50 : 10
                    span (class:"menuButton") {
                        def elem
                        if (controller.equals ('home')) {
                            elem = link (class: adminClasses[controller], controller: "manageSite", action:"home",style:"color: #333;") {
                                messageSource.getMessage ('toolbar.' + controller, null, null)
                            }
                        } else if (controller.equals ('roles')) {
                            elem = link (class: adminClasses[controller], controller: "admin", action:"roles",style:"color: #333;") {
                                messageSource.getMessage ('toolbar.' + controller, null, null)
                            }
                        } else if (controller.equals ('auth')) {
                            if (SecurityUtils.subject.authenticated) {
                                elem = link (class: 'logout', controller: controller, action: "signOut",style:"color: #333;") {
                                    messageSource.getMessage ('toolbar.logout', null, null)
                                }
                            } else {
                                def user = [SecurityUtils.subject.principal].toArray()
                                if (user[0]) {
                                    user = user[0] ? user : [""] as String[]
                                    elem = link (class: 'login', controller: controller, action: "login",style:"color: #333;") {
                                        messageSource.getMessage("toolbar.login", user, null)
                                    }
                                } else {
                                    elem = link (class: 'login', controller: controller, action: "login",style:"color: #333;") {
                                        messageSource.getMessage("toolbar.login", null, null)
                                    }
                                }
                            }
                        } else if (controller.equals (attrs.controller)) {
                            if ("manage".equals (attrs.action)) {
                                if (SecurityUtils.subject.hasRoles(["Admin","Author","VenueManager","EventOrganiser"]).any()) {
                                    elem = link (class: "${controller}Create", controller: controller, action:"create",style:"color: #333;") {
                                        messageSource.getMessage ("toolbar.${controller}.create", null, null)
                                    }
                                }
                            } else {
                                elem = link (class: "${adminClasses[controller]}", controller: controller, action: "manage", params: [offset: 0, max:max],style:"color: #333;") {
                                    messageSource.getMessage ('toolbar.' + controller, null, null)
                                }
                            }
                        } else {
                            elem = link (class: adminClasses[controller], controller: controller, action: "manage", params: [offset: 0, max:max],style:"color: #333;") {
                                messageSource.getMessage ('toolbar.' + controller, null, null)
                            }
                        }

                        if (elem != null) {
                            mkp.yieldUnescaped (elem)
                        }
                    }
                }
            }
        }

        def builder = new StreamingMarkupBuilder ()
        builder.encoding = "UTF-8"
        out << builder.bind (toolbar)
    }

     def grid = { attrs ->
        out << """<div id="grid">"""
        0.times {
            out << """<span class="gcol"><span class="gleft">&nbsp;</span><span class="ggap">&nbsp;</span><span class="gright">&nbsp;</span></span>"""
        }
        out << "</div>"
    }
}
