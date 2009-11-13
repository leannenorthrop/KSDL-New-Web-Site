package org.samye.dzong.london
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser
import org.eclipse.mylyn.wikitext.textile.core.TextileLanguage

class TextileCodec {
    static encode = { str ->
        StringWriter sw = new StringWriter();

        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(sw)
        builder.emitAsDocument = false
        builder.useInlineStyles = false
        builder.suppressBuiltInStyles = true
        MarkupParser parser = new MarkupParser(new TextileLanguage())
        parser.setBuilder(builder)

        parser.parse(str)
        return sw.toString()
    }
}