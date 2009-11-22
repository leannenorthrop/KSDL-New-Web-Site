package org.samye.dzong.london
import org.apache.shiro.SecurityUtils
import groovy.xml.StreamingMarkupBuilder

class CommonLayoutTagLib {
    static namespace = 'lsdc'

    def nav = { attrs ->
        def navControllers = ['home', 'news', 'events', 'meditation', 'buddhism', 'community','wellbeing']
        def navTitles = [home: 'Welcome', news: 'News', events: 'Events', meditation: 'Meditation', buddhism: 'Buddhism', community:'Community',wellbeing:'Well&#x2740;Being',info:'Info',manageSite:'Manage',admin:'Administration',help:'Help']
        def navSubTitles = [home: 'Find Out About Us', news: 'Latest Events', events: 'Courses, Workshops & More', meditation: 'Info,Resources &amp; more...', buddhism: 'Buddhism', community:'&nbsp;',wellbeing:'Therapies &amp; Courses',info:'Press, Room Hire...',manageSite:'Create New Content &amp; more..',admin:'Configure Settings',help:'Questions? Try here...']

        if (SecurityUtils.subject.hasRole("Administrator")) {
            navControllers = ['home', 'news', 'events', 'meditation', 'buddhism', 'community','wellbeing','manageSite','admin']
        } else if (SecurityUtils.subject.principal != null) {
            navControllers = ['home', 'news', 'events', 'meditation', 'buddhism', 'community','wellbeing','manageSite']
        }

        def current = attrs.current
        def navList = {
            div(class: "nav group") {
                ul {
                    navControllers.each { controllerName ->
                        def aElem = link(controller:controllerName) {"<strong>${navTitles[controllerName]}</strong><em>${navSubTitles[controllerName]}</em>"}
                        if (controllerName.equals(attrs.current) && controllerName.equals(navControllers[-1])) {
                            mkp.yieldUnescaped("<li class='last current'>${aElem}</li>")
                        } else if (controllerName.equals(navControllers[-1])){
                            mkp.yieldUnescaped("<li class='last'>${aElem}</li>")
                        } else if (controllerName.equals(attrs.current) && controllerName.equals(navControllers[0])) {
                            mkp.yieldUnescaped("<li class='first current'><a href='${resource(dir:'')}'><strong>${navTitles[controllerName]}</strong><em>${navSubTitles[controllerName]}</em></a></li>")
                        } else if (controllerName.equals(navControllers[0])){
                            mkp.yieldUnescaped("<li class='first'><a href='${resource(dir:'')}'><strong>${navTitles[controllerName]}</strong><em>${navSubTitles[controllerName]}</em></a></li>")
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
        out << '''<div class="logo group">
            <img src="''' + resource(dir:'images',file:'logo.png') +'''" alt="Kagyu Samye Dzong London Logo" />
            <h1 style="margin-left:20px;">Kagyu Samye Dzong London</h1>
            <h2>'''
        if (SecurityUtils.subject.principal != null) {
            out << "Hi " + SecurityUtils.subject.principal + ", how are you today? " + link(controller:"auth", action:"signOut"){"Sign Out"}
        } else {
            out << "Welcome! " + link(controller:"auth", action:"index"){"Sign In"}
        }
        out << '''
            </h2>
        </div>'''
    }

    def grid = { attrs ->
        out << """<div id="grid">"""
        20.times {
            out << """<span class="gcol"><span class="gleft">&nbsp;</span><span class="ggap">&nbsp;</span><span class="gright">&nbsp;</span></span>"""
        }
        out << "</div>"
    }
}
