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
  Create Event template

  Author: Leanne Northrop
  Date: 29th January, 2010, 17:54
--}%
<html>
  <head>
    <meta name="layout" content="main"/>
    <title><g:message code="slideshow.title" default="Slide Show"/></title>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/site/slideshow', file: 'slideshow.css')}"/>      
  
  </head>
  <body>
      <div class="grid_16" style="height: 50em;">
          <div id="show" class="slideshow"> 
            <img src="${images[0].src}" alt="${images[0].name}" /> 
          </div>
      </div>     
      <g:javascript>
  	    var data = {
            <g:each var="i" in="${ images }">
               '${i.src}': { caption: '${i.name}', thumbnail: '${i.thumbnail}' },
            </g:each>	      
            '${images[0].src}': { caption: '${images[0].name}' }
  	    };
  	    var myShow = new Slideshow('show', data, {controller: false, resize: "length", captions: false, titles: true, height: 180, thumbnails: false, width: 290, overlap: false, delay:4000});
      </g:javascript>      
  </body>
</html>


