package org.samye.dzong.london
import org.apache.shiro.SecurityUtils
class CommonLayoutTagLib {
    static namespace = 'lsdc'

    def nav = { attrs ->
        if (SecurityUtils.subject.hasRole("Administrator")) {
            out << '''<div class="nav group">
                <ul>
                    <li class="home first"><a class="home" href="''' + resource(dir:'') + '''">Welcome</a></li>
                    <li class="articles">''' + link(controller:'article'){'Articles'} + '''</li>
                    <li class="content-admin">'''  + link(controller:'manageSite'){'Manage Content'} + '''</li>
                    <li class="admin">''' + link(controller:'admin'){'Administration'} + '''</li>
                    <li class="help last"><a class="help" href="''' + resource(dir:'') + '''">Help</a></li>
                </ul>
            </div>'''
        } else if (SecurityUtils.subject.principal != null) {
            out << '''<div class="nav group">
                <ul>
                    <li class="home first"><a class="home" href="''' + resource(dir:'') + '''">Welcome</a></li>
                    <li class="articles">''' + link(controller:'article'){'Articles'} + '''</li>
                    <li class="content-admin">'''  + link(controller:'manageSite'){'Manage Content'} + '''</li>
                    <li class="help last"><a class="help" href="''' + resource(dir:'') + '''">Help</a></li>
                </ul>
            </div>'''
        } else {
            out << '''<div class="nav group">
                <ul>
                    <li class="home first"><a class="home" href="''' + resource(dir:'') + '''">Welcome</a></li>
                    <li class="articles">''' + link(controller:'article'){'Articles'} + '''</li>
                    <li class="help last"><a class="help" href="''' + resource(dir:'') + '''">Help</a></li>
                </ul>
            </div>'''
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
