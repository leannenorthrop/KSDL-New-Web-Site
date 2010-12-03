/** *****************************************************************************
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
 ***************************************************************************** */
package org.samye.dzong.london.codec.textile;

import org.eclipse.mylyn.wikitext.textile.core.TextileLanguage;
import java.util.*;

/**
 * Site specific textile markup language. Supports marking up internal
 * links, video embedding and more.
 *
 * @author Leanne Northrop
 * @since 1.0.0-SNAPSHOT, December 2009
 */
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
