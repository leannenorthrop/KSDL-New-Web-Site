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
package org.samye.dzong.london.media;

import java.util.*;
import net.sf.ehcache.loader.*;
import net.sf.ehcache.*;

/**
 * EHCache loader for flickr objects.
 */
public class FlickrPhotosetCacheLoader implements CacheLoader {
    private FlickrPhotosetLoader loader;
    
    public FlickrPhotosetCacheLoader(FlickrPhotosetLoader loader) {
        this.loader = loader;
    }
    
    public String toString() {
        return "FlickrPhotosetCacheLoader";
    }
    
    public CacheLoader clone(Ehcache cache) throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    public void dispose() {}
    
    public String getName() { 
        return "FlickrPhotosetLoader"; 
    }
    
    public Status getStatus() { 
        return Status.STATUS_ALIVE; 
    }
    
    public void init() {}
    
    public Object load(Object key) { 
        return loader.getPhotoset(key); 
    }
    
    public Object load(Object key, Object argument) { 
        return loader.getPhotoset(key); 
    }
     
    public Map loadAll(Collection keys) { 
        return loader.getPhotosets(keys); 
    }
    
    public Map loadAll(Collection keys, Object argument) { 
        return loader.getPhotosets(keys); 
    }
}
