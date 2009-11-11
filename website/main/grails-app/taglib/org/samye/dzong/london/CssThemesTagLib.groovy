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
                def href = resource(dir: "css/${name}", file:'main.css')
                out << "<link rel=\"alternate stylesheet\" type=\"text/css\" href=\"${href}\" title=\"${capitalize(name)}\" />\n"
            }
        }
    }
}