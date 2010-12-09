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

<%--
Home Page for About Us
User: Leanne Northrop
Date: Feb 12, 2010, 5:43:08 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title><g:message code="aboutUs"/></title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_4">
        <g:render template="/aboutUsNav"/>
    </div>
          
    <div class="grid_12">
      <g:render template="/toparticles" model="[articles:homeArticles,displayTitle:false]"/>
      <div class="container_16">
            <div class="grid_4">1
            </div>
            <div class="grid_4">2
            </div> 
            <div class="grid_4">3
            </div>
            <div class="grid_4">4
            </div>                      
      </div>      
    </div>

    <div class="clear"></div>

  </body>
</html>
