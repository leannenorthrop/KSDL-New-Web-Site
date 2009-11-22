package org.samye.dzong.london
import org.apache.shiro.SecurityUtils
class CommonLayoutTagLib {
    static namespace = 'lsdc'

    def nav = { attrs ->
        def welcomeLink = '''<li class="home first"><a href="''' + resource(dir:'') + '''"><strong>Welcome</strong><em>Find Out About Us</em></a></li>'''
        def newsLink = '''<li class="news">''' + link(controller:'news'){'<strong>News</strong><em>Latest Events</em>'} + '''</li>'''
        def eventsLink = '''<li class="events">''' + link(controller:'events'){'<strong>Events</strong><em>Courses, Workshops & More</em>'} + '''</li>'''
        def meditationLink = '''<li class="meditation">''' + link(controller:'meditation'){'<strong>Meditation</strong><em>Articles,Resources...</em>'} + '''</li>'''
        def buddhismLink = '''<li class="buddhism">''' + link(controller:'buddhism'){'<strong>Buddhism</strong><em>Articles,Resources...</em>'} + '''</li>'''
        def communityLink = '''<li class="community">''' + link(controller:'community'){'<strong>Community</strong><em>&nbsp;</em>'} + '''</li>'''
        def wellBeingLink = '''<li class="wellbeing">''' + link(controller:'wellbeing'){'<strong>Well-Being</strong><em>&nbsp;</em>'} + '''</li>'''
        def articlesLink = '''<li class="articles">''' + link(controller:'article'){'<strong>Articles</strong><em>?</em>'} + '''</li>'''
        def infoLink = '''<li class="info">''' + link(controller:'info'){'<strong>Info</strong><em>Press, Room Hire</em>'} + '''</li>'''
        def editorsLink = '''<li class="content-admin">'''  + link(controller:'manageSite'){'<strong>Manage</strong><em>Publish New Articles</em>'} + '''</li>'''
        def adminLink = '''<li class="admin">''' + link(controller:'admin'){'<strong>Administration</strong><em>Settings</em>'} + '''</li>'''
        def helpLink = '''<li class="help last">''' + link(controller:'help'){'<strong>Help</strong><em>Problems with website?</em>'} + '''</li>'''

        if (SecurityUtils.subject.hasRole("Administrator")) {
            out << "<div class='nav group'><ul>${welcomeLink} ${newsLink} ${eventsLink} ${meditationLink} ${buddhismLink} ${communityLink} ${wellBeingLink} ${infoLink}${editorsLink} ${adminLink} ${helpLink}</ul></div>"
        } else if (SecurityUtils.subject.principal != null) {
            out << "<div class='nav group'><ul>${welcomeLink} ${newsLink} ${eventsLink} ${meditationLink} ${buddhismLink} ${communityLink} ${wellBeingLink} ${infoLink} ${editorsLink} ${helpLink}</ul></div>"
        } else {
            out << "<div class='nav group'><ul>${welcomeLink} ${newsLink} ${eventsLink} ${meditationLink} ${buddhismLink} ${communityLink} ${wellBeingLink} ${infoLink}</ul></div>"
        }

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
        128.times {
            out << """<span class="gcol"><span class="gleft">&nbsp;</span><span class="ggap">&nbsp;</span><span class="gright">&nbsp;</span></span>"""
        }
        out << "</div>"
    }
}
