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
<html>
<head>
    <g:javascript src="jquery/jquery-1.4.2.min.js"/>
    <g:javascript src="jquery/visuallightbox/visuallightbox.js"/>
    <g:javascript src="jquery/visuallightbox/vlbdata.js"/>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/site/slideshow', file: 'vlightbox1.css')}"/>    
    <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/site/slideshow', file: 'visuallightbox.css')}"/>    
</head>
<body>
    <g:if test="${album?.images && album?.images.size() > 0}">
        <div id="vlightbox1">
            <g:each var='img' in="${album?.images}">
            <a class="vlightbox1" href="${img?.src}" title="${img?.name}"><img src="${img?.thumbnail}" alt="${img?.name}"/></a>
            </g:each>
        </div>
    </g:if>
</body>
</html>
