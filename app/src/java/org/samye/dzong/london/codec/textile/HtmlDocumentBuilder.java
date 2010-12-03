/* 
 * Copyright © 2010 Leanne Northrop
 *
 * This file is part of Samye Content Management System.
 *
 * Samye Content Management System is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Samye Content Management System is distributed in the hope that it will be
 * useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Samye Content Management System.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * BT plc, hereby disclaims all copyright interest in the program
 * “Samye Content Management System” written by Leanne Northrop.
 */

package org.samye.dzong.london.codec.textile;

import java.util.*;
import org.eclipse.mylyn.wikitext.core.parser.Attributes;
import java.io.*;

/**
 * Specialized html builder to support site specific enhancements such as 
 * embedding video, files and more.
 *
 * @author Leanne Northrop
 * @since  1.0.7-SNAPSHOT, Febuary 2010
 */
public class HtmlDocumentBuilder extends org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder {
    public HtmlDocumentBuilder(final Writer writer) {
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

	public void youTubePlayer(Attributes attributes, String vid) {
        String url = "http://www.youtube.com/v/" + vid + "?rel=0&autoplay=0&loop=0&egm=0&border=0&fs=1&hd=1&showsearch=0&showinfo=0";

		writer.writeStartElement("http://www.w3.org/1999/xhtml", "div");
		writer.writeAttribute("style", "text-align:center");
		writer.writeAttribute("class", "youtubevideo");

		writer.writeStartElement("http://www.w3.org/1999/xhtml", "object");
		writer.writeAttribute("width", "425");
		writer.writeAttribute("height", "344");

		writer.writeStartElement("http://www.w3.org/1999/xhtml", "param");
		writer.writeAttribute("name", "movie");
		writer.writeAttribute("value", url);
		writer.writeEndElement(); // movie param
        
		writer.writeStartElement("http://www.w3.org/1999/xhtml", "param");
		writer.writeAttribute("name", "allowFullScreen");
		writer.writeAttribute("value", "true");
		writer.writeEndElement(); // fullscreen param

		writer.writeStartElement("http://www.w3.org/1999/xhtml", "embed");
		writer.writeAttribute("src", url);
		writer.writeAttribute("type", "application/x-shockwave-flash");
		writer.writeAttribute("allowfullscreen", "true");
		writer.writeAttribute("width", "425");
		writer.writeAttribute("height", "344");
		writer.writeEndElement(); // embed 

		writer.writeEndElement(); // video

		writer.writeEndElement(); // div  
	}
}
