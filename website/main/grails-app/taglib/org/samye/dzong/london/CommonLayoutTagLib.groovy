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
                        aElem = messageSource.getMessage('signed.in.greeting', [SecurityUtils.subject.principal],null) + link(controller:"auth", action:"signOut"){messageSource.getMessage('sign.out',null,null)}
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

    def grid = { attrs ->
        out << """<div id="grid">"""
        0.times {
            out << """<span class="gcol"><span class="gleft">&nbsp;</span><span class="ggap">&nbsp;</span><span class="gright">&nbsp;</span></span>"""
        }
        out << "</div>"
    }
}
