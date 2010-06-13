%{------------------------------------------------------------------------------
  - Copyright © 2010 Leanne Northrop
  -
  - This file is part of Samye Content Management System.
  -
  - Samye Content Management System is free software: you can redistribute it
  - and/or modify it under the terms of the GNU General Public License as
  - published by the Free Software Foundation, either version 3 of the License,
  - or (at your option) any later version.
  -
  - Samye Content Management System is distributed in the hope that it will be
  - useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
  - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  - GNU General Public License for more details.
  -
  - You should have received a copy of the GNU General Public License
  - along with Samye Content Management System.
  - If not, see <http://www.gnu.org/licenses/>.
  -
  - BT plc, hereby disclaims all copyright interest in the program
  - “Samye Content Management System” written by Leanne Northrop.
  ----------------------------------------------------------------------------}%
<%@ page contentType="text/html;charset=UTF-8" %>

%{--
  Slide-show template

  Author: Leanne Northrop
  Date: 13th June, 2010, 12:54
--}%
<g:if test="${album?.images && album?.images.size() > 0}">
<div id="show" class="slideshow" style="width:600px;height:500px"> 
        <img src="${album?.images[0].src}" alt="${album?.images[0].name}" style="width:600px;height:500px" /> 
</div>    
<g:javascript>
  var data = {
      <g:each var="i" in="${ album?.images }">
         '${i.src}': { caption: '${i.name}',thumbnail: '${i.thumbnail}'},
      </g:each>	      
      '${album?.images[0].src}': { caption: '${album?.images[0].name}',thumbnail: '${album?.images[0].thumbnail}' }
  };
  var myShow = new Slideshow('show', data, {controller: true, captions: true, titles: true, height: 500, thumbnails: true, width: 600, overlap: false, delay:6000});
</g:javascript>
</g:if>