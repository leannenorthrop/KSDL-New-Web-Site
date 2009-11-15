package org.samye.dzong.london
import java.io.File
import static org.apache.commons.lang.WordUtils.*

class CssThemesTagLib {
    static namespace = 'lsd'

    def link = { attrs ->
        def application = attrs.obj
        new File(application.getRealPath("/css/themes")).listFiles().each{ file ->
            if (file.isDirectory()) {
                def name = file.name
                def href = resource(dir: "css/themes/${name}", file:'screen.css')
                out << "<link rel=\"alternate stylesheet\" type=\"text/css\" media=\"screen, projection\" href=\"${href}\" title=\"${capitalize(name)}\" />\n"
            }
        }
    }
}