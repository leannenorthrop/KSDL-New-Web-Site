package org.samye.dzong.london;

import org.eclipse.mylyn.wikitext.core.parser.markup.PatternBasedElement;
import org.eclipse.mylyn.wikitext.core.parser.markup.PatternBasedElementProcessor;
import org.eclipse.mylyn.wikitext.core.parser.Attributes;
import java.util.*;

/**
 * User: Leanne Northrop
 * Date: Dec 6, 2009
 * Time: 6:29:51 PM
 */
public class TextileLinkReplacementToken extends PatternBasedElement {
    private static Map urlBases = new HashMap();

    public TextileLinkReplacementToken(final Map urls) {
        urlBases = new HashMap(urls);
    }

    @Override
    protected String getPattern(int groupOffset) {
        return "(?:(?:(?<=\\W)|^)(\\[[a-zA-Z0-9 ,]{3,}\\])\\(([^\\)]+)\\))";
    }

    @Override
    protected int getPatternGroupCount() {
        return 2;
    }

    @Override
    protected PatternBasedElementProcessor newProcessor() {
        return new LinkReplacementTokenProcessor();
    }

    private static class LinkReplacementTokenProcessor extends PatternBasedElementProcessor {

        @Override
        public void emit() {
            String name = group(1).substring(1, group(1).length()-1);
            String type = group(2);
            if (type.equals("image")) {
                String[] attributes = name.split(",");
                Attributes a = new Attributes();
                String style = "";
                if (attributes.length >= 2) {
                    if (!"normal".equals(attributes[1])) {
                        style += "float:" + attributes[1] + ";";
                    }
                }
                style += attributes.length >= 3 ? "width:" + attributes[2] + ";" : "";
                style += attributes.length >= 4 ? "height:" + attributes[3] + ";" : "";
                a.setCssStyle(style);
                a.setTitle(attributes[0]);
                String href = urlBases.get(type) + "/" + attributes[0];
                builder.image(a, href);
            } else {
                String href = (String)urlBases.get(type);
                builder.link(href, name);
            }
        }

    }
}
