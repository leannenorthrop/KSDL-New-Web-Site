package org.samye.dzong.london;

import java.util.*;
import net.sf.ehcache.loader.*;
import net.sf.ehcache.*;

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