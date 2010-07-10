package org.samye.dzong.london;

import java.util.*;
import org.eclipse.mylyn.wikitext.core.parser.Attributes;
import java.io.*;

public class HtmlDocumentBuilder extends org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder {
    public HtmlDocumentBuilder(final StringWriter writer) {
		super(writer);
    }

	public void video(Attributes attributes, String href) {
		writer.writeStartElement("http://www.w3.org/1999/xhtml", "video");
		writer.writeAttribute("src", href);
		writer.writeAttribute("controls", "controls");
		writer.writeAttribute("autoplay", "autoplay");
		writer.writeAttribute("height", "270");
		writer.writeAttribute("width", "480");
		writer.writeEndElement(); // video
	}
}