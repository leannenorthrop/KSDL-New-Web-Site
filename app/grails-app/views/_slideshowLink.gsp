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
  Link to slide-show

  Author: Leanne Northrop
  Date: 10th July, 2010, 19:15
--}%
<div class="box slideshowlink">
<h5><g:message code="slideshow.title" default="Photos"/></h5>
<g:if test="${album}">
<a href="${relUrl}" class="slideshow"><img src="${album.image.src}" alt="${album.title}" title="${album.title}" style="max-height:150px"/>  
<h6>${album.title}</h6>
</a>
</g:if>
</div>