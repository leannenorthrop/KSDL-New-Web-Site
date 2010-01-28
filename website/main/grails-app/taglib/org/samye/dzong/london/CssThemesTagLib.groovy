package org.samye.dzong.london
import java.io.File
import static org.apache.commons.lang.WordUtils.*
import org.apache.shiro.SecurityUtils
import javax.servlet.http.Cookie

class CssThemesTagLib {
    static namespace = 'lsd'

    def cssTheme = { attrs ->
        def themeCookie = request.getCookies().find() { it.name == "cssTheme"}
        if (!themeCookie) {
            themeCookie = new Cookie("cssTheme","default")
            themeCookie.setMaxAge(60*60*24*365)
            themeCookie.setPath("/")
            response.addCookie(themeCookie)
        }
        def application = attrs.app
        new File(application.getRealPath("/css/themes")).listFiles().each{ file ->
            if (file.isDirectory()) {
                def name = file.name
                if (name == themeCookie.value) {
                    def href = resource(dir: "css/themes/${name}", file:'screen.css')
                    out << "<link rel=\"stylesheet\" type=\"text/css\" media=\"screen, projection\" href=\"${href}\" title=\"${capitalize(name)}\" />\n"
                }
            }
        }
    }
}