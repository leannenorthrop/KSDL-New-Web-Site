package org.samye.dzong.london
import java.io.File

class CssThemesTagLib {
    static namespace = 'lsd'

    def link = { attrs ->
        def application = attrs.obj
        String dir = application.getRealPath("/css/themes")
        out << "<!--" + dir + "-->"
        File cssDir = new File(dir)
        cssDir.eachFile{ file ->
            if (file.isDirectory()) {
                out << "<link rel=\"alternate stylesheet\" type=\"text/css\" href=\"" + resource(dir: file.absoluteFile, file:'main.css') + "\" title=\"${file.name}\" />\n"
            }
        }
    }
}