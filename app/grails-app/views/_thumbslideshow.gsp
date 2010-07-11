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
  Thumbnail slide-show template

  Author: Leanne Northrop
  Date: 13th June, 2010, 12:54
--}%
<g:if test="${images && images.size() > 0}">
<div id="show" class="slideshow" style="width:175px;height:175px"> 
  <img src="${images[0].src}" alt="${images[0].name}" style="width:175px;height:175px;overflow:hidden"/> 
</div>    
<g:javascript>
  var data = {
      <g:each var="i" in="${ images }">
         '${i.src}': { caption: '${i.name}'},
      </g:each>	      
      '${images[0].src}': { caption: '${images[0].name}' }
  };
  var myShow = new Slideshow.KenBurns('show', data, {href:"${relUrl}", controller: false, captions: false, titles: true, height: 175, thumbnails: false, width: 175, overlap: false, delay:6500});
</g:javascript>
</g:if>