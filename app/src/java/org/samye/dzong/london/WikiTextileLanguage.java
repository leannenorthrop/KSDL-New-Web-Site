package org.samye.dzong.london;

import org.eclipse.mylyn.wikitext.textile.core.TextileLanguage;
import java.util.*;

public class WikiTextileLanguage extends TextileLanguage {
    private Map urlBases = new HashMap();

    public WikiTextileLanguage(final Map urls) {
        urlBases = new HashMap(urls);
    }

    @Override
    protected void addStandardTokens(PatternBasedSyntax tokenSyntax) {
        tokenSyntax.add(new TextileLinkReplacementToken(urlBases));
        super.addStandardTokens(tokenSyntax);
    }
}