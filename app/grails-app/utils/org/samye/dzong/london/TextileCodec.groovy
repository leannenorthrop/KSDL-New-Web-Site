package org.samye.dzong.london
import org.samye.dzong.london.HtmlDocumentBuilder
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser
import org.eclipse.mylyn.wikitext.textile.core.TextileLanguage
import org.codehaus.groovy.grails.web.mapping.DefaultUrlCreator

class TextileCodec {

    static encode = { str ->
        StringWriter sw = new StringWriter();

        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(sw);
        builder.emitAsDocument = false
        builder.useInlineStyles = false
        builder.suppressBuiltInStyles = true

        def baseUrls = [image: '', teacher:'', news: '',video:'',file:'',room:'',contactUs:'',teacher:'',about:'']
        baseUrls.each { key, value ->
            if (key.equals("image")) {
                def defaultUrlCreator = new DefaultUrlCreator(key, "src")
                baseUrls[key] = defaultUrlCreator.createURL(new HashMap(), "UTF-8")
            } else if (key.equals("room")||key.equals("venue")||key.equals("contactUs")||key.equals("visiting")||key.equals("teacher")||key.equals("about")) {
				def defaultUrlCreator = new DefaultUrlCreator("aboutUs", key == "about" ? "information" : key)
                baseUrls[key] = defaultUrlCreator.createURL(new HashMap(), "UTF-8")	
			} else if (key.equals("video")||key.equals("file")) {
                def defaultUrlCreator = new DefaultUrlCreator("file", "src")
                baseUrls[key] = defaultUrlCreator.createURL(new HashMap(), "UTF-8")	
			} else {
                def defaultUrlCreator = new DefaultUrlCreator(key, "view")
                baseUrls[key] = defaultUrlCreator.createURL(new HashMap(), "UTF-8")
            }
        }
        MarkupParser parser = new MarkupParser(new WikiTextileLanguage(new HashMap(baseUrls)))
        parser.setBuilder(builder)

        parser.parse(str)
        return sw.toString()
    }
}