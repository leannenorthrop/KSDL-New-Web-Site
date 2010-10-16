package org.samye.dzong.london;

import java.util.*;

public interface FlickrPhotosetLoader {    
    Object getPhotoset(Object key); 
    Map getPhotosets(Collection keys); 
}